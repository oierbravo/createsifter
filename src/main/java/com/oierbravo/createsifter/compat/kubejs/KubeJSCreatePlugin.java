package com.oierbravo.createsifter.compat.kubejs;

import com.oierbravo.createsifter.ModRecipeTypes;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.create.ProcessingRecipeJS;
import dev.latvian.mods.kubejs.recipe.RegisterRecipeHandlersEvent;

public class KubeJSCreatePlugin extends KubeJSPlugin {

    @Override
    public void addRecipes(RegisterRecipeHandlersEvent event) {

        for (var createRecipeType : ModRecipeTypes.values()) {
            if (createRecipeType.getSerializer() instanceof ProcessingRecipeSerializer) {
                event.register(createRecipeType.getSerializer().getRegistryName(), ProcessingRecipeJS::new);
            }
        }
    }
}