package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.oierbravo.createsifter.register.ModItems;
import com.oierbravo.createsifter.register.ModPartials;
import com.oierbravo.createsifter.register.ModTags;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.components.millstone.MillstoneRenderer;
import com.simibubi.create.content.contraptions.processing.BasinBlock;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import org.jetbrains.annotations.NotNull;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.List;

public class SifterRenderer extends MillstoneRenderer {
    public SifterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {


        //boolean usingFlywheel = Backend.canUseInstancing(te.getLevel());
        SifterTileEntity sifterTileEntity = (SifterTileEntity) te;
        VertexConsumer vb = buffer.getBuffer(RenderType.cutout());
        ItemStack meshItemStack = sifterTileEntity.meshInv.getStackInSlot(0);
        /*if(!meshItemStack.isEmpty()){
            BlockState state = getRenderedBlockState(te);
            PartialModel meshModel = ModPartials.getFromItemStack(meshItemStack);

            CachedBufferer.partial(meshModel,state)

                    .translateY(1.01)
                    .light(light)
                    .renderInto(ms, vb);

        }*/
        /*if(!meshItemStack.isEmpty()){
            BlockState state = getRenderedBlockState(te);
            PartialModel meshModel = ModPartials.getFromItemStack(meshItemStack);
            CachedBufferer.block(state)

                    .translateY(1.5)
                    .light(light)
                    .renderInto(ms, vb);

        }*/
        if(!meshItemStack.isEmpty()){
            ms.pushPose();
            TransformStack.cast(ms).translate(new Vec3(0.5, 1.51, 0.5));
            renderStaticBlock(ms,buffer,light, overlay,meshItemStack);
            ms.popPose();
        }
        //In progress Block renderer
        ItemStack inProccessItemStack = sifterTileEntity.getInputItemStack();

        if(!inProccessItemStack.equals(ItemStack.EMPTY)){
            float progress = sifterTileEntity.getProcessingRemainingPercent();
            ms.pushPose();
            TransformStack.cast(ms)
                    .scale((float) .9, progress,(float) .9)
                .translate(new Vec3(0.05, 1.05 / progress, 0.05));
            renderBlockFromItemStack(sifterTileEntity.getInputItemStack(),ms,buffer,light, overlay);
            ms.popPose();
        }
        super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
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
                .renderSingleBlock(blockState, ms,buffer,light,overlay,EmptyModelData.INSTANCE);
    }


}
