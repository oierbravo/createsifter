package com.oierbravo.createsifter.content.contraptions.components.sifter.andesite;

import com.oierbravo.createsifter.content.contraptions.components.meshes.IAdvancedMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.IMesh;
import com.oierbravo.createsifter.content.contraptions.components.sifter.AbstractSifterBlockEntity;
import com.oierbravo.createsifter.infrastucture.config.MConfigs;
import com.oierbravo.createsifter.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import static com.oierbravo.createsifter.register.ModRecipes.findRecipesWithMatchingIngredients;

public class SifterBlockEntity extends AbstractSifterBlockEntity {

    @Override
    protected boolean isValidMesh(ItemStack meshStack) {
        return meshStack.getItem() instanceof IMesh && !(meshStack.getItem() instanceof IAdvancedMesh);
    }

    public SifterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

    }
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.SIFTER.get(),
                (be, context) -> be.getItemHandler()
        );
    }
}
