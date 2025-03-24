package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.oierbravo.createsifter.content.contraptions.components.meshes.AbstractAdvancedMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.MeshUtils;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.foundation.util.ModLang;
import com.oierbravo.createsifter.infrastucture.config.MConfigs;
import com.oierbravo.createsifter.register.ModBlockEntities;
import com.oierbravo.createsifter.register.ModRecipes;
import com.oierbravo.mechanicals.foundation.blockEntity.behaviour.DynamicCycleBehavior;
import com.oierbravo.mechanicals.foundation.blockEntity.behaviour.RecipeRequirementsBehaviour;
import com.oierbravo.mechanicals.register.MechanicalRecipeRequirementTypes;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        inputAndMeshCombined = new SifterInventoryHandler(inputInventory,outputInventory);
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
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.SIFTER.get(),
                (be, context) -> be.getItemHandler()
        );
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean added = super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        if(!this.meshInventory.getStackInSlot(0).isEmpty()) {
            ModLang.translate("tooltip.mesh", this.meshInventory.getStackInSlot(0).getDisplayName().getString()).style(ChatFormatting.YELLOW).forGoggles(tooltip);
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

        if(simulate)
            return true;

        ItemStack stackInSlot = inputInventory.getStackInSlot(0);
        if(!stackInSlot.isEmpty()) {
            stackInSlot.shrink(1);
            inputInventory.setStackInSlot(0, stackInSlot);

            siftingRecipe.rollResults()
                    .forEach(stack -> tryToInsertOutputItem(outputInventory, stack, false));
        }
        return true;
    }

    @Override
    public void playCompletionSound() {

    }


    private Optional<SiftingRecipe> getRecipe(){
        if(this.level == null)
            return Optional.empty();
        Optional<SiftingRecipe> recipe = ModRecipes.findMergedRecipesWithMatchingIngredients(this);
        return recipe;
    }

    protected void tryToInsertOutputItem(ItemStackHandler outputInv,ItemStack stack, boolean simulate){
        ItemHandlerHelper.insertItemStacked(outputInv, stack, simulate);
    }
    protected int getItemsProcessedPerCycle(){
        return itemsProcessedPerCycle;
    }

    public void spawnParticles() {
        if (inputInventory.getStackInSlot(0).isEmpty() || meshInventory.getStackInSlot(0).isEmpty())
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
        super.write(compound, registries, clientPacket);

    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        inputInventory.deserializeNBT(registries, compound.getCompound("InputInventory"));
        outputInventory.deserializeNBT(registries, compound.getCompound("OutputInventory"));
        meshInventory.deserializeNBT(registries, compound.getCompound("MeshInventory"));
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

    public void insertMesh(ItemStack meshStack, Player player) {
        if(meshInventory.getStackInSlot(0).isEmpty()){
            ItemStack meshToInsert = meshStack.copy();
            meshToInsert.setCount(1);
            if(!player.isCreative())
                meshStack.shrink(1);
            meshInventory.setStackInSlot(0, meshToInsert);
            setChanged();
        }
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
    public void tickAudio() {
        super.tickAudio();

        if (getSpeed() == 0)
            return;
        if (dynamicCycleBehaviour.isRunning())
            return;

        float pitch = Mth.clamp((Math.abs(getSpeed()) / 256f) + .45f, .85f, 1f);
        SoundScapes.play(SoundScapes.AmbienceGroup.MILLING, worldPosition, pitch);
    }

    @Override
    public boolean matchesIngredients(SiftingRecipe siftingRecipeRecipeHolder) {
        boolean incorrectInput = Arrays.stream(siftingRecipeRecipeHolder.getInput().getItems()).filter(itemStack -> ItemStack.isSameItem(itemStack,inputInventory.getStackInSlot(0))).toList().isEmpty();
        if(incorrectInput)
            return false;
        return ItemStack.isSameItem(meshInventory.getStackInSlot(0),siftingRecipeRecipeHolder.getMesh());
    }
    /*public boolean matchesIngredients(RecipeHolder<SiftingRecipe> siftingRecipeRecipeHolder) {
        return matchesIngredients(siftingRecipeRecipeHolder.value());
    }*/


    @Override
    public void onOperationCompleted() {

    }

    @Override
    public float getKineticSpeed() {
        return getSpeed();
    }

    @Override
    public int getProcessingTime() {
        if(getRecipe().isEmpty())
            return 0;
        return getRecipe().get().getProcessingTime();
    }


    @Override
    public boolean hasEnoughOutputSpace() {
        return true;
    }

    private class SifterInventoryHandler extends CombinedInvWrapper {

        public SifterInventoryHandler(ItemStackHandler inputInventory, ItemStackHandler outputInventory) {
            super(inputInventory, outputInventory);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (outputInventory == getHandlerFromIndex(getIndexForSlot(slot)))
                return false;
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
            return super.extractItem(slot, amount, simulate);
        }

    }
}
