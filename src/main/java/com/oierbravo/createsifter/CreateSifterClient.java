package com.oierbravo.createsifter;

import com.oierbravo.createsifter.register.ModPartials;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CreateSifterClient {

    public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(CreateSifterClient::clientInit);
    }
    public static void clientInit(final FMLClientSetupEvent event) {
        ModPartials.load();
    }
}
