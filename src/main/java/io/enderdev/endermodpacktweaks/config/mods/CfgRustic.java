package io.enderdev.endermodpacktweaks.config.mods;

import net.minecraftforge.common.config.Config;

public class CfgRustic {
    @Config.Name("[01] Enable Rustic Tweaks")
    @Config.Comment("Set to false to disable Rustic tweaks")
    @Config.RequiresMcRestart
    public boolean enable = true;

    @Config.Name("[02] Berry Bush generation spread")
    @Config.Comment("Tweaking the max radius Rustic's berry bushes try to generate in per patch")
    public int maxWildberrySpread = 7;

    @Config.Name("[03] Override Berry Bush placement")
    @Config.Comment("Set to true to override Rustic's berry bush placement")
    public boolean overrideBerryBushPlacement = false;

    @Config.Name("[04] Valid Berry Bush blocks")
    @Config.Comment("List of blocks that Rustic's berry bushes can be placed on")
    public String[] listBerryBushBlocks = new String[]{
            "minecraft:grass",
            "minecraft:dirt",
            "minecraft:farmland",
            "rustic:fertile_soil"
    };

    @Config.Name("[05] Override Berry Bush biome blacklist")
    @Config.Comment("Set to true to override Rustic's berry bush biome blacklist")
    public boolean overrideBerryBushBiomeBlacklist = false;

    @Config.Name("[06] Berry Bush biomes blacklist")
    @Config.Comment("List of biomes that Rustic's berry bushes won't be generated in")
    public String[] listBiomesBlacklist = new String[]{
            "COLD",
            "SNOWY",
            "SANDY",
            "SAVANNA",
            "MESA",
            "MUSHROOM",
            "NETHER",
            "END",
            "DEAD",
            "WASTELAND",
    };
}
