package com.oierbravo.createsifter.content.contraptions.components.sifter.brass;

import com.mojang.blaze3d.vertex.PoseStack;
import com.oierbravo.createsifter.content.contraptions.components.sifter.AbstractSifterRenderer;
import com.oierbravo.createsifter.register.ModPartials;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class BrassSifterRenderer extends AbstractSifterRenderer<BrassSifterBlockEntity> {

    public BrassSifterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected PartialModel getCogModel() {
        return ModPartials.BRASS_SIFTER_COG;
    }

    @Override
    protected void renderSafeInternal(BrassSifterBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        FilteringRenderer.renderOnBlockEntity(be, partialTicks, ms, buffer, light, overlay);
    }
}
