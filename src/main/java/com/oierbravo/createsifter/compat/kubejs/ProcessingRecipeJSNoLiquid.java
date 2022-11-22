package com.oierbravo.createsifter.compat.kubejs;
import com.google.gson.JsonArray;
import com.simibubi.create.foundation.fluid.FluidIngredient;

import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.util.ListJS;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class ProcessingRecipeJSNoLiquid extends RecipeJS {
    public final List<FluidIngredient> inputFluids = new ArrayList<>();
    public final List<FluidStackJS> outputFluids = new ArrayList<>();

    @Override
    public void create(ListJS args) {
        for (var result : ListJS.orSelf(args.get(0))) {

                outputItems.add(parseResultItem(result));

        }

        for (var input : ListJS.orSelf(args.get(1))) {
                inputItems.add(parseIngredientItem(input));
        }

        json.addProperty("processingTime", 100);
    }

    @Override
    public void deserialize() {
        for (var ingredient : json.get("ingredients").getAsJsonArray()) {

                inputItems.add(parseIngredientItem(ingredient));

        }

        for (var result : json.get("results").getAsJsonArray()) {
            var resultJson = result.getAsJsonObject();

                outputItems.add(parseResultItem(result));

        }
    }

    public ProcessingRecipeJSNoLiquid processingTime(int t) {
        json.addProperty("processingTime", t);
        save();
        return this;
    }
    public ProcessingRecipeJSNoLiquid waterlogged() {
        json.addProperty("waterlogged", true);
        save();
        return this;
    }

    @Override
    public void serialize() {
        var jsonIngredients = new JsonArray();
        var jsonOutputs = new JsonArray();

        for (var inputStack : inputItems) {
            for (var ingredient : inputStack.unwrapStackIngredient()) {
                jsonIngredients.add(ingredient.toJson());
            }
        }

        for (var fluid : inputFluids) {
            jsonIngredients.add(fluid.serialize());
        }

        for (var item : outputItems) {
            jsonOutputs.add(item.toResultJson());
        }

        for (var fluid : outputFluids) {
            jsonOutputs.add(fluid.toJson());
        }

        json.add("ingredients", jsonIngredients);
        json.add("results", jsonOutputs);
    }
}