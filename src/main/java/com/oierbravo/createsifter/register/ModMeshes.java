package com.oierbravo.createsifter.register;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.oierbravo.createsifter.content.contraptions.components.meshes.BaseMesh;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.Deserializers;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ModMeshes extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = Deserializers.createLootTableSerializer().create();
    private Map<ResourceLocation, BaseMesh> meshes = ImmutableMap.of();
    private static final Map<ResourceLocation, BaseMesh> BUILTIN_TYPE_MAP = new HashMap<>();
    private static final Map<ResourceLocation, BaseMesh> CUSTOM_TYPE_MAP = new HashMap<>();

    public ModMeshes() {
        super(GSON, "meshes");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {

    }
}
