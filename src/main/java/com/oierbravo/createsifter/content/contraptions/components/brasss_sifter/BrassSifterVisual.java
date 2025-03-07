package com.oierbravo.createsifter.content.contraptions.components.brasss_sifter;

import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;

public class BrassSifterVisual extends SingleAxisRotatingVisual<BrassSifterBlockEntity> {
    public BrassSifterVisual(VisualizationContext context, BrassSifterBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Direction.UP, Models.partial(ModPartials.BRASS_SIFTER_COG));
    }
}
