package com.oierbravo.createsifter.content.contraptions.components.brasss_sifter;

import com.oierbravo.createsifter.content.contraptions.components.meshes.AdvancedBaseMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.BaseMesh;
import com.oierbravo.createsifter.content.contraptions.components.sifter.ISifterBlock;
import com.oierbravo.createsifter.register.ModBlockEntities;
import com.oierbravo.createsifter.register.ModShapes;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class BrassSifterBlock extends KineticBlock implements IBE<BrassSifterBlockEntity>, ICogWheel, SimpleWaterloggedBlock, ISifterBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BrassSifterBlock(Properties properties) {
        super(properties);
        registerDefaultState(super.defaultBlockState()
                .setValue(BlockStateProperties.WATERLOGGED, false)
                .setValue(BlockStateProperties.POWERED, false)
        );

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
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            withBlockEntityDo(worldIn, pos, te -> {
                ItemHelper.dropContents(worldIn, pos, te.inputInv);
                ItemHelper.dropContents(worldIn, pos, te.meshInv);
                ItemHelper.dropContents(worldIn, pos, te.outputInv);
            });

            worldIn.removeBlockEntity(pos);
        }
    }


    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
                                boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        if (worldIn.isClientSide)
            return;
        if (!worldIn.getBlockTicks()
                .willTickThisTick(pos, this))
            worldIn.scheduleTick(pos, this, 0);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource r) {
        boolean previouslyPowered = state.getValue(POWERED);
        if (previouslyPowered != worldIn.hasNeighborSignal(pos))
            worldIn.setBlock(pos, state.cycle(POWERED), 2);
    }


    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return true;
    }




    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult hit) {
        BrassSifterBlockEntity sifterBlockEntity = (BrassSifterBlockEntity) worldIn.getBlockEntity(pos);
        ItemStack handInStack = player.getItemInHand(handIn);

        if (worldIn.isClientSide)
            return InteractionResult.SUCCESS;
        if(handInStack.getItem() instanceof BaseMesh || handInStack.getItem() instanceof AdvancedBaseMesh){
            sifterBlockEntity.insertMesh(handInStack, player);
        }

        if (!handInStack.isEmpty())
            return InteractionResult.PASS;

        withBlockEntityDo(worldIn, pos, sifter -> {
            boolean emptyOutput = true;
            IItemHandlerModifiable inv = sifter.outputInv;
            if(handInStack.isEmpty() && sifterBlockEntity.hasMesh() && player.isShiftKeyDown()){
                sifterBlockEntity.removeMesh(player);
            }

            for (int slot = 0; slot < inv.getSlots(); slot++) {
                ItemStack stackInSlot = inv.getStackInSlot(slot);
                if (!stackInSlot.isEmpty())
                    emptyOutput = false;
                player.getInventory()
                        .placeItemBackInInventory(stackInSlot);
                inv.setStackInSlot(slot, ItemStack.EMPTY);
            }

            if (emptyOutput) {
                inv = sifter.inputInv;
                for (int slot = 0; slot < inv.getSlots(); slot++) {
                    player.getInventory()
                            .placeItemBackInInventory(inv.getStackInSlot(slot));
                    inv.setStackInSlot(slot, ItemStack.EMPTY);
                }
            }

            sifter.setChanged();
            sifter.sendData();
        });

        return InteractionResult.SUCCESS;
    }
    @Override
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        super.updateEntityAfterFallOn(worldIn, entityIn);

        if (entityIn.level().isClientSide)
            return;
        if (!(entityIn instanceof ItemEntity))
            return;
        if (!entityIn.isAlive())
            return;

        BrassSifterBlockEntity sifter = null;
        for (BlockPos pos : Iterate.hereAndBelow(entityIn.blockPosition()))
            if (sifter == null)
                sifter = getBlockEntity(worldIn, pos);

        if (sifter == null)
            return;

        ItemEntity itemEntity = (ItemEntity) entityIn;
        LazyOptional<IItemHandler> capability = sifter.getCapability(ForgeCapabilities.ITEM_HANDLER);
        if (!capability.isPresent())
            return;

        ItemStack remainder = capability.orElse(new ItemStackHandler())
                .insertItem(0, itemEntity.getItem(), false);
        if (remainder.isEmpty())
            itemEntity.discard();
        if (remainder.getCount() < itemEntity.getItem()
                .getCount())
            itemEntity.setItem(remainder);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;

    }

    @Override
    public Class<BrassSifterBlockEntity> getBlockEntityClass() {
        return BrassSifterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BrassSifterBlockEntity> getBlockEntityType() {
        return ModBlockEntities.BRASS_SIFTER.get();
    }
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState FluidState = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(FluidState.getType() == Fluids.WATER))
                .setValue(POWERED, context.getLevel()
                        .hasNeighborSignal(context.getClickedPos()));
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
        builder.add(BlockStateProperties.POWERED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return super.getMinimumRequiredSpeedLevel();
    }
}
