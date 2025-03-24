package com.oierbravo.createsifter.content.contraptions.components.meshes;

import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class MeshConfigs extends ConfigBase {
    private static final int VERSION = 1;

    public final ConfigBase.ConfigBool useMeshDurabilityWithSifter = b(false,"useMeshDurabilityWithSifter", MeshConfigs.Comments.useMeshDurabilityWithSifter);
    public final ConfigBase.ConfigBool useMeshDurabilityWithHand = b(true,"useMeshDurabilityWithHand", MeshConfigs.Comments.useMeshDurabilityWithHand);

    private static class Comments {
        static String useMeshDurabilityWithSifter = "Use mesh durability when sifting with sifter.";
        static String useMeshDurabilityWithHand = "Use mesh durability when sifting by hand.";
    }

    @Override
    public @NotNull String getName() {
        return "Meshes";
    }
}
