package io.enderdev.endermodpacktweaks.utils;

public final class EmtClass {
    public static boolean isClassPresent(String className) {
        try {
            Class.forName(className, false, EmtClass.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
