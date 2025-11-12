package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.oierbravo.createsifter.ModLang;
import com.oierbravo.createsifter.content.contraptions.components.meshes.AbstractAdvancedMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.MeshUtils;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeManager;
import com.oierbravo.createsifter.infrastucture.config.MConfigs;
import com.oierbravo.mechanicals.foundation.blockEntity.behaviour.DynamicCycleBehavior;
import com.oierbravo.mechanicals.foundation.blockEntity.behaviour.RecipeRequirementsBehaviour;
import com.oierbravo.mechanicals.register.MechanicalRecipeRequirementTypes;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.sound.SoundScapes;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractSifterBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation, DynamicCycleBehavior.DynamicCycleBehaviorSpecifics, RecipeRequirementsBehaviour.RecipeRequirementsSpecifics<SiftingRecipe> {

    public float DEFAULT_MINIMUM_SPEED;

    protected float minimumSpeed = getDefaultMinimumSpeed();

    protected int itemsProcessedPerCycle = 1;

    private final ItemStackHandler inputInventory;
    private final ItemStackHandler outputInventory;
    public ItemStackHandler meshInventory;
    protected IItemHandler inputAndMeshCombined;

    public DynamicCycleBehavior dynamicCycleBehaviour;

    public RecipeRequirementsBehaviour<SiftingRecipe> recipeRequirementsBehaviour;

    protected DeployerFakePlayer player;

    protected UUID owner;

    protected abstract boolean isValidMesh(ItemStack meshStack);

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        dynamicCycleBehaviour = new DynamicCycleBehavior(this);
        behaviours.add(dynamicCycleBehaviour);
        recipeRequirementsBehaviour = new RecipeRequirementsBehaviour<>(this);
        behaviours.add(recipeRequirementsBehaviour);
    }



    public AbstractSifterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inputInventory = createInputInventory();
        outputInventory = createOutputInventory();
        meshInventory = createMeshInventory();
        inputAndMeshCombined = new SifterInventoryHandler(inputInventory,outputInventory,meshInventory);
    }

    @Override
    public void initialize() {
        super.initialize();
        initHandler();
    }
    private void initHandler() {
        if (level instanceof ServerLevel sLevel) {
            player = new DeployerFakePlayer(sLevel, owner);
            Vec3 initialPos = VecHelper.getCenterOf(worldPosition);
            player.setPos(initialPos.x, initialPos.y, initialPos.z);
        }
    }
    private @NotNull ItemStackHandler createMeshInventory() {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return MeshUtils.isMeshItem(stack);
            }

            @Override
            protected void onContentsChanged(int slot) {
                sendData();
            }
        };
    }

    protected ItemStackHandler createInputInventory(){
        return new ItemStackHandler(1){
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return hasMesh();
            }
            @Override
            protected void onContentsChanged(int slot) {
                sendData();
            }
        };
    }
    protected ItemStackHandler createOutputInventory(){ return new ItemStackHandler(MConfigs.server().sifter.outputCapacity.get());}

    public ItemStackHandler getInputInventory(){
        return inputInventory;
    }
    public ItemStackHandler getOutputInventory(){
        return outputInventory;
    }
    public ItemStackHandler getMeshInventory(){
        return meshInventory;
    }
    public @Nullable IItemHandler getItemHandler() {
        return inputAndMeshCombined;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean added = super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        if(!this.meshInventory.getStackInSlot(0).isEmpty()) {
            ModLang.translate("tooltip.mesh", this.meshInventory.getStackInSlot(0).getDisplayName().getString()).style(ChatFormatting.GREEN).forGoggles(tooltip);
            added = true;
        }

        boolean addedRequirements = recipeRequirementsBehaviour.addToGoggleTooltip(tooltip, isPlayerSneaking, added);
        if(addedRequirements)
            added = true;

        return added;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        invalidateCapabilities();
    }


    @Override
    public boolean tryProcess(boolean simulate) {
        Optional<SiftingRecipe> recipe = getRecipe();
        if(recipe.isEmpty()){
            recipeRequirementsBehaviour.cleanRequirements();
            return false;
        }

        if(!isSpeedRequirementFulfilled()){
            return false;
        }

        SiftingRecipe siftingRecipe = recipe.get();

        if(!recipeRequirementsBehaviour.checkRequirements(siftingRecipe))
            return false;
        //if(siftingRecipe.requiresAdvancedSifter() && !isAdvancedSifter())
        //    return false;

        if(simulate)
            return true;

        ItemStack stackInSlot = inputInventory.getStackInSlot(0);
        for(int i = 0;i <getItemsPerCycle();i++){
            if(!stackInSlot.isEmpty()) {
                stackInSlot.shrink(1);
                inputInventory.setStackInSlot(0, stackInSlot);

                siftingRecipe.rollResults(level.random)
                        .forEach(stack -> tryToInsertOutputItem(outputInventory, stack, false));
            }
        }
        if(MConfigs.server().mesh.useMeshDurabilityWithSifter.get()){
            ItemStack meshStack = getMeshInventory().getStackInSlot(0);
            player.setItemInHand(InteractionHand.MAIN_HAND, meshStack.copy());
            getMeshInventory().getStackInSlot(0).hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        }

        return true;
    }
    public boolean isAdvancedSifter(){
        return false;
    }
    protected int getItemsPerCycle(){
        return 1;
    }


    private Optional<SiftingRecipe> getRecipe(){
        if(this.level == null)
            return Optional.empty();
        Optional<SiftingRecipe> recipe = SiftingRecipeManager.getRecipeForSifter(this);
        //Optional<SiftingRecipe> recipe = ModRecipes.findMergedRecipesWithMatchingIngredients(this);
        return recipe;
    }

    protected void tryToInsertOutputItem(ItemStackHandler outputInv,ItemStack stack, boolean simulate){
        ItemHandlerHelper.insertItemStacked(outputInv, stack, simulate);
    }
    protected int getItemsProcessedPerCycle(){
        return itemsProcessedPerCycle;
    }

    public void showParticles() {
        if (inputInventory.getStackInSlot(0).isEmpty() || meshInventory.getStackInSlot(0).isEmpty())
            return;
        if(!isSpeedRequirementFulfilled())
            return;
        if(getAbsSpeed() == 0)
            return;

        ItemParticleOption data = new ItemParticleOption(ParticleTypes.ITEM, inputInventory.getStackInSlot(0));
        float angle = level.random.nextFloat() * 360;
        Vec3 offset = new Vec3(0, 0, 0.5f);
        offset = VecHelper.rotate(offset, angle, Direction.Axis.Y);
        Vec3 target = VecHelper.rotate(offset, getSpeed() > 0 ? 25 : -25, Direction.Axis.Y);

        Vec3 center = offset.add(VecHelper.getCenterOf(worldPosition));
        target = VecHelper.offsetRandomly(target.subtract(offset), level.random, 1 / 128f);
        level.addParticle(data, center.x, center.y, center.z, target.x, target.y, target.z);
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.put("InputInventory", inputInventory.serializeNBT(registries));
        compound.put("OutputInventory", outputInventory.serializeNBT(registries));
        compound.put("MeshInventory", meshInventory.serializeNBT(registries));
        if (owner != null)
            compound.putUUID("Owner", owner);
        super.write(compound, registries, clientPacket);

    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        inputInventory.deserializeNBT(registries, compound.getCompound("InputInventory"));
        outputInventory.deserializeNBT(registries, compound.getCompound("OutputInventory"));
        meshInventory.deserializeNBT(registries, compound.getCompound("MeshInventory"));
        if (compound.contains("Owner"))
            owner = compound.getUUID("Owner");
        super.read(compound, registries, clientPacket);

    }
    @Override
    public boolean isSpeedRequirementFulfilled() {
        Optional<SiftingRecipe> recipe = getRecipe();
        if(recipe.isEmpty())
            return getAbsSpeed() >= minimumSpeed;
        if(recipe.get().getRequirement(MechanicalRecipeRequirementTypes.MIN_SPEED.get()).isPresent())
            return recipe.get().getRequirement(MechanicalRecipeRequirementTypes.MIN_SPEED.get()).get().test(level, this);
        if(recipe.get().getRequirement(MechanicalRecipeRequirementTypes.MAX_SPEED.get()).isPresent())
            return recipe.get().getRequirement(MechanicalRecipeRequirementTypes.MAX_SPEED.get()).get().test(level, this);
        return super.isSpeedRequirementFulfilled();
    }

    protected float getDefaultMinimumSpeed() {
        return DEFAULT_MINIMUM_SPEED;
    }

    public boolean tryToInsertMesh(ItemStack meshStack, Player player, boolean simulate) {
        if(!isValidMesh(meshStack))
            return false;

        ItemStack meshToInsert = meshStack.copy();
        meshToInsert.setCount(1);
        if(getMeshItemStack().is(meshStack.getItem()))
            return false;

        if(simulate)
            return true;

        meshStack.shrink(1);
        if(!meshInventory.getStackInSlot(0).isEmpty() && player != null) {
            removeMesh(player);
        }
        meshInventory.setStackInSlot(0, meshToInsert);
        setChanged();

        return true;
    }

    public boolean hasMesh(){
        return !getMeshItemStack().isEmpty();
    }
    public ItemStack getMeshItemStack(){
        return meshInventory.getStackInSlot(0);
    }
    public boolean hasAdvancedMesh(){
        return !meshInventory.getStackInSlot(0).isEmpty() && meshInventory.getStackInSlot(0).getItem() instanceof AbstractAdvancedMesh;
    }


    public void removeMesh(Player player) {
        player.getInventory().placeItemBackInInventory(meshInventory.getStackInSlot(0));
        meshInventory.setStackInSlot(0, ItemStack.EMPTY);
        minimumSpeed = getDefaultMinimumSpeed();
        sendData();
    }

    public boolean isWaterlogged() {
        return this.getBlockState().getValue(BlockStateProperties.WATERLOGGED);
    }

    public float getAbsSpeed(){
        return Math.abs(getSpeed());
    }

    public ItemStack getInputItemStack(){
        return this.inputInventory.getStackInSlot(0);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void playRunningSound() {
        float pitch = Mth.clamp((Math.abs(getSpeed()) / 256f) + .45f, .85f, 1f);
        SoundScapes.play(SoundScapes.AmbienceGroup.MILLING, worldPosition, pitch);    }

    @Override
    public boolean matchesIngredients(SiftingRecipe siftingRecipeRecipeHolder) {
        boolean incorrectInput = Arrays.stream(siftingRecipeRecipeHolder.getInput().getItems()).filter(itemStack -> ItemStack.isSameItem(itemStack,inputInventory.getStackInSlot(0))).toList().isEmpty();
        if(incorrectInput)
            return false;
        return ItemStack.isSameItem(meshInventory.getStackInSlot(0),siftingRecipeRecipeHolder.getMesh());
    }

    @Override
    public boolean hasEnoughOutputSpace(SiftingRecipe siftingRecipe) {
        return true;
    }

    @Override
    public float getKineticSpeed() {
        return getSpeed();
    }

    @Override
    public int getProcessingTime() {
        if(getRecipe().isEmpty())
            return 1;
        return getRecipe().get().getProcessingTime();
    }


    private class SifterInventoryHandler extends CombinedInvWrapper {

        public SifterInventoryHandler(ItemStackHandler inputInventory, ItemStackHandler outputInventory, ItemStackHandler meshInventory) {
            super(inputInventory, outputInventory, meshInventory);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (outputInventory == getHandlerFromIndex(getIndexForSlot(slot)))
                return false;
            if (meshInventory == getHandlerFromIndex(getIndexForSlot(slot)))
                return MeshUtils.isMeshItem(stack);
            return super.isItemValid(slot, stack);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (outputInventory == getHandlerFromIndex(getIndexForSlot(slot)))
                return stack;
            if (!isItemValid(slot, stack))
                return stack;
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (inputInventory == getHandlerFromIndex(getIndexForSlot(slot)))
                return ItemStack.EMPTY;
            if (meshInventory == getHandlerFromIndex(getIndexForSlot(slot)))
                return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }

    }
}
