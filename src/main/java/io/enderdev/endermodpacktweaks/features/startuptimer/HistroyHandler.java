package io.enderdev.endermodpacktweaks.features.startuptimer;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.utils.EmtColor;
import lumien.custommainmenu.gui.GuiCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.I18n;
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
            EnderModpackTweaks.LOGGER.info("Startup: {}m{}s", minutes(), seconds());
            TimeHistory.saveHistory(done_time);
        }
    }

    @SubscribeEvent
    public void onGuiDraw(GuiScreenEvent.DrawScreenEvent event) {
        if (CfgModpack.STARTUP_TIMER.display && (event.getGui() instanceof GuiMainMenu || (Loader.isModLoaded("custommainmenu") && event.getGui() instanceof GuiCustom))) {
            float guiScale = (float) Minecraft.getMinecraft().gameSettings.guiScale;
            if (guiScale <= 0) guiScale = 1; // failsafe to prevent divide by 0

            String txt = I18n.format("msg.endermodpacktweaks.timer", minutes(), seconds());
            Color color = EmtColor.parseColorFromHexString(CfgModpack.STARTUP_TIMER.color);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(txt, (float) ((event.getGui().width - Minecraft.getMinecraft().fontRenderer.getStringWidth(txt)) / 2) + CfgModpack.STARTUP_TIMER.xOffset, 10 + CfgModpack.STARTUP_TIMER.yOffset, color.getRGB());
        }
    }

    private long minutes() {
        return (done_time / 1000) / 60;
    }

    private long seconds() {
        return (done_time / 1000) % 60;
    }
}
