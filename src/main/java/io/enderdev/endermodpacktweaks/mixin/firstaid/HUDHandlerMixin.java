package io.enderdev.endermodpacktweaks.mixin.firstaid;

import com.llamalad7.mixinextras.sugar.Local;
import ichttt.mods.firstaid.FirstAidConfig;
import ichttt.mods.firstaid.client.HUDHandler;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = HUDHandler.class, remap = false)
public class HUDHandlerMixin {
    @ModifyArg(method = "renderOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0, remap = true), index = 0)
    private float modifyTranslateX(float x, @Local(argsOnly = true) ScaledResolution scaledResolution) {
        return CfgTweaks.FIRST_AID.centerHUD ? ((float) Math.floorDiv(scaledResolution.getScaledWidth(), 2) + FirstAidConfig.overlay.xOffset) : x;
    }
}
