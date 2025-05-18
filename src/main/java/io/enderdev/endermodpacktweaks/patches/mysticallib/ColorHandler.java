package io.enderdev.endermodpacktweaks.patches.mysticallib;

import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import io.enderdev.endermodpacktweaks.utils.EmtColor;

import java.awt.Color;

public class ColorHandler {
    private static String colorStr;
    private static Color color;

    public static Color getColor() {
        if (!CfgTweaks.CRISSAEGRIM.color.equals(colorStr)) {
            colorStr = CfgTweaks.CRISSAEGRIM.color;
            color = EmtColor.parseColorFromHexString(colorStr);
        }
        return color;
    }
}
