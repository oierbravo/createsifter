package com.oierbravo.createsifter.compat.jei.category.animations;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.platform.NeoForgeCatnipServices;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.material.Fluids;

public abstract class AbstractAnimatedSifter<SIFTER extends KineticBlock> extends AnimatedKinetics {
    private boolean isWaterlogged = false;

    public AbstractAnimatedSifter<SIFTER> waterlogged(boolean value) {
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
            renderWaterlogged(guiGraphics);
        }
        matrixStack.popPose();
    }
    private void renderWaterlogged(GuiGraphics guiGraphics){
        AnimatedKinetics.DEFAULT_LIGHTING.applyLighting();
        MultiBufferSource buffer = MultiBufferSource.immediate(new ByteBufferBuilder(1536));
        /*MultiBufferSource.BufferSource buffer =
                MultiBufferSource.immediate(Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX));*/
        PoseStack matrixStack = guiGraphics.pose();
        matrixStack.pushPose();
        UIRenderHelper.flipForGuiRender(matrixStack);
        matrixStack.scale(22, 18, 22);
        matrixStack.translate(-0.3,-0.1,0);
        float from = 1f / 16f;
        float to = 18f / 16f;
        matrixStack.mulPose(Axis.XP.rotationDegrees(22.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));

        float xMin = 2 / 16f;
        float xMax = 2 / 16f;
        final float yMin = 2 / 16f;
        final float yMax = yMin + 12 / 16f * 1;
        final float zMin = 2 / 16f;
        final float zMax = 14 / 16f;
        //matrixStack.scale(16, 16, 16);
        NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(Fluids.WATER.defaultFluidState(), from, from, from, to, to, to, guiGraphics.bufferSource(), matrixStack, LightTexture.FULL_BRIGHT, false, true);
        guiGraphics.flush();
        Lighting.setupFor3DItems();

        matrixStack.popPose();
    }

    abstract PartialModel getMeshModel();
    abstract PartialModel getCogModel();
    abstract BlockEntry<SIFTER> getSifterBlock();
}
