package io.enderdev.endermodpacktweaks.mixin.pickletweaks;

import com.blakebr0.pickletweaks.feature.FeatureBowInfo;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = FeatureBowInfo.class, remap = false)
public class FeatureBowInfoMixin {
    // wrong method name, I guess he just copy pasted the code from the FeatureSwordInfo.class
    @WrapOperation(method = "addSwordInfoTooltip", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z"))
    private boolean modifyIsKeyDown(int key, Operation<Boolean> original) {
        return CfgTweaks.PICKLE_TWEAKS.alwaysShowBowInfo || original.call(key);
    }
}
