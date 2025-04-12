package io.enderdev.endermodpacktweaks.mixin.matteroverdrive;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import matteroverdrive.handler.TooltipHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TooltipHandler.class, remap = false)
public class TooltipHandlerMixin {
    @WrapOperation(method = "onItemTooltip", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z"))
    private boolean onItemTooltip(int key, Operation<Boolean> original) {
        return CfgTweaks.MATTER_OVERDRIVE.alwaysShowMatterInfo || original.call(key);
    }
}
