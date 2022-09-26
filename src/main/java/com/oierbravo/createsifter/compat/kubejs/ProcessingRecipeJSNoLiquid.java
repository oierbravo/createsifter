package com.oierbravo.createsifter.compat.kubejs;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.simibubi.create.foundation.fluid.FluidIngredient;

import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.util.ListJS;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class ProcessingRecipeJSNoLiquid extends RecipeJS {
    public final List<FluidIngredient> inputFluids = new ArrayList<>();
    public final List<FluidStackJS> outputFluids = new ArrayList<>();
    public List<ItemStack> outputItems;
    public List<Ingredient> inputIngredients;
    @Override
    public void create(RecipeArguments recipeArguments) {
       //for (var result : ListJS.orSelf(recipeArguments.get(0))) {
            //outputItems.add(parse)
            outputItems = parseItemOutputList(recipeArguments.get(0));

//        }
        inputIngredients = parseItemInputList(recipeArguments.get(1));

        //for (var input : ListJS.orSelf(args.get(1))) {
        //    inputItems.add(parseIngredientItem(input));
        //}

        json.addProperty("processingTime", 100);
    }
    public ProcessingRecipeJSNoLiquid processingTime(int t) {
        json.addProperty("processingTime", t);
        save();
        return this;
    }
    @Override
    public void deserialize() {

        inputIngredients = parseItemInputList(json.get("ingredients"));
        outputItems = parseItemOutputList(json.get("result"));
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
            for (var item : outputItems) {
                jsonOutputs.add(item.toString());
            }

            for (var fluid : outputFluids) {
                jsonOutputs.add(fluid.toJson());
            }
            json.add("results", jsonOutputs);
        }




    }


    @Override
    public boolean hasInput(IngredientMatch ingredientMatch) {
        return false;
    }

    @Override
    public boolean replaceInput(IngredientMatch ingredientMatch, Ingredient ingredient, ItemInputTransformer itemInputTransformer) {
        return false;
    }

    @Override
    public boolean hasOutput(IngredientMatch ingredientMatch) {
        return false;
    }

    @Override
    public boolean replaceOutput(IngredientMatch ingredientMatch, ItemStack itemStack, ItemOutputTransformer itemOutputTransformer) {
        return false;
    }



    /*@Override
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
    }*/
}