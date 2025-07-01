package io.enderdev.endermodpacktweaks.features.startuptimer;

import de.keksuccino.fancymenu.menu.fancy.guicreator.CustomGuiBase;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.utils.EmtColor;
import lumien.custommainmenu.gui.GuiCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.lang.management.ManagementFactory;

@SideOnly(Side.CLIENT)
public class HistroyHandler {
    private boolean flag;

    public static long startupInstant = ManagementFactory.getRuntimeMXBean().getStartTime();
    public static long done_time = 0;

    @SubscribeEvent
    public void atMainMenu(GuiOpenEvent event) {
        if (!flag && event.getGui() instanceof GuiMainMenu) {
            flag = true;
            done_time = System.currentTimeMillis() - startupInstant;
            EnderModpackTweaks.LOGGER.info("Startup took: {}", CfgModpack.STARTUP_TIMER.timeFormat.replace("[minutes]", String.valueOf(minutes())).replace("[seconds]", String.valueOf(seconds())));
            TimeHistory.saveHistory(done_time);
        }
    }

    @SubscribeEvent
    public void onGuiDraw(GuiScreenEvent.DrawScreenEvent event) {
        if (CfgModpack.STARTUP_TIMER.display && checkGui(event.getGui())) {
            float guiScale = (float) Minecraft.getMinecraft().gameSettings.guiScale;
            if (guiScale <= 0) guiScale = 1; // failsafe to prevent divide by 0

            String txt = I18n.format("msg.endermodpacktweaks.timer", minutes(), seconds());
            Color color = EmtColor.parseColorFromHexString(CfgModpack.STARTUP_TIMER.color);
            Tuple<Integer, Integer> coordinates = getCoordinates(event.getGui().width, event.getGui().height, Minecraft.getMinecraft().fontRenderer.getStringWidth(txt), Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(txt, coordinates.getFirst(), coordinates.getSecond(), color.getRGB());
        }
    }

    private long minutes() {
        return (done_time / 1000) / 60;
    }

    private long seconds() {
        return (done_time / 1000) % 60;
    }

    private boolean checkGui(GuiScreen guiScreen) {
        return (guiScreen) instanceof GuiMainMenu
                || (Loader.isModLoaded("custommainmenu") && guiScreen instanceof GuiCustom)
                || (Loader.isModLoaded("fancymenu") && guiScreen instanceof CustomGuiBase);
    }

    private Tuple<Integer, Integer> getCoordinates(int width, int height, int stringLength, int stringHeight) {
        int x;
        int y;

        switch (CfgModpack.STARTUP_TIMER.anchor) {
            case TOP_CENTER:
                x = (width - stringLength) / 2;
                y = 0;
                break;
            case TOP_RIGHT:
                x = width - stringLength;
                y = 0;
                break;
            case MIDDLE_LEFT:
                x = 0;
                y = (height - stringHeight) / 2;
                break;
            case MIDDLE_CENTER:
                x = (width - stringLength) / 2;
                y = (height - stringHeight) / 2;
                break;
            case MIDDLE_RIGHT:
                x = width - stringLength;
                y = (height - stringHeight) / 2;
                break;
            case BOTTOM_LEFT:
                x = 0;
                y = height - stringHeight;
                break;
            case BOTTOM_CENTER:
                x = (width - stringLength) / 2;
                y = height - stringHeight;
                break;
            case BOTTOM_RIGHT:
                x = width - stringLength;
                y = height - stringHeight;
                break;
            case TOP_LEFT:
            default:
                x = 0;
                y = 0;
        }

        return new Tuple<>(x + CfgModpack.STARTUP_TIMER.xOffset, y + CfgModpack.STARTUP_TIMER.yOffset);
    }
}
