package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.oierbravo.createsifter.content.contraptions.components.meshes.AbstractMesh;
import com.oierbravo.createsifter.register.*;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.item.ItemHelper;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public class SifterBlock  extends KineticBlock implements IBE<SifterBlockEntity>, ICogWheel , SimpleWaterloggedBlock, ISifterBlock {
    public SifterBlock(Properties properties) {
        super(properties);
        registerDefaultState(super.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return ModShapes.SIFTER;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.DOWN;
    }
    
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide)
            return ItemInteractionResult.SUCCESS;

        if(stack.getItem() instanceof AbstractMesh){
            withBlockEntityDo(level, pos, sifter -> {
                sifter.insertMesh(stack, player);
            });
        }

        if (!stack.isEmpty())
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;


        withBlockEntityDo(level, pos, sifter -> {


            boolean emptyOutput = true;
            IItemHandlerModifiable inv = sifter.getOutputInventory();

            for (int slot = 0; slot < inv.getSlots(); slot++) {
                ItemStack stackInSlot = inv.getStackInSlot(slot);
                if (!stackInSlot.isEmpty())
                    emptyOutput = false;
                player.getInventory()
                        .placeItemBackInInventory(stackInSlot);
                inv.setStackInSlot(slot, ItemStack.EMPTY);
            }

            boolean emptyInput = true;

            if (emptyOutput) {
                inv = sifter.getInputInventory();
                ItemStack inputStack = inv.getStackInSlot(0);
                inv.setStackInSlot(0, ItemStack.EMPTY);
                if(!inputStack.isEmpty()){
                    emptyInput = false;
                    player.getInventory()
                            .placeItemBackInInventory(inputStack);
                }

            }

            if(emptyInput){
                if(stack.isEmpty() && sifter.hasMesh() && player.isShiftKeyDown()){
                    sifter.removeMesh(player);
                }
            }

            sifter.setChanged();
            sifter.sendData();
        });

        return ItemInteractionResult.SUCCESS;
    }


    @Override
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        super.updateEntityAfterFallOn(worldIn, entityIn);

        if (entityIn.level().isClientSide)
            return;
        if (!(entityIn instanceof ItemEntity itemEntity))
            return;
        if (!entityIn.isAlive())
            return;


        SifterBlockEntity sifter = null;
        for (BlockPos pos : Iterate.hereAndBelow(entityIn.blockPosition()))
            if (sifter == null)
                sifter = getBlockEntity(worldIn, pos);

        if (sifter == null)
            return;

        IItemHandler capability = sifter.getLevel().getCapability(Capabilities.ItemHandler.BLOCK, sifter.getBlockPos(), null);
        if (capability == null)
            return;

        ItemStack remainder = capability
                .insertItem(0, itemEntity.getItem(), false);
        if (remainder.isEmpty())
            itemEntity.discard();
        if (remainder.getCount() < itemEntity.getItem()
                .getCount())
            itemEntity.setItem(remainder);

    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            withBlockEntityDo(worldIn, pos, te -> {
                ItemHelper.dropContents(worldIn, pos, te.getInputInventory());
                ItemHelper.dropContents(worldIn, pos, te.getMeshInventory());
                ItemHelper.dropContents(worldIn, pos, te.getOutputInventory());
            });

            worldIn.removeBlockEntity(pos);
        }
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<SifterBlockEntity> getBlockEntityClass() {
        return SifterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SifterBlockEntity> getBlockEntityType() {
        return ModBlockEntities.SIFTER.get();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState FluidState = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(FluidState.getType() == Fluids.WATER));
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }
}
