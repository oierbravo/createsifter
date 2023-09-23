package com.oierbravo.createsifter.content.contraptions.components.sifter;

import net.minecraftforge.common.ForgeConfigSpec;

public class SifterConfig {
    public static ForgeConfigSpec.DoubleValue SIFTER_STRESS_IMPACT;
    public static ForgeConfigSpec.DoubleValue SIFTER_MINIMUM_SPEED;
    public static ForgeConfigSpec.IntValue SIFTER_OUTPUT_CAPACITY;

    public static ForgeConfigSpec.BooleanValue SIFTER_RENDER_SIFTED_BLOCK;
    public static ForgeConfigSpec.BooleanValue SIFTER_RENDER_MOVING_MESH;
    public static void registerCommonConfig(ForgeConfigSpec.Builder COMMON_BUILDER) {
        COMMON_BUILDER.comment("Settings for the mechanical sifter").push("sifter");
        SIFTER_STRESS_IMPACT = COMMON_BUILDER
                .comment("Stress impact")
                .defineInRange("stressImpact", 4.0, 0.0, 64.0);
        SIFTER_MINIMUM_SPEED = COMMON_BUILDER
                .comment("Minimum required speed")
                .defineInRange("minimumSpeed", 1, 0.0, 254);
        SIFTER_OUTPUT_CAPACITY = COMMON_BUILDER
                .comment("Output item capacity")
                .defineInRange("outputCapacity", 16, 1, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
    }

    public static void registerClientConfig(ForgeConfigSpec.Builder CLIENT_BUILDER){
        CLIENT_BUILDER.comment("Settings for the mechanical sifter").push("sifter");

        SIFTER_RENDER_SIFTED_BLOCK = CLIENT_BUILDER
                .comment("Render sifted block").define("renderSiftedBlock",true);

        SIFTER_RENDER_MOVING_MESH = CLIENT_BUILDER
                .comment("Render moving mesh").define("renderMovingMesh",true);

        CLIENT_BUILDER.pop();
    }
}
