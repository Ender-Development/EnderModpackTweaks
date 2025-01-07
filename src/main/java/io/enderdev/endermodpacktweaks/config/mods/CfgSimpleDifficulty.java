package io.enderdev.endermodpacktweaks.config.mods;

import net.minecraftforge.common.config.Config;

public class CfgSimpleDifficulty {
    @Config.Name("Enable Simple Difficulty Tweaks")
    @Config.Comment("Set to false to disable Simple Difficulty tweaks.")
    @Config.RequiresMcRestart
    public boolean enable = true;

    @Config.Name("Thirstbar X-Offset")
    @Config.Comment("Set the X-Offset of the Thirstbar.")
    public int thirstbarXOffset = 82;

    @Config.Name("Thirstbar Y-Offset")
    @Config.Comment("Set the Y-Offset of the Thirstbar.")
    public int thirstbarYOffset = -53;
}
