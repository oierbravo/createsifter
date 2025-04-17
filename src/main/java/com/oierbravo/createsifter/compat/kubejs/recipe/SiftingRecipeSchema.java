package com.oierbravo.createsifter.compat.kubejs.recipe;

import com.oierbravo.mechanicals.compat.kubejs.components.ProcessingOutputComponent;
import com.oierbravo.mechanicals.compat.kubejs.components.RecipeRequirementsComponent;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface SiftingRecipeSchema {
    //RecipeKey<List<ProcessingOutput>> OUTPUTS = ProcessingOutputComponent.UNWRAPPED_INGREDIENT_LIST
    RecipeKey<List<ProcessingOutput>> OUTPUTS = ProcessingOutputComponent.OUTPUT.asList()
            .key("results", ComponentRole.OUTPUT)
            .noFunctions();
    RecipeKey<Ingredient> INPUT = IngredientComponent.NON_EMPTY_INGREDIENT.key("input", ComponentRole.INPUT).noFunctions();
    RecipeKey<ItemStack> MESH = ItemStackComponent.ITEM_STACK.key("mesh", ComponentRole.INPUT).noFunctions();
    RecipeKey<Integer> PROCESSING_TIME = NumberComponent.INT.key("processingTime", ComponentRole.OTHER).optional(500).alwaysWrite();
    RecipeKey<Boolean> WATERLOGGED = BooleanComponent.BOOLEAN.key("waterlogged", ComponentRole.OTHER).optional(false);
    RecipeKey<List<IRecipeRequirement>> RECIPE_REQUIREMENTS = RecipeRequirementsComponent.RECIPE_REQUIREMENT.asList().key("requirements", ComponentRole.OTHER).optional(List.of()).allowEmpty();


    RecipeSchema SCHEMA = new RecipeSchema(OUTPUTS, INPUT, MESH, PROCESSING_TIME,WATERLOGGED, RECIPE_REQUIREMENTS).factory(SiftingKubeRecipe.FACTORY);

}
