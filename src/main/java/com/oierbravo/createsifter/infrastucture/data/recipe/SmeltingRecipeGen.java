package com.oierbravo.createsifter.infrastucture.data.recipe;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.mechanicals.foundation.recipe.StandardMechanicalRecipeProvider;
import com.simibubi.create.AllItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class SmeltingRecipeGen extends StandardMechanicalRecipeProvider {



    public SmeltingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ModConstants.MODID);
    }

    public SmeltingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String defaultNamespace) {
        super(output, registries, defaultNamespace);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        create(ResourceLocation.fromNamespaceAndPath("bigreactors","yellorium_ingot"))
                .whenModLoaded("bigreactors")
                .viaCooking(() -> AllItems.CRUSHED_URANIUM)
                .inBlastFurnace()
                .register(recipeOutput);
    }
    @Override
    public final String getName() {
        return "Mechanical Sifters's smelting recipes.";
    }
}
