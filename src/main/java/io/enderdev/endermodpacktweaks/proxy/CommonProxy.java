package io.enderdev.endermodpacktweaks.proxy;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.events.*;
import io.enderdev.endermodpacktweaks.features.materialtweaker.MaterialTweaker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        MinecraftForge.EVENT_BUS.register(new WorldEvents());

        if (EMTConfig.ELENAI_DODGE.enable && Loader.isModLoaded("elenaidodge2")) {
            MinecraftForge.EVENT_BUS.register(new ElenaiDodgeEvents());
        }

        if (EMTConfig.RESKILLABLE.enable && Loader.isModLoaded("reskillable")) {
            MinecraftForge.EVENT_BUS.register(new ReskillableEvents());
        }

        if (EMTConfig.PYROTECH.enable && Loader.isModLoaded("pyrotech")) {
            MinecraftForge.EVENT_BUS.register(new PyrotechEvents());
        }

        if (EMTConfig.SIMPLE_DIFFICULTY.enable && Loader.isModLoaded("simpledifficulty")) {
            MinecraftForge.EVENT_BUS.register(new SimpleDifficultyEvents());
        }
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (EMTConfig.MODPACK.MATERIAL_TWEAKER.enable) {
            MaterialTweaker.INSTANCE.load();
        }
    }
}
