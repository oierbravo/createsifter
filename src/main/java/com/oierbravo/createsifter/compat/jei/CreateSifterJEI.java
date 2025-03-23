package com.oierbravo.createsifter.compat.jei;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.compat.jei.category.SiftingCategory;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

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
        registration.addRecipes(SiftingCategory.TYPE,SiftingCategory.INFO.recipes().get().stream().map(RecipeHolder::value).toList());

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        SiftingCategory.INFO.catalysts().forEach(supplier -> registration.addRecipeCatalyst(supplier.get(),SiftingCategory.TYPE));
    }
}
