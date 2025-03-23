package com.oierbravo.createsifter.infrastucture.config;

import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterConfigsClient;
import net.createmod.catnip.config.ConfigBase;

public class ModConfigClient extends ConfigBase {
    public final SifterConfigsClient sifter = nested(0, SifterConfigsClient::new, "Mechanical Sifter Configs");

    @Override
    public String getName() {
        return "client";
    }
}
