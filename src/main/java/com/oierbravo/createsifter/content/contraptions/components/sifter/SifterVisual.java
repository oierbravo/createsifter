package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.minecraft.core.Direction;

import java.util.function.Consumer;

public class SifterVisual extends SingleAxisRotatingVisual<SifterBlockEntity>{
    public SifterVisual(VisualizationContext context, SifterBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick, Direction.UP, Models.partial(ModPartials.SIFTER_COG));
    }
}
