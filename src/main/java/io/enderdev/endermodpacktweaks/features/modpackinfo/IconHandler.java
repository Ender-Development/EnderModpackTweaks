package io.enderdev.endermodpacktweaks.features.modpackinfo;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.nio.file.Paths;

@SideOnly(Side.CLIENT)
public class IconHandler {
    public static void changeIcon() {
        final File icon = Paths.get(Minecraft.getMinecraft().gameDir.getAbsolutePath(), CfgModpack.CUSTOMIZATION.windowIconPath).toFile();
        if (icon.exists() && !icon.isDirectory()) {
            EnderModpackTweaks.LOGGER.debug("Found modpack icon!");
            Display.setIcon(IconLoader.load(icon));
        } else {
            EnderModpackTweaks.LOGGER.error("Unable to find modpack icon at {}!", icon.getAbsolutePath());
        }
    }
}

