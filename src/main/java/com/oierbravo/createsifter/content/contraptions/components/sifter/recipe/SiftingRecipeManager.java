package com.oierbravo.createsifter.content.contraptions.components.sifter.recipe;

import com.google.common.collect.ArrayListMultimap;
import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.content.contraptions.components.sifter.AbstractSifterBlockEntity;
import com.oierbravo.createsifter.register.ModRecipes;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanicals.utility.MechanicalItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
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

    public static ArrayListMultimap<Ingredient,SiftingRecipe> getRecipesGroupedByIngredient(){
        ArrayListMultimap<Ingredient,SiftingRecipe> groupedRecipes = ArrayListMultimap.create();

        SiftingRecipeManager.getAllHolders().stream().map(RecipeHolder::value).forEach(siftingRecipe -> {
            ModRecipes.SiftingRecipeJEICacheKey key = new ModRecipes.SiftingRecipeJEICacheKey(siftingRecipe);
            groupedRecipes.put(siftingRecipe.getInput(), siftingRecipe);
        });
        return groupedRecipes;
    }
    public static List<RecipeHolder<SiftingRecipe>> getRecipesMerged(){
        ArrayListMultimap<Ingredient,SiftingRecipe> groupedRecipes = getRecipesGroupedByIngredient();

        List<RecipeHolder<SiftingRecipe>> mergedRecipes = new java.util.ArrayList<>(List.of());

        for(Ingredient key : groupedRecipes.keySet()){
            ArrayListMultimap<Item,SiftingRecipe> groupedMeshes = ArrayListMultimap.create();

            List<SiftingRecipe> recipes = groupedRecipes.get(key);
            recipes.forEach(siftingRecipe -> {
                groupedMeshes.put(siftingRecipe.getMesh().getItem(),siftingRecipe);
            });
            for(Item mesh : groupedMeshes.keySet()){
                List<SiftingRecipe> meshRecipes = groupedMeshes.get(mesh);



                List<SiftingRecipe> baseRecipes =  meshRecipes.stream().filter(SiftingRecipe::isNotWaterlogged).toList();
                if(!baseRecipes.isEmpty())
                    mergedRecipes.add(mergeJEIRecipes("merged", baseRecipes));

                List<SiftingRecipe> waterloggedRecipes =  meshRecipes.stream().filter(SiftingRecipe::isWaterlogged).toList();
                if(!waterloggedRecipes.isEmpty())
                    mergedRecipes.add(mergeJEIRecipes("merged_waterlogged", waterloggedRecipes));

            }
        }
        return mergedRecipes;
    }
    public static RecipeHolder<SiftingRecipe> mergeJEIRecipes(String id, List<SiftingRecipe> recipes){
        if(recipes.isEmpty())
            return null;
        SiftingRecipeManager.mergeRecipes(recipes);

        SiftingRecipeBuilder builder = new SiftingRecipeBuilder();
        recipes.forEach(siftingRecipe -> {
                    builder.output(siftingRecipe.getResults())
                            .requiresAdvancedSifter(siftingRecipe.advancedSifter())
                            .waterlogged(siftingRecipe.isWaterlogged())
                            .requiredMesh(siftingRecipe.getMesh())
                            .require(siftingRecipe.getInput());
                    if(!siftingRecipe.getJeiRecipeRequirements().isEmpty())
                        builder.withRequirements(siftingRecipe.getRecipeRequirements());

                }
        );
        SiftingRecipe resultRecipe = builder.build();
        return new RecipeHolder<>(generateResourceLocation(resultRecipe, id),resultRecipe);
    }
    private static ResourceLocation generateResourceLocation(SiftingRecipe siftingRecipe, String suffix){
        String meshPath = MechanicalItemStackUtils.getResorceLocation(siftingRecipe.getMesh()).getPath();
        String inputPath = MechanicalItemStackUtils.getResorceLocation(siftingRecipe.getInput()).getPath();
        return ModConstants.asResource().withPath(meshPath + "_" + inputPath + "_" + suffix);
    }
}
