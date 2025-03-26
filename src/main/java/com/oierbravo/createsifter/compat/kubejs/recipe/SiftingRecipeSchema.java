package com.oierbravo.createsifter.compat.kubejs.recipe;

import com.google.gson.JsonObject;
import com.oierbravo.createsifter.compat.kubejs.components.ProcessingOutputComponent;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface SiftingRecipeSchema {
    RecipeKey<Ingredient> INPUT = IngredientComponent.NON_EMPTY_INGREDIENT.key("input", ComponentRole.INPUT).noFunctions();
    RecipeKey<List<ProcessingOutput>> OUTPUTS = ProcessingOutputComponent.OUTPUT.asList()
            .key("results", ComponentRole.OUTPUT)
            .noFunctions();
    RecipeKey<Integer> PROCESSING_TIME = NumberComponent.INT.key("processingTime", ComponentRole.OTHER).optional(500);

    RecipeKey<Boolean> WATERLOGGED = BooleanComponent.BOOLEAN.key("waterlogged", ComponentRole.OTHER).optional(false);
    RecipeKey<Boolean> BY_HAND_ONLY = BooleanComponent.BOOLEAN.key("byHandOnly", ComponentRole.OTHER).optional(false);

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUTS, INPUT, PROCESSING_TIME,WATERLOGGED, BY_HAND_ONLY).factory(SiftingKubeRecipe.FACTORY);

}
