package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterVisual;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterRenderer;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterRenderer;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterVisual;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
public class ModBlockEntities {
    public static final BlockEntityEntry<SifterBlockEntity> SIFTER = CreateSifter.registrate()
            .blockEntity("sifter", SifterBlockEntity::new)
            .visual(() -> SifterVisual::new)
            .validBlocks(ModBlocks.SIFTER)
            .renderer(() -> SifterRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassSifterBlockEntity> BRASS_SIFTER = CreateSifter.registrate()
            .blockEntity("brass_sifter", BrassSifterBlockEntity::new)
            .visual(() -> BrassSifterVisual::new)
            //.visual(() -> SingleAxisRotatingVisual.of(ModPartials.BRASS_SIFTER_COG), false)
            .validBlocks(ModBlocks.BRASS_SIFTER)
            .renderer(() -> BrassSifterRenderer::new)
            .register();
    public static void register() {}
}