package com.oierbravo.createsifter.register;

import com.jozufozu.flywheel.core.PartialModel;
import com.oierbravo.createsifter.CreateSifter;
import com.simibubi.create.Create;

public class ModPartials {
    public static final PartialModel
        STRING_MESH = block("meshes/string_mesh"),
            ANDESITE_MESH = block("meshes/andesite_mesh"),
            ZINK_MESH = block("meshes/zink_mesh"),
            BRASS_MESH = block("meshes/brass_mesh");


    /* From AllModPartials */
    private static PartialModel block(String path) {
        return new PartialModel(CreateSifter.asResource("block/" + path));
    }

    private static PartialModel entity(String path) {
        return new PartialModel(CreateSifter.asResource("entity/" + path));
    }

    public static void load() {
        // init static fields
    }
}
