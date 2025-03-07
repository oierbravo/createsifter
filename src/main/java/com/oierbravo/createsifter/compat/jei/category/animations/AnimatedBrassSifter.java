package com.oierbravo.createsifter.compat.jei.category.animations;

import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterBlock;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModPartials;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class AnimatedBrassSifter extends BaseAnimatedSifter<BrassSifterBlock> {

    @Override
    PartialModel getMeshModel() {
        return ModPartials.BRASS_SIFTER_MESH;
    }

    @Override
    PartialModel getCogModel() {
        return ModPartials.BRASS_SIFTER_COG;
    }

    @Override
    BlockEntry<BrassSifterBlock> getSifterBlock() {
        return ModBlocks.BRASS_SIFTER;
    }
}
