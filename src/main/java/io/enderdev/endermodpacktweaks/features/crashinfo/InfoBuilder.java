package io.enderdev.endermodpacktweaks.features.crashinfo;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.utils.EmtModpackInfo;
import net.minecraftforge.fml.common.ICrashCallable;

public class InfoBuilder implements ICrashCallable {
    private final String name;
    private final StringBuilder builder = new StringBuilder();

    public InfoBuilder(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        EmtModpackInfo modpackInfo = new EmtModpackInfo(EMTConfig.MODPACK.CRASH_INFO.readFromManifest);

        builder.append("---\n");
        builder.append("Modpack Name: '").append(modpackInfo.getName()).append("'\n");
        builder.append("Modpack Version: '").append(modpackInfo.getVersion()).append("'\n");
        builder.append("Modpack Author: '").append(modpackInfo.getAuthor()).append("'\n");
        builder.append("---\n");

        if (modpackInfo.error()) {
            builder.append("!!! Failed to read manifest file, using config values instead. !!!\n");
        }

        return builder.toString();
    }

    @Override
    public String getLabel() {
        return name;
    }
}
