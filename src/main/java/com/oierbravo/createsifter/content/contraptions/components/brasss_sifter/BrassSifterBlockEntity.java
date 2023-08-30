package com.oierbravo.createsifter.content.contraptions.components.brasss_sifter;

import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterConfig;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class BrassSifterBlockEntity extends SifterBlockEntity {
    private FilteringBehaviour filtering;

    protected int itemsProcessedPerCycle = BrassSifterConfig.BRASS_SIFTER_ITEMS_PER_CYCLE.get();

    public static float DEFAULT_MINIMUM_SPEED = BrassSifterConfig.BRASS_SIFTER_MINIMUM_SPEED.get().floatValue();

    public BrassSifterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Override
    protected ItemStackHandler createOutputInv(){
        return new ItemStackHandler(BrassSifterConfig.BRASS_SIFTER_OUTPUT_CAPACITY.get());
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
    public void tick() {
        if (getBlockState().getOptionalValue(BlockStateProperties.POWERED)
                .orElse(false))
            return;
        super.tick();
    }

    @Override
    protected void tryToInsertOutputItem(ItemStackHandler outputInv, ItemStack stack, boolean simulate) {
        if(filtering.test(stack)) {
            super.tryToInsertOutputItem(outputInv, stack, simulate);
        }
    }

    @Override
    protected float getDefaultMinimumSpeed() {
        return DEFAULT_MINIMUM_SPEED;
    }
}
