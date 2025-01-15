package io.enderdev.endermodpacktweaks.features.crashinfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraftforge.fml.common.ICrashCallable;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InfoBuilder implements ICrashCallable {
    private final String name;
    private final StringBuilder builder = new StringBuilder();

    private String modpackName = EMTConfig.MODPACK.CRASH_INFO.modpackName;
    private String modpackVersion = EMTConfig.MODPACK.CRASH_INFO.modpackVersion;
    private String modpackAuthor = EMTConfig.MODPACK.CRASH_INFO.modpackAuthor;

    public InfoBuilder(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        boolean sourcedFromManifest = false;
        if (EMTConfig.MODPACK.CRASH_INFO.readFromManifest) {
            Gson gson = new GsonBuilder().create();
            File f = new File("manifest.json");
            if (!f.exists()) {
                EnderModpackTweaks.LOGGER.warn("manifest.json file not found, trying minecraftinstance.json");
                f = new File("minecraftinstance.json");
            }
            if (f.exists()) {
                try (FileReader reader = new FileReader(f)) {
                    ManifestInfo manifestInfo = gson.fromJson(reader, ManifestInfo.class);
                    if (manifestInfo != null) {
                        modpackName = manifestInfo.name;
                        modpackVersion = manifestInfo.version;
                        modpackAuthor = manifestInfo.author;
                        sourcedFromManifest = true;
                    }
                } catch (JsonSyntaxException | JsonIOException | IOException e) {
                    EnderModpackTweaks.LOGGER.error("Failed to read manifest file", e);
                }
            } else {
                EnderModpackTweaks.LOGGER.warn("Manifest file not found");
            }
        }

        builder.append("---\n");
        builder.append("Modpack Name: '").append(modpackName).append("'\n");
        builder.append("Modpack Version: '").append(modpackVersion).append("'\n");
        builder.append("Modpack Author: '").append(modpackAuthor).append("'\n");
        builder.append("---\n");

        if (!sourcedFromManifest && EMTConfig.MODPACK.CRASH_INFO.readFromManifest) {
            builder.append("!!! Failed to read manifest file, using config values instead. !!!\n");
        }

        return builder.toString();
    }

    @Override
    public String getLabel() {
        return name;
    }

    private static class ManifestInfo {
        String name = null;
        String version = null;
        String author = null;
    }
}
