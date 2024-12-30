package io.enderdev.endermodpacktweaks.config.minecraft;

import net.minecraftforge.common.config.Config;

public class CfgDragon {
    @Config.Name("Enable Dragon Tweaks")
    @Config.Comment({
            "This tweak kills the first dragon when the player enters the end for the first time.",
            "Useful for modpacks that want to make the dragon fight non-free."
    })
    @Config.RequiresMcRestart
    public boolean enable = false;

    @Config.Name("Drop Dragon Egg")
    @Config.Comment("Should the auto killed dragon drop the dragon egg?")
    public boolean dropEgg = false;

    @Config.Name("Multiple Dragon Egg")
    @Config.Comment("Should every dragon drop an egg?")
    public boolean multipleEgg = false;

    @Config.Name("Create End Portal")
    @Config.Comment("Should the auto killed dragon create the end portal?")
    public boolean createPortal = false;

    @Config.Name("Create End Gateway")
    @Config.Comment("Should the auto killed dragon create an end gateway?")
    public boolean createGateway = false;

    @Config.Name("Disable End Portal")
    @Config.Comment("Should spawning the end portal when killing the dragon be disabled?")
    public boolean disablePortal = false;

    @Config.Name("Disable End Gateway")
    @Config.Comment("Should spawning the end gateway when killing the dragon be disabled?")
    public boolean disableGateway = false;
}