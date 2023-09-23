package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;

public class SifterRenderer extends KineticBlockEntityRenderer<SifterBlockEntity> {
    public SifterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(SifterBlockEntity be) {
        return true;
    }
    @Override
    protected void renderSafe(SifterBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {

        VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
        ItemStack meshItemStack = be.meshInv.getStackInSlot(0);

        Double xPos = 0.0;
        if(SifterConfig.SIFTER_RENDER_MOVING_MESH.get())
            xPos = Math.sin(be.getProgress())/40;

        if(!meshItemStack.isEmpty()){
            ms.pushPose();
            TransformStack.cast(ms).translate(new Vec3(0.5 - xPos, 1.51, 0.5));
            renderStaticBlock(ms,buffer,light, overlay,meshItemStack);
            ms.popPose();
        }
        //In progress Block renderer
        if(!meshItemStack.isEmpty() && SifterConfig.SIFTER_RENDER_SIFTED_BLOCK.get()) {
            ItemStack inProccessItemStack = be.getInputItemStack();

            if (!inProccessItemStack.equals(ItemStack.EMPTY)) {
                float progress = be.getProcessingRemainingPercent();
                ms.pushPose();
                TransformStack.cast(ms)
                        .scale((float) .9, progress, (float) .9)
                        .translate(new Vec3(-xPos + 0.05, 1.05 / progress, 0.05));
                renderBlockFromItemStack(be.getInputItemStack(), ms, buffer, light, overlay);
                ms.popPose();
            }
        }
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
    }
    @Override
    protected SuperByteBuffer getRotatedModel(SifterBlockEntity be, BlockState state) {
        return CachedBufferer.partial(ModPartials.SIFTER_COG, state);
    }
    protected void renderStaticBlock(PoseStack ms, MultiBufferSource buffer, int light, int overlay, ItemStack itemStack, SifterBlockEntity entity) {
        Minecraft.getInstance()
                .getItemRenderer()
                .renderStatic(itemStack, ItemDisplayContext.NONE, light, overlay, ms,
                        buffer, entity.getLevel(), 0);
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
