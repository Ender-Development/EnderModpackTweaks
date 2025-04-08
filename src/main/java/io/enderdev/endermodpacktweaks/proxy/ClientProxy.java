package io.enderdev.endermodpacktweaks.proxy;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.core.EMTAssetMover;
import io.enderdev.endermodpacktweaks.events.ClientEvents;
import io.enderdev.endermodpacktweaks.features.modpackinfo.ModpackInfoEventHandler;
import io.enderdev.endermodpacktweaks.patches.mysticallib.EffectManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.*;

public class ClientProxy extends CommonProxy implements IProxy {
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

    @Override
    public void construct(FMLConstructionEvent event) throws Exception {
        super.construct(event);
        if (Loader.isModLoaded("assetmover")) {
            EMTAssetMover.getAssets();
        }
    }

    @Override
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        super.onLoadComplete(event);
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
        if (EMTConfig.MINECRAFT.CLIENT.enable && EMTConfig.MINECRAFT.CLIENT.disableItemNames) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            gameSettings.heldItemTooltips = false;
        }
    }
}
