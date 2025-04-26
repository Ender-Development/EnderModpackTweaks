package io.enderdev.endermodpacktweaks.features.noautojump;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AutoJumpHandler {
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiMainMenu) {
            // doing it like this avoids having any extra event handlers and also ensures that any player won't see a disabled "Auto Jump: On" button
            Minecraft.getMinecraft().gameSettings.autoJump = false;
        } else if (event.getGui() instanceof GuiControls) {
            for (GuiButton button : event.getButtonList()) {
                if (button instanceof GuiOptionButton && ((GuiOptionButton) button).getOption() == GameSettings.Options.AUTO_JUMP) {
                    button.enabled = false;
                    break;
                }
            }
        }
    }
}
