package com.oierbravo.createsifter.register.config;

import com.oierbravo.createsifter.CreateSifter;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.block.BlockStressValues;
import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModServerConfig extends ModConfigs.Config {
    public static class StressValues extends ModConfigs.Config implements BlockStressValues.IStressValueProvider {
        protected Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> impacts = new HashMap<>();
        protected Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> capacities = new HashMap<>();

        @Override
        protected void registerAll(ForgeConfigSpec.Builder builder) {
            builder.push("impact");
            BlockStressDefaults.DEFAULT_IMPACTS.forEach((r, i) -> {
                if (r.getNamespace().equals(CreateSifter.MODID)) {
                    impacts.put(r, builder.define(r.getPath(), i));
                }
            });
            builder.pop().push("capacities");
            BlockStressDefaults.DEFAULT_CAPACITIES.forEach((r, i) -> {
                if (r.getNamespace().equals(CreateSifter.MODID)) {
                    capacities.put(r, builder.define(r.getPath(), i));
                }
            });
            builder.pop();
            BlockStressValues.registerProvider(CreateSifter.MODID, this);
            super.registerAll(builder);
        }

        @Override
        public double getImpact(Block block) {
            ForgeConfigSpec.ConfigValue<Double> c = impacts.get(block.getRegistryName());
            return c == null ? 0d : c.get();
        }

        @Override
        public double getCapacity(Block block) {
            ForgeConfigSpec.ConfigValue<Double> c = capacities.get(block.getRegistryName());
            return c == null ? 0d : c.get();
        }

        @Override
        public boolean hasImpact(Block block) {
            return impacts.containsKey(block.getRegistryName());
        }

        @Override
        public boolean hasCapacity(Block block) {
            return capacities.containsKey(block.getRegistryName());
        }

        @Nullable
        @Override
        public Couple<Integer> getGeneratedRPM(Block block) {
            return null;
        }
    }

    public StressValues stressValues = nested(0, StressValues::new);
}
