package com.oierbravo.createsifter.compat.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.recipe.component.RecipeComponentEqualityCheckers;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;

public class RecipeComponents {
    public static final class Input {

        public static final IRecipeComponent<Boolean> BOOLEAN = IRecipeComponent.simple(
                CraftTweakerConstants.rl("simple/boolean"),
                new TypeToken<>() {},
                Object::equals

        );
        public static final IRecipeComponent<Float> FLOAT = IRecipeComponent.simple(
                CraftTweakerConstants.rl("simple/float"),
                new TypeToken<>() {},
                Object::equals
        );

        private Input() {}

    }
}
