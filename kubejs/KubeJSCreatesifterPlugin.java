package com.oierbravo.createsifter.compat.kubejs;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.compat.kubejs.recipe.SiftingKubeRecipe;
import com.oierbravo.createsifter.compat.kubejs.recipe.SiftingRecipeSchema;
import com.oierbravo.createsifter.content.contraptions.components.sifter.recipe.SiftingRecipe;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeFactoryRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
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
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.ITEM, reg -> {
                    reg.add(ModConstants.asResource("mesh").toString(), MeshItemBuilder.class, MeshItemBuilder::new);
                    reg.add(ModConstants.asResource("advanced_mesh").toString(), AdvancedMeshItemBuilder.class, MeshItemBuilder::new);
        });
    }
}