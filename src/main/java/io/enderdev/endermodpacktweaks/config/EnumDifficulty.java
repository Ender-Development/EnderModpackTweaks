package io.enderdev.endermodpacktweaks.config;

public enum EnumDifficulty {
    PEACEFUL(0),
    EASY(1),
    NORMAL(2),
    HARD(3);

    private final int value;

    EnumDifficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
