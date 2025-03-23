package com.oierbravo.createsifter.ponders;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ModConstants;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.oierbravo.createsifter.ModConstants.MODID;

public class ModPonderPlugin implements PonderPlugin {

	@Override
	public @NotNull String getModId() {
		return MODID;
	}

	@Override
	public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
		ModPonderScenes.register(helper);
	}

	@Override
	public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
		ModPonderTags.register(helper);
	}


}
