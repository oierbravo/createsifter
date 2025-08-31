package com.oierbravo.createsifter.compat.emi.animations;

import com.simibubi.create.foundation.gui.CustomLightingSettings;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.gui.ILightingSettings;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.world.level.block.state.BlockState;

/**
 * From Create Fabric
 * https://github.com/Fabricators-of-Create/Create/blob/mc1.20.1/fabric/dev/src/main/java/com/simibubi/create/compat/emi/CreateEmiAnimations.java
 */
public class EmiAnimations {
    public static final ILightingSettings DEFAULT_LIGHTING = CustomLightingSettings.builder()
            .firstLightRotation(12.5f, 45.0f)
            .secondLightRotation(-20.0f, 50.0f)
            .build();

    public static GuiGameElement.GuiRenderBuilder defaultBlockElement(BlockState state) {
        return GuiGameElement.of(state)
                .lighting(DEFAULT_LIGHTING);
    }

    public static GuiGameElement.GuiRenderBuilder defaultBlockElement(PartialModel partial) {
        return GuiGameElement.of(partial)
                .lighting(DEFAULT_LIGHTING);
    }
    public static GuiGameElement.GuiRenderBuilder blockElement(BlockState state) {
        return defaultBlockElement(state);
    }

    public static GuiGameElement.GuiRenderBuilder blockElement(PartialModel partial) {
        return defaultBlockElement(partial);
    }
    public static float getCurrentAngle() {
        return (AnimationTickHolder.getRenderTime() * 4f) % 360;
    }
}
