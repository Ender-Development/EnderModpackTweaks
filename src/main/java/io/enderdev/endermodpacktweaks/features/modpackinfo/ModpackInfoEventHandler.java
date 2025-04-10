package io.enderdev.endermodpacktweaks.features.modpackinfo;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.net.URI;

@SideOnly(Side.CLIENT)
public class ModpackInfoEventHandler implements GuiYesNoCallback {
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!(event.getGui() instanceof GuiMainMenu) && !(event.getGui() instanceof GuiIngameMenu) && !(event.getGui() instanceof GuiOptions)) {
            return;
        }
        OptionsButtonHandler optionsButtonHandler = new OptionsButtonHandler(event);
        optionsButtonHandler.clearButtons();
        optionsButtonHandler.loadButtons();
        optionsButtonHandler.addButtons();
    }

    @SubscribeEvent
    public void actionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (!(event.getGui() instanceof GuiMainMenu) && !(event.getGui() instanceof GuiIngameMenu)) {
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
        try {
            String url = getUrl(id);
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new URI(url));
        } catch (Throwable throwable1) {
            Throwable throwable = throwable1.getCause();
            EnderModpackTweaks.LOGGER.error("Couldn't open link: {}", throwable == null ? "<UNKNOWN>" : throwable.getMessage());
        }
    }

    private String getUrl(int id) {
        switch (id) {
            case 50:
                return EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.CHANGELOG_BUTTON.url;
            case 51:
                return EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.DONATION_BUTTON.url;
            case 52:
                return EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.GITHUB_BUTTON.url;
            case 53:
                return EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.DISCORD_BUTTON.url;
            case 54:
                return EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.TWITCH_BUTTON.url;
            case 55:
                return EMTConfig.MODPACK.OPTIONS_MENU_BUTTONS.YOUTUBE_BUTTON.url;
            default:
                return "";
        }
    }
}
