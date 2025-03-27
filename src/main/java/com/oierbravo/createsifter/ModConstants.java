package com.oierbravo.createsifter;

import net.minecraft.resources.ResourceLocation;

public class ModConstants {
    public static final String MODID = "createsifter";
    public static final String DISPLAY_NAME = "Create Sifter";
    public static ResourceLocation asResource(String path) {
        return asResource().withPath(path);
    }

    public static ResourceLocation asResource() {
        return ResourceLocation.fromNamespaceAndPath(MODID,"");
    }
}
