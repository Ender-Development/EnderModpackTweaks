package io.enderdev.endermodpacktweaks.features.packupdater;

import com.google.gson.Gson;
import io.enderdev.endermodpacktweaks.config.CfgModpack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Updater {
    private boolean needsUpdate = false;
    private String newVersion = "UNKNOWN";

    private Updater() {}

    public static Updater INSTANCE = new Updater();

    public void checkForUpdate() throws IOException, NumberFormatException {
        String json = readUrl(CfgModpack.PACK_UPDATER.jsonUrl);

        Gson gson = new Gson();
        PackVersion versionFromJSON = gson.fromJson(json, PackVersion.class);

        String newVersion = versionFromJSON.version;
        String currentVersion = CfgModpack.MODPACK.modpackVersion;

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

    private String readUrl(String urlString) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
