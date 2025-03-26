package com.oierbravo.createsifter.compat.kubejs;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.compat.kubejs.components.ProcessingOutputComponent;
import com.oierbravo.createsifter.compat.kubejs.recipe.SiftingKubeRecipe;
import com.oierbravo.createsifter.compat.kubejs.recipe.SiftingRecipeSchema;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeFactoryRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import net.minecraft.core.registries.Registries;

public class KubeJSCreatesifterPlugin implements KubeJSPlugin {
    @Override
    public void registerRecipeFactories(RecipeFactoryRegistry registry) {
        registry.register(SiftingKubeRecipe.FACTORY);
    }
    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(ModConstants.asResource(SiftingRecipe.Type.ID), SiftingRecipeSchema.SCHEMA);
    }
    @Override
    public void registerRecipeComponents(RecipeComponentFactoryRegistry registry) {
        registry.register(ProcessingOutputComponent.OUTPUT);
        //registry.register(ProcessingOutputComponent.UNWRAPPED_OUTPUT_LIST);
    }
    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.ITEM, reg -> {
                    reg.add(ModConstants.asResource("mesh").toString(), MeshItemBuilder.class, MeshItemBuilder::new);
                    //reg.add(ModConstants.asResource("mesh"), MeshItemBuilder.class, MeshItemBuilder::new);
                    reg.add(ModConstants.asResource("advanced_mesh").toString(), AdvancedMeshItemBuilder.class, MeshItemBuilder::new);
                    //reg.add(ModConstants.asResource("advanced_mesh"), AdvancedMeshItemBuilder.class, MeshItemBuilder::new);
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