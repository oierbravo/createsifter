package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import com.oierbravo.createsifter.ponders.PonderScenes;
public class ModPonders {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CreateSifter.MODID);

    public static void register() {

        HELPER.addStoryBoard(ModBlocks.SIFTER, "sifter", PonderScenes::sifter, AllPonderTags.KINETIC_APPLIANCES);
        HELPER.addStoryBoard(ModBlocks.BRASS_SIFTER, "sifter", PonderScenes::sifter, AllPonderTags.KINETIC_APPLIANCES);

        PonderRegistry.TAGS.forTag(AllPonderTags.KINETIC_APPLIANCES)
                .add(ModBlocks.SIFTER);

        PonderRegistry.TAGS.forTag(AllPonderTags.KINETIC_APPLIANCES)
                .add(ModBlocks.BRASS_SIFTER);
    }
}
