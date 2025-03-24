package com.oierbravo.createsifter.content.contraptions.components.sifter.brass;

import com.oierbravo.createsifter.content.contraptions.components.sifter.andesite.SifterBlockEntity;
import com.oierbravo.createsifter.infrastucture.config.MConfigs;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class BrassSifterBlockEntity extends SifterBlockEntity {
    private FilteringBehaviour filtering;

    protected int itemsProcessedPerCycle = MConfigs.server().brassSifter.itemsPerCycle.get();

    public static float DEFAULT_MINIMUM_SPEED = MConfigs.server().brassSifter.minimumSpeed.getF();

    public BrassSifterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        DEFAULT_MINIMUM_SPEED = MConfigs.server().sifter.minimumSpeed.getF();
    }


    @Override
    protected ItemStackHandler createOutputInventory(){
        return new ItemStackHandler(MConfigs.server().brassSifter.outputCapacity.get());
    }

    @Override
    protected int getItemsProcessedPerCycle() {
        return itemsProcessedPerCycle;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        filtering = new FilteringBehaviour(this, new BrassSifterFilterSlotPositioning())
                .forRecipes();
        behaviours.add(filtering);
    }


    @Override
    public boolean tryProcess(boolean simulate) {
        if (getBlockState().getOptionalValue(BlockStateProperties.POWERED)
                .orElse(false))
            return false;
        return super.tryProcess(simulate);
    }
}
