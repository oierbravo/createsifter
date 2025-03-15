package com.oierbravo.createsifter.compat.jade;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterBlock;
import com.oierbravo.createsifter.content.contraptions.components.brasss_sifter.BrassSifterBlockEntity;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlock;
import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlockEntity;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class SifterPlugin implements IWailaPlugin {
    public static final ResourceLocation SIFTER_DATA = CreateSifter.asResource("sifter_data");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new MeshComponentProvider(), SifterBlockEntity.class);
        registration.registerBlockDataProvider(new MeshComponentProvider(), BrassSifterBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
      registration.registerBlockComponent(new MeshComponentProvider(), SifterBlock.class);
      registration.registerBlockComponent(new MeshComponentProvider(), BrassSifterBlock.class);
    }
}
