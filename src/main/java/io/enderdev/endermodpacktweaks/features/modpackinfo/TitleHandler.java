package io.enderdev.endermodpacktweaks.features.modpackinfo;

import io.enderdev.endermodpacktweaks.config.CfgModpack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public final class TitleHandler {
    public static String getTitle() {
        Minecraft.getMinecraft().gameSettings.loadOptions();
        String language = Minecraft.getMinecraft().gameSettings.language;
        String[] titles = CfgModpack.CUSTOMIZATION.windowTitleFormat;
        // This is just here so the game doesn't crash when updating from a previous version.
        if (titles == null || titles.length == 0) titles = new String[]{"en_us;[name] ([version]) by [author]"};
        return Arrays.stream(titles)
                .filter(entry -> entry.split(";")[0].equals(language) && entry.split(";").length == 2)
                .findFirst()
                .orElse(titles[0])
                .split(";")[1]
                .replace("[name]", CfgModpack.MODPACK.modpackName)
                .replace("[version]", CfgModpack.MODPACK.modpackVersion)
                .replace("[author]", CfgModpack.MODPACK.modpackAuthor);
    }
}
