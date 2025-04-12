package io.enderdev.endermodpacktweaks.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgModpack;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EmtModpackInfo {
    private String modpackName = CfgModpack.MODPACK.modpackName;
    private String modpackVersion = CfgModpack.MODPACK.modpackVersion;
    private String modpackAuthor = CfgModpack.MODPACK.modpackAuthor;

    private boolean sourcedFromManifest = false;
    private boolean readFromManifest = true;

    public EmtModpackInfo(boolean readFromManifest) {
        if (!readFromManifest || !EmtSide.isClient()) {
            this.readFromManifest = false;
            return;
        }
        File f = new File("manifest.json");
        if (!f.exists()) {
            EnderModpackTweaks.LOGGER.warn("manifest.json file not found, trying minecraftinstance.json");
            f = new File("minecraftinstance.json");
        }
        if (f.exists()) {
            try (FileReader reader = new FileReader(f)) {
                Gson gson = new GsonBuilder().create();
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

    public boolean error() {
        return !sourcedFromManifest && readFromManifest;
    }

    public String getName() {
        return modpackName;
    }

    public String getVersion() {
        return modpackVersion;
    }

    public String getAuthor() {
        return modpackAuthor;
    }

    private static class ManifestInfo {
        String name = null;
        String version = null;
        String author = null;
    }
}
