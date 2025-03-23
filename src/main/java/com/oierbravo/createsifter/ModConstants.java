package com.oierbravo.createsifter;

import net.minecraft.resources.ResourceLocation;

public class ModConstants {
    public static final String MODID = "createsifter";
    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
