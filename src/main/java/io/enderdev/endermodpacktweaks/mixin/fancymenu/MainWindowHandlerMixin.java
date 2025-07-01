package io.enderdev.endermodpacktweaks.mixin.fancymenu;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import de.keksuccino.fancymenu.mainwindow.MainWindowHandler;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.features.modpackinfo.IconHandler;
import io.enderdev.endermodpacktweaks.features.modpackinfo.TitleHandler;
import io.enderdev.endermodpacktweaks.utils.EmtSide;
import org.spongepowered.asm.mixin.Mixin;

import java.io.FileNotFoundException;

@Mixin(value = MainWindowHandler.class, remap = false, priority = 1001)
public class MainWindowHandlerMixin {
    @WrapMethod(method = "getCustomWindowTitle")
    private static String emt$getCustomWindowTitle(Operation<String> original) {
        // make sure we're on the client side as this is a client-only feature
        return CfgModpack.CUSTOMIZATION.windowTitle && EmtSide.isClient() ? TitleHandler.getTitle() : original.call();
    }

    @WrapMethod(method = "updateWindowIcon")
    private static void emt$updateWindowIcon(Operation<Void> original) {
        // make sure we're on the client side as this is a client-only feature
        if (CfgModpack.CUSTOMIZATION.windowIcon && EmtSide.isClient()) {
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
