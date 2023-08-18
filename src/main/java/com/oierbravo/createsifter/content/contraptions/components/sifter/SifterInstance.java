package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;

    public class SifterInstance extends SingleRotatingInstance<SifterBlockEntity> {

        public SifterInstance(MaterialManager materialManager, SifterBlockEntity blockEntity) {
            super(materialManager, blockEntity);
        }

        @Override
        protected Instancer<RotatingData> getModel() {
            return getRotatingMaterial().getModel(ModPartials.SIFTER_COG, blockEntity.getBlockState());
        }
    }

