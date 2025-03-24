package com.oierbravo.createsifter.compat.jei.category.animations;

import com.oierbravo.createsifter.content.contraptions.components.sifter.andesite.SifterBlock;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModPartials;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class AnimatedSifter  extends AbstractAnimatedSifter<SifterBlock> {
    @Override
    PartialModel getMeshModel() {
        return ModPartials.SIFTER_MESH;
    }

    @Override
    PartialModel getCogModel() {
        return ModPartials.SIFTER_COG;
    }

    @Override
    BlockEntry<SifterBlock> getSifterBlock() {
        return ModBlocks.SIFTER;
    }
}
