package com.oierbravo.createsifter.content.contraptions.components.sifter.recipe;

import com.oierbravo.createsifter.content.contraptions.components.meshes.AdvancedBaseMesh;
import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipe;
import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipeParams;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SiftingRecipe extends AbstractMechanicalRecipe<RecipeInput, SiftingRecipe.SiftingRecipeParams> {
    private Ingredient input;
    private NonNullList<ProcessingOutput> results;
    private ItemStack mesh;
    private int processingTime;
    private boolean waterlogged;

    public SiftingRecipe(SiftingRecipeParams params) {
        super(params);
        input = params.input;
        results = params.results;
        mesh = params.mesh;
        processingTime = params.processingTime;
        waterlogged = params.waterlogged;
        this.recipeRequirements.addAll(params.recipeRequirements);
    }
    public ResourceLocation getId(){
        return id;
    }
    @Override
    public ArrayList<IRecipeRequirement> getRecipeRequirements() {
        return recipeRequirements;
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return getResultItem(provider);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return getRollableResults().isEmpty() ? ItemStack.EMPTY
                : getRollableResults().getFirst()
                .getStack();
    }

    public List<ProcessingOutput> getRollableResults() {
        return results;
    }

    public List<ItemStack> getRollableResultsAsItemStacks() {
        return getRollableResults().stream()
                .map(ProcessingOutput::getStack)
                .collect(Collectors.toList());
    }

    public List<ItemStack> rollResults() {
        return rollResults(this.getRollableResults());
    }

    public List<ItemStack> rollResults(List<ProcessingOutput> rollableResults) {
        List<ItemStack> results = new ArrayList<>();
        for (int i = 0; i < rollableResults.size(); i++) {
            ProcessingOutput output = rollableResults.get(i);
            //ItemStack stack = i == 0 && forcedResult != null ? forcedResult.get() : output.rollOutput();
            ItemStack stack = output.rollOutput();
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }

    public Ingredient getInput(){
        return input;
    }
    public NonNullList<ProcessingOutput> getResults(){
        return results;
    }
    public ItemStack getMesh(){
        return mesh;
    }
    public int getProcessingTime(){
        return processingTime;
    }
    public boolean isWaterlogged(){
        return waterlogged;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return SiftingRecipeSerializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public boolean requiresAdvancedMesh() {
        return getMesh().getItem() instanceof AdvancedBaseMesh;
    }
    public SiftingRecipe addOutput(NonNullList<ProcessingOutput> output){
        this.results.addAll(output);
        return this;
    }


    public static class Type implements RecipeType<SiftingRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final RecipeType<SiftingRecipe> RECIPE_TYPE = new Type();
        public static final String ID = "sifting";
    }

    public static class SiftingRecipeParams extends AbstractMechanicalRecipeParams {
        protected Ingredient input;
        protected NonNullList<ProcessingOutput> results;
        protected ItemStack mesh;
        protected int processingTime;
        protected boolean waterlogged;

        protected SiftingRecipeParams(ResourceLocation id) {
            super(id);
            input = Ingredient.EMPTY;
            results = NonNullList.create();
            mesh = ItemStack.EMPTY;
            processingTime = 0;
            waterlogged = false;

        }

    }
}
