package com.oierbravo.createsifter.content.contraptions.components.sifter.andesite;

import com.mojang.blaze3d.vertex.PoseStack;
import com.oierbravo.createsifter.content.contraptions.components.sifter.AbstractSifterRenderer;
import com.oierbravo.createsifter.register.ModPartials;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class SifterRenderer extends AbstractSifterRenderer<SifterBlockEntity> {
    public SifterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected PartialModel getCogModel() {
        return ModPartials.SIFTER_COG;
    }

    @Override
    protected void renderSafeInternal(SifterBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

    }
}
