package com.oierbravo.createsifter.compat.jei;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ibm.icu.impl.locale.XCldrStub;
import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.compat.jei.category.SiftingCategory;
import com.oierbravo.createsifter.content.contraptions.components.meshes.IMesh;
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
import org.spongepowered.include.com.google.common.collect.Multimaps;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import java.util.HashMap;
import java.util.List;

import static com.oierbravo.createsifter.register.ModRecipes.generateMergedResourceLocation;

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

    public static List<SiftingRecipe> getRecipesMerged(){
        //HashMap<SiftingRecipeJEICacheKey,List<SiftingRecipe>> groupedRecipes = new HashMap<>();
        //HashMap<Item,List<SiftingRecipe>> groupedRecipes = new HashMap<>();
        ArrayListMultimap<Ingredient,SiftingRecipe> groupedRecipes = ArrayListMultimap.create();
        //Multimap<Item,List<SiftingRecipe>> groupedRecipes = new HashMap<>();

        List<RecipeHolder<SiftingRecipe>> allRecipes = ModRecipes.getAllHolders();
        allRecipes.stream().map(RecipeHolder::value).forEach(siftingRecipe -> {
            ModRecipes.SiftingRecipeJEICacheKey key = new ModRecipes.SiftingRecipeJEICacheKey(siftingRecipe);
            groupedRecipes.put(siftingRecipe.getInput(), siftingRecipe);
            //SiftingRecipeJEICacheKey key = new SiftingRecipeJEICacheKey(siftingRecipe);
            /*if(groupedRecipes.containsKey(siftingRecipe.getMesh().getItem())){
                List<SiftingRecipe> currentGroupRecipes = groupedRecipes.get(siftingRecipe.getMesh().getItem());
                currentGroupRecipes.add(siftingRecipe);
                groupedRecipes.put(siftingRecipe.getMesh().getItem(), currentGroupRecipes);
            } else {
                groupedRecipes.put(siftingRecipe.getMesh().getItem(), List.of(siftingRecipe));
            }*/
        });
        List<SiftingRecipe> mergedRecipes = new java.util.ArrayList<>(List.of());
        /*groupedRecipes.forEach((siftingRecipeCacheKey, siftingRecipes) -> {
            mergedRecipes.add(mergeJEIRecipes( siftingRecipes, false));
        });*/
        for(Ingredient key : groupedRecipes.keySet()){
            ArrayListMultimap<Item,SiftingRecipe> groupedMeshes = ArrayListMultimap.create();

            List<SiftingRecipe> recipes = groupedRecipes.get(key);
            recipes.forEach(siftingRecipe -> {
                groupedMeshes.put(siftingRecipe.getMesh().getItem(),siftingRecipe);
            });
            for(Item mesh : groupedMeshes.keySet()){
                List<SiftingRecipe> meshRecipes = groupedMeshes.get(mesh);

                mergedRecipes.add(mergeJEIRecipes("test", meshRecipes,false));

            }
        }
        return mergedRecipes;
    }
    public static SiftingRecipe mergeJEIRecipes(String id, List<SiftingRecipe> recipes, boolean byHand){
        if(recipes.isEmpty())
            return null;
        recipes.stream().filter(siftingRecipe -> siftingRecipe.isHandOnly() == byHand || !siftingRecipe.requiresAdvancedMesh());

            SiftingRecipeBuilder builder = new SiftingRecipeBuilder(ModConstants.asResource(id));
            recipes.forEach(siftingRecipe ->
                builder.output(siftingRecipe.getResults())
                       .waterlogged(siftingRecipe.isWaterlogged())
                       .requiredMesh(siftingRecipe.getMesh())
                       .require(siftingRecipe.getInput())
        );
        return builder.build();
    }
}
