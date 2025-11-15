package com.oierbravo.createsifter.compat.kubejs.recipe;

import com.oierbravo.mechanicals.compat.kubejs.components.ProcessingOutputComponent;
import com.oierbravo.mechanicals.compat.kubejs.components.RecipeRequirementsComponent;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface SiftingRecipeSchema {
    RecipeKey<List<ProcessingOutput>> OUTPUTS = ProcessingOutputComponent.PROCESSING_OUTPUT.instance().asListOrSelf()
            .key("results", ComponentRole.OUTPUT)
            .noFunctions();
    RecipeKey<Ingredient> INPUT = IngredientComponent.INGREDIENT.key("input", ComponentRole.INPUT).noFunctions();
    RecipeKey<ItemStack> MESH = ItemStackComponent.ITEM_STACK.key("mesh", ComponentRole.INPUT).noFunctions();
    RecipeKey<Integer> PROCESSING_TIME = NumberComponent.INT.key("processingTime", ComponentRole.OTHER).optional(500).alwaysWrite();
    RecipeKey<Boolean> ADVANCED_SIFTER = BooleanComponent.BOOLEAN.key("advancedSifter", ComponentRole.OTHER).optional(false);
    RecipeKey<Boolean> WATERLOGGED = BooleanComponent.BOOLEAN.key("waterlogged", ComponentRole.OTHER).optional(false);
    RecipeKey<List<IRecipeRequirement>> RECIPE_REQUIREMENTS = RecipeRequirementsComponent.REQUIREMENT.instance().asListOrSelf().withBounds(IntBounds.OPTIONAL).key("requirements", ComponentRole.OTHER).optional(List.of());


    RecipeSchema SCHEMA = new RecipeSchema(OUTPUTS, INPUT, MESH, PROCESSING_TIME, ADVANCED_SIFTER, WATERLOGGED, RECIPE_REQUIREMENTS).factory(SiftingKubeRecipe.FACTORY);

}
