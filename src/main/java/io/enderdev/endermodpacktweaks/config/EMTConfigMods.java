package io.enderdev.endermodpacktweaks.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.config.mods.CfgPerfectSpawn;
import io.enderdev.endermodpacktweaks.config.mods.CfgPyrotech;
import io.enderdev.endermodpacktweaks.config.mods.CfgRustic;
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

    static {
        ConfigAnytime.register(EMTConfigMinecraft.class);
    }
}
