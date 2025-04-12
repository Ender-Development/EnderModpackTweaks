package io.enderdev.endermodpacktweaks.config;

public enum EnumGamemode {
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    private final int value;

    EnumGamemode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
