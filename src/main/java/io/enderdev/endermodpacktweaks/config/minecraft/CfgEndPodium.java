package io.enderdev.endermodpacktweaks.config.minecraft;

import net.minecraftforge.common.config.Config;

public class CfgEndPodium {
    @Config.Name("Enable End Podium Tweaks")
    @Config.Comment({
            "This tweak allows you to customize the End Portal.",
            "You can change the blocks that make up the End Portal."
    })
    @Config.RequiresMcRestart
    public boolean enable = false;

    @Config.Name("Replace Bedrock")
    @Config.Comment({
            "Replace the bedrock block in the End Portal.",
            "Replacing the bedrock makes it impossible to respawn the Ender Dragon."
    })
    public String bedrock = "minecraft:bedrock";

    @Config.Name("Replace End Stone")
    @Config.Comment("Replace the end stone block in the End Portal.")
    public String endStone = "minecraft:end_stone";

    @Config.Name("Replace Torch")
    @Config.Comment("Replace the torch block in the End Portal.")
    public String torch = "minecraft:torch";

    @Config.Name("Replace Air")
    @Config.Comment("Replace the air block in the End Portal.")
    public String air = "minecraft:air";
}
