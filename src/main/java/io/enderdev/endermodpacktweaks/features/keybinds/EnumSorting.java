package io.enderdev.endermodpacktweaks.features.keybinds;

public enum EnumSorting {
    DEFAULT,
    AZ,
    ZA;

    public EnumSorting cycle() {
        switch (this) {
            case DEFAULT:
                return AZ;
            case AZ:
                return ZA;
            case ZA:
            default:
                return DEFAULT;
        }
    }

    public String getName() {
        switch (this) {
            case AZ:
                return "A->Z";
            case ZA:
                return "Z->A";
            case DEFAULT:
            default:
                return "Default";
        }
    }
}
