package com.oierbravo.createsifter.ponders;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.register.ModBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.oierbravo.createsifter.ModConstants.MODID;
import static com.simibubi.create.infrastructure.ponder.AllCreatePonderTags.KINETIC_APPLIANCES;

public class ModPonderPlugin implements PonderPlugin {

	@Override
	public @NotNull String getModId() {
		return MODID;
	}

	@Override
	public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
		PonderSceneRegistrationHelper<ItemProviderEntry<?,?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

		HELPER.forComponents(ModBlocks.SIFTER)
				.addStoryBoard("sifter", SifterScenes::sifter);
	}

	@Override
	public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
		PonderTagRegistrationHelper<RegistryEntry<?,?>> TAG_HELPER = helper.withKeyFunction(RegistryEntry::getId);
		TAG_HELPER.addToTag(KINETIC_APPLIANCES).add(ModBlocks.SIFTER);
	}


}
