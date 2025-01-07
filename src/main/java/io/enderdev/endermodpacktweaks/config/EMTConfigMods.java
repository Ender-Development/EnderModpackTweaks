package io.enderdev.endermodpacktweaks.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.config.mods.*;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID, category = "mods")
@Config.LangKey("config.endermodpacktweaks.mods")
public class EMTConfigMods {
    @Config.Name("Perfect Spawn")
    @Config.LangKey("config.endermodpacktweaks.mods.perfectspawn")
    public static final CfgPerfectSpawn PERFECT_SPAWN = new CfgPerfectSpawn();

    @Config.Name("Pyrotech")
    @Config.LangKey("config.endermodpacktweaks.mods.pyrotech")
    public static final CfgPyrotech PYROTECH = new CfgPyrotech();

    @Config.Name("Rustic")
    @Config.LangKey("config.endermodpacktweaks.mods.rustic")
    public static final CfgRustic RUSTIC = new CfgRustic();

    @Config.Name("Default World Generator Port")
    @Config.LangKey("config.endermodpacktweaks.mods.defaultworldgeneratorport")
    public static final CfgDefaultWorldGenerator DEFAULT_WORLD_GENERATOR_PORT = new CfgDefaultWorldGenerator();

    @Config.Name("Simple Difficulty")
    @Config.LangKey("config.endermodpacktweaks.mods.simpledifficulty")
    public static final CfgSimpleDifficulty SIMPLE_DIFFICULTY = new CfgSimpleDifficulty();

    static {
        ConfigAnytime.register(EMTConfigMinecraft.class);
    }
}
