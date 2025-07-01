package io.enderdev.endermodpacktweaks.features.modpackinfo;

import de.keksuccino.fancymenu.menu.fancy.guicreator.CustomGuiBase;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.utils.EmtWeb;
import lumien.custommainmenu.gui.GuiCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModpackInfoEventHandler implements GuiYesNoCallback {
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!guiCheck(event.getGui())) {
            return;
        }
        OptionsButtonHandler optionsButtonHandler = new OptionsButtonHandler(event);
        optionsButtonHandler.clearButtons();
        optionsButtonHandler.loadButtons();
        optionsButtonHandler.addButtons();
    }

    @SubscribeEvent
    public void actionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (!guiCheck(event.getGui())) {
            return;
        }
        if (event.getButton() instanceof SlideButton) {
            SlideButton button = (SlideButton) event.getButton();
            String url = getUrl(button.id);
            if (url.isEmpty()) {
                return;
            }
            event.getGui().mc.displayGuiScreen(new GuiConfirmOpenLink(this, url, button.id, true));
        }
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if (!result) {
            Minecraft.getMinecraft().displayGuiScreen(null);
            return;
        }
        EmtWeb.openUrl(getUrl(id));
    }

    private String getUrl(int id) {
        switch (id) {
            case 50:
                return CfgModpack.OPTIONS_MENU_BUTTONS.CHANGELOG_BUTTON.url;
            case 51:
                return CfgModpack.OPTIONS_MENU_BUTTONS.DONATION_BUTTON.url;
            case 52:
                return CfgModpack.OPTIONS_MENU_BUTTONS.GITHUB_BUTTON.url;
            case 53:
                return CfgModpack.OPTIONS_MENU_BUTTONS.DISCORD_BUTTON.url;
            case 54:
                return CfgModpack.OPTIONS_MENU_BUTTONS.TWITCH_BUTTON.url;
            case 55:
                return CfgModpack.OPTIONS_MENU_BUTTONS.YOUTUBE_BUTTON.url;
            default:
                return "";
        }
    }

    private boolean guiCheck(Gui gui) {
        return (gui instanceof GuiMainMenu)
                || (gui instanceof GuiIngameMenu)
                || (gui instanceof GuiOptions)
                || (Loader.isModLoaded("custommainmenu") && gui instanceof GuiCustom)
                || (Loader.isModLoaded("fancymenu") && gui instanceof CustomGuiBase);
    }
}
