package com.oierbravo.createsifter.compat.kubejs;

import dev.latvian.mods.kubejs.create.ProcessingRecipeSchema;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface SiftingRecipeSchema extends ProcessingRecipeSchema{
    RecipeKey<Boolean> WATERLOGGED = BooleanComponent.BOOLEAN.key("waterlogged").optional(false);
    class SiftingRecipeJS extends ProcessingRecipeJS {
        public RecipeJS waterlogged() {
            return setValue(WATERLOGGED, true);
        }

    }
    RecipeSchema SIFTING = new RecipeSchema(SiftingRecipeJS.class, SiftingRecipeJS::new, RESULTS, INGREDIENTS, PROCESSING_TIME, WATERLOGGED);


}
