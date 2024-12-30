package io.enderdev.endermodpacktweaks.config;

import net.minecraftforge.common.config.Config;

public class CategoryClient {
    @Config.Name("Disable Inventory Crafting")
    @Config.Comment({
            "This tweak disables the crafting grid in the player inventory.",
            "Useful for modpacks that want to make the player use a crafting table",
            "or want to realize crafting in a different way."
    })
    public boolean disableInventoryCrafting = false;
}
