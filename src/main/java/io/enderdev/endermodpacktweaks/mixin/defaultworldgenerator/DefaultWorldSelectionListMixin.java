package io.enderdev.endermodpacktweaks.mixin.defaultworldgenerator;

import com.ezrol.terry.minecraft.defaultworldgenerator.gui.DefaultWorldSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = DefaultWorldSelectionList.class, remap = false)
public class DefaultWorldSelectionListMixin {

    @ModifyArgs(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiButton;<init>(IIIIILjava/lang/String;)V"))
    private void modifyButtonH(Args args) {
        DefaultWorldSelectionList self = (DefaultWorldSelectionList) (Object) this;
        int width = 320;

        args.set(3, width);
        args.set(1, self.width / 2 - width / 2);
    }
}
