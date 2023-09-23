package com.oierbravo.createsifter.content.contraptions.components.brasss_sifter;

import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class BrassSifterFilterSlotPositioning extends ValueBoxTransform.Sided {

    @Override
    protected boolean isSideActive(BlockState state, Direction direction) {
        if(direction == Direction.UP || direction == Direction.DOWN)
            return false;
        return true;
    }
    @Override
    protected Vec3 getSouthLocation() {
        return VecHelper.voxelSpace(8f, 13.5f,  16f);
    }
}
