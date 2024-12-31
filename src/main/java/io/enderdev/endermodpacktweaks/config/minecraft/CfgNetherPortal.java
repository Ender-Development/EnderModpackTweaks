package io.enderdev.endermodpacktweaks.config.minecraft;

import net.minecraftforge.common.config.Config;

public class CfgNetherPortal {
    @Config.Name("Nether Portal Tweaks")
    @Config.Comment("Enable Nether Portal Tweaks")
    @Config.RequiresMcRestart
    public boolean enable = false;

    @Config.Name("Nether Portal Creation")
    @Config.Comment("Allow Nether Portal Creation")
    public boolean canBeCreated = true;

    @Config.Name("End Nether Portal")
    @Config.Comment("Allow Nether Portal Creation in the End")
    public boolean canBeCreatedInEnd = false;

    @Config.Name("No Entity Traverse")
    @Config.Comment("Disallow Entities to enter Nether Portals")
    public boolean disallowTraverse = false;
}
