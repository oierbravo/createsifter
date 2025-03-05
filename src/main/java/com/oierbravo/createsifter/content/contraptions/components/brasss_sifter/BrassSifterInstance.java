package com.oierbravo.createsifter.content.contraptions.components.brasss_sifter;

import com.oierbravo.createsifter.register.ModPartials;

public class BrassSifterInstance extends SingleRotatingInstance<BrassSifterBlockEntity> {
    public BrassSifterInstance(MaterialManager materialManager, BrassSifterBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(ModPartials.BRASS_SIFTER_COG, blockEntity.getBlockState());
    }
}
