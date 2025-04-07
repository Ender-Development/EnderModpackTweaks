package io.enderdev.endermodpacktweaks.proxy;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.events.ClientEvents;
import io.enderdev.endermodpacktweaks.features.modpackinfo.ModpackInfoEventHandler;
import io.enderdev.endermodpacktweaks.patches.mysticallib.EffectManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());

        if (Loader.isModLoaded("crissaegrim")) {
            MinecraftForge.EVENT_BUS.register(new EffectManager());
        }

        if (EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.enable) {
            MinecraftForge.EVENT_BUS.register(new ModpackInfoEventHandler());
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        GameSettings.Options.RENDER_DISTANCE.setValueMax(EMTConfig.MINECRAFT.CLIENT.maxRenderDistance);
    }
}
