package com.oierbravo.createsifter.foundation.data.recipe;

import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeBuilder;
import com.oierbravo.createsifter.register.ModBlocks;
import com.simibubi.create.foundation.data.recipe.Mods;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class SiftingCompatRecipeGen extends SiftingRecipeGen{
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
                .output(.01f, ResourceLocation.fromNamespaceAndPath("enderio","grains_of_infinity"),1)
                .withCondition(new ModLoadedCondition("enderio"))
                .saveCompat(recipeOutput);
        createBrass("enderio_dust").require(ModBlocks.DUST)
                .output(.05f, ResourceLocation.fromNamespaceAndPath("enderio","grains_of_infinity"),1)
                .withCondition(new ModLoadedCondition("enderio"))
                .saveCompat(recipeOutput);

        createAndesite("ae2_dust").require(ModBlocks.DUST)
                .output(.01f, Mods.AE2,"sky_dust",1)
                .withCondition(new ModLoadedCondition("enderio"))
                .saveCompat(recipeOutput);
        createBrass("ae2_dust").require(ModBlocks.DUST)
                .output(.05f, Mods.AE2,"sky_dust",1)
                .withCondition(new ModLoadedCondition("enderio"))
                .saveCompat(recipeOutput);

        createAndesite("ae2_sand").require(ModBlocks.DUST)
                .output(.05f, Mods.AE2,"certus_quartz_crystal",1)
                .withCondition(new ModLoadedCondition(Mods.AE2.getId()))
                .saveCompat(recipeOutput);
        createBrass("ae2_sand").require(ModBlocks.DUST)
                .output(.1f, Mods.AE2,"certus_quartz_crystal",1)
                .withCondition(new ModLoadedCondition(Mods.AE2.getId()))
                .saveCompat(recipeOutput);
        createAdvancedBrass("ae2_sand").require(ModBlocks.DUST)
                .output(.1f, Mods.AE2,"certus_quartz_crystal",1)
                .output(.005f, Mods.AE2,"charged_certus_quartz_crystal",1)
                .withCondition(new ModLoadedCondition(Mods.AE2.getId()))
                .saveCompat(recipeOutput);

        createString("arch_leaves").require(ItemTags.LEAVES)
                .output(.01f, Mods.ARS_N,"blue_archwood_sapling",1)
                .output(.01f, Mods.ARS_N,"red_archwood_sapling",1)
                .output(.01f, Mods.ARS_N,"green_archwood_sapling",1)
                .output(.01f, Mods.ARS_N,"purple_archwood_sapling",1)
                .withCondition(new ModLoadedCondition(Mods.ARS_N.getId()))
                .saveCompat(recipeOutput);

        createAndesite("arch_leaves").require(ItemTags.LEAVES)
                .output(.1f, Mods.ARS_N,"blue_archwood_sapling",1)
                .output(.1f, Mods.ARS_N,"red_archwood_sapling",1)
                .output(.1f, Mods.ARS_N,"green_archwood_sapling",1)
                .output(.1f, Mods.ARS_N,"purple_archwood_sapling",1)
                .withCondition(new ModLoadedCondition(Mods.ARS_N.getId()))
                .saveCompat(recipeOutput);
    }
}
