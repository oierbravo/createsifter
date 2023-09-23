package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterBlock;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterConfig;
import com.oierbravo.createsifter.content.contraptions.components.meshes.CustomMesh;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlock;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterConfig;
import com.oierbravo.createsifter.groups.ModGroup;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.Tags;

import static com.simibubi.create.foundation.data.BlockStateGen.simpleCubeAll;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ModBlocks {


    private static final CreateRegistrate REGISTRATE = CreateSifter.registrate()
            .creativeModeTab(() -> ModGroup.MAIN);


    public static void register() {

    }
    public static final BlockEntry<SifterBlock> SIFTER = REGISTRATE.block("sifter", SifterBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .transform(BlockStressDefaults.setImpact(SifterConfig.SIFTER_STRESS_IMPACT.get()))
            .item()
            .transform(customItemModel())
            .register();
    public static final BlockEntry<BrassSifterBlock> BRASS_SIFTER = REGISTRATE.block("brass_sifter", BrassSifterBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .properties(p -> p.noOcclusion())
            .properties(p -> p.isRedstoneConductor((level, pos, state) -> false))
            .transform(pickaxeOnly())
            .blockstate((c, p) -> BlockStateGen.simpleBlock(c, p, AssetLookup.forPowered(c, p)))
            .transform(BlockStressDefaults.setImpact(BrassSifterConfig.BRASS_SIFTER_MINIMUM_SPEED.get()))
            .item()
            .transform(customItemModel())
            .register();


    public static final BlockEntry<Block> DUST = REGISTRATE.block("dust", Block::new)
            .initialProperties(() ->Blocks.SAND)
            .lang("Dust block")
            .properties(p -> p.color(MaterialColor.SAND))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> CRUSHED_END_STONE = REGISTRATE.block("crushed_end_stone", Block::new)
            .lang("Crushed end stone")
            .initialProperties(() ->Blocks.SAND)
            .properties(p -> p.color(MaterialColor.SAND))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .simpleItem()
            .register();
}
