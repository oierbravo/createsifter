package com.oierbravo.createsifter.compat.jei.category.animations;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Quaternion;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class AnimatedBrassSifter extends AnimatedKinetics {
    private boolean isWaterlogged = false;

    public AnimatedBrassSifter waterlogged(boolean value) {
        this.isWaterlogged = value;
        return this;
    }
    @Override
    public void draw(PoseStack matrixStack, int xOffset, int yOffset) {
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 0);
        AllGuiTextures.JEI_SHADOW.render(matrixStack, -16, 13);
        matrixStack.translate(-2, 18, 0);
        int scale = 22;

        blockElement(ModPartials.BRASS_SIFTER_COG)
                .atLocal(0,0.1,0)
                .rotateBlock(22.5, getCurrentAngle() * 2, 0)
                .scale(scale)
                .render(matrixStack);

        blockElement(ModBlocks.BRASS_SIFTER.getDefaultState())
                .atLocal(0,0.1,0)
                .rotateBlock(22.5, 22.5, 0)
                .scale(scale)
                .render(matrixStack);

        blockElement(ModPartials.BRASS_SIFTER_MESH)
                .atLocal(0,-1,0)
                .rotateBlock(22.5, 22.5, 0)
                .scale(scale)
                .render(matrixStack);



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
        matrixStack.mulPose(new Quaternion(22.5F,22.5F,0,true));
        FluidRenderer.renderFluidBox(new FluidStack(Fluids.WATER.getSource(),1000), from, from, from, to, to, to, buffer, matrixStack, LightTexture.FULL_BRIGHT, true);
        matrixStack.popPose();
        buffer.endBatch();
        Lighting.setupFor3DItems();
    }
}
