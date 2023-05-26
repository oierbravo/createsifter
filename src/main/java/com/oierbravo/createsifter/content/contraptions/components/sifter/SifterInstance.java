package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;

    public class SifterInstance extends SingleRotatingInstance<SifterBlockEntity> {

        public SifterInstance(MaterialManager materialManager, SifterBlockEntity blockEntity) {
            super(materialManager, blockEntity);
        }

        @Override
        protected Instancer<RotatingData> getModel() {
            return getRotatingMaterial().getModel(AllPartialModels.MILLSTONE_COG, blockEntity.getBlockState());
        }
    }

