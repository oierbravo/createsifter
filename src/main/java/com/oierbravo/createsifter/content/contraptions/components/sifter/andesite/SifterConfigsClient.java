package com.oierbravo.createsifter.content.contraptions.components.sifter.andesite;

import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class SifterConfigsClient extends ConfigBase {
    private static final int VERSION = 1;

    public final ConfigBool renderSiftedBlock = b(true,"renderSiftedBlock", Comments.renderSiftedBlock);
    public final ConfigBool renderMovingMesh = b(true,"renderMovingMesh", Comments.renderMovingMesh);

    private static class Comments {
        static String renderSiftedBlock = "Render sifted block.";
        static String renderMovingMesh = "Render moving mesh.";
    }

    @Override
    public @NotNull String getName() {
        return "mechanicalSifterClient.v" + VERSION;
    }

}
