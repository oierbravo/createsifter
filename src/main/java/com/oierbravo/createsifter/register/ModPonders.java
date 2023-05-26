package com.oierbravo.createsifter.register;

import com.oierbravo.createmechanicalextruder.CreateMechanicalExtruder;
import com.oierbravo.createmechanicalextruder.ponder.PonderScenes;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;

public class ModPonders {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CreateMechanicalExtruder.MODID);

    public static void register() {

        HELPER.addStoryBoard(ModBlocks.MECHANICAL_EXTRUDER, "mechanical_extruder_basic", PonderScenes::extruderBasic, AllPonderTags.KINETIC_APPLIANCES);

        PonderRegistry.TAGS.forTag(AllPonderTags.KINETIC_APPLIANCES)
                .add(ModBlocks.MECHANICAL_EXTRUDER);
    }
}
