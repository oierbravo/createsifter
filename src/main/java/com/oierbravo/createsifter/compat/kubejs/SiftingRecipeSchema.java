package com.oierbravo.createsifter.compat.kubejs;

import com.mojang.datafixers.util.Either;
import dev.latvian.mods.kubejs.create.ProcessingRecipeSchema;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface SiftingRecipeSchema extends ProcessingRecipeSchema{
    RecipeKey<Boolean> WATERLOGGED = BooleanComponent.BOOLEAN.key("waterlogged").optional(false);
    RecipeKey<Float> MINIMUM_SPEED = NumberComponent.FLOAT.key("minimumSpeed").optional(16.f);
    class SiftingRecipeJS extends ProcessingRecipeJS {
        public RecipeJS waterlogged() {
            return setValue(WATERLOGGED, true);
        }

    }
    RecipeSchema SIFTING = new RecipeSchema(SiftingRecipeJS.class, SiftingRecipeJS::new, RESULTS, INGREDIENTS, PROCESSING_TIME, WATERLOGGED);


}
