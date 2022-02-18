package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterRenderer;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterTileEntity;
import com.simibubi.create.content.contraptions.components.millstone.MillStoneCogInstance;
import com.simibubi.create.repack.registrate.util.entry.BlockEntityEntry;

public class ModTiles {
    public static final BlockEntityEntry<SifterTileEntity> SIFTER = CreateSifter.registrate()
            .tileEntity("sifter", SifterTileEntity::new)
            .instance(() -> MillStoneCogInstance::new)
            .validBlocks(ModBlocks.SIFTER)
            .renderer(() -> SifterRenderer::new)
            .register();
    public static void register() {}
}