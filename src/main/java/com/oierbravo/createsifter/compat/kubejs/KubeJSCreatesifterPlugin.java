package com.oierbravo.createsifter.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;

import dev.latvian.mods.kubejs.recipe.RegisterRecipeHandlersEvent;

public class KubeJSCreatesifterPlugin extends KubeJSPlugin {

    @Override
    public void addRecipes(RegisterRecipeHandlersEvent event) {

/*        for (var createRecipeType : ModRecipeTypes.values()) {
            if (createRecipeType.getSerializer() instanceof ProcessingRecipeSerializer) {
                event.register(createRecipeType.getId(), ProcessingRecipeJSNoLiquid::new);
            }
        }*/
        event.register("createsifter:sifting", ProcessingRecipeJSNoLiquid::new);
    }
}