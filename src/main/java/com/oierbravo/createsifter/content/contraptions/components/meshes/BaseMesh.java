package com.oierbravo.createsifter.content.contraptions.components.meshes;

import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipe;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.foundation.item.CustomUseEffectsItem;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.mixin.accessor.LivingEntityAccessor;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public abstract class BaseMesh extends Item implements CustomUseEffectsItem {
    protected MeshTypes mesh;
    public BaseMesh(Properties pProperties) {
        super(pProperties);
    }
    public MeshTypes getMesh(){
        return this.mesh;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        InteractionResultHolder<ItemStack> FAIL = new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);

        if (itemstack.getOrCreateTag()
                .contains("Sifting")) {
            playerIn.startUsingItem(handIn);
            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
        }

        InteractionHand otherHand =
                handIn == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack itemInOtherHand = playerIn.getItemInHand(otherHand);

        Block blockUnderPlayer = playerIn.getFeetBlockState().getBlock();
        boolean waterlogged = blockUnderPlayer instanceof LiquidBlock || blockUnderPlayer instanceof IFluidBlock;

        if (SiftingRecipe.canHandSift(worldIn, itemInOtherHand,itemstack,waterlogged)) {
            ItemStack item = itemInOtherHand.copy();
            ItemStack toSift = item.split(1);
            playerIn.startUsingItem(handIn);
            itemstack.getOrCreateTag()
                    .put("Sifting", toSift.serializeNBT());
            playerIn.setItemInHand(otherHand, item);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }

        HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.NONE);
        if (!(raytraceresult instanceof BlockHitResult))
            return FAIL;
        BlockHitResult ray = (BlockHitResult) raytraceresult;
        Vec3 hitVec = ray.getLocation();

        AABB bb = new AABB(hitVec, hitVec).inflate(1f);
        ItemEntity pickUp = null;
        for (ItemEntity itemEntity : worldIn.getEntitiesOfClass(ItemEntity.class, bb)) {
            if (!itemEntity.isAlive())
                continue;
            if (itemEntity.position()
                    .distanceTo(playerIn.position()) > 3)
                continue;
            ItemStack stack = itemEntity.getItem();

            if (!SiftingRecipe.canHandSift(worldIn, stack, itemstack,waterlogged))
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
            itemstack.getOrCreateTag()
                    .put("Sifting", toSift.serializeNBT());
            if (item.isEmpty())
                pickUp.discard();
            else
                pickUp.setItem(item);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {

        if (!(entityLiving instanceof Player))
            return stack;
        Player player = (Player) entityLiving;
        CompoundTag tag = stack.getOrCreateTag();
        Block blockUnderPlayer = player.getFeetBlockState().getBlock();
        boolean waterlogged = blockUnderPlayer instanceof LiquidBlock || blockUnderPlayer instanceof IFluidBlock;
        if (tag.contains("Sifting")) {
            ItemStack toSift = ItemStack.of(tag.getCompound("Sifting"));
            List<ItemStack> sifted =
                    SiftingRecipe.applyHandSift(worldIn, entityLiving.position(), toSift, stack,waterlogged);

            if (worldIn.isClientSide) {
                spawnParticles(entityLiving.getEyePosition(1)
                                .add(entityLiving.getLookAngle()
                                        .scale(.5f)),
                        toSift, worldIn);
                return stack;
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
            tag.remove("Sifting");
            stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
        }

        return stack;
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
        if (!(entityLiving instanceof Player))
            return;
        Player player = (Player) entityLiving;
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Sifting")) {
            ItemStack toSift = ItemStack.of(tag.getCompound("Sifting"));
            player.getInventory()
                    .placeItemBackInInventory(toSift);
            tag.remove("Sifting");
        }
    }
    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == ToolActions.AXE_SCRAPE || toolAction == ToolActions.AXE_WAX_OFF;
    }

    @Override
    public Boolean shouldTriggerUseEffects(ItemStack stack, LivingEntity entity) {
        // Trigger every tick so that we have more fine grain control over the animation
        return true;
    }

    @Override
    public boolean triggerUseEffects(ItemStack stack, LivingEntity entity, int count, Random random) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Sifting")) {
            ItemStack sifting = ItemStack.of(tag.getCompound("Sifting"));
            ((LivingEntityAccessor) entity).create$callSpawnItemParticles(sifting, 1);
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
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new MeshItemRenderer()));
    }
}
