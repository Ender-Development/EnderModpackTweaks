package io.enderdev.endermodpacktweaks.config.minecraft;

import net.minecraftforge.common.config.Config;

public class CfgEndGateway {
    @Config.Name("Enable End Gateway Tweaks")
    @Config.Comment({
            "This tweak allows you to customize the End Gateway.",
            "You can change the blocks that make up the End Gateway."
    })
    @Config.RequiresMcRestart
    public boolean enable = false;

    @Config.Name("Replace Air")
    @Config.Comment("Replace the air block in the End Gateway.")
    public String air = "minecraft:air";

    @Config.Name("Replace Bedrock")
    @Config.Comment("Replace the bedrock block in the End Gateway.")
    public String bedrock = "minecraft:bedrock";
}
