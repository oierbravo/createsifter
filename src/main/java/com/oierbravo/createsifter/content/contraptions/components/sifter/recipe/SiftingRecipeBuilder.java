package com.oierbravo.createsifter.content.contraptions.components.sifter.recipe;

import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.data.recipe.Mods;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class SiftingRecipeBuilder extends AbstractMechanicalRecipeBuilder<SiftingRecipe, SiftingRecipe.SiftingRecipeParams, SiftingRecipeBuilder> {

    public SiftingRecipeBuilder(){
        super();
    }
    @Override
    public SiftingRecipeBuilder create(ResourceLocation id) {
        params = new SiftingRecipe.SiftingRecipeParams(id);
        return this;
    }
    public SiftingRecipeBuilder(ResourceLocation id){
        this();
        params = new SiftingRecipe.SiftingRecipeParams(id);
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
    @Override
    public SiftingRecipe build() {
        return new SiftingRecipe(this.params);
    }




}
