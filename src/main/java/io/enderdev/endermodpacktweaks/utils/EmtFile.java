package io.enderdev.endermodpacktweaks.utils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class EmtFile {
    public static File getFile(String path) {
        return Paths.get(EmtSide.isClient() ? Minecraft.getMinecraft().gameDir.getAbsolutePath() : FMLServerHandler.instance().getSavesDirectory().getParentFile().getAbsolutePath(), path).toFile();
    }

    public static InputStream getInputStream(String path) throws IOException {
        return Files.newInputStream(EmtFile.getFile(path).toPath(), StandardOpenOption.READ);
    }

    public static InputStream getInputStream(File file) throws IOException {
        return Files.newInputStream(file.toPath(), StandardOpenOption.READ);
    }
}
