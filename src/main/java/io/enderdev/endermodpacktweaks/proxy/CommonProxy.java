package io.enderdev.endermodpacktweaks.proxy;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import io.enderdev.endermodpacktweaks.events.PyrotechEvents;
import io.enderdev.endermodpacktweaks.events.ReskillableEvents;
import io.enderdev.endermodpacktweaks.features.compatscreen.CompatModsHandler;
import io.enderdev.endermodpacktweaks.features.crashinfo.InfoBuilder;
import io.enderdev.endermodpacktweaks.features.dodgethirst.DodgeHandler;
import io.enderdev.endermodpacktweaks.features.healthbar.BarHandler;
import io.enderdev.endermodpacktweaks.features.healthbar.BarRenderer;
import io.enderdev.endermodpacktweaks.features.instantbonemeal.BoneMealhandler;
import io.enderdev.endermodpacktweaks.features.materialtweaker.MaterialTweaker;
import io.enderdev.endermodpacktweaks.features.netherportal.PortalHandler;
import io.enderdev.endermodpacktweaks.features.playerpotions.SimpleDifficultyHandler;
import io.enderdev.endermodpacktweaks.features.playerpotions.VanillaHandler;
import io.enderdev.endermodpacktweaks.features.servermsg.ServerHandler;
import io.enderdev.endermodpacktweaks.features.timesync.TimeEventHandler;
import io.enderdev.endermodpacktweaks.features.worldoptions.WorldOptionsHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.Objects;

public class CommonProxy implements IProxy {
    private static final InfoBuilder INFO_BUILDER = new InfoBuilder("Modpack Informations");
    // Player Effects
    private VanillaHandler vanillaHandler;
    private SimpleDifficultyHandler simpleDifficultyHandler;

    // EMT Internal
    private BarHandler barHandler;

    public void preInit(FMLPreInitializationEvent event) {
        EnderModpackTweaks.network = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MOD_ID);

        if (CfgModpack.SERVER_MESSAGE.enable) {
            MinecraftForge.EVENT_BUS.register(new ServerHandler());
        }

        if (CfgFeatures.PLAYER_EFFECTS.enable) {
            vanillaHandler = new VanillaHandler();
            MinecraftForge.EVENT_BUS.register(vanillaHandler);
        }

        if (CfgMinecraft.NETHER_PORTAL.enable) {
            MinecraftForge.EVENT_BUS.register(new PortalHandler());
        }

        if (CfgFeatures.INSTANT_BONE_MEAL.enable) {
            MinecraftForge.EVENT_BUS.register(new BoneMealhandler());
        }

        if (CfgFeatures.SYNC_TIME.enable) {
            MinecraftForge.EVENT_BUS.register(new TimeEventHandler());
        }

        if (CfgMinecraft.WORLD.enable) {
            MinecraftForge.EVENT_BUS.register(new WorldOptionsHandler());
        }

        if (CfgTweaks.ELENAI_DODGE.enable
                && Loader.isModLoaded("elenaidodge2")
                && Loader.isModLoaded("simpledifficulty")
                && CfgTweaks.ELENAI_DODGE.enableSimpleDifficulty
        ) {
            MinecraftForge.EVENT_BUS.register(new DodgeHandler());
        }

        if (CfgTweaks.RESKILLABLE.enable
                && Loader.isModLoaded("reskillable")
        ) {
            MinecraftForge.EVENT_BUS.register(new ReskillableEvents());
        }

        if (CfgTweaks.PYROTECH.enable
                && Loader.isModLoaded("pyrotech")
        ) {
            MinecraftForge.EVENT_BUS.register(new PyrotechEvents());
        }

        if (CfgTweaks.SIMPLE_DIFFICULTY.enable
                && Loader.isModLoaded("simpledifficulty")
        ) {
            simpleDifficultyHandler = new SimpleDifficultyHandler();
            MinecraftForge.EVENT_BUS.register(simpleDifficultyHandler);
        }

        if (CfgFeatures.MOB_HEALTH_BAR.enable) {
            barHandler = new BarHandler();
            MinecraftForge.EVENT_BUS.register(barHandler);
        }
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (CfgFeatures.MATERIAL_TWEAKER.enable) {
            MaterialTweaker.INSTANCE.load();
        }

        if (CfgFeatures.MOB_HEALTH_BAR.enable) {
            barHandler.whitelist.init();
            BarRenderer.rangeModifiers.init();
        }

        if (CfgTweaks.SIMPLE_DIFFICULTY.enable
                && Loader.isModLoaded("simpledifficulty")
        ) {
            simpleDifficultyHandler.temperaturePotionHandler.init();
            simpleDifficultyHandler.thirstPotionHandler.init();
        }

        if (CfgFeatures.PLAYER_EFFECTS.enable) {
            vanillaHandler.healthPotionHandler.init();
            vanillaHandler.hungerPotionHandler.init();
        }
    }

    @Override
    public void construct(FMLConstructionEvent event) throws Exception {
        if (CfgModpack.CRASH_INFO.enable) {
            FMLCommonHandler.instance().registerCrashCallable(INFO_BUILDER);
        }
    }

    @Override
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        if (CompatModsHandler.hasObsoleteModsMessage()) {
            CompatModsHandler.obsoleteModsMessage().stream().filter(s -> !Objects.equals(s, "")).forEach(EnderModpackTweaks.LOGGER::warn);
        }
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {

    }
}
