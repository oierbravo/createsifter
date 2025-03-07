package com.oierbravo.createsifter.ponders;

import com.oierbravo.createsifter.register.ModBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class ModPonderScenes {

    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(ModBlocks.SIFTER)
                .addStoryBoard("sifter", SifterScenes::sifter);
        HELPER.forComponents(ModBlocks.SIFTER)
                .addStoryBoard("sifter", SifterScenes::sifter);

    }
}
