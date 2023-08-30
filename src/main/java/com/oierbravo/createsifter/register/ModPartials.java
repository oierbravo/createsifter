package com.oierbravo.createsifter.register;

import com.jozufozu.flywheel.core.PartialModel;
import com.oierbravo.createsifter.CreateSifter;
import com.simibubi.create.Create;

public class ModPartials {
    public static final PartialModel SIFTER_COG = block("sifter/inner");
    public static final PartialModel SIFTER_WITH_MESH = block("sifter/item_with_mesh");
    public static final PartialModel SIFTER_MESH = block("meshes/mesh");
    public static final PartialModel BRASS_SIFTER_COG = block("brass_sifter/inner");




    private static PartialModel block(String path) {
        return new PartialModel(CreateSifter.asResource("block/" + path));
    }
    public static void init() {
        // init static fields
    }
}
