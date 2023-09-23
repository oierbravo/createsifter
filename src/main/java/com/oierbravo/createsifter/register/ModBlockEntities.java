package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterInstance;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterRenderer;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterInstance;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.simibubi.create.content.kinetics.millstone.MillstoneCogInstance;
public class ModBlockEntities {
    public static final BlockEntityEntry<SifterBlockEntity> SIFTER = CreateSifter.registrate()
            .blockEntity("sifter", SifterBlockEntity::new)
            .instance(() -> SifterInstance::new)
            .validBlocks(ModBlocks.SIFTER)
            .renderer(() -> SifterRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassSifterBlockEntity> BRASS_SIFTER = CreateSifter.registrate()
            .blockEntity("brass_sifter", BrassSifterBlockEntity::new)
            .instance(() -> BrassSifterInstance::new)
            .validBlocks(ModBlocks.BRASS_SIFTER)
            .renderer(() -> BrassSifterRenderer::new)
            .register();
    public static void register() {}
}