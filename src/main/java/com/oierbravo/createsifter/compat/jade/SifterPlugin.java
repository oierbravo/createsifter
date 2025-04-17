package com.oierbravo.createsifter.compat.jade;

import com.oierbravo.createsifter.ModConstants;
import com.oierbravo.createsifter.content.contraptions.components.sifter.andesite.SifterBlock;
import com.oierbravo.createsifter.content.contraptions.components.sifter.andesite.SifterBlockEntity;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class SifterPlugin implements IWailaPlugin {
    public static final ResourceLocation SIFTER_DATA = ModConstants.asResource("data");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new MeshComponentProvider(), SifterBlockEntity.class);
        //registration.registerBlockDataProvider(new MeshComponentProvider(), BrassSifterBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
      registration.registerBlockComponent(new MeshComponentProvider(), SifterBlock.class);
      //registration.registerBlockComponent(new MeshComponentProvider(), BrassSifterBlock.class);
    }
}
