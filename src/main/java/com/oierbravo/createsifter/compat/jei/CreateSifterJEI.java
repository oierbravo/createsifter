package com.oierbravo.createsifter.compat.jei;

import com.google.common.base.Predicates;
import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ModRecipeTypes;
import com.oierbravo.createsifter.compat.jei.category.SiftingCategory;
import com.oierbravo.createsifter.register.ModBlocks;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.config.CRecipes;
import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@JeiPlugin
@SuppressWarnings("unused")
public class CreateSifterJEI implements IModPlugin {

    private static final ResourceLocation ID = CreateSifter.asResource("jei_plugin");

    public IIngredientManager ingredientManager;
    private final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();
    private final CreateRecipeCategory<?> sifting = register("sifting", SiftingCategory::new).recipes(ModRecipeTypes.SIFTING)
            .catalyst(ModBlocks.SIFTER::get)
            .build();

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }
    private <T extends Recipe<?>> CategoryBuilder<T> register(String name,
                                                              Supplier<CreateRecipeCategory<T>> supplier) {
        return new CategoryBuilder<T>(name, supplier);
    }
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        allCategories.forEach(registration::addRecipeCategories);
    }
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        allCategories.forEach(c -> c.recipes.forEach(s -> registration.addRecipes(s.get(), c.getUid())));

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.recipeCatalysts.forEach(s -> registration.addRecipeCatalyst(s.get(), c.getUid())));
    }

    private class CategoryBuilder<T extends Recipe<?>> {
        private CreateRecipeCategory<T> category;
        private List<Consumer<List<Recipe<?>>>> recipeListConsumers = new ArrayList<>();
        private Predicate<CRecipes> pred;

        public CategoryBuilder(String name, Supplier<CreateRecipeCategory<T>> category) {
            this.category = category.get();
            this.category.setCategoryId(name);
            pred = Predicates.alwaysTrue();
        }

        public CategoryBuilder<T> recipes(IRecipeTypeInfo recipeTypeEntry) {
            return recipes(recipeTypeEntry::getType);
        }

        public CategoryBuilder<T> recipes(Supplier<RecipeType<? extends T>> recipeType) {
            return recipes(r -> r.getType() == recipeType.get());
        }

        public CategoryBuilder<T> recipes(ResourceLocation serializer) {
            return recipes(r -> r.getSerializer()
                    .getRegistryName()
                    .equals(serializer));
        }

        public CategoryBuilder<T> recipes(Predicate<Recipe<?>> pred) {
            return recipeList(() -> findRecipes(pred));
        }

        public CategoryBuilder<T> recipes(Predicate<Recipe<?>> pred, Function<Recipe<?>, T> converter) {
            return recipeList(() -> findRecipes(pred), converter);
        }

        public CategoryBuilder<T> recipeList(Supplier<List<? extends Recipe<?>>> list) {
            return recipeList(list, null);
        }

        public CategoryBuilder<T> recipeList(Supplier<List<? extends Recipe<?>>> list,
                                             Function<Recipe<?>, T> converter) {
            recipeListConsumers.add(recipes -> {
                List<? extends Recipe<?>> toAdd = list.get();
                if (converter != null)
                    toAdd = toAdd.stream()
                            .map(converter)
                            .collect(Collectors.toList());
                recipes.addAll(toAdd);
            });
            return this;
        }

        public CategoryBuilder<T> recipesExcluding(Supplier<RecipeType<? extends T>> recipeType,
                                                   Supplier<RecipeType<? extends T>> excluded) {
            recipeListConsumers.add(recipes -> {
                recipes.addAll(findRecipesByTypeExcluding(recipeType.get(), excluded.get()));
            });
            return this;
        }

        public CategoryBuilder<T> removeRecipes(Supplier<RecipeType<? extends T>> recipeType) {
            recipeListConsumers.add(recipes -> {
                removeRecipesByType(recipes, recipeType.get());
            });
            return this;
        }

        public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
            return catalystStack(() -> new ItemStack(supplier.get()
                    .asItem()));
        }

        public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
            category.recipeCatalysts.add(supplier);
            return this;
        }

        public CategoryBuilder<T> enableWhen(Function<CRecipes, ConfigBase.ConfigBool> configValue) {
            pred = c -> configValue.apply(c)
                    .get();
            return this;
        }

        public CategoryBuilder<T> enableWhenBool(Function<CRecipes, Boolean> configValue) {
            pred = configValue::apply;
            return this;
        }

        public CreateRecipeCategory<T> build() {
            if (pred.test(AllConfigs.SERVER.recipes))
                category.recipes.add(() -> {
                    List<Recipe<?>> recipes = new ArrayList<>();
                    for (Consumer<List<Recipe<?>>> consumer : recipeListConsumers)
                        consumer.accept(recipes);
                    return recipes;
                });
            allCategories.add(category);
            return category;
        }

    }
    public static List<Recipe<?>> findRecipes(Predicate<Recipe<?>> predicate) {
        return Minecraft.getInstance().getConnection().getRecipeManager()
                .getRecipes()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static List<Recipe<?>> findRecipesByType(RecipeType<?> type) {
        return findRecipes(recipe -> recipe.getType() == type);
    }

    public static List<Recipe<?>> findRecipesByTypeExcluding(RecipeType<?> type, RecipeType<?> excludingType) {
        List<Recipe<?>> byType = findRecipesByType(type);
        removeRecipesByType(byType, excludingType);
        return byType;
    }

    public static List<Recipe<?>> findRecipesByTypeExcluding(RecipeType<?> type, RecipeType<?>... excludingTypes) {
        List<Recipe<?>> byType = findRecipesByType(type);
        for (RecipeType<?> excludingType : excludingTypes)
            removeRecipesByType(byType, excludingType);
        return byType;
    }

    public static void removeRecipesByType(List<Recipe<?>> recipes, RecipeType<?> type) {
        List<Recipe<?>> byType = findRecipesByType(type);
        recipes.removeIf(recipe -> {
            for (Recipe<?> r : byType)
                if (doInputsMatch(recipe, r))
                    return true;
            return false;
        });
    }

    public static boolean doInputsMatch(Recipe<?> recipe1, Recipe<?> recipe2) {
        ItemStack[] matchingStacks = recipe1.getIngredients()
                .get(0)
                .getItems();
        if (matchingStacks.length == 0)
            return true;
        if (recipe2.getIngredients()
                .get(0)
                .test(matchingStacks[0]))
            return true;
        return false;
    }
}
