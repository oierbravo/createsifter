package com.oierbravo.createsifter.compat.emi.animations;

import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModPartials;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.world.level.material.Fluids;

import static com.oierbravo.createsifter.compat.emi.animations.EmiAnimations.blockElement;
import static com.oierbravo.createsifter.compat.emi.animations.EmiAnimations.getCurrentAngle;


public class AnimatedSifter {
    public static void sifter(WidgetHolder widgets, int x, int y, boolean waterlogged, boolean advanced) {
        PartialModel COG = (advanced) ? ModPartials.BRASS_SIFTER_COG : ModPartials.SIFTER_COG;
        BlockEntry<?> SIFTER = (advanced) ? ModBlocks.BRASS_SIFTER : ModBlocks.SIFTER;

        widgets.addDrawable(x, y, 0, 0, (matrices, mouseX, mouseY, delta) -> {
            int scale = 22;
            if(waterlogged)
                GuiGameElement.of(Fluids.WATER).rotateBlock(22.5, 22.5, 0)
                    .atLocal(.01,.01,-.01)
                    .scale(scale)
                    .render(matrices);

            blockElement(COG)
                    .rotateBlock(22.5, getCurrentAngle() * 2, 0)
                    .scale(scale)
                    .render(matrices);

            blockElement(SIFTER.getDefaultState())
                    .rotateBlock(22.5, 22.5, 0)
                    .scale(scale)
                    .render(matrices);
        });
    }

}
