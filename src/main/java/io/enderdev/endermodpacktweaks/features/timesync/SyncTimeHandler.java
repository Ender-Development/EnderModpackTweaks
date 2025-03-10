package io.enderdev.endermodpacktweaks.features.timesync;

import java.time.LocalTime;

public class SyncTimeHandler {
    public static final SyncTimeHandler INSTANCE = new SyncTimeHandler();

    private LocalTime realTime;

    private SyncTimeHandler() {
        realTime = LocalTime.now();
    }

    public int getMappedTime() {
        return (int) ((getTotalSeconds() / 86400.0) * 24000);
    }

    private int getTotalSeconds() {
        realTime = LocalTime.now();
        return (realTime.getHour() * 3600) + (realTime.getMinute() * 60) + realTime.getSecond();
    }
}
