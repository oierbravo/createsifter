package com.oierbravo.createsifter.content.contraptions.components.brasss_sifter;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterConfig;
import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;

public class BrassSifterRenderer extends KineticBlockEntityRenderer<BrassSifterBlockEntity> {
    public BrassSifterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    public boolean shouldRenderOffScreen(BrassSifterBlockEntity be) {
        return true;
    }
    @Override
    protected void renderSafe(BrassSifterBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {

        FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, buffer, light, overlay);
        //if (Backend.canUseInstancing(be.getLevel()))
        //    return;

        VertexConsumer vb = buffer.getBuffer(RenderType.solid());

        FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, buffer, light, overlay);

        ItemStack meshItemStack = be.meshInv.getStackInSlot(0);
        float progress = be.getProgress();

        Double xPos = 0.0;
        if(SifterConfig.SIFTER_RENDER_MOVING_MESH.get())
            xPos = Math.sin(progress)/40;

        if(!meshItemStack.isEmpty()){
            ms.pushPose();
            TransformStack.cast(ms).translate(new Vec3( 0.5 - xPos, 1.51, 0.5));
            renderStaticBlock(ms,buffer,light, overlay,meshItemStack);
            ms.popPose();
        }
        //In progress Block renderer
        if(!meshItemStack.isEmpty() && SifterConfig.SIFTER_RENDER_SIFTED_BLOCK.get()) {
            ItemStack inProccessItemStack = be.getInputItemStack();
            float remainingPercent = be.getProcessingRemainingPercent();

            if (!inProccessItemStack.equals(ItemStack.EMPTY)) {
                ms.pushPose();
                TransformStack.cast(ms)
                        .scale((float) .9, remainingPercent, (float) .9)
                        .translate(new Vec3( -xPos + 0.05, 1.05 / remainingPercent, 0.05));
                renderBlockFromItemStack(be.getInputItemStack(), ms, buffer, light, overlay);
                ms.popPose();
            }
        }
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
    }
    @Override
    protected SuperByteBuffer getRotatedModel(BrassSifterBlockEntity be, BlockState state) {
        return CachedBufferer.partial(ModPartials.BRASS_SIFTER_COG, state);
    }
    protected void renderStaticBlock(PoseStack ms, MultiBufferSource buffer, int light, int overlay, ItemStack itemStack) {
        Minecraft.getInstance()
                .getItemRenderer()
                .renderStatic(itemStack, ItemTransforms.TransformType.NONE, light, overlay, ms, buffer, 0);
    }
    protected void renderBlockFromItemStack(ItemStack itemStack,PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Item item = itemStack.getItem();
        BlockState blockState = Blocks.AIR.defaultBlockState();
        if(item instanceof BlockItem){
            blockState = ((BlockItem) item).getBlock().defaultBlockState();
        }

        Minecraft.getInstance()
                .getBlockRenderer()
                .renderSingleBlock(blockState, ms,buffer,light,overlay, ModelData.EMPTY,RenderType.solid());
    }
}
