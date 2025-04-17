package com.oierbravo.createsifter.compat.jei;

import com.google.common.collect.ArrayListMultimap;
import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.compat.jei.category.SiftingCategory;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeBuilder;
import com.oierbravo.createsifter.register.ModRecipes;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class CreateSifterJEI implements IModPlugin {

    private static final ResourceLocation ID = ModConstants.asResource("jei_plugin");

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        CreateRecipeCategory.Factory<SiftingRecipe> factory = SiftingCategory::new;
        CreateRecipeCategory<SiftingRecipe> category = factory.create(SiftingCategory.INFO);

        registration.addRecipeCategories(category);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<SiftingRecipe> recipes = getRecipesMerged();
        registration.addRecipes(SiftingCategory.TYPE, getRecipesMerged());

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        SiftingCategory.INFO.catalysts().forEach(supplier -> registration.addRecipeCatalyst(supplier.get(),SiftingCategory.TYPE));
    }

    public static ArrayListMultimap<Ingredient,SiftingRecipe> getRecipesGroupedByIngredient(){
        ArrayListMultimap<Ingredient,SiftingRecipe> groupedRecipes = ArrayListMultimap.create();

        ModRecipes.getAllHolders().stream().map(RecipeHolder::value).forEach(siftingRecipe -> {
            ModRecipes.SiftingRecipeJEICacheKey key = new ModRecipes.SiftingRecipeJEICacheKey(siftingRecipe);
            groupedRecipes.put(siftingRecipe.getInput(), siftingRecipe);
        });
        return groupedRecipes;
    }
    public static List<SiftingRecipe> getRecipesMerged(){
        ArrayListMultimap<Ingredient,SiftingRecipe> groupedRecipes = getRecipesGroupedByIngredient();

        List<SiftingRecipe> mergedRecipes = new java.util.ArrayList<>(List.of());

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
    public static SiftingRecipe mergeJEIRecipes(String id, List<SiftingRecipe> recipes){
        if(recipes.isEmpty())
            return null;

        SiftingRecipeBuilder builder = new SiftingRecipeBuilder();
            recipes.forEach(siftingRecipe ->
                builder.output(siftingRecipe.getResults())
                        .withId(ModConstants.asResource(id))
                       .waterlogged(siftingRecipe.isWaterlogged())
                       .requiredMesh(siftingRecipe.getMesh())
                       .require(siftingRecipe.getInput())
        );
        return builder.build();
    }
}
