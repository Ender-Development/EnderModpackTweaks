package io.enderdev.endermodpacktweaks.features.healthbar;

public class HealthBarData {
    public String name;
    // 0 for the top one. 1 for the second one and so on
    public int ridingStackPos;

    public HealthBarData(String name, int ridingStackPos) {
        this.name = name;
        this.ridingStackPos = ridingStackPos;
    }
}
