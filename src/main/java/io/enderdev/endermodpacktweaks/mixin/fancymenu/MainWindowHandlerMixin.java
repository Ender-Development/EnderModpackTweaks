package io.enderdev.endermodpacktweaks.mixin.fancymenu;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.features.modpackinfo.IconHandler;
import io.enderdev.endermodpacktweaks.features.modpackinfo.TitleHandler;
import org.spongepowered.asm.mixin.Mixin;

import java.io.FileNotFoundException;

@Mixin(targets = "de.keksuccino.fancymenu.mainwindow.MainWindowHandler", remap = false, priority = 1001)
public class MainWindowHandlerMixin {
    @WrapMethod(method = "getCustomWindowTitle")
    private static String emt$getCustomWindowTitle(Operation<String> original) {
        return CfgModpack.CUSTOMIZATION.windowTitle ? TitleHandler.getTitle() : original.call();
    }

    @WrapMethod(method = "updateWindowIcon")
    private static void emt$updateWindowIcon(Operation<Void> original) {
        if (CfgModpack.CUSTOMIZATION.windowIcon) {
            try {
                IconHandler.changeIcon();
            } catch (FileNotFoundException exception) {
                original.call();
            }
        } else {
            original.call();
        }
    }
}
