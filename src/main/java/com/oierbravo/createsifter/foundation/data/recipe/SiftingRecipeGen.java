package com.oierbravo.createsifter.foundation.data.recipe;


import com.oierbravo.createsifter.ModRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class SiftingRecipeGen extends ModProcessingRecipes {
    GeneratedRecipe GRAVEL = create("gravel", b -> b.duration(100).require(Blocks.GRAVEL).output(Items.FLINT));
    GeneratedRecipe COBBLE_STONE = create("cobblestone", b ->
            b.duration(100).require(Blocks.DIRT)
            .output(Blocks.COBBLESTONE)
    );
   /* GeneratedRecipe STONE = create(ResourceLocation.tryParse("dirt"), b -> b.duration(100)
            .require(ItemTags.)
            .output(Items.STRING));*/

   /* GeneratedRecipe

            GRANITE = create(() -> Blocks.GRANITE, b -> b.duration(200)
            .output(Blocks.RED_SAND)),

    WOOL = create("wool", b -> b.duration(100)
            .require(ItemTags.WOOL)
            .output(Items.STRING)),

    CLAY = create(() -> Blocks.CLAY, b -> b.duration(50)
            .output(Items.CLAY_BALL, 3)
            .output(.5f, Items.CLAY_BALL)),

    CALCITE = create(() -> Items.CALCITE, b -> b.duration(250)
            .output(.125f, Items.BONE_MEAL, 1)),
            DRIPSTONE = create(() -> Items.DRIPSTONE_BLOCK, b -> b.duration(250)
                    .output(Items.CLAY_BALL, 1)),

    TERRACOTTA = create(() -> Blocks.TERRACOTTA, b -> b.duration(200)
            .output(Blocks.RED_SAND)),
            ANDESITE = create(() -> Blocks.ANDESITE, b -> b.duration(200)
                    .output(Blocks.COBBLESTONE)),
            COBBLESTONE = create(() -> Blocks.COBBLESTONE, b -> b.duration(250)
                    .output(Blocks.GRAVEL)),
            GRAVEL = create(() -> Blocks.GRAVEL, b -> b.duration(250)
                    .output(Items.FLINT)),
            SANDSTONE = create(() -> Blocks.SANDSTONE, b -> b.duration(150)
                    .output(Blocks.SAND)),

    WHEAT = create(() -> Items.WHEAT, b -> b.duration(150)
            .output(AllItems.WHEAT_FLOUR.get())
            .output(.25f, AllItems.WHEAT_FLOUR.get(), 2)
            .output(.25f, Items.WHEAT_SEEDS)),

    */

    public SiftingRecipeGen(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected ModRecipeTypes getRecipeType() {
        return ModRecipeTypes.SIFTING;
    }

}
