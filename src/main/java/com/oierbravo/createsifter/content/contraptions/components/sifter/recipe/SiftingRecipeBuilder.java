package com.oierbravo.createsifter.content.contraptions.components.sifter.recipe;

import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipeBuilder;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanicals.foundation.recipe.RecipeRequirementType;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.data.SimpleDatagenIngredient;
import com.simibubi.create.foundation.data.recipe.Mods;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public class SiftingRecipeBuilder extends AbstractMechanicalRecipeBuilder<SiftingRecipe, SiftingRecipe.SiftingRecipeParams, SiftingRecipeBuilder> {

    public SiftingRecipeBuilder create() {
        params = new SiftingRecipe.SiftingRecipeParams();
        return this;
    }
    public SiftingRecipeBuilder(){
        params = new SiftingRecipe.SiftingRecipeParams();
    }

    public SiftingRecipeBuilder require(Ingredient ingredient){
        params.input = ingredient;
        return this;
    }
    public SiftingRecipeBuilder require(Block block){
        return require(block.asItem());
    }
    public SiftingRecipeBuilder require(ItemLike item){
        return require(Ingredient.of(item.asItem()));
    }
    public SiftingRecipeBuilder require(ItemStack item){
        return require(Ingredient.of(item));
    }
    public SiftingRecipeBuilder require(Mods mod, String id) {
        return require(new SimpleDatagenIngredient(mod, id).toVanilla());
    }
    public SiftingRecipeBuilder require(ResourceLocation resourceLocation) {
        Item item = BuiltInRegistries.ITEM.get(resourceLocation);
        return require(item);
    }
    public SiftingRecipeBuilder require(TagKey<Item> tag) {
        return require(Ingredient.of(tag));
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
        return output(new ProcessingOutput(mod.asResource(id), amount, chance));
    }

    public SiftingRecipeBuilder output(ResourceLocation id) {
        return output(1, id, 1);
    }

    public SiftingRecipeBuilder output(Mods mod, String id) {
        return output(1, mod.asResource(id), 1);
    }

    public SiftingRecipeBuilder output(float chance, ResourceLocation registryName, int amount) {
        return output(new ProcessingOutput(registryName, amount, chance));
    }

    public SiftingRecipeBuilder output(ProcessingOutput output) {
        params.results.add(output);
        return this;
    }

    public SiftingRecipeBuilder output(List<ProcessingOutput> outputs) {
        params.results.addAll(outputs);
        return this;
    }
    public SiftingRecipeBuilder requiredMesh(ItemStack mesh){
        params.mesh = mesh;
        return this;
    }
    public SiftingRecipeBuilder requiredMesh(ItemLike item) {
        return requiredMesh(new ItemStack(item));
    }
    public SiftingRecipeBuilder processingTime(int processingTime){
        params.processingTime = processingTime;
        return this;
    }

    public SiftingRecipeBuilder waterlogged(boolean waterlogged){
        params.waterlogged = waterlogged;
        return this;
    }
    public SiftingRecipeBuilder isWaterlogged(){
        return waterlogged(true);
    }

    public SiftingRecipeBuilder requiresAdvancedSifter(){
        return requiresAdvancedSifter(true);
    }
    public SiftingRecipeBuilder requiresAdvancedSifter(boolean advancedSifter){
        params.advancedSifter = advancedSifter;
        return this;
    }
    @Override
    public SiftingRecipe build() {
        return new SiftingRecipe(this.params);
    }


    public boolean hasRequirement(RecipeRequirementType<?> type) {
        Optional<IRecipeRequirement> requirement = params.recipeRequirements.stream().filter(iRecipeRequirement -> iRecipeRequirement.getType() == type).findFirst();
        return requirement.isPresent();
    }
}
