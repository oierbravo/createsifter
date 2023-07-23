package com.oierbravo.createsifter.compat.kubejs;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.TimeComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.item.Item;

public interface SifterRecipeSchema {

    RecipeKey<OutputItem[]> RESULTS = ItemComponents.OUTPUT_ARRAY.key("results");
    RecipeKey<InputItem[]> INGREDIENT = ItemComponents.INPUT_ARRAY.key("ingredients");
    RecipeKey<Long> PROCESSING_TIME = TimeComponent.TICKS.key("processingTime").optional(500L);
    RecipeKey<Boolean> WATERLOGGED = BooleanComponent.BOOLEAN.key("waterlogged").optional(false);

    RecipeSchema SCHEMA = new RecipeSchema(ProcessingRecipeJSNoLiquid.class, ProcessingRecipeJSNoLiquid::new, RESULTS, INGREDIENT, PROCESSING_TIME, WATERLOGGED);
}
