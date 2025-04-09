package io.enderdev.endermodpacktweaks.proxy;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.events.*;
import io.enderdev.endermodpacktweaks.features.compatscreen.CompatModsHandler;
import io.enderdev.endermodpacktweaks.features.crashinfo.InfoBuilder;
import io.enderdev.endermodpacktweaks.features.healthbar.BarHandler;
import io.enderdev.endermodpacktweaks.features.materialtweaker.MaterialTweaker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.Objects;

public class CommonProxy implements IProxy {
    private static final InfoBuilder INFO_BUILDER = new InfoBuilder("Modpack Informations");
    // Minecraft Internal
    private final BlockEvents blockEvents = new BlockEvents();
    private final PlayerEvents playerEvents = new PlayerEvents();
    private final WorldEvents worldEvents = new WorldEvents();
    private final ServerEvents serverEvents = new ServerEvents();
    // Mod Compatibility
    private ElenaiDodgeEvents elenaiDodgeEvents;
    private ReskillableEvents reskillableEvents;
    private PyrotechEvents pyrotechEvents;
    private SimpleDifficultyEvents simpleDifficultyEvents;

    public void preInit(FMLPreInitializationEvent event) {
        EnderModpackTweaks.network = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MOD_ID);

        MinecraftForge.EVENT_BUS.register(blockEvents);
        MinecraftForge.EVENT_BUS.register(playerEvents);
        MinecraftForge.EVENT_BUS.register(worldEvents);
        MinecraftForge.EVENT_BUS.register(serverEvents);

        if (EMTConfig.ELENAI_DODGE.enable && Loader.isModLoaded("elenaidodge2")) {
            elenaiDodgeEvents = new ElenaiDodgeEvents();
            MinecraftForge.EVENT_BUS.register(elenaiDodgeEvents);
        }

        if (EMTConfig.RESKILLABLE.enable && Loader.isModLoaded("reskillable")) {
            reskillableEvents = new ReskillableEvents();
            MinecraftForge.EVENT_BUS.register(reskillableEvents);
        }

        if (EMTConfig.PYROTECH.enable && Loader.isModLoaded("pyrotech")) {
            pyrotechEvents = new PyrotechEvents();
            MinecraftForge.EVENT_BUS.register(pyrotechEvents);
        }

        if (EMTConfig.SIMPLE_DIFFICULTY.enable && Loader.isModLoaded("simpledifficulty")) {
            simpleDifficultyEvents = new SimpleDifficultyEvents();
            MinecraftForge.EVENT_BUS.register(simpleDifficultyEvents);
        }

        if (EMTConfig.MODPACK.MOB_HEALTH_BAR.enable) {
            MinecraftForge.EVENT_BUS.register(new BarHandler());
        }
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (EMTConfig.MODPACK.MATERIAL_TWEAKER.enable) {
            MaterialTweaker.INSTANCE.load();
        }
        if (EMTConfig.SIMPLE_DIFFICULTY.enable && Loader.isModLoaded("simpledifficulty")) {
            simpleDifficultyEvents.temperaturePotionHandler.init();
            simpleDifficultyEvents.thirstPotionHandler.init();
        }
        if (EMTConfig.MINECRAFT.PLAYER_EFFECTS.enable) {
            playerEvents.healthPotionHandler.init();
            playerEvents.hungerPotionHandler.init();
        }
    }

    @Override
    public void construct(FMLConstructionEvent event) throws Exception {
        if (EMTConfig.MODPACK.CRASH_INFO.enable) {
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
