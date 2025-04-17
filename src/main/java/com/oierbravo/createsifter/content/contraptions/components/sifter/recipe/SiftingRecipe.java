package com.oierbravo.createsifter.content.contraptions.components.sifter.recipe;

import com.oierbravo.createsifter.content.contraptions.components.meshes.AbstractAdvancedMesh;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.requirements.AdvancedSifterRecipeRequirement;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.requirements.WaterloggedRecipeRequirement;
import com.oierbravo.createsifter.register.ModRecipes;
import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipe;
import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipeParams;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SiftingRecipe extends AbstractMechanicalRecipe<RecipeInput, SiftingRecipe.SiftingRecipeParams> {
    private final Ingredient input;
    private final NonNullList<ProcessingOutput> results;
    private final ItemStack mesh;
    private final int processingTime;
    private final boolean waterlogged;
    private final boolean advancedSifter;

    public SiftingRecipe(SiftingRecipeParams params) {
        super(params);
        input = params.input;
        results = params.results;
        mesh = params.mesh;
        processingTime = params.processingTime;
        waterlogged = params.waterlogged;
        advancedSifter = params.advancedSifter;
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
    public boolean isNotWaterlogged(){
        return !isWaterlogged();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SiftingRecipeSerializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public boolean usesAdvancedMesh() {
        return getMesh().getItem() instanceof AbstractAdvancedMesh;
    }
    public SiftingRecipe addOutput(NonNullList<ProcessingOutput> output){
        this.results.addAll(output);
        return this;
    }
    public static boolean canHandSift(Level world, ModRecipes.SiftingRecipeCacheKey key) {
        return !ModRecipes.findRecipesWithMatchingIngredients(world, key).isEmpty();
    }
    public static List<ItemStack> applyHandSifting(Level world, Vec3 position, ModRecipes.SiftingRecipeCacheKey key) {

        Optional<SiftingRecipe> recipe = ModRecipes.findMergedRecipesWithMatchingIngredients(world, key);

        if(recipe.isPresent()){
            return recipe.get().rollResults();
        }
        return Collections.singletonList(key.input());
    }

    public boolean notRequiresAdvancedMesh() {
        return !usesAdvancedMesh();
    }

    @Override
    public List<IRecipeRequirement> getJeiRecipeRequirements() {
        ArrayList<IRecipeRequirement> extraRequirements = new ArrayList<>();
        if(usesAdvancedMesh())
            extraRequirements.add(new AdvancedSifterRecipeRequirement(true));
        if(isWaterlogged())
            extraRequirements.add(new WaterloggedRecipeRequirement(true));
        return Stream.concat(extraRequirements.stream(), super.getJeiRecipeRequirements().stream()).toList();
    }

    public Boolean requiresAdvancedSifter() {
        if(advancedSifter)
            return true;
        return usesAdvancedMesh();
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
        protected boolean advancedSifter;

        protected SiftingRecipeParams() {
            super();
            input = Ingredient.EMPTY;
            results = NonNullList.create();
            mesh = ItemStack.EMPTY;
            processingTime = 500;
            waterlogged = false;
            advancedSifter = false;
        }

    }
}
