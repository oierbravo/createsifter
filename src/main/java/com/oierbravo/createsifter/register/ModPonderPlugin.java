package com.oierbravo.createsifter.register;

import org.jetbrains.annotations.NotNull;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ponders.PonderScenes;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class ModPonderPlugin implements PonderPlugin {
    
	@Override
	public @NotNull String getModId() {
		return CreateSifter.MODID;
	}


	@Override
	public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
		 PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
	     HELPER.forComponents(ModBlocks.SIFTER).addStoryBoard("sifter", PonderScenes::sifter);
	}


	@Override
	public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
		 PonderTagRegistrationHelper<RegistryEntry<?>> TAG_HELPER = helper.withKeyFunction(RegistryEntry::getId);
	     TAG_HELPER.addToTag(AllCreatePonderTags.KINETIC_APPLIANCES).add(ModBlocks.SIFTER);
	     TAG_HELPER.addToTag(AllCreatePonderTags.KINETIC_APPLIANCES).add(ModBlocks.BRASS_SIFTER);
	}

   
}
