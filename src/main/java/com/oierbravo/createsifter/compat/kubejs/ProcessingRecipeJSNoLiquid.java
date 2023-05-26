package com.oierbravo.createsifter.compat.kubejs;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonObject;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.kubejs.util.ListJS;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class ProcessingRecipeJSNoLiquid extends RecipeJS {
    public List<ItemStack> outputItems;
    public List<ProcessingOutput> processingOutputs;
    public List<Ingredient> inputIngredients;
    @Override
    public void create(RecipeArguments recipeArguments) {
       //for (var result : ListJS.orSelf(recipeArguments.get(0))) {
            //outputItems.add(parse)
            //outputItems = parseItemOutputList(recipeArguments.get(0));
            processingOutputs = parseProcessingOutputList(recipeArguments.get(0));
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

    public ProcessingRecipeJSNoLiquid waterlogged() {
        json.addProperty("waterlogged", true);
        save();
        return this;
    }


    @Override
    public void deserialize() {

        inputIngredients = parseItemInputList(json.get("ingredients"));
        processingOutputs = parseProcessingOutputList(json.get("results"));
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
            for (ProcessingOutput item : processingOutputs) {
                jsonOutputs.add(item.serialize());
            }
            json.add("results", jsonOutputs);
        }




    }
    public List<ProcessingOutput> parseProcessingOutputList(@Nullable Object o) {
        List<ProcessingOutput> list = new ArrayList<>();

        if (o instanceof JsonElement elem) {
            var array = elem instanceof JsonArray arr ? arr : Util.make(new JsonArray(), (arr) -> arr.add(elem));
            for (var e : array) {
                list.add(parseProcessingOutput(e));
            }
        } else {
            for (var o1 : ListJS.orSelf(o)) {
                list.add(parseProcessingOutput(o1));
            }
        }

        return list;
    }
    public ProcessingOutput parseProcessingOutput(@Nullable Object o) {
        ProcessingOutput result = ProcessingOutput.EMPTY;
        if(o instanceof JsonObject jsonObject) {
            JsonElement test =  jsonObject.get("chance");
            float chance = 1.0F;
            if(jsonObject.has("chance"))
                chance = jsonObject.get("chance").getAsFloat();
            result = new ProcessingOutput(ItemStackJS.of(o), chance);

        }

        if (result.getStack().isEmpty()) {
            throw new RecipeExceptionJS(o + " is not a valid result!");
        }

        return result;
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