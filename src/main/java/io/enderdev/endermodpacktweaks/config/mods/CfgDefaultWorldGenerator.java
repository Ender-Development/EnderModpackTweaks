package io.enderdev.endermodpacktweaks.config.mods;

import net.minecraftforge.common.config.Config;

public class CfgDefaultWorldGenerator {
    @Config.Name("Enable Default World Generator Port")
    @Config.Comment("This fixes the glitched button texture in the default world generator screen.")
    public boolean enable = true;
}
