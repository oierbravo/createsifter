package com.oierbravo.createsifter.register;

import com.jozufozu.flywheel.core.PartialModel;
import com.oierbravo.createsifter.CreateSifter;
import com.simibubi.create.Create;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModPartials {
    public static final PartialModel
        STRING_MESH = block("meshes/string_mesh"),
            ANDESITE_MESH = block("meshes/andesite_mesh"),
            ZINC_MESH = block("meshes/zinc_mesh"),
            BRASS_MESH = block("meshes/brass_mesh");


    /* From AllBlockPartials */
    private static PartialModel block(String path) {
        return new PartialModel(CreateSifter.asResource("block/" + path));
    }
    public static PartialModel getFromItemStack(ItemStack itemStack){
        String itemRegistryName = itemStack.getItem().getRegistryName().toString();
        return switch (itemRegistryName) {
            case "createsifter:string_mesh" -> STRING_MESH;
            case "createsifter:andesite_mesh" -> ANDESITE_MESH;
            case "createsifter:zinc_mesh" -> ZINC_MESH;
            case "createsifter:brass_mesh" -> BRASS_MESH;
            default -> ANDESITE_MESH;
        };
    }
    public static void load() {
        // init static fields
    }
}
