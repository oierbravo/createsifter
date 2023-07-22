package com.oierbravo.createsifter.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.oierbravo.createsifter.register.ModBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import net.minecraft.client.gui.GuiGraphics;

public class AnimatedSifter  extends AnimatedKinetics {
    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        PoseStack matrixStack = guiGraphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 0);
        AllGuiTextures.JEI_SHADOW.render(guiGraphics, -16, 13);
        matrixStack.translate(-2, 18, 0);
        int scale = 22;

        blockElement(AllPartialModels.MILLSTONE_COG)
                .rotateBlock(22.5, getCurrentAngle() * 2, 0)
                .scale(scale)
                .render(guiGraphics);

        blockElement(ModBlocks.SIFTER.getDefaultState())
                .rotateBlock(22.5, 22.5, 0)
                .scale(scale)
                .render(guiGraphics);

        matrixStack.popPose();
    }
}
