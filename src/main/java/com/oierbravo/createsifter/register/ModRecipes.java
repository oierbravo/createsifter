package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipeSerializer;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MODID);


    public static final Supplier<RecipeType<SiftingRecipe>> SIFTING_TYPE =
            RECIPE_TYPES.register(
                    "sifting_type",
                    () -> RecipeType.simple(ModConstants.asResource("sifting_type"))

        );

    public static final Supplier<SiftingRecipeSerializer> SIFTING_SERIALIZER =
            SERIALIZERS.register(SiftingRecipe.Type.ID, () -> SiftingRecipeSerializer.INSTANCE);

    public static void register(IEventBus eventBus) {

        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);

    }

    /*public static Predicate<RecipeHolder<? extends Recipe<?>>> matchesKey(SiftingRecipeCacheKey key) {

        return recipeHolder -> {
            if(!(recipeHolder.value() instanceof SiftingRecipe siftingRecipe))
                return false;
            if(siftingRecipe.getInput().isEmpty() || !siftingRecipe.getInput().test(key.input))
                return false;
            if(siftingRecipe.getMesh().isEmpty() || !siftingRecipe.getMesh().is(key.mesh.getItem()))
                return false;
            if(siftingRecipe.advancedSifter() != key.advancedSifter)
                return false;
            return siftingRecipe.isWaterlogged() == key.waterlogged;
        };
    }*/
    /*public static <EXB extends AbstractSifterBlockEntity> List<SiftingRecipe> findRecipesWithMatchingIngredients(EXB sifter){
        return findRecipesWithMatchingIngredients(sifter.getLevel(), sifter.getMeshItemStack(), sifter.getInputItemStack(), sifter.isAdvancedSifter(), sifter.isWaterlogged());
    }*/
    /*public static <EXB extends AbstractSifterBlockEntity> Optional<SiftingRecipe> findMergedRecipesWithMatchingIngredients(EXB sifter){
        return findMergedRecipesWithMatchingIngredients(sifter.getLevel(), new SiftingRecipeCacheKey(sifter));
    }*/
    /*public static Optional<SiftingRecipe> findMergedRecipesWithMatchingIngredients(Level level, SiftingRecipeCacheKey key){
        return Optional.ofNullable(mergeRecipes(key, findRecipesWithMatchingIngredients(level,key), false));
    }*/
    /*public static List<SiftingRecipe> findRecipesWithMatchingIngredients(Level level,SiftingRecipeCacheKey key) {
        List<RecipeHolder<? extends Recipe<?>>> recipes =  RecipeFinder.get(key,level,matchesKey(key));
        return recipes.stream().map(recipeHolder -> (SiftingRecipe) recipeHolder.value()).toList();
    }*/

    /*public static List<SiftingRecipe> findRecipesWithMatchingIngredients(Level level, ItemStack mesh, ItemStack input, boolean advancedSifter, boolean waterlogged){
        SiftingRecipeCacheKey key = new SiftingRecipeCacheKey(mesh, input, advancedSifter, waterlogged, List.of());
        return findRecipesWithMatchingIngredients(level, key);
    }*/
    /*public record SiftingRecipeCacheKey(ItemStack mesh, ItemStack input, boolean advancedSifter, boolean waterlogged, List<IRecipeRequirement> recipeRequirements) implements ISiftingRecipeCacheKey {
        public SiftingRecipeCacheKey(AbstractSifterBlockEntity sifter){
            this(sifter.getMeshItemStack(), sifter.getInputItemStack(), true, sifter.isWaterlogged(), List.of());
        }

        @Override
        public String toString() {
            String name = input.getItem() + "_" + mesh.getItem();
            if(waterlogged)
                name += "_waterlogged";
            if(advancedSifter)
                name += "_advanced";
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
        @Override
        public boolean getAdvancedSifter() {
            return advancedSifter;
        }

        @Override
        public List<IRecipeRequirement> getRecipeRequirements() {
            return recipeRequirements;
        }
    }*/
    public record SiftingRecipeJEICacheKey(ItemStack mesh, Ingredient input, boolean waterlogged, boolean advancedSifter, List<IRecipeRequirement> recipeRequirements) implements ISiftingRecipeCacheKey{
        public SiftingRecipeJEICacheKey(SiftingRecipe recipe){
            this(recipe.getMesh(), recipe.getInput(), recipe.isWaterlogged(), recipe.advancedSifter(), recipe.getRecipeRequirements());
        }

        @Override
        public String toString() {
            String name = input.toString() + "_" + mesh.getItem();
            if(waterlogged)
                name += "_waterlogged";
            if(advancedSifter)
                name += "_advanced";
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

        @Override
        public boolean getAdvancedSifter() {
            return advancedSifter;
        }

        @Override
        public List<IRecipeRequirement> getRecipeRequirements() {
            return recipeRequirements;
        }
    }
    public interface ISiftingRecipeCacheKey{
        String toString();
        Ingredient getInput();
        ItemStack getMesh();
        boolean getWaterlogged();
        boolean getAdvancedSifter();
        List<IRecipeRequirement> getRecipeRequirements();
    }
}
