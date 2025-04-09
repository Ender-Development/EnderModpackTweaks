package io.enderdev.endermodpacktweaks.utils;

import java.awt.*;

public class EmtColor {
     public static Color parseColor(String colorStr) {
        if (colorStr == null || colorStr.length() != 9 || colorStr.charAt(0) != '#') {
            throw new IllegalArgumentException("Invalid color format");
        }

        String hex = colorStr.substring(1);
        int red = Integer.parseInt(hex.substring(0, 2), 16);
        int green = Integer.parseInt(hex.substring(2, 4), 16);
        int blue = Integer.parseInt(hex.substring(4, 6), 16);
        int alpha = Integer.parseInt(hex.substring(6, 8), 16);

        return new Color(red, green, blue, alpha);
    }
}
