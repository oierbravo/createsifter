package com.oierbravo.createsifter.compat.kubejs;

import com.oierbravo.createsifter.ModConstants;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import net.minecraft.core.registries.Registries;

public class KubeJSCreatesifterPlugin implements KubeJSPlugin {


    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.ITEM, reg -> {
                    reg.add(ModConstants.asResource("mesh"), MeshItemBuilder.class, MeshItemBuilder::new);
                    reg.add(ModConstants.asResource("advanced_mesh"), AdvancedMeshItemBuilder.class, MeshItemBuilder::new);
        });
    }

    /*
    private static final Map<ModRecipeTypes, RecipeSchema> recipeSchemas = Map.of(
            ModRecipeTypes.SIFTING, SiftingRecipeSchema.SIFTING
    );

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