package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.content.contraptions.components.meshes.IMesh;
import com.oierbravo.createsifter.content.contraptions.components.sifter.AbstractSifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeBuilder;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeSerializer;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MODID);


    public static final Supplier<RecipeType<SiftingRecipe>> SIFTING_TYPE =
            RECIPE_TYPES.register(
                    "extruding_type",
                    // We need the qualifying generic here due to generics being generics.
                    () -> RecipeType.<SiftingRecipe>simple(ModConstants.asResource("extruding_type"))

        );

    public static final Supplier<SiftingRecipeSerializer> EXTRUDING_SERIALIZER =
            SERIALIZERS.register(SiftingRecipe.Type.ID, () -> SiftingRecipeSerializer.INSTANCE);

    public static void register(IEventBus eventBus) {

        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);

    }

    public static Predicate<RecipeHolder<? extends Recipe<?>>> matchesKey(SiftingRecipeCacheKey key) {

        return recipeHolder -> {
            if(!(recipeHolder.value() instanceof SiftingRecipe siftingRecipe))
                return false;
            if(siftingRecipe.getInput().isEmpty() || !siftingRecipe.getInput().test(key.input))
                return false;
            if(siftingRecipe.getMesh().isEmpty() || !siftingRecipe.getMesh().is(key.mesh.getItem()))
                return false;
            return siftingRecipe.isWaterlogged() == key.waterlogged;
        };
    }
    public static <EXB extends AbstractSifterBlockEntity> List<SiftingRecipe> findRecipesWithMatchingIngredients(EXB sifter){
        return findRecipesWithMatchingIngredients(sifter.getLevel(), sifter.getMeshItemStack(), sifter.getInputItemStack(), sifter.isWaterlogged());
    }
    public static <EXB extends AbstractSifterBlockEntity> Optional<SiftingRecipe> findMergedRecipesWithMatchingIngredients(EXB sifter){
        return findMergedRecipesWithMatchingIngredients(sifter.getLevel(), new SiftingRecipeCacheKey(sifter));
    }
    public static Optional<SiftingRecipe> findMergedRecipesWithMatchingIngredients(Level level, SiftingRecipeCacheKey key){
        return Optional.ofNullable(mergeRecipes(key, findRecipesWithMatchingIngredients(level,key), false));
    }
    public static Optional<SiftingRecipe> findMergedRecipesWithMatchingIngredients(Level level, SiftingRecipeCacheKey key, boolean  byHand){
        return Optional.ofNullable(mergeRecipes(key, findRecipesWithMatchingIngredients(level,key),byHand));
    }
    public static List<SiftingRecipe> findRecipesWithMatchingIngredients(Level level,SiftingRecipeCacheKey key) {
        List<RecipeHolder<? extends Recipe<?>>> recipes =  RecipeFinder.get(key,level,matchesKey(key));
        return recipes.stream().map(recipeHolder -> (SiftingRecipe) recipeHolder.value()).toList();
    }

    public static List<SiftingRecipe> findRecipesWithMatchingIngredients(Level level, ItemStack mesh, ItemStack input, boolean waterlogged){
        SiftingRecipeCacheKey key = new SiftingRecipeCacheKey(mesh, input, waterlogged);
        return findRecipesWithMatchingIngredients(level, key);
    }
    public record SiftingRecipeCacheKey(ItemStack mesh, ItemStack input, boolean waterlogged) implements ISiftingRecipeCacheKey{
        public SiftingRecipeCacheKey(AbstractSifterBlockEntity sifter){
            this(sifter.getMeshItemStack(), sifter.getInputItemStack(), sifter.isWaterlogged());
        }

        @Override
        public String toString() {
            String name = input.getItem() + "_" + mesh.getItem();
            if(waterlogged)
                name += "_waterlogged";
            return name.replace(":", "_");
        }

        @Override
        public Ingredient getInput() {
            return Ingredient.of(input);
        }

        @Override
        public ItemStack getMesh() {
            return mesh;
        }

        @Override
        public boolean getWaterlogged() {
            return waterlogged;
        }
    }
    public record SiftingRecipeJEICacheKey(ItemStack mesh, Ingredient input, boolean waterlogged) implements ISiftingRecipeCacheKey{
        public SiftingRecipeJEICacheKey(SiftingRecipe recipe){
            this(recipe.getMesh(), recipe.getInput(), recipe.isWaterlogged());
        }



        @Override
        public String toString() {
            String name = input.toString() + "_" + mesh.getItem();
            if(waterlogged)
                name += "_waterlogged";
            return name.replace(":", "_");
        }

        @Override
        public Ingredient getInput() {
            return input;
        }

        @Override
        public ItemStack getMesh() {
            return mesh;
        }

        @Override
        public boolean getWaterlogged() {
            return waterlogged;
        }
    }
    public static interface ISiftingRecipeCacheKey{
        String toString();
        Ingredient getInput();
        ItemStack getMesh();
        boolean getWaterlogged();
    }

    public static List<RecipeHolder<SiftingRecipe>> getAllHolders() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection())
                .getRecipeManager()
                .getAllRecipesFor(SiftingRecipe.Type.INSTANCE);
    }
    public static SiftingRecipe mergeRecipes(ISiftingRecipeCacheKey key, List<SiftingRecipe> recipes, boolean byHand){
        if(recipes.isEmpty())
            return null;
        recipes.stream().filter(siftingRecipe -> siftingRecipe.isHandOnly() == byHand || !siftingRecipe.requiresAdvancedMesh());
        SiftingRecipeBuilder builder = new SiftingRecipeBuilder(generateMergedResourceLocation(key));
        recipes.forEach(siftingRecipe ->
                builder.output(siftingRecipe.getResults())
                        .waterlogged(key.getWaterlogged())
                        .requiredMesh(key.getMesh())
                        .require(key.getInput())
        );
        return builder.build();
    }
    public static ResourceLocation generateMergedResourceLocation(ISiftingRecipeCacheKey key){
        return ModConstants.asResource(key.toString());
    }
    //public static HashMap<SiftingRecipeCacheKey,List<SiftingRecipe>> getRecipesMerged(){

}
