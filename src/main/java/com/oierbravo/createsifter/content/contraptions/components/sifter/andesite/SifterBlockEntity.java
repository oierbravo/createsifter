package com.oierbravo.createsifter.content.contraptions.components.sifter.andesite;

import com.oierbravo.createsifter.content.contraptions.components.sifter.AbstractSifterBlockEntity;
import com.oierbravo.createsifter.infrastucture.config.MConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static com.oierbravo.createsifter.register.ModRecipes.findRecipesWithMatchingIngredients;

public class SifterBlockEntity extends AbstractSifterBlockEntity {

    public SifterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

    }
}
