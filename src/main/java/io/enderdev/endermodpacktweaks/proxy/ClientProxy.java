package io.enderdev.endermodpacktweaks.proxy;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.core.EMTAssetMover;
import io.enderdev.endermodpacktweaks.features.additionalmastervolume.MasterVolumeHandler;
import io.enderdev.endermodpacktweaks.features.healthbar.HealthBarHandler;
import io.enderdev.endermodpacktweaks.features.keybinds.KeybindHandler;
import io.enderdev.endermodpacktweaks.features.modpackinfo.ModpackInfoEventHandler;
import io.enderdev.endermodpacktweaks.features.noautojump.AutoJumpHandler;
import io.enderdev.endermodpacktweaks.features.nofovchange.FovHandler;
import io.enderdev.endermodpacktweaks.features.noinventorycrafting.InventoryHandler;
import io.enderdev.endermodpacktweaks.features.nonametags.NameTagHandler;
import io.enderdev.endermodpacktweaks.features.nooverlay.OverlayHandler;
import io.enderdev.endermodpacktweaks.patches.mysticallib.EffectManager;
import io.enderdev.endermodpacktweaks.utils.EmtOptifine;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.*;

public class ClientProxy extends CommonProxy implements IProxy {
    // EMT Internal
    private HealthBarHandler healthBarHandler;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        if (CfgMinecraft.CLIENT.enable) {
            MinecraftForge.EVENT_BUS.register(new OverlayHandler());

            if (CfgMinecraft.CLIENT.disableFovChange) {
                MinecraftForge.EVENT_BUS.register(new FovHandler());
            }

            if (CfgMinecraft.CLIENT.hideNameTags) {
                MinecraftForge.EVENT_BUS.register(new NameTagHandler());
            }

            if (CfgMinecraft.CLIENT.additionalMasterVolume) {
                MinecraftForge.EVENT_BUS.register(new MasterVolumeHandler());
            }

            if (CfgMinecraft.CLIENT.disableAutoJump) {
                MinecraftForge.EVENT_BUS.register(new AutoJumpHandler());
            }

            if (CfgMinecraft.CLIENT.disableInventoryCrafting) {
                MinecraftForge.EVENT_BUS.register(new InventoryHandler());
            }
        }

        if (Loader.isModLoaded("crissaegrim")) {
            MinecraftForge.EVENT_BUS.register(new EffectManager());
        }

        if (CfgModpack.OPTIONS_MENU_BUTTONS.enable) {
            MinecraftForge.EVENT_BUS.register(new ModpackInfoEventHandler());
        }

        if (CfgFeatures.IMPROVED_KEYBINDS.enable) {
            MinecraftForge.EVENT_BUS.register(new KeybindHandler());
        }

        if (CfgFeatures.MOB_HEALTH_BAR.enable) {
            healthBarHandler = new HealthBarHandler();
            MinecraftForge.EVENT_BUS.register(healthBarHandler);

            int majorGlVersion = EmtRender.getMajorGlVersion();
            int minorGlVersion = EmtRender.getMinorGlVersion();
            // requires gl version 3.3 or above
            if (majorGlVersion > 3 || (majorGlVersion == 3 && minorGlVersion >= 3))
                healthBarHandler.instancing = true;
        }

        // init getters
        EmtRender.getModelViewMatrix();
        EmtRender.getPartialTick();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        if (CfgMinecraft.CLIENT.enable && !EmtOptifine.isOptiFineInstalled()) {
            GameSettings.Options.RENDER_DISTANCE.setValueMax(CfgMinecraft.CLIENT.maxRenderDistance);
        }

        if (CfgFeatures.MOB_HEALTH_BAR.enable) {
            healthBarHandler.whitelist.init();
            healthBarHandler.rangeModifiers.init();
        }
    }

    @Override
    public void construct(FMLConstructionEvent event) throws Exception {
        super.construct(event);
        if (Loader.isModLoaded("assetmover") && CfgFeatures.BOSS_BAR.enable) {
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
        if (CfgMinecraft.CLIENT.enable && CfgMinecraft.CLIENT.disableItemNames) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            gameSettings.heldItemTooltips = false;
        }
    }

    @Override
    public void serverStarted(FMLServerStartedEvent event) {
    }
}
