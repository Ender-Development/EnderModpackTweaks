package io.enderdev.endermodpacktweaks.features.packupdater;

import com.google.gson.Gson;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.utils.EmtWeb;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.io.IOException;

public class Updater {
    private boolean needsUpdate = false;
    private String newVersion = "UNKNOWN";

    private Updater() {
    }

    public static Updater INSTANCE = new Updater();

    public void checkForUpdate() throws IOException, NumberFormatException {
        String json = EmtWeb.readUrl(CfgModpack.PACK_UPDATER.jsonUrl);

        Gson gson = new Gson();
        PackVersion versionFromJSON = gson.fromJson(json, PackVersion.class);

        DefaultArtifactVersion newVersion = new DefaultArtifactVersion(versionFromJSON.version);
        DefaultArtifactVersion currentVersion = new DefaultArtifactVersion(CfgModpack.MODPACK.modpackVersion);

        if (!newVersion.equals(currentVersion)) {
            needsUpdate = true;
        }

        this.newVersion = versionFromJSON.version;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public boolean doesNeedUpdate() {
        return needsUpdate;
    }

    private static class PackVersion {
        String version;
    }
}
