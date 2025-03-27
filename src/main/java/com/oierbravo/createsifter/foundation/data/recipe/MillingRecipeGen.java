package com.oierbravo.createsifter.foundation.data.recipe;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.mechanicals.foundation.data.AbstractCreateRecipeGen;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MillingRecipeGen extends AbstractCreateRecipeGen {

    public MillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ModConstants::asResource);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
        createMilling("dust").require(Blocks.SAND).output(ModBlocks.DUST).duration(200).build(pRecipeOutput);
        createMilling("soul_sand").require(Blocks.SOUL_SOIL).output(Blocks.SOUL_SAND).duration(500).build(pRecipeOutput);
        createMilling("crushed_end_stone").require(Blocks.END_STONE).output(ModBlocks.CRUSHED_END_STONE).duration(500).build(pRecipeOutput);
        createMilling("crushed_basalt").require(Blocks.BASALT).output(ModBlocks.CRUSHED_BASALT).duration(500).build(pRecipeOutput);
        createMilling("crushed_netherrack").require(Blocks.NETHERRACK).output(ModBlocks.CRUSHED_NETHERRACK).duration(500).build(pRecipeOutput);
    }

}
