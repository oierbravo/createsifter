package com.oierbravo.createsifter.compat.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;


import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.resources.ResourceLocation;

public class KubeJSCreatesifterPlugin extends KubeJSPlugin {
    @Override
    public void init() {
        RegistryInfo.ITEM.addType("createsifter:mesh", MeshItemBuilder.class, MeshItemBuilder::new);
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.register(new ResourceLocation("createsifter:sifting"), SifterRecipeSchema.SCHEMA);
    }
}