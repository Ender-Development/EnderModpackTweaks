package io.enderdev.endermodpacktweaks.mixin.pickletweaks;

import com.blakebr0.pickletweaks.feature.FeatureSwordInfo;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = FeatureSwordInfo.class, remap = false)
public class FeatureSwordInfoMixin {
    @WrapOperation(method = "addSwordInfoTooltip", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z"))
    private boolean modifyIsKeyDown(int key, Operation<Boolean> original) {
        return EMTConfig.PICKLE_TWEAKS.alwaysShowSwordInfo || original.call(key);
    }
}
