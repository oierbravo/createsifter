package com.oierbravo.createsifter.content.contraptions.components.brasss_sifter;

import net.minecraftforge.common.ForgeConfigSpec;

public class BrassSifterConfig {
    public static ForgeConfigSpec.DoubleValue BRASS_SIFTER_STRESS_IMPACT;
    public static ForgeConfigSpec.DoubleValue BRASS_SIFTER_MINIMUM_SPEED;
    public static ForgeConfigSpec.IntValue BRASS_SIFTER_OUTPUT_CAPACITY;
    public static ForgeConfigSpec.IntValue BRASS_SIFTER_ITEMS_PER_CYCLE;

    public static void registerCommonConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment("Settings for the mechanical brass sifter").push("brass_sifter");
        BRASS_SIFTER_STRESS_IMPACT = COMMON_BUILDER
                .comment("Stress impact")
                .defineInRange("stressImpact", 8.0, 0.0, 64.0);
        BRASS_SIFTER_MINIMUM_SPEED = COMMON_BUILDER
                .comment("Minimum required speed")
                .defineInRange("minimumSpeed", 16, 0.0, 254);
        BRASS_SIFTER_OUTPUT_CAPACITY = COMMON_BUILDER
                .comment("Output item capacity")
                .defineInRange("outputCapacity", 64, 1, Integer.MAX_VALUE);
        BRASS_SIFTER_ITEMS_PER_CYCLE = COMMON_BUILDER
                .comment("Items processed per cycle")
                .defineInRange("outputCapacity", 8, 1, 64);
        COMMON_BUILDER.pop();
    }
}
