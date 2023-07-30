package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.oierbravo.createsifter.ModRecipeTypes;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.sound.SoundScapes;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SifterBlockEntity extends KineticBlockEntity {
    public ItemStackHandler inputInv;
    public ItemStackHandler outputInv;
    public LazyOptional<IItemHandler> capability;
    public int timer;
    private SiftingRecipe lastRecipe;

    public ItemStackHandler meshInv;

    protected CombinedInvWrapper inputAndMeshCombined ;

    public static float DEFAULT_MINIMUM_SPEED = SifterConfig.SIFTER_MINIMUM_SPEED.get().floatValue();
    protected int totalTime;
    protected float minimumSpeed = DEFAULT_MINIMUM_SPEED;


    public SifterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        inputInv = new ItemStackHandler(1);
        outputInv = new ItemStackHandler(SifterConfig.SIFTER_OUTPUT_CAPACITY.get());
        capability = LazyOptional.of(SifterInventoryHandler::new);
        meshInv = new ItemStackHandler(1){
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if(SiftingRecipe.isMeshItemStack(stack)){
                    return true;
                }
                return false;
            }

            @Override
            protected void onContentsChanged(int slot) {
                sendData();
            }
        };
        inputAndMeshCombined = new CombinedInvWrapper(inputInv,meshInv);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        super.tickAudio();

        if (getSpeed() == 0)
            return;
        if (inputInv.getStackInSlot(0)
                .isEmpty())
            return;

        float pitch = Mth.clamp((Math.abs(getSpeed()) / 256f) + .45f, .85f, 1f);
        SoundScapes.play(SoundScapes.AmbienceGroup.MILLING, worldPosition, pitch);
    }

    @Override
    public void tick() {
        super.tick();

        if (getSpeed() == 0)
            return;

        if(!isSpeedRequirementFulfilled()){
            return;
        }
        for (int i = 0; i < outputInv.getSlots(); i++)
            if (outputInv.getStackInSlot(i)
                    .getCount() == outputInv.getSlotLimit(i))
                return;

        if (timer > 0) {
            timer -= getProcessingSpeed();

            if (level.isClientSide) {
                spawnParticles();
                return;
            }
            if (timer <= 0)
                process();
            return;
        }

        if (inputInv.getStackInSlot(0)
                .isEmpty())
            return;

        RecipeWrapper inventoryIn = new RecipeWrapper(inputAndMeshCombined);
        if (lastRecipe == null || !lastRecipe.matches(inventoryIn, level,this.isWaterlogged(),getAbsSpeed())) {
            Optional<SiftingRecipe> recipe = ModRecipeTypes.SIFTING.find(inventoryIn, level, this.isWaterlogged(),getAbsSpeed());
            if (!recipe.isPresent()) {
                timer = 100;
                totalTime = 100;
                minimumSpeed = DEFAULT_MINIMUM_SPEED;
                sendData();
            } else {
                lastRecipe = recipe.get();
                timer = lastRecipe.getProcessingDuration();
                totalTime =  lastRecipe.getProcessingDuration();
                minimumSpeed = lastRecipe.getSpeedRequeriment();
                sendData();
            }
            return;
        }

        timer = lastRecipe.getProcessingDuration();
        totalTime =  lastRecipe.getProcessingDuration();
        minimumSpeed = lastRecipe.getSpeedRequeriment();
        sendData();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        capability.invalidate();
    }

    private void process() {

        RecipeWrapper inventoryIn = new RecipeWrapper(inputAndMeshCombined);

        if (lastRecipe == null || !lastRecipe.matches(inventoryIn, level, this.isWaterlogged(),getAbsSpeed())) {
            Optional<SiftingRecipe> recipe = ModRecipeTypes.SIFTING.find(inventoryIn, level,this.isWaterlogged(), getAbsSpeed());
            if (!recipe.isPresent())
                return;
            lastRecipe = recipe.get();
        }

        ItemStack stackInSlot = inputInv.getStackInSlot(0);
        stackInSlot.shrink(1);
        inputInv.setStackInSlot(0, stackInSlot);
        lastRecipe.rollResults()
                .forEach(stack -> ItemHandlerHelper.insertItemStacked(outputInv, stack, false));
        sendData();
        setChanged();
    }

    public void spawnParticles() {
        ItemStack stackInSlot = inputInv.getStackInSlot(0);
        if (stackInSlot.isEmpty())
            return;

        ItemParticleOption data = new ItemParticleOption(ParticleTypes.ITEM, stackInSlot);
        float angle = level.random.nextFloat() * 360;
        Vec3 offset = new Vec3(0, 0, 0.5f);
        offset = VecHelper.rotate(offset, angle, Direction.Axis.Y);
        Vec3 target = VecHelper.rotate(offset, getSpeed() > 0 ? 25 : -25, Direction.Axis.Y);

        Vec3 center = offset.add(VecHelper.getCenterOf(worldPosition));
        target = VecHelper.offsetRandomly(target.subtract(offset), level.random, 1 / 128f);
        level.addParticle(data, center.x, center.y, center.z, target.x, target.y, target.z);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.putInt("Timer", timer);
        compound.put("InputInventory", inputInv.serializeNBT());
        compound.put("OutputInventory", outputInv.serializeNBT());
        compound.put("MeshInventory", meshInv.serializeNBT());
        compound.putInt("TotalTime", totalTime);
        compound.putFloat("MinimumSpeed", minimumSpeed);
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        timer = compound.getInt("Timer");
        inputInv.deserializeNBT(compound.getCompound("InputInventory"));
        outputInv.deserializeNBT(compound.getCompound("OutputInventory"));
        meshInv.deserializeNBT(compound.getCompound("MeshInventory"));
        totalTime = compound.getInt("TotalTime");
        minimumSpeed = compound.getFloat("MinimumSpeed");
        super.read(compound, clientPacket);
    }

    @Override
    public boolean isSpeedRequirementFulfilled() {
        return getAbsSpeed() >= minimumSpeed;
    }

    private boolean hasRecipeSpeedRequeriment() {
        if(minimumSpeed != DEFAULT_MINIMUM_SPEED){
            return true;
        }
        return false;
    }

    public int getProcessingSpeed() {
        return Mth.clamp((int) Math.abs(getSpeed() / 16f), 1, 512);
    }
    public float getProcessingRemainingPercent() {
        float timer = this.timer;
        float total = this.totalTime;
        float remaining = total - timer;
        float result =  remaining/total;
        return 1 - result;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (isItemHandlerCap(cap))
            return capability.cast();
        return super.getCapability(cap, side);
    }
    private boolean canProcess(ItemStack stack) {

        ItemStackHandler tester = new ItemStackHandler(2);
        tester.setStackInSlot(0, stack);
        tester.setStackInSlot(1, this.meshInv.getStackInSlot(0));
        RecipeWrapper inventoryIn = new RecipeWrapper(tester);

        if (lastRecipe != null && lastRecipe.matches(inventoryIn, level,this.isWaterlogged(),getAbsSpeed()))
            return true;
        return ModRecipeTypes.SIFTING.find(inventoryIn, level,this.isWaterlogged(),getAbsSpeed())
                .isPresent();
    }

    public void insertMesh(ItemStack meshStack, Player player) {
        if(meshInv.getStackInSlot(0).isEmpty()){
            ItemStack meshToInsert = meshStack.copy();
            meshToInsert.setCount(1);
            meshStack.shrink(1);
            meshInv.setStackInSlot(0, meshToInsert);
            setChanged();
        }
    }
    public boolean hasMesh(){
        return !meshInv.getStackInSlot(0).isEmpty();
    }

    public void removeMesh(Player player) {
        player.getInventory().placeItemBackInInventory(meshInv.getStackInSlot(0));
        meshInv.setStackInSlot(0, ItemStack.EMPTY);
    }
    public boolean isWaterlogged() {
        return this.getBlockState().getValue(BlockStateProperties.WATERLOGGED);
    }

    public float getAbsSpeed(){
        return Math.abs(getSpeed());
    }

    public ItemStack getInputItemStack(){
        return this.inputInv.getStackInSlot(0);
    }
    private class SifterInventoryHandler extends CombinedInvWrapper {

        public SifterInventoryHandler() {
            super(inputInv, outputInv);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (outputInv == getHandlerFromIndex(getIndexForSlot(slot)))
                return false;
            return canProcess(stack) && super.isItemValid(slot, stack);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (outputInv == getHandlerFromIndex(getIndexForSlot(slot)))
                return stack;
            if (!isItemValid(slot, stack))
                return stack;
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (inputInv == getHandlerFromIndex(getIndexForSlot(slot)))
                return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }

    }
}
