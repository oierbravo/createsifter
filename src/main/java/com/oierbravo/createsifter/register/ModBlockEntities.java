package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.sifter.andesite.SifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.sifter.andesite.SifterRenderer;
import com.oierbravo.createsifter.content.contraptions.components.sifter.andesite.SifterVisual;
import com.oierbravo.createsifter.content.contraptions.components.sifter.brass.BrassSifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.sifter.brass.BrassSifterRenderer;
import com.oierbravo.createsifter.content.contraptions.components.sifter.brass.BrassSifterVisual;
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
            .validBlocks(ModBlocks.BRASS_SIFTER)
            .renderer(() -> BrassSifterRenderer::new)
            .register();
    public static void register() {}
}