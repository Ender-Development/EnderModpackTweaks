package io.enderdev.endermodpacktweaks.features.timesync;

public class SyncTimeHandler {
    public static final SyncTimeHandler INSTANCE = new SyncTimeHandler();

    private SyncTimeHandler() {}

    public int getMappedTime() {
        return (int) ((getTotalSeconds() / 86400.0) * 24000);
    }

    private int getTotalSeconds() {
        return (int) (System.currentTimeMillis() / 1000L);
    }
}
