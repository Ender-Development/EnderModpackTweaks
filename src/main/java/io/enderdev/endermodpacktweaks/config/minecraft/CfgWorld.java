package io.enderdev.endermodpacktweaks.config.minecraft;

import net.minecraftforge.common.config.Config;

public class CfgWorld {

    @Config.Name("Enable World Tweaks")
    @Config.Comment("Enable world tweaks")
    public boolean enable = true;

    @Config.Name("Difficulty")
    @Config.LangKey("config.endermodpacktweaks.minecraft.world.difficulty")
    public final DifficultyCategory DIFFICULTY = new DifficultyCategory();

    @Config.Name("Gamemode")
    @Config.LangKey("config.endermodpacktweaks.minecraft.world.gamemode")
    public final GamemodeCategory GAMEMODE = new GamemodeCategory();

    public static class DifficultyCategory {
        @Config.Name("Force Difficulty")
        @Config.Comment("Force the difficulty to a specific value")
        public boolean force = false;

        @Config.Name("Forced Difficulty")
        @Config.RangeInt(min = 0, max = 3)
        @Config.Comment("0: Peaceful, 1: Easy, 2: Normal, 3: Hard")
        public int difficulty = 0;

        @Config.Name("Lock Difficulty")
        @Config.Comment("Prevent players from changing the difficulty")
        public boolean lock = false;
    }

    public static class GamemodeCategory {
        @Config.Name("Force Gamemode")
        @Config.Comment("Force the gamemode to a specific value")
        public boolean force = false;

        @Config.Name("Forced Gamemode")
        @Config.RangeInt(min = 0, max = 3)
        @Config.Comment("0: Survival, 1: Creative, 2: Adventure, 3: Spectator")
        public int gamemode = 0;

        @Config.Name("Allow Cheats")
        @Config.Comment("Allow cheats in the world")
        public boolean cheats = true;

        @Config.Name("Hardcore Mode")
        @Config.Comment("Enable hardcore mode")
        public boolean hardcore = false;
    }
}
