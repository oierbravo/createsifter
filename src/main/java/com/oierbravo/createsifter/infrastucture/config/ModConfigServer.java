package com.oierbravo.createsifter.infrastucture.config;

import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterConfigs;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterConfigsClient;
import net.createmod.catnip.config.ConfigBase;

public class ModConfigServer extends ConfigBase {
    public final SifterConfigs sifter = nested(0, SifterConfigs::new, "Mechanical Sifter Configs");

    public final ModStress stressValues = nested(1, ModStress::new, "Stress values");

    @Override
    public String getName() {
        return "server";
    }
}
