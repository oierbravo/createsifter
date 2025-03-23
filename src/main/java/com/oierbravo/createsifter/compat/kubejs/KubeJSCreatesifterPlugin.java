package com.oierbravo.createsifter.compat.kubejs;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;

public class KubeJSCreatesifterPlugin implements KubeJSPlugin {

    /*private static final Map<ModRecipeTypes, RecipeSchema> recipeSchemas = Map.of(
            ModRecipeTypes.SIFTING, SiftingRecipeSchema.SIFTING
    );
    @Override
    public void init() {
        RegistryInfo.ITEM.addType("createsifter:mesh", MeshItemBuilder.class, MeshItemBuilder::new);
        RegistryInfo.ITEM.addType("createsifter:advanced_mesh", AdvancedMeshItemBuilder.class, AdvancedMeshItemBuilder::new);
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {

        for (var sifterRecipeType : ModRecipeTypes.values()) {
            if (sifterRecipeType.getSerializer() instanceof SiftingRecipeSerializer) {
                var schema = recipeSchemas.getOrDefault(sifterRecipeType, SiftingRecipeSchema.SIFTING);
                event.register(sifterRecipeType.getId(), schema);
            }
        }
    }*/
}