package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.utils.SmoothDamp;

public class HealthBarData {
    public String name;
    // 0 for the top one. 1 for the second one and so on
    public int ridingStackPos;
    public SmoothDamp healthSmoothDamp;

    public HealthBarData(String name, int ridingStackPos) {
        this.name = name;
        this.ridingStackPos = ridingStackPos;
        healthSmoothDamp = null;
    }

    public HealthBarData(String name, int ridingStackPos, float smoothTime) {
        this.name = name;
        this.ridingStackPos = ridingStackPos;
        healthSmoothDamp = new SmoothDamp(0, 0, smoothTime);
    }
}
