package com.oierbravo.createsifter.compat.kubejs.recipe;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;

public class SiftingKubeRecipe extends KubeRecipe {

    public static final KubeRecipeFactory FACTORY = new KubeRecipeFactory(
            ModConstants.asResource(SiftingRecipe.Type.ID),
            SiftingKubeRecipe.class,
            SiftingKubeRecipe::new
    );
}
