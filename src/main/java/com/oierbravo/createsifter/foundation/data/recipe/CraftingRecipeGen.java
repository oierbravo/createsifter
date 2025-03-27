package com.oierbravo.createsifter.foundation.data.recipe;

import com.oierbravo.createsifter.register.ModBlocks;
import com.oierbravo.createsifter.register.ModItems;
import com.simibubi.create.AllItems;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CraftingRecipeGen extends RecipeProvider {
    public CraftingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.MYCELIUM,1)
                .requires(Items.BROWN_MUSHROOM,1)
                .requires(Items.COARSE_DIRT,1)
                .unlockedBy("has_coarse_dirt", RegistrateRecipeProvider.has(Items.COARSE_DIRT))
                .save(recipeOutput);
    }
    @Override
    public final String getName() {
        return "Mechanical Sifters's crafting recipes.";
    }
}
