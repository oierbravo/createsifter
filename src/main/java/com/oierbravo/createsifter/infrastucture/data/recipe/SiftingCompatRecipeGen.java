package com.oierbravo.createsifter.infrastucture.data.recipe;

import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeBuilder;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.mechanicals.foundation.data.CompatMods;
import com.simibubi.create.AllItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class SiftingCompatRecipeGen extends SiftingRecipeGen {
    public SiftingCompatRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,
                MODID,
                SiftingRecipe.Type.ID,
                SiftingRecipeBuilder::new,
                "Sifting Compat recipes"
        );
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        createAndesite("enderio_dust").require(ModBlocks.DUST)
                .output(CompatMods.EIO,"grains_of_infinity",.01f,1)
                .forCompat(CompatMods.EIO)
                .saveCompat(recipeOutput);
        createBrass("enderio_dust").require(ModBlocks.DUST)
                .output(CompatMods.EIO,"grains_of_infinity",.05f,1)
                .forCompat(CompatMods.EIO)
                .saveCompat(recipeOutput);

        createAndesite("ae2_dust").require(ModBlocks.DUST)
                .output(CompatMods.AE2,"sky_dust",.01f,1)
                .forCompat(CompatMods.AE2)
                .saveCompat(recipeOutput);
        createBrass("ae2_dust").require(ModBlocks.DUST)
                .output(CompatMods.AE2,"sky_dust",.05f,1)
                .forCompat(CompatMods.AE2)
                .saveCompat(recipeOutput);

        createAndesite("ae2_sand").require(ModBlocks.DUST)
                .output(CompatMods.AE2,"certus_quartz_crystal",.05f,1)
                .forCompat(CompatMods.AE2)
                .saveCompat(recipeOutput);
        createBrass("ae2_sand").require(ModBlocks.DUST)
                .output(CompatMods.AE2,"certus_quartz_crystal",.1f,1)
                .forCompat(CompatMods.AE2)
                .saveCompat(recipeOutput);
        createAdvancedBrass("ae2_sand").require(ModBlocks.DUST)
                .output(CompatMods.AE2,"certus_quartz_crystal",.1f,1)
                .output(CompatMods.AE2,"charged_certus_quartz_crystal",.005f,1)
                .forCompat(CompatMods.AE2)
                .saveCompat(recipeOutput);

        createString("arch_leaves").require(ItemTags.LEAVES)
                .output(CompatMods.ARS_N,"blue_archwood_sapling",.05f,1)
                .output(CompatMods.ARS_N,"red_archwood_sapling",.05f,1)
                .output(CompatMods.ARS_N,"green_archwood_sapling",.05f,1)
                .output(CompatMods.ARS_N,"purple_archwood_sapling",.05f,1)
                .forCompat(CompatMods.ARS_N)
                .saveCompat(recipeOutput);

        createAndesite("arch_leaves").require(ItemTags.LEAVES)
                .output(CompatMods.ARS_N,"blue_archwood_sapling",.1f,1)
                .output(CompatMods.ARS_N,"red_archwood_sapling",.1f,1)
                .output(CompatMods.ARS_N,"green_archwood_sapling",.1f,1)
                .output(CompatMods.ARS_N,"purple_archwood_sapling",.1f,1)
                .forCompat(CompatMods.ARS_N)
                .saveCompat(recipeOutput);

        createString("aa_string_dirt").require(Blocks.DIRT)
                .output(CompatMods.AA, "canola_seeds",0.05f,1)
                .output(CompatMods.AA, "rice_seeds",0.05f,1)
                .output(CompatMods.AA, "flax_seeds",0.05f,1)
                .forCompat(CompatMods.AA)
                .saveCompat(recipeOutput);

        createAndesite("aa_dirt").require(Blocks.DIRT)
                .output(CompatMods.AA, "canola_seeds",0.1f,1)
                .output(CompatMods.AA, "rice_seeds",0.1f,1)
                .output(CompatMods.AA, "flax_seeds",0.1f,1)
                .forCompat(CompatMods.AA)
                .saveCompat(recipeOutput);

        createAndesite("aa_gravel").require(Blocks.GRAVEL)
                .output(CompatMods.AA, "black_quartz",0.1f,1)
                .forCompat(CompatMods.AA)
                .saveCompat(recipeOutput);

        createBrass("aa_gravel").require(Blocks.GRAVEL)
                .output(CompatMods.AA, "black_quartz",0.2f,1)
                .forCompat(CompatMods.AA)
                .saveCompat(recipeOutput);

        createBrass("br_gravel").require(Blocks.GRAVEL)
                .output(0.1f, AllItems.CRUSHED_URANIUM.get(), 1)
                .forCompat(CompatMods.BR)
                .saveCompat(recipeOutput);

        createAdvancedBrass("br_gravel").require(Blocks.GRAVEL)
                .output(0.2f, AllItems.CRUSHED_URANIUM.get(), 1)                .forCompat(CompatMods.BR)
                .saveCompat(recipeOutput);


        createAndesite("fluxnetworks_dust").require(ModBlocks.DUST)
                .output(CompatMods.FN,"flux_dust",.01f,1)
                .forCompat(CompatMods.FN)
                .saveCompat(recipeOutput);
        createBrass("fluxnetworks_dust").require(ModBlocks.DUST)
                .output(CompatMods.FN,"flux_dust",.05f,1)
                .forCompat(CompatMods.FN)
                .saveCompat(recipeOutput);
    }
}
