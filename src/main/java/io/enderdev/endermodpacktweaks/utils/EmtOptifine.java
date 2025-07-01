package io.enderdev.endermodpacktweaks.utils;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

// Reference: <https://github.com/Red-Studio-Ragnarok/Red-Core/blob/cc3b67cb66c9f84ddc52404771145b13734da714/mc/1-8---1-12/src/main/java/dev/redstudio/redcore/utils/OptiNotFine.java>
@SideOnly(Side.CLIENT)
public final class EmtOptifine {
    private static final String OPTIFINE_CLASS = "optifine.Installer";
    private static final String SHADERS_CLASS = "net.optifine.shaders.Shaders";
    private static final String SHADER_PACK_LOADED_FIELD = "shaderPackLoaded";

    private static boolean checkedOptiFineInstalled = false;
    private static boolean isOptiFineInstalled = true;

    private static MethodHandle shaderPackLoadedHandle = null;

    public static boolean isOptiFineInstalled() {
        if (!checkedOptiFineInstalled) {
            isOptiFineInstalled = EmtClass.isClassPresent(OPTIFINE_CLASS);
            checkedOptiFineInstalled = true;
        }
        return isOptiFineInstalled;
    }

    public static boolean shadersEnabled() {
        if (!isOptiFineInstalled()) {
            return false;
        }
        try {
            if (shaderPackLoadedHandle == null) {
                final Field shaderPackLoadedField = Class.forName(SHADERS_CLASS).getDeclaredField(SHADER_PACK_LOADED_FIELD);
                shaderPackLoadedHandle = MethodHandles.lookup().unreflectGetter(shaderPackLoadedField);
            }
            return (boolean) shaderPackLoadedHandle.invoke();
        } catch (Throwable exception) {
            EnderModpackTweaks.LOGGER.error("Could not get OptiFine shaders status.");
            EnderModpackTweaks.LOGGER.error("If shaders are enabled things might break");
            EnderModpackTweaks.LOGGER.error(exception.getMessage());
            return false;
        }
    }
}
