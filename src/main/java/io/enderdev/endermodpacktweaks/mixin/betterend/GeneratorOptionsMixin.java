package io.enderdev.endermodpacktweaks.mixin.betterend;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = GeneratorOptions.class, remap = false)
public class GeneratorOptionsMixin {
    @ModifyReturnValue(method = "replacePortal", at = @At("RETURN"))
    private static boolean replacePortal(boolean original) {
        return !CfgMinecraft.END_PODIUM.replacePortal;
    }
}
