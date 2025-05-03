package com.oierbravo.createsifter.content.contraptions.components.sifter.brass;

import net.createmod.catnip.config.ConfigBase;
import org.jetbrains.annotations.NotNull;

public class BrassSifterConfigs extends ConfigBase {

    public final ConfigInt outputCapacity = i(16,1,"outputCapacity", Comments.outputCapacity);
    public final ConfigInt itemsPerCycle = i(8,1,"outputCapacity", Comments.itemsPerCycle);


    private static class Comments {
        static String minimumSpeed = "Minimum required speed.";
        static String outputCapacity = "Output item capacity.";
        static String itemsPerCycle = "Items processed per cycle.";
    }

    @Override
    public @NotNull String getName() {
        return "Brass Sifter";
    }

}
