package io.enderdev.endermodpacktweaks;

import io.enderdev.endermodpacktweaks.core.EMTAssetMover;
import io.enderdev.endermodpacktweaks.features.compatscreen.CompatModsHandler;
import io.enderdev.endermodpacktweaks.features.crashinfo.InfoBuilder;
import io.enderdev.endermodpacktweaks.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = EnderModpackTweaks.DEPENDENCIES)
public class EnderModpackTweaks {
    public static final String DEPENDENCIES = "required-after:mixinbooter;after:fermiumbooter;after:assetmover;required-after:configanytime";

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    public static final String COMMON_PROXY = "io.enderdev.endermodpacktweaks.proxy.CommonProxy";
    public static final String CLIENT_PROXY = "io.enderdev.endermodpacktweaks.proxy.ClientProxy";

    private static final InfoBuilder INFO_BUILDER = new InfoBuilder("Modpack Informations");

    @Mod.Instance(value = Tags.MOD_ID)
    public static EnderModpackTweaks instance;

    @SidedProxy(modId = Tags.MOD_ID, clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) throws Exception {
        if (event.getSide() == Side.CLIENT && Loader.isModLoaded("assetmover")) {
            EMTAssetMover.getAssets();
        }
        if (EMTConfig.MODPACK.CRASH_INFO.enable) {
            FMLCommonHandler.instance().registerCrashCallable(INFO_BUILDER);
        }
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        if (CompatModsHandler.hasObsoleteModsMessage()) {
            CompatModsHandler.obsoleteModsMessage().stream().filter(s -> !Objects.equals(s, "")).forEach(LOGGER::warn);
        }
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        if (EMTConfig.MINECRAFT.CLIENT.enable && EMTConfig.MINECRAFT.CLIENT.disableItemNames) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            gameSettings.heldItemTooltips = false;
        }
    }
}
