package io.enderdev.endermodpacktweaks.features.additionalmastervolume;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
public class MasterVolumeHandler implements GuiPageButtonList.GuiResponder, GuiSlider.FormatHelper {
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiOptions) {
            int x = 0;
            int y = 0;
            for (GuiButton guiButton : event.getButtonList()) {
                if (guiButton instanceof GuiOptionSlider) {
                    x = guiButton.x;
                    y = guiButton.y;
                }
            }
            event.getButtonList().add(new GuiSlider(this, -999, x, y + 27, SoundCategory.MASTER.getName(), 0f, 1f, Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER), this));
        }
    }

    @Override
    public void setEntryValue(int id, boolean value) {
    }

    @Override
    public void setEntryValue(int id, float value) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.gameSettings.setSoundLevel(SoundCategory.MASTER, value);
        mc.gameSettings.saveOptions();
    }

    @Override
    public void setEntryValue(int id, @NotNull String value) {
    }

    @NotNull
    @Override
    public String getText(int id, @NotNull String name, float value) {
        float volume = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER);
        String displayVolume = volume == 0f ? I18n.format("options.off") : (int) (volume * 100f) + "%";
        return I18n.format("soundCategory." + SoundCategory.MASTER.getName()) + ": " + displayVolume;
    }
}
