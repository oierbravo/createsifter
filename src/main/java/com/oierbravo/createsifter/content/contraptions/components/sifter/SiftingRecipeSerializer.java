package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.oierbravo.createsifter.foundation.data.recipe.SiftingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SiftingRecipeSerializer implements RecipeSerializer<SiftingRecipe> {
    private final SiftingRecipeBuilder.SiftingRecipeFactory  factory;

    public SiftingRecipeSerializer(SiftingRecipeBuilder.SiftingRecipeFactory factory) {
        this.factory = factory;
    }

    protected void writeToJson(JsonObject json, SiftingRecipe recipe) {
        JsonArray jsonIngredients = new JsonArray();
        JsonArray jsonOutputs = new JsonArray();

        recipe.getIngredients().forEach(i -> jsonIngredients.add(i.toJson()));

        recipe.results.forEach(o -> jsonOutputs.add(o.serialize()));

        json.add("ingredients", jsonIngredients);
        json.add("results", jsonOutputs);

        int processingDuration = recipe.getProcessingDuration();
        if (processingDuration > 0)
            json.addProperty("processingTime", processingDuration);

        recipe.writeAdditional(json);
    }

    protected SiftingRecipe readFromJson(ResourceLocation recipeId, JsonObject json) {
        SiftingRecipeBuilder builder = new SiftingRecipeBuilder(factory, recipeId);
        NonNullList<Ingredient> ingredients = NonNullList.create();
        NonNullList<ProcessingOutput> results = NonNullList.create();

        for (JsonElement je : GsonHelper.getAsJsonArray(json, "ingredients")) {
            ingredients.add(Ingredient.fromJson(je));
        }

        for (JsonElement je : GsonHelper.getAsJsonArray(json, "results")) {
            JsonObject jsonObject = je.getAsJsonObject();
            results.add(ProcessingOutput.deserialize(je));
        }

        builder.withItemIngredients(ingredients)
                .withItemOutputs(results);

        if (GsonHelper.isValidNode(json, "processingTime"))
            builder.duration(GsonHelper.getAsInt(json, "processingTime"));

        SiftingRecipe recipe = builder.build();
        recipe.readAdditional(json);
        return recipe;
    }

    protected void writeToBuffer(FriendlyByteBuf buffer, SiftingRecipe recipe) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        NonNullList<ProcessingOutput> outputs = recipe.results;

        buffer.writeVarInt(ingredients.size());
        ingredients.forEach(i -> i.toNetwork(buffer));

        buffer.writeVarInt(outputs.size());
        outputs.forEach(o -> o.write(buffer));

        buffer.writeVarInt(recipe.getProcessingDuration());

        recipe.writeAdditional(buffer);
    }

    protected SiftingRecipe readFromBuffer(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        NonNullList<ProcessingOutput> results = NonNullList.create();

        int size = buffer.readVarInt();
        for (int i = 0; i < size; i++)
            ingredients.add(Ingredient.fromNetwork(buffer));


        size = buffer.readVarInt();
        for (int i = 0; i < size; i++)
            results.add(ProcessingOutput.read(buffer));


        SiftingRecipe recipe = new SiftingRecipeBuilder(factory, recipeId).withItemIngredients(ingredients)
                .withItemOutputs(results)
                .duration(buffer.readVarInt())
                .build();
        recipe.readAdditional(buffer);
        return recipe;
    }

    public final void write(JsonObject json, SiftingRecipe recipe) {
        writeToJson(json, recipe);
    }

    @Override
    public final SiftingRecipe fromJson(ResourceLocation id, JsonObject json) {
        return readFromJson(id, json);
    }

    @Override
    public final void toNetwork(FriendlyByteBuf buffer, SiftingRecipe recipe) {
        writeToBuffer(buffer, recipe);
    }

    @Override
    public final SiftingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        return readFromBuffer(id, buffer);
    }


    public SiftingRecipeBuilder.SiftingRecipeFactory getFactory() {
        return factory;
    }

}
