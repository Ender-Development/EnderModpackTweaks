package io.enderdev.endermodpacktweaks.features.modpackinfo;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.utils.EmtFile;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.io.FileNotFoundException;

@SideOnly(Side.CLIENT)
public class IconHandler {
    public static void changeIcon() throws FileNotFoundException {
        final File icon = EmtFile.getFile(CfgModpack.CUSTOMIZATION.windowIconPath);
        if (icon.exists() && !icon.isDirectory()) {
            EnderModpackTweaks.LOGGER.debug("Found modpack icon!");
            Display.setIcon(IconLoader.load(icon));
        } else {
            EnderModpackTweaks.LOGGER.error("Unable to find modpack icon at {}!", icon.getAbsolutePath());
            throw new FileNotFoundException("Unable to find modpack icon!");
        }
    }
}

