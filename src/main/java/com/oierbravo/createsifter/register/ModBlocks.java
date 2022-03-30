package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlock;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.repack.registrate.util.entry.BlockEntry;

import static com.simibubi.create.AllTags.pickaxeOnly;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class ModBlocks {
    //private static final CreateRegistrate REGISTRATE = Create.registrate()
    //        .creativeModeTab(() -> Create.BASE_CREATIVE_TAB);
    private static final CreateRegistrate REGISTRATE = CreateSifter.registrate()
            .creativeModeTab(() -> ModItems.itemGroup);

    public static void register() {

        Create.registrate().addToSection(SIFTER, AllSections.KINETICS);
    }
    public static final BlockEntry<SifterBlock> SIFTER = REGISTRATE.block("sifter", SifterBlock::new)
            .initialProperties(SharedProperties::stone)
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .transform(BlockStressDefaults.setImpact(4.0))
            .item()
            .transform(customItemModel())
            .register();
}
