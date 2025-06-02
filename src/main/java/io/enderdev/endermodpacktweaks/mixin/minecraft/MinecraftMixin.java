package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.features.modpackinfo.IconHandler;
import io.enderdev.endermodpacktweaks.features.modpackinfo.TitleHandler;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.FileNotFoundException;

@Mixin(value = Minecraft.class)
public class MinecraftMixin {
    @WrapOperation(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    private void changeWindowTitle(String newTitle, Operation<Void> original) {
        if (CfgModpack.CUSTOMIZATION.windowTitle) {
            Display.setTitle(TitleHandler.getTitle());
        } else {
            original.call(newTitle);
        }
    }

    @WrapMethod(method = "setWindowIcon")
    private void changeWindowIcon(Operation<Void> original) {
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
