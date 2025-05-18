package io.enderdev.endermodpacktweaks.features.packupdater;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UpdateGUI extends GuiScreen {
    private final GuiScreen parent;
    private GuiButton continueAnyway;
    private GuiButton goToCurseForge;

    public UpdateGUI(@Nullable GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(this.mc);

        this.continueAnyway = new GuiButton(10, ((sr.getScaledWidth() / 2) - 90), 190, 180, 20, "Continue to Main Menu");
        this.goToCurseForge = new GuiButton(11, ((sr.getScaledWidth() / 2) - 90), 160, 180, 20, "More Info (Opens Browser!)");

        this.buttonList.add(this.continueAnyway);
        this.buttonList.add(this.goToCurseForge);

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String title = String.format("An update is available for %s!", CfgModpack.MODPACK.modpackName);
        String[] description;

        description = (String.format("You have %s. New version is %s\n", CfgModpack.MODPACK.modpackVersion, Updater.INSTANCE.getNewVersion())
                + "Make sure you visit the modpack's CurseForge page soon to grab the latest\n"
                + "update so you don't miss out on the new goodies the developers have added!\n\n\n").split("\n");

        ScaledResolution screenRes = new ScaledResolution(this.mc);

        this.drawDefaultBackground();
        this.drawString(this.fontRenderer, title, (screenRes.getScaledWidth() / 2) - (this.fontRenderer.getStringWidth(title) / 2), 50, 0xFFFFFF);

        for (int i = 0; i < description.length; i++) {
            String str = description[i];
            this.drawString(this.fontRenderer, str, (screenRes.getScaledWidth() / 2) - (this.fontRenderer.getStringWidth(str) / 2), 70 + (i * 10), 0xFFFFFF);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == this.continueAnyway.id) {
            UpdateHandler.showGUI = false;

            this.mc.displayGuiScreen(this.parent);
        }

        if (button.id == this.goToCurseForge.id) {
            try {
                openWebLink(new URI(CfgModpack.MODPACK.modpackDownload));
            } catch (URISyntaxException e) {
                EnderModpackTweaks.LOGGER.error("Unable to open the Modpack page! Maybe check your Modpack URL in config/endermodpacktweaks/modpack.cfg?");
                EnderModpackTweaks.LOGGER.error("Error: {}", String.valueOf(e));
            }
        }

        super.actionPerformed(button);
    }

    private void openWebLink(URI url) {
        try {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, url);
        } catch (Throwable throwable) {
            Throwable e = throwable.getCause();
            EnderModpackTweaks.LOGGER.error("Couldn't open link: {}", e == null ? "<UNKNOWN>" : e.getMessage());
        }
    }
}

