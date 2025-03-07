package com.oierbravo.createsifter.ponders;

import com.oierbravo.createsifter.register.ModBlocks;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.KINETIC_APPLIANCES;
public class ModPonderTags {
    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?>> TAG_HELPER = helper.withKeyFunction(RegistryEntry::getId);
        TAG_HELPER.addToTag(KINETIC_APPLIANCES).add(ModBlocks.SIFTER);
        TAG_HELPER.addToTag(KINETIC_APPLIANCES).add(ModBlocks.BRASS_SIFTER);
    }
}
