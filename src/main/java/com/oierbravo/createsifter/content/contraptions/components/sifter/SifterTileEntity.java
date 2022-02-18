package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.simibubi.create.content.contraptions.components.millstone.MillstoneTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SifterTileEntity extends MillstoneTileEntity {
    public SifterTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
