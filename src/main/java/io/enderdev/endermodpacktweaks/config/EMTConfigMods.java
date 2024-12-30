package io.enderdev.endermodpacktweaks.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.config.mods.CfgPerfectSpawn;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID, category = "mods")
@Config.LangKey("config.endermodpacktweaks.mods")
public class EMTConfigMods {
    @Config.Name("Perfect Spawn")
    @Config.LangKey("config.endermodpacktweaks.mods.perfectspawn")
    public static final CfgPerfectSpawn PERFECT_SPAWN = new CfgPerfectSpawn();

    static {
        ConfigAnytime.register(EMTConfigMinecraft.class);
    }
}
