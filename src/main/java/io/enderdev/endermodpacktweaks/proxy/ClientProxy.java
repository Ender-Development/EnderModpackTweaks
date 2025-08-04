package io.enderdev.endermodpacktweaks.proxy;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.core.EMTAssetMover;
import io.enderdev.endermodpacktweaks.features.additionalmastervolume.MasterVolumeHandler;
import io.enderdev.endermodpacktweaks.features.forcedresourcepack.ResourcePackHandler;
import io.enderdev.endermodpacktweaks.features.healthbar.HealthBarHandler;
import io.enderdev.endermodpacktweaks.features.keybinds.KeybindHandler;
import io.enderdev.endermodpacktweaks.features.modpackinfo.ModpackInfoEventHandler;
import io.enderdev.endermodpacktweaks.features.modpackinfo.ServerHandler;
import io.enderdev.endermodpacktweaks.features.noautojump.AutoJumpHandler;
import io.enderdev.endermodpacktweaks.features.nofovchange.FovHandler;
import io.enderdev.endermodpacktweaks.features.noinventorycrafting.InventoryHandler;
import io.enderdev.endermodpacktweaks.features.nonametags.NameTagHandler;
import io.enderdev.endermodpacktweaks.features.nooverlay.OverlayHandler;
import io.enderdev.endermodpacktweaks.features.packupdater.UpdateHandler;
import io.enderdev.endermodpacktweaks.features.packupdater.Updater;
import io.enderdev.endermodpacktweaks.features.startuptimer.HistroyHandler;
import io.enderdev.endermodpacktweaks.patches.mysticallib.EffectManager;
import io.enderdev.endermodpacktweaks.utils.EmtOptifine;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.*;

import java.io.IOException;

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

            if (CfgMinecraft.INVENTORY_CRAFTING.enable) {
                MinecraftForge.EVENT_BUS.register(new InventoryHandler());
            }
        }

        if (Loader.isModLoaded("crissaegrim")) {
            MinecraftForge.EVENT_BUS.register(new EffectManager());
        }

        if (CfgModpack.OPTIONS_MENU_BUTTONS.enable) {
            MinecraftForge.EVENT_BUS.register(new ModpackInfoEventHandler());
        }

        if (CfgFeatures.FORCED_RESOURCEPACK.enable) {
            ResourcePackHandler.loadResourcepacks();
        }

        if (CfgFeatures.IMPROVED_KEYBINDS.enable) {
            MinecraftForge.EVENT_BUS.register(new KeybindHandler());
        }

        if (CfgFeatures.MOB_HEALTH_BAR.enable) {
            healthBarHandler = new HealthBarHandler();
            MinecraftForge.EVENT_BUS.register(healthBarHandler);

            if (CfgFeatures.MOB_HEALTH_BAR.enableInstancing) {
                int majorGlVersion = EmtRender.getMajorGlVersion();
                int minorGlVersion = EmtRender.getMinorGlVersion();
                // requires gl version 3.3 or above
                if (majorGlVersion > 3 || (majorGlVersion == 3 && minorGlVersion >= 3))
                    healthBarHandler.instancing = true;
            }
        }

        if (CfgModpack.PACK_UPDATER.enable) {
            MinecraftForge.EVENT_BUS.register(new UpdateHandler());
        }

        if (CfgModpack.DEFAULT_SERVER.enable && !CfgModpack.DEFAULT_SERVER.serverIp.isEmpty() && !CfgModpack.DEFAULT_SERVER.serverName.isEmpty()) {
            ServerHandler.addServer();
        }

        if (CfgModpack.STARTUP_TIMER.enable) {
            MinecraftForge.EVENT_BUS.register(new HistroyHandler());
        }

        // init getters
        EmtRender.getModelViewMatrix();
        EmtRender.getPartialTick();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        if (CfgModpack.PACK_UPDATER.enable) {
            try {
                if (!CfgModpack.MODPACK.modpackVersion.isEmpty()) {
                    Updater.INSTANCE.checkForUpdate();
                } else {
                    EnderModpackTweaks.LOGGER.error("Modpack Version hasn't been configured!");
                }
            } catch (IOException e) {
                EnderModpackTweaks.LOGGER.error("Unable to check for updates for the modpack! Is the URL in the config malformed,");
                EnderModpackTweaks.LOGGER.error("or is the version String in the URL not correctly formatted?");
                EnderModpackTweaks.LOGGER.error(String.valueOf(e));
            } catch (NumberFormatException e) {
                EnderModpackTweaks.LOGGER.error("Unable to parse the version number correctly!");
                EnderModpackTweaks.LOGGER.error("Is the version element from the URL malformed?");
                EnderModpackTweaks.LOGGER.error(String.valueOf(e));
            }
        }
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
        if (!Loader.isModLoaded("assetmover")) return;
        if (CfgFeatures.BOSS_BAR.enable) {
            EMTAssetMover.getInternalAssets();
        }
        if (CfgModpack.CUSTOM_ASSETS.enable && CfgModpack.CUSTOM_ASSETS.assetMoverJsonFiles.length > 0) {
            EMTAssetMover.getExternalAssets();
        }
    }

    @Override
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        super.onLoadComplete(event);
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        gameSettings.heldItemTooltips = !(CfgMinecraft.CLIENT.enable && CfgMinecraft.CLIENT.disableItemNames);
    }

    @Override
    public void serverStarted(FMLServerStartedEvent event) {
    }
}
