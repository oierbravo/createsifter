package com.oierbravo.createsifter.content.contraptions.components.sifter.andesite;

import com.oierbravo.createsifter.content.contraptions.components.sifter.AbstractSifterBlock;
import com.oierbravo.createsifter.register.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SifterBlock  extends AbstractSifterBlock<SifterBlockEntity> {
    public SifterBlock(Properties properties) {
        super(properties);
        registerDefaultState(super.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }
    @Override
    public Class<SifterBlockEntity> getBlockEntityClass() {
        return SifterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SifterBlockEntity> getBlockEntityType() {
        return ModBlockEntities.SIFTER.get();
    }
}
