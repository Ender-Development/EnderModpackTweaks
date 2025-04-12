package io.enderdev.endermodpacktweaks.features.keybinds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KeybindHandler {
    @SubscribeEvent
    public void onGuiInit(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiControls && !(event.getGui() instanceof GuiNewControls)) {
            Minecraft mc = Minecraft.getMinecraft();
            event.setGui(new GuiNewControls(mc.currentScreen, mc.gameSettings));
        }
    }
}
