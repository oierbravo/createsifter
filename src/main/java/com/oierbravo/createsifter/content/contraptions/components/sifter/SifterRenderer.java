package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.oierbravo.createsifter.register.ModItems;
import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.components.millstone.MillstoneRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class SifterRenderer extends MillstoneRenderer {
    public SifterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {


        //boolean usingFlywheel = Backend.canUseInstancing(te.getLevel());
        SifterTileEntity sifterTileEntity = (SifterTileEntity) te;
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());

        if(!sifterTileEntity.meshInv.getStackInSlot(0).isEmpty()){
            BlockState state = getRenderedBlockState(te);
            CachedBufferer.partial(ModPartials.ANDESITE_MESH,state)

                    .translateY(1.01)
                    .light(light)
                    .renderInto(ms, vb);
        }

        super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    }
}
