package io.enderdev.endermodpacktweaks.mixin.storagenetwork;

import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import mrriegel.storagenetwork.gui.GuiContainerStorageInventory;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiContainerStorageInventory.class, remap = false)
public abstract class GuiContainerStorageInventoryMixin {
    @Shadow
    protected GuiTextField searchBar;

    /**
     * @author Invadermonky
     * @reason Simple Storage Network always focuses the text box when the Gui opens. This behavior is not configurable
     * and is a massive annoyance to anyone who uses JEI lookup keybinds and not the mouse right and left click.
     */
    @Inject(method = "initGui", at = @At("TAIL"), remap = true)
    public void initGuiMixin(CallbackInfo ci) {
        this.searchBar.setFocused(CfgTweaks.SIMPLE_STORAGE_NETWORK.autoSelect);
    }
}
