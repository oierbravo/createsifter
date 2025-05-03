package com.oierbravo.createsifter.content.contraptions.components.sifter.recipe;

import com.oierbravo.createsifter.content.contraptions.components.sifter.AbstractSifterBlockEntity;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SiftingRecipeManager {
    public static Optional<SiftingRecipe> getRecipeForSifter(AbstractSifterBlockEntity sifter){
        List<RecipeHolder<SiftingRecipe>> ingredientMatchingRecipes = getRecipesMatchingIngredients(SiftingRecipeInput.fromSifter(sifter), sifter.getLevel());
        List<RecipeHolder<SiftingRecipe>> matchingRecipes = ingredientMatchingRecipes
                .stream()
                .filter(siftingRecipeRecipeHolder -> siftingRecipeRecipeHolder.value().isWaterlogged() == sifter.isWaterlogged())
                .toList();
        if(matchingRecipes.isEmpty())
            return Optional.empty();
        return Optional.of(mergeRecipes(matchingRecipes.stream().map(RecipeHolder::value).toList()));
    }

    public static Optional<SiftingRecipe> getRecipeForHandSifting(Level level, SiftingRecipeInput input, boolean waterlogged){
        List<RecipeHolder<SiftingRecipe>> ingredientMatchingRecipes = getRecipesMatchingIngredients(input, level);
        List<RecipeHolder<SiftingRecipe>> matchingRecipes = ingredientMatchingRecipes
                .stream()
                .filter(siftingRecipeRecipeHolder -> siftingRecipeRecipeHolder.value().isWaterlogged() == waterlogged)
                .toList();
        if(matchingRecipes.isEmpty())
            return Optional.empty();
        return Optional.of(mergeRecipes(matchingRecipes.stream().map(RecipeHolder::value).toList()));
    }

    public static List<RecipeHolder<SiftingRecipe>> getRecipesMatchingIngredients(SiftingRecipeInput input, Level level){
        assert level != null;
        return level.getRecipeManager().getRecipesFor(SiftingRecipe.Type.INSTANCE, input, level
        );
    }
    public static SiftingRecipe mergeRecipes(List<SiftingRecipe> siftingRecipes){
        SiftingRecipeBuilder builder = new SiftingRecipeBuilder();
        for(SiftingRecipe siftingRecipeHolder : siftingRecipes){
            builder.require(siftingRecipeHolder.getInput())
                    .requiredMesh(siftingRecipeHolder.getMesh())
                    .output(siftingRecipeHolder.getResults())
                    .processingTime(siftingRecipeHolder.getProcessingTime())
                    .waterlogged(siftingRecipeHolder.isWaterlogged())
                    .requiresAdvancedSifter(siftingRecipeHolder.advancedSifter());
            for(IRecipeRequirement recipeRequirement : siftingRecipeHolder.getRecipeRequirements()){
                if(!builder.hasRequirement(recipeRequirement.getType()))
                    builder.withRequirement(recipeRequirement);
            }
        }
        return builder.build();
    }
    public static List<RecipeHolder<SiftingRecipe>> getAllHolders() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection())
                .getRecipeManager()
                .getAllRecipesFor(SiftingRecipe.Type.INSTANCE);
    }
}
