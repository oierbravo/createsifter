package com.oierbravo.createsifter.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;

import dev.latvian.mods.kubejs.RegistryObjectBuilderTypes;
import dev.latvian.mods.kubejs.recipe.RegisterRecipeHandlersEvent;

public class KubeJSCreatesifterPlugin extends KubeJSPlugin {
    @Override
    public void init() {
        RegistryObjectBuilderTypes.ITEM.addType("createsifter:mesh", MeshItemBuilder.class, MeshItemBuilder::new);
    }
    @Override
    public void addRecipes(RegisterRecipeHandlersEvent event) {
        event.register("createsifter:sifting", ProcessingRecipeJSNoLiquid::new);
    }
}