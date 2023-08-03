package com.oierbravo.createsifter.foundation.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterConfig;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipeSerializer;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.SimpleDatagenIngredient;
import com.simibubi.create.foundation.data.recipe.Mods;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Pair;
import com.tterrag.registrate.util.DataIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class is a simplified version of ProcessingRecipeBuilder.
 */
public class SiftingRecipeBuilder {

    protected SiftingRecipeFactory factory;
    protected SiftingRecipeParams params;
    protected List<ICondition> recipeConditions;

    public SiftingRecipeBuilder(SiftingRecipeFactory factory, ResourceLocation recipeId) {
        params = new SiftingRecipeParams(recipeId);
        recipeConditions = new ArrayList<>();
        this.factory = factory;
    }
    public SiftingRecipeBuilder withItemIngredients(Ingredient... ingredients) {
        return withItemIngredients(NonNullList.of(Ingredient.EMPTY, ingredients));
    }

    public SiftingRecipeBuilder withItemIngredients(NonNullList<Ingredient> ingredients) {
        params.ingredients = ingredients;
        return this;
    }

    public SiftingRecipeBuilder withSingleItemOutput(ItemStack output) {
        return withItemOutputs(new ProcessingOutput(output, 1));
    }

    public SiftingRecipeBuilder withItemOutputs(ProcessingOutput... outputs) {
        return withItemOutputs(NonNullList.of(ProcessingOutput.EMPTY, outputs));
    }

    public SiftingRecipeBuilder withItemOutputs(NonNullList<ProcessingOutput> outputs) {
        params.results = outputs;
        return this;
    }

    public SiftingRecipeBuilder duration(int ticks) {
        params.processingDuration = ticks;
        return this;
    }

    public SiftingRecipeBuilder averageProcessingDuration() {
        return duration(100);
    }

    public SiftingRecipeBuilder waterlogged(boolean value) {
        params.waterlogged = value;
        return this;
    }
    public SiftingRecipeBuilder isWaterlogged() {
        params.waterlogged = true;
        return this;
    }
    public SiftingRecipeBuilder minimumSpeed(float speed) {
        params.minimumSpeed = speed;
        return this;
    }
    public SiftingRecipeBuilder require(TagKey<Item> tag) {
        return require(Ingredient.of(tag));
    }

    public SiftingRecipeBuilder require(ItemLike item) {
        return require(Ingredient.of(item));
    }

    public SiftingRecipeBuilder require(Ingredient ingredient) {
        params.ingredients.add(ingredient);
        return this;
    }

    public SiftingRecipeBuilder require(Mods mod, String id) {
        params.ingredients.add(new SimpleDatagenIngredient(mod, id));
        return this;
    }

    public SiftingRecipeBuilder require(ResourceLocation ingredient) {
        params.ingredients.add(DataIngredient.ingredient(null, ingredient));
        return this;
    }

    public SiftingRecipeBuilder output(ItemLike item) {
        return output(item, 1);
    }

    public SiftingRecipeBuilder output(float chance, ItemLike item) {
        return output(chance, item, 1);
    }

    public SiftingRecipeBuilder output(ItemLike item, int amount) {
        return output(1, item, amount);
    }

    public SiftingRecipeBuilder output(float chance, ItemLike item, int amount) {
        return output(chance, new ItemStack(item, amount));
    }

    public SiftingRecipeBuilder output(ItemStack output) {
        return output(1, output);
    }

    public SiftingRecipeBuilder output(float chance, ItemStack output) {
        return output(new ProcessingOutput(output, chance));
    }

    public SiftingRecipeBuilder output(float chance, Mods mod, String id, int amount) {
        return output(new ProcessingOutput(Pair.of(mod.asResource(id), amount), chance));
    }

    public SiftingRecipeBuilder output(float chance, ResourceLocation registryName, int amount) {
        return output(new ProcessingOutput(Pair.of(registryName, amount), chance));
    }

    public SiftingRecipeBuilder output(ProcessingOutput output) {
        params.results.add(output);
        return this;
    }

    public SiftingRecipeBuilder whenModLoaded(String modid) {
        return withCondition(new ModLoadedCondition(modid));
    }

    public SiftingRecipeBuilder whenModMissing(String modid) {
        return withCondition(new NotCondition(new ModLoadedCondition(modid)));
    }

    public SiftingRecipeBuilder withCondition(ICondition condition) {
        recipeConditions.add(condition);
        return this;
    }
    public SiftingRecipe build() {
        return factory.create(params);
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new SiftingRecipeBuilder.DataGenResult(build(), recipeConditions));
    }

    @FunctionalInterface
    public interface SiftingRecipeFactory {
        SiftingRecipe create(SiftingRecipeParams params);
    }

    public static class SiftingRecipeParams extends ProcessingRecipeParams {

        public ResourceLocation id;
        public NonNullList<Ingredient> ingredients;
        public NonNullList<ProcessingOutput> results;
        public int processingDuration;

        public boolean waterlogged;
        public float minimumSpeed;

        protected SiftingRecipeParams(ResourceLocation id) {
            super(id);
            this.id = id;
            ingredients = NonNullList.create();
            results = NonNullList.create();
            processingDuration = 0;
            waterlogged = false;
            minimumSpeed = SifterConfig.SIFTER_MINIMUM_SPEED.get().floatValue();
        }

    }
    public static class DataGenResult implements FinishedRecipe {

        private List<ICondition> recipeConditions;
        private SiftingRecipeSerializer serializer;
        private ResourceLocation id;
        private SiftingRecipe recipe;

        @SuppressWarnings("unchecked")
        public DataGenResult(SiftingRecipe recipe, List<ICondition> recipeConditions) {
            this.recipe = recipe;
            this.recipeConditions = recipeConditions;
            IRecipeTypeInfo recipeType = this.recipe.getTypeInfo();
            ResourceLocation typeId = recipeType.getId();

            if (!(recipeType.getSerializer() instanceof SiftingRecipeSerializer))
                throw new IllegalStateException("Cannot datagen ProcessingRecipe of type: " + typeId);

            this.id = new ResourceLocation(recipe.getId().getNamespace(),
                    typeId.getPath() + "/" + recipe.getId().getPath());
            this.serializer = recipe.getSerializer();
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            serializer.write(json, recipe);
            if (recipeConditions.isEmpty())
                return;

            JsonArray conds = new JsonArray();
            recipeConditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            json.add("conditions", conds);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return serializer;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }
}
