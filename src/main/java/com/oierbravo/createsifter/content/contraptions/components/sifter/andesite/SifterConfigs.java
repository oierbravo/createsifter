package com.oierbravo.createsifter.content.contraptions.components.sifter.andesite;

import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class SifterConfigs extends ConfigBase {

    public final ConfigFloat minimumSpeed = f(1,1,"minimumSpeed", Comments.minimumSpeed);
    public final ConfigInt outputCapacity = i(16,1,"outputCapacity", Comments.outputCapacity);


    private static class Comments {
        static String minimumSpeed = "Minimum required speed.";
        static String outputCapacity = "Output item capacity.";
        static String useMeshDurabilityWithSifter = "Use mesh durability when sifting with sifter.";
        static String useMeshDurabilityWithHand = "Use mesh durability when sifting by hand.";
    }

    @Override
    public @NotNull String getName() {
        return "Sifter";
    }

}
