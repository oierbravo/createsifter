package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.oierbravo.createsifter.infrastucture.config.ModConfigs;
import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;

public abstract class AbstractSifterRenderer extends KineticBlockEntityRenderer<SifterBlockEntity> {
    public AbstractSifterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    abstract protected PartialModel getCogModel();
    abstract protected void renderSafeInternal(SifterBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                                          int light, int overlay);
    @Override
    public boolean shouldRenderOffScreen(SifterBlockEntity be) {
        return true;
    }
    @Override
    protected void renderSafe(SifterBlockEntity be, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                              int light, int overlay) {

        renderSafeInternal(be, partialTicks, poseStack, buffer,light, overlay);

        ItemStack meshItemStack = be.getMeshItemStack();

        double xPos = 0.0;
        if(ModConfigs.client().sifter.renderMovingMesh.get())
            xPos = Math.sin(be.dynamicCycleBehaviour.getProgressPercent())/40;

        if(!meshItemStack.isEmpty()){
            poseStack.pushPose();
            TransformStack.of(poseStack).translate(new Vec3(0.5 - xPos, 1.51, 0.5));
            renderStaticBlock(poseStack,buffer,light, overlay,meshItemStack,be);
            poseStack.popPose();
        }
        //In progress Block renderer
        if(!meshItemStack.isEmpty() && ModConfigs.client().sifter.renderSiftedBlock.get()) {
            ItemStack inProccessItemStack = be.getInputItemStack();

            if (!inProccessItemStack.equals(ItemStack.EMPTY)) {
                float progress = be.dynamicCycleBehaviour.getProcessingRemainingPercentFloat();
                poseStack.pushPose();
                TransformStack.of(poseStack)
                        .scale((float) .9, progress, (float) .9)
                        .translate(new Vec3(-xPos + 0.05, 1.05 / progress, 0.05));
                renderBlockFromItemStack(be.getInputItemStack(), poseStack, buffer, light, overlay);
                poseStack.popPose();
            }
        }
        super.renderSafe(be, partialTicks, poseStack, buffer, light, overlay);
    }
    @Override
    protected SuperByteBuffer getRotatedModel(SifterBlockEntity be, BlockState state) {
        return CachedBuffers.partial(getCogModel(), state);
    }
    protected void renderStaticBlock(PoseStack ms, MultiBufferSource buffer, int light, int overlay, ItemStack itemStack, SifterBlockEntity entity) {
        Minecraft.getInstance()
                .getItemRenderer()
                .renderStatic(itemStack, ItemDisplayContext.NONE, light, overlay, ms,
                        buffer, entity.getLevel(), 0);
    }
    protected void renderBlockFromItemStack(ItemStack itemStack,PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Block block = Block.byItem(itemStack.getItem());
        BlockState blockState = block.defaultBlockState();
        Minecraft.getInstance()
                .getBlockRenderer()
                .renderSingleBlock(blockState, ms,buffer,light,overlay, ModelData.EMPTY,RenderType.solid());
    }
}
