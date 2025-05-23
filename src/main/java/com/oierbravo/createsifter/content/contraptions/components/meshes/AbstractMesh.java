package com.oierbravo.createsifter.content.contraptions.components.meshes;

import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeInput;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeManager;
import com.oierbravo.createsifter.infrastucture.config.MConfigs;
import com.oierbravo.createsifter.register.ModItemComponents;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.foundation.item.CustomUseEffectsItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.mixin.accessor.LivingEntityAccessor;
import net.createmod.catnip.data.TriState;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.util.FakePlayer;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbstractMesh extends Item implements CustomUseEffectsItem, IMesh {
    public AbstractMesh(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.getKey() == Enchantments.UNBREAKING;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        ItemEnchantments itemEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(book);
        Set<Holder<Enchantment>> enchantments = itemEnchantments.keySet();
        if(enchantments.size() > 1)
            return false;
        for(Holder<Enchantment> enchantment : enchantments ){
            if(enchantment.is(Enchantments.UNBREAKING))
                return true;
        }
        return false;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if(!enchantment.is(Enchantments.UNBREAKING))
            return false;
        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        InteractionResultHolder<ItemStack> FAIL = new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);

        if (itemstack.has(ModItemComponents.MESH_SIFTING)) {
            playerIn.startUsingItem(handIn);
            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
        }

        Block blockPlayerIn = playerIn.getInBlockState().getBlock();
        boolean waterlogged = blockPlayerIn instanceof LiquidBlock ;

        InteractionHand otherHand =
                handIn == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack itemInOtherHand = playerIn.getItemInHand(otherHand);

        SiftingRecipeInput recipeInput = SiftingRecipeInput.fromStacks(itemstack, itemInOtherHand);

        if (SiftingRecipe.canHandSift(worldIn, recipeInput, waterlogged)) {
            ItemStack item = itemInOtherHand.copy();
            ItemStack toSift = item.split(1);
            playerIn.startUsingItem(handIn);
            itemstack.set(ModItemComponents.MESH_SIFTING, new MeshItemComponent(toSift));
            playerIn.setItemInHand(otherHand, item);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }

        BlockHitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.NONE);
        Vec3 hitVec = raytraceresult.getLocation();

        AABB bb = new AABB(hitVec, hitVec).inflate(1f);
        ItemEntity pickUp = null;
        for (ItemEntity itemEntity : worldIn.getEntitiesOfClass(ItemEntity.class, bb)) {
            if (!itemEntity.isAlive())
                continue;
            if (itemEntity.position()
                    .distanceTo(playerIn.position()) > 3)
                continue;
            if (!SiftingRecipe.canHandSift(worldIn, recipeInput, waterlogged))
                continue;
            pickUp = itemEntity;
            break;
        }

        if (pickUp == null)
            return FAIL;

        ItemStack item = pickUp.getItem()
                .copy();
        ItemStack toSift = item.split(1);

        playerIn.startUsingItem(handIn);

        if (!worldIn.isClientSide) {
            itemstack.set(ModItemComponents.MESH_SIFTING, new MeshItemComponent(toSift));
            if (item.isEmpty())
                pickUp.discard();
            else
                pickUp.setItem(item);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack meshStack, Level worldIn, LivingEntity entityLiving) {
        if (!(entityLiving instanceof Player player))
            return meshStack;
        if (meshStack.has(ModItemComponents.MESH_SIFTING)) {
            Block blockPlayerIn = player.getInBlockState().getBlock();
            boolean waterlogged = blockPlayerIn instanceof LiquidBlock;

            ItemStack toSift = meshStack.get(ModItemComponents.MESH_SIFTING).item();
            Optional<SiftingRecipe> siftingRecipe = SiftingRecipeManager.getRecipeForHandSifting(player.level(), SiftingRecipeInput.fromStacks(toSift, meshStack), waterlogged);

            if(siftingRecipe.isEmpty())
                return meshStack;
            List<ItemStack> sifted =
                    SiftingRecipe.applyHandSifting(siftingRecipe.get());

            if (worldIn.isClientSide) {
                spawnParticles(entityLiving.getEyePosition(1)
                                .add(entityLiving.getLookAngle()
                                        .scale(.5f)),
                        toSift, worldIn);
                return meshStack;
            }

            if (!sifted.isEmpty()) {
                sifted.forEach(outputStack -> {
                    if (player instanceof FakePlayer) {
                        player.drop(outputStack, false, false);
                    } else {
                        player.getInventory()
                                .placeItemBackInInventory(outputStack);
                    }
                });

            }
            meshStack.remove(ModItemComponents.MESH_SIFTING);
            if(MConfigs.server().mesh.useMeshDurabilityWithHand.get())
                meshStack.hurtAndBreak(1, entityLiving, LivingEntity.getSlotForHand(entityLiving.getUsedItemHand()));
        }

        return meshStack;
    }

    public static void spawnParticles(Vec3 location, ItemStack polishedStack, Level world) {
        for (int i = 0; i < 20; i++) {
            Vec3 motion = VecHelper.offsetRandomly(Vec3.ZERO, world.random, 1 / 8f);
            world.addParticle(new ItemParticleOption(ParticleTypes.ITEM, polishedStack), location.x, location.y,
                    location.z, motion.x, motion.y, motion.z);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player))
            return;
        if (stack.has(ModItemComponents.MESH_SIFTING)) {
            ItemStack toPolish = stack.get(ModItemComponents.MESH_SIFTING).item();
            //noinspection DataFlowIssue - toPolish won't be null as we do call .has before calling .get
            player.getInventory()
                    .placeItemBackInInventory(toPolish);
            stack.remove(ModItemComponents.MESH_SIFTING);
        }
    }
    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return itemAbility == ItemAbilities.AXE_SCRAPE || itemAbility == ItemAbilities.AXE_WAX_OFF;
    }
    @Override
    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return super.isEnabled(enabledFeatures);
    }
    @Override
    public TriState shouldTriggerUseEffects(ItemStack stack, LivingEntity entity) {
        // Trigger every tick so that we have more fine grain control over the animation
        return TriState.TRUE;
    }


    @Override
    public boolean triggerUseEffects(ItemStack stack, LivingEntity entity, int count, RandomSource random) {
        if (stack.has(ModItemComponents.MESH_SIFTING)) {
            ItemStack polishing = stack.get(ModItemComponents.MESH_SIFTING).item();
            ((LivingEntityAccessor) entity).create$callSpawnItemParticles(polishing, 1);
        }

        // After 6 ticks play the sound every 7th
        if ((entity.getTicksUsingItem() - 6) % 7 == 0)
            entity.playSound(entity.getEatingSound(stack), 0.9F + 0.2F * random.nextFloat(),
                    random.nextFloat() * 0.2F + 0.9F);

        return true;
    }


    @Override
    public SoundEvent getEatingSound() {
        return AllSoundEvents.SANDING_SHORT.getMainEvent();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }


    @Override
    public int getEnchantmentValue() {
        return 20;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new MeshItemRenderer()));
    }

}
