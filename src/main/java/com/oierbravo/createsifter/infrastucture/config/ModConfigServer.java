package com.oierbravo.createsifter.infrastucture.config;

import com.oierbravo.createsifter.content.contraptions.components.meshes.MeshConfigs;
import com.oierbravo.createsifter.content.contraptions.components.sifter.andesite.SifterConfigs;
import com.oierbravo.createsifter.content.contraptions.components.sifter.brass.BrassSifterConfigs;
import net.createmod.catnip.config.ConfigBase;

public class ModConfigServer extends ConfigBase {
    public final SifterConfigs sifter = nested(0, SifterConfigs::new, "Sifter");
    public final BrassSifterConfigs brassSifter = nested(0, BrassSifterConfigs::new, "Brass Sifter");
    public final MeshConfigs mesh = nested(0, MeshConfigs::new, "Meshes");

    public final ModStress stressValues = nested(1, ModStress::new, "Stress values");

    @Override
    public String getName() {
        return "server";
    }
}
