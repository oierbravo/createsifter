package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlock;
import com.oierbravo.createsifter.infrastucture.config.ModStress;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;

import static com.oierbravo.createsifter.CreateSifter.REGISTRATE;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ModBlocks {
    public static final BlockEntry<SifterBlock> SIFTER = REGISTRATE.block("sifter", SifterBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(pickaxeOnly())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
//            .transform(BlockStressDefaults.setImpact(SifterConfig.SIFTER_STRESS_IMPACT.get()))
            .transform(ModStress.setImpact(4.0))
            .item()
            .transform(customItemModel())
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                    .define('W', ItemTags.PLANKS)
                    .define('A', AllBlocks.ANDESITE_CASING)
                    .define('C', AllBlocks.COGWHEEL)
                    .define('P', AllTags.commonItemTag("stone"))
                    .define('S', Items.STICK)
                    .pattern("WAW")
                    .pattern("SCS")
                    .pattern(" P ")
                    .unlockedBy("has_andesite_casing", RegistrateRecipeProvider.has(AllTags.AllItemTags.CASING.tag))
                    .save(p, ModConstants.asResource("crafting/" + c.getName())))
            .register();
    /*public static final BlockEntry<BrassSifterBlock> BRASS_SIFTER = REGISTRATE.block("brass_sifter", BrassSifterBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL))
            .properties(p -> p.noOcclusion())
            .properties(p -> p.isRedstoneConductor((level, pos, state) -> false))
            .transform(pickaxeOnly())
            .transform(ModStress.setImpact(16.0))
            .blockstate((c, p) -> BlockStateGen.simpleBlock(c, p, AssetLookup.forPowered(c, p)))
            //.transform(BlockStressDefaults.setImpact(BrassSifterConfig.BRASS_SIFTER_MINIMUM_SPEED.get()))
            .item()
            .transform(customItemModel())
            .register();*/


    public static final BlockEntry<Block> DUST = REGISTRATE.block("dust", Block::new)
            .initialProperties(() -> Blocks.SAND)
            .lang("Dust block")
            .properties(p -> p.mapColor(MapColor.SAND))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> CRUSHED_END_STONE = REGISTRATE.block("crushed_end_stone", Block::new)
            .lang("Crushed end stone")
            .initialProperties(() -> Blocks.SAND)
            .properties(p -> p.mapColor(MapColor.SAND))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .simpleItem()
            .register();

    public static void register() {}
}
