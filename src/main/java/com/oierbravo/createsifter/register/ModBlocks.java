package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterBlock;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterConfig;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlock;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterConfig;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import com.simibubi.create.infrastructure.config.CStress;
import com.tterrag.registrate.builders.BlockBuilder;
import net.minecraft.resources.ResourceLocation;

import static com.oierbravo.createsifter.CreateSifter.REGISTRATE;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import com.simibubi.create.api.stress.BlockStressValues;

public class ModBlocks {


    static { REGISTRATE.setCreativeTab(ModCreativeTabs.MAIN_TAB); }


    public static void register() {

    }
    


    
    public static final BlockEntry<SifterBlock> SIFTER = REGISTRATE.block("sifter", SifterBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            //.transform(CStress.setImpact(BrassSifterConfig.BRASS_SIFTER_MINIMUM_SPEED.get()))
            .item()
            .transform(customItemModel())
            .register();
    public static final BlockEntry<BrassSifterBlock> BRASS_SIFTER = REGISTRATE.block("brass_sifter", BrassSifterBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL))
            .properties(p -> p.noOcclusion())
            .properties(p -> p.isRedstoneConductor((level, pos, state) -> false))
            .transform(pickaxeOnly())
            .blockstate((c, p) -> BlockStateGen.simpleBlock(c, p, AssetLookup.forPowered(c, p)))
            //.transform(CStress.setImpact(BrassSifterConfig.BRASS_SIFTER_MINIMUM_SPEED.get()))
            .item()
            .transform(customItemModel())
            .register();


    public static final BlockEntry<Block> DUST = REGISTRATE.block("dust", Block::new)
            .initialProperties(() ->Blocks.SAND)
            .lang("Dust block")
            .properties(p -> p.mapColor(MapColor.SAND))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> CRUSHED_END_STONE = REGISTRATE.block("crushed_end_stone", Block::new)
            .lang("Crushed end stone")
            .initialProperties(() ->Blocks.SAND)
            .properties(p -> p.mapColor(MapColor.SAND))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .simpleItem()
            .register();
}
