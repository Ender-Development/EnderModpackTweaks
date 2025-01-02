package io.enderdev.endermodpacktweaks.config.mods;

import net.minecraftforge.common.config.Config;

public class CfgPyrotech {
    @Config.Name("Enable Pyrotech Tweaks")
    @Config.Comment("Enable tweaks for the Pyrotech mod.")
    @Config.RequiresMcRestart
    public boolean enable = true;

    @Config.Name("Random Rocks")
    @Config.Comment("Enable random rocks in the world.")
    public boolean randomRocks = false;

    @Config.Name("[01] Weight: rock_stone")
    @Config.Comment("The weight of rock_stone.")
    public int rock_stone = 100;

    @Config.Name("[02] Weight: rock_granite")
    @Config.Comment("The weight of rock_granite.")
    public int rock_granite = 0;

    @Config.Name("[03] Weight: rock_diorite")
    @Config.Comment("The weight of rock_diorite.")
    public int rock_diorite = 0;

    @Config.Name("[04] Weight: rock_andesite")
    @Config.Comment("The weight of rock_andesite.")
    public int rock_andesite = 0;

    @Config.Name("[05] Weight: rock_dirt")
    @Config.Comment("The weight of rock_dirt.")
    public int rock_dirt = 0;

    @Config.Name("[06] Weight: rock_sand")
    @Config.Comment("The weight of rock_sand.")
    public int rock_sand = 0;

    @Config.Name("[07] Weight: rock_sandstone")
    @Config.Comment("The weight of rock_sandstone.")
    public int rock_sandstone = 0;

    @Config.Name("[08] Weight: rock_wood_chips")
    @Config.Comment("The weight of rock_wood_chips.")
    public int rock_wood_chips = 0;

    @Config.Name("[09] Weight: rock_limestone")
    @Config.Comment("The weight of rock_limestone.")
    public int rock_limestone = 0;

    @Config.Name("[10] Weight: rock_sand_red")
    @Config.Comment("The weight of rock_sand_red.")
    public int rock_sand_red = 0;

    @Config.Name("[11] Weight: rock_sandstone_red")
    @Config.Comment("The weight of rock_sandstone_red.")
    public int rock_sandstone_red = 0;

    @Config.Name("[12] Weight: rock_mud")
    @Config.Comment("The weight of rock_mud.")
    public int rock_mud = 0;
}
