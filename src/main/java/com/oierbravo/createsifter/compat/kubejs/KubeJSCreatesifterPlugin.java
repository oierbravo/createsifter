package com.oierbravo.createsifter.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;

import dev.latvian.mods.kubejs.recipe.RegisterRecipeTypesEvent;
import net.minecraft.resources.ResourceLocation;

public class KubeJSCreatesifterPlugin extends KubeJSPlugin {

    //@Override
    public void registerRecipeTypes(RegisterRecipeTypesEvent event) {
        event.register(new ResourceLocation("createsifter:sifting"), ProcessingRecipeJSNoLiquid::new);
    }
}