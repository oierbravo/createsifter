package com.oierbravo.createsifter.compat.crafttweaker;

import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler;

@CraftTweakerPlugin("createtweaker:sifter")
public class CTCreateSifterPlugin implements ICraftTweakerPlugin {
    @Override
    public void registerRecipeComponents(final IRecipeComponentRegistrationHandler handler) {

        handler.registerRecipeComponent(RecipeComponents.Input.BOOLEAN);
        handler.registerRecipeComponent(RecipeComponents.Input.FLOAT);
    }
}
