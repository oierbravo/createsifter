package com.oierbravo.createsifter.compat.kubejs;

import com.google.gson.JsonArray;

import dev.latvian.mods.kubejs.recipe.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

/**
 * @author LatvianModder
 * @author Prunoideae
 */
public class ProcessingRecipeJSNoLiquid extends RecipeJS {

    public List<ItemStack> outputItemStacks;
    public List<Ingredient> inputIngredients;

    @Override
    public void create(RecipeArguments recipeArguments) {
        outputItemStacks = parseItemOutputList(recipeArguments.get(0));
        inputIngredients = parseItemInputList(recipeArguments.get(1));
        json.addProperty("processingTime", 100);
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
    public void deserialize() {
        inputIngredients = parseItemInputList(json.get("ingredients"));
        outputItemStacks = parseItemOutputList(json.get("results"));
    }

    @Override
    public void serialize() {
        if (serializeInputs) {
            var jsonIngredients = new JsonArray();
            for (var inputIngredient : inputIngredients) {
                jsonIngredients.add(inputIngredient.toJson());
            }
            json.add("ingredients", jsonIngredients);
        }


        if (serializeOutputs) {
            var jsonOutputs = new JsonArray();
            for (ItemStack item : outputItemStacks) {
                jsonOutputs.add(itemToJson(item));
            }
            json.add("results", jsonOutputs);
        }
    }

    @Override
    public boolean hasInput(IngredientMatch ingredientMatch) {
        return inputIngredients.stream().anyMatch(ingredientMatch::contains);
    }

    @Override
    public boolean replaceInput(IngredientMatch ingredientMatch, Ingredient ingredient, ItemInputTransformer itemInputTransformer) {
        if (hasInput(ingredientMatch)) {
            for (int i = 0; i < inputIngredients.size(); i++) {
                if (ingredientMatch.contains(inputIngredients.get(i))) {
                    inputIngredients.set(i, itemInputTransformer.transform(this, ingredientMatch, inputIngredients.get(i), ingredient));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasOutput(IngredientMatch ingredientMatch) {
        return outputItemStacks.stream().anyMatch(ingredientMatch::contains);
    }

    @Override
    public boolean replaceOutput(IngredientMatch ingredientMatch, ItemStack itemStack, ItemOutputTransformer itemOutputTransformer) {
        if (hasOutput(ingredientMatch)) {
            for (int i = 0; i < outputItemStacks.size(); i++) {
                if (ingredientMatch.contains(outputItemStacks.get(i))) {
                    outputItemStacks.set(i, itemOutputTransformer.transform(this, ingredientMatch, outputItemStacks.get(i), itemStack));
                }
            }
        }
        return false;
    }
}