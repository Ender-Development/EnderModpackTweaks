package io.enderdev.endermodpacktweaks.features.packupdater;

import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.utils.EmtWeb;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class UpdateGUI extends GuiScreen {
    private final GuiScreen parent;
    private GuiButton continueAnyway;
    private GuiButton goToCurseForge;

    public UpdateGUI(@Nullable GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);

        this.continueAnyway = new GuiButton(10, ((sr.getScaledWidth() / 2) - 90), 190, 180, 20, I18n.format("msg.endermodpacktweaks.update.mainmenu"));
        this.goToCurseForge = new GuiButton(11, ((sr.getScaledWidth() / 2) - 90), 160, 180, 20, I18n.format("msg.endermodpacktweaks.update.openurl"));

        this.buttonList.add(this.continueAnyway);
        this.buttonList.add(this.goToCurseForge);

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String title = I18n.format("msg.endermodpacktweaks.update.title", CfgModpack.MODPACK.modpackName);
        String description = I18n.format("msg.endermodpacktweaks.update.desc");

        ScaledResolution screenRes = new ScaledResolution(mc);

        drawDefaultBackground();
        int posX = (screenRes.getScaledWidth() / 2) - (fontRenderer.getStringWidth(title) / 2);
        drawString(fontRenderer, title, posX, 50, 0xFFFFFF);
        drawString(fontRenderer, I18n.format("msg.endermodpacktweaks.update.oldversion", CfgModpack.MODPACK.modpackVersion), posX, 65, 0xFFFFFF);
        drawString(fontRenderer, I18n.format("msg.endermodpacktweaks.update.newversion", Updater.INSTANCE.getNewVersion()), posX, 75, 0xFFFFFF);
        fontRenderer.drawSplitString(description, posX, 90, fontRenderer.getStringWidth(title), 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == continueAnyway.id) {
            mc.displayGuiScreen(parent);
        }

        if (button.id == goToCurseForge.id) {
            EmtWeb.openUrl(CfgModpack.MODPACK.modpackDownload);
        }

        super.actionPerformed(button);
    }
}

