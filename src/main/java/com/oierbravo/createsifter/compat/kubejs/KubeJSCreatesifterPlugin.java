package com.oierbravo.createsifter.compat.kubejs;

import com.oierbravo.createsifter.ModRecipeTypes;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipeSerializer;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;

import java.util.Map;

public class KubeJSCreatesifterPlugin extends KubeJSPlugin {

    private static final Map<ModRecipeTypes, RecipeSchema> recipeSchemas = Map.of(
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
    }
}