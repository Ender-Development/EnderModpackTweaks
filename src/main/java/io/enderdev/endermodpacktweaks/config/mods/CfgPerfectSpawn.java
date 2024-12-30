package io.enderdev.endermodpacktweaks.config.mods;

import net.minecraftforge.common.config.Config;

public class CfgPerfectSpawn {
    @Config.Name("Enable Perfect Spawn Tweaks")
    @Config.Comment({
            "This tweaks moves the PerfectSpawn config file to the config directory.",
            "It also creates a new config file if it doesn't exist."
    })
    @Config.RequiresMcRestart
    public boolean enable = true;
}
