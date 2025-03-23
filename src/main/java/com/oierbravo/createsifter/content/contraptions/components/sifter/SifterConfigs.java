package com.oierbravo.createsifter.content.contraptions.components.sifter;

import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class SifterConfigs extends ConfigBase {
    private static final int VERSION = 1;

    public final ConfigFloat minimumSpeed = f(1,1,"minimumSpeed", Comments.minimumSpeed);
    public final ConfigInt outputCapacity = i(16,1,"outputCapacity", Comments.outputCapacity);


    private static class Comments {
        static String minimumSpeed = "Minimum required speed.";
        static String outputCapacity = "Output item capacity.";
    }

    @Override
    public @NotNull String getName() {
        return "mechanicalSifterServer.v" + VERSION;
    }

}
