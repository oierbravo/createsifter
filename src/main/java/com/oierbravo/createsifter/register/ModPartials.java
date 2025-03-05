package com.oierbravo.createsifter.register;

import com.oierbravo.createsifter.CreateSifter;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class ModPartials {
    public static final PartialModel SIFTER_COG = block("sifter/inner");
    public static final PartialModel SIFTER_WITH_MESH = block("sifter/item_with_mesh");
    public static final PartialModel SIFTER_MESH = block("meshes/mesh");
    public static final PartialModel BRASS_SIFTER_MESH = item("advanced_brass_mesh");
    public static final PartialModel BRASS_SIFTER_COG = block("brass_sifter/inner");




    private static PartialModel block(String path) {
        return PartialModel.of(CreateSifter.asResource("block/" + path));
    }
    private static PartialModel item(String path) {
        return PartialModel.of(CreateSifter.asResource("item/" + path));
    }
    public static void init() {
        // init static fields
    }
}
