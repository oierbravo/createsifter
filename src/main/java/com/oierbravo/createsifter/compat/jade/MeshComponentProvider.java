package com.oierbravo.createsifter.compat.jade;

import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlockEntity;
import com.oierbravo.createsifter.foundation.util.ModLang;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.util.Color;

public class MeshComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("sifter.mesh")) {
            String meshName = accessor.getServerData().getString("sifter.mesh");
            tooltip.add(ModLang.translate("tooltip.mesh", meshName ).component().withStyle(ChatFormatting.GOLD));


        }

    }

    @Override
    public ResourceLocation getUid() {
        return SifterPlugin.SIFTER_DATA;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if(blockAccessor.getBlockEntity() instanceof SifterBlockEntity sifter){

            if(sifter.hasMesh()){
                compoundTag.putString("sifter.mesh", sifter.meshInv.getStackInSlot(0).getDisplayName().getString());

            }

        }
    }

}
