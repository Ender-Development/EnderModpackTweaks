package io.enderdev.endermodpacktweaks.config;

import net.minecraftforge.common.config.Config;

public class CategoryDragon {
    @Config.Name("Enable Dragon Tweaks")
    @Config.Comment({
            "This tweak kills the first dragon when the player enters the end for the first time.",
            "Useful for modpacks that want to make the dragon fight non-free."
    })
    @Config.RequiresMcRestart
    public boolean enable = false;

    @Config.Name("Drop Dragon Egg")
    @Config.Comment("Should the auto killed dragon drop the dragon egg?")
    @Config.RequiresMcRestart
    public boolean dropEgg = false;

    @Config.Name("Multiple Dragon Egg")
    @Config.Comment("Should every dragon drop an egg?")
    @Config.RequiresMcRestart
    public boolean multipleEgg = false;

    @Config.Name("Create End Portal")
    @Config.Comment("Should the auto killed dragon create the end portal?")
    @Config.RequiresMcRestart
    public boolean createPortal = false;
}
