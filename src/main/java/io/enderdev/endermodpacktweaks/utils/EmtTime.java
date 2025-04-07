package io.enderdev.endermodpacktweaks.utils;

import net.minecraft.client.Minecraft;

public class EmtTime {
    private final double waitTime;
    private double deltaTime;
    private long lastTime;

    public EmtTime(double ticks) {
        this.lastTime = Minecraft.getSystemTime();
        this.waitTime = 1000 / ticks;
        this.deltaTime = 0;
    }

    public void update() {
        long currentTime = Minecraft.getSystemTime();
        this.deltaTime = (currentTime - lastTime) / waitTime;
        this.lastTime = currentTime;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public void decreaseDeltaTime(int value) {
        this.deltaTime -= value;
    }
}
