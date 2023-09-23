package com.oierbravo.createsifter.compat.kubejs;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface SiftingRecipeSchema {
    RecipeKey<InputItem[]> INGREDIENTS = ItemComponents.INPUT_ARRAY.key("ingredients");
    RecipeKey<OutputItem[]> RESULTS = ItemComponents.OUTPUT_ARRAY.key("results");

    RecipeKey<Long> PROCESSING_TIME_REQUIRED = TimeComponent.TICKS.key("processingTime").optional(100L).alwaysWrite();

    RecipeKey<Boolean> WATERLOGGED = BooleanComponent.BOOLEAN.key("waterlogged").optional(false);
    RecipeKey<Float> MINIMUM_SPEED = NumberComponent.FLOAT.key("minimumSpeed").optional(1.0f);
    class SiftingRecipeJS extends RecipeJS {
        @Override
        public OutputItem readOutputItem(Object from) {
            if (from instanceof ProcessingOutput output) {
                return OutputItem.of(output.getStack(), output.getChance());
            } else {
                var outputItem = super.readOutputItem(from);
                if (from instanceof JsonObject j && j.has("chance")) {
                    return outputItem.withChance(j.get("chance").getAsFloat());
                }
                return outputItem;
            }
        }
        public RecipeJS waterlogged() {
            return setValue(WATERLOGGED, true);
        }

    }
    RecipeSchema SIFTING = new RecipeSchema(SiftingRecipeJS.class, SiftingRecipeJS::new, RESULTS, INGREDIENTS, PROCESSING_TIME_REQUIRED, WATERLOGGED, MINIMUM_SPEED);


}
