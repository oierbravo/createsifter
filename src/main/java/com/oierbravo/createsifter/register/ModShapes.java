package com.oierbravo.createsifter.register;

import com.simibubi.create.AllShapes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ModShapes {

    public static final VoxelShape SIFTER = shape(0, 0, 0, 16, 6, 16).add(2, 6, 2, 14, 13, 14)
			.add(3, 13, 3, 13, 16, 13)
			.build();

    private static AllShapes.Builder shape(VoxelShape shape) {
        return new AllShapes.Builder(shape);
    }
    private static AllShapes.Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
        return shape(cuboid(x1, y1, z1, x2, y2, z2));
    }

    private static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }
}
