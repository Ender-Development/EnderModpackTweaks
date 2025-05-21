package io.enderdev.endermodpacktweaks;

import io.enderdev.endermodpacktweaks.proxy.IProxy;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, dependencies = EnderModpackTweaks.DEPENDENCIES)
public class EnderModpackTweaks {
    /**
     * This list misses 'mysicallib' as it causes a circular dependency.
     */
    public static final String DEPENDENCIES = "required-after:mixinbooter;after:fermiumbooter;after:assetmover;required-after:configanytime;"
            + "after:astralsorcery;"
            + "after:betterend;"
            + "after:crissaegrim;"
            + "after:darkutils;"
            + "after:defaultworldgenerator;"
            + "after:dshuds;"
            + "after:elenaidodge;"
            + "after:enderstorage;"
            + "after:firstaid;"
            + "after:fluxnetworks;"
            + "after:gamestages;"
            + "after:itemphysic;"
            + "after:itemstages;"
            + "after:ibm;"
            + "after:matteroverdrive;"
            + "after:mbtool;"
            + "after:perfectspawn;"
            + "after:potioncore;"
            + "after:pyrotech;"
            + "after:quark;"
            + "after:reskillable;"
            + "after:rustic;"
            + "after:simpledifficulty;"
            + "after:tinyinv;"
            + "after:toolprogression;"
            + "after:waila;";

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    public static final String COMMON_PROXY = "io.enderdev.endermodpacktweaks.proxy.CommonProxy";
    public static final String CLIENT_PROXY = "io.enderdev.endermodpacktweaks.proxy.ClientProxy";

    @Mod.Instance(value = Tags.MOD_ID)
    public static EnderModpackTweaks instance;

    @SidedProxy(modId = Tags.MOD_ID, clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static IProxy proxy;

    public static SimpleNetworkWrapper network;

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
        proxy.construct(event);
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        proxy.onLoadComplete(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.serverStarted(event);
    }
}
