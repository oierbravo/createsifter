package com.oierbravo.createsifter.compat.jei.category.animations;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import com.oierbravo.createsifter.content.contraptions.components.sifter.ISifterBlock;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public abstract class BaseAnimatedSifter<SIFTER extends KineticBlock> extends AnimatedKinetics {
    private boolean isWaterlogged = false;

    public BaseAnimatedSifter<SIFTER> waterlogged(boolean value) {
        this.isWaterlogged = value;
        return this;
    }
    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        PoseStack matrixStack = guiGraphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 0);
        AllGuiTextures.JEI_SHADOW.render(guiGraphics, -16, 13);
        matrixStack.translate(-2, 18, 0);
        int scale = 22;

       blockElement(getCogModel())
                .atLocal(0,0.1,0)
                .rotateBlock(22.5, getCurrentAngle() * 2, 0)
                .scale(scale)
                .render(guiGraphics);

       blockElement(getSifterBlock().getDefaultState())
                .atLocal(0,0.1,0)
                .rotateBlock(22.5, 22.5, 0)
                .scale(scale)
                .render(guiGraphics);

        blockElement(getMeshModel())
                .atLocal(0,-1,0)
                .rotateBlock(22.5, 22.5, 0)
                .scale(scale)
                .render(guiGraphics);



        if(isWaterlogged){
            renderWaterlogged(matrixStack);
        }
        matrixStack.popPose();
    }
    private void renderWaterlogged(PoseStack matrixStack){
        AnimatedKinetics.DEFAULT_LIGHTING.applyLighting();
        MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance()
                .getBuilder());
        matrixStack.pushPose();
        UIRenderHelper.flipForGuiRender(matrixStack);
        matrixStack.scale(22, 18, 22);
        matrixStack.translate(-0.3,-0.1,0);
        float from = 1f / 16f;
        float to = 18f / 16f;
        matrixStack.mulPose(Axis.XP.rotationDegrees(22.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));
        FluidRenderer.renderFluidBox(new FluidStack(Fluids.WATER.getSource(),1000), from, from, from, to, to, to, buffer, matrixStack, LightTexture.FULL_BRIGHT, true);
        matrixStack.popPose();
        buffer.endBatch();
        Lighting.setupFor3DItems();
    }

    abstract PartialModel getMeshModel();
    abstract PartialModel getCogModel();
    abstract BlockEntry<SIFTER> getSifterBlock();
}
