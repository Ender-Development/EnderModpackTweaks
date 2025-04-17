package io.enderdev.endermodpacktweaks.mixin.scalinghealth;

import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.silentchaos512.scalinghealth.client.HeartDisplayHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = HeartDisplayHandler.class, remap = false)
public class HeartDisplayHandlerMixin {
    @ModifyArg(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/silentchaos512/scalinghealth/client/HeartDisplayHandler;drawVanillaHearts(IZIFIIII[IIII)V"), index = 6)
    private int vanillaHeight(int height) {
        return height + CfgTweaks.SCALING_HEALTH.yOffset;
    }

    @ModifyArg(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/silentchaos512/scalinghealth/client/HeartDisplayHandler;drawTexturedModalRect(IIIIIII)V", remap = true), remap = false, index = 1)
    private int yOffset(int y) {
        return y + CfgTweaks.SCALING_HEALTH.yOffset;
    }
}
