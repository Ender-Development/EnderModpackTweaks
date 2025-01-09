package io.enderdev.endermodpacktweaks.mixin.quark;

import io.enderdev.endermodpacktweaks.EMTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.client.feature.UsageTicker;

@Mixin(value = UsageTicker.TickerElement.class, remap = false)
public class UsageTickerMixin {
    @Shadow
    public int liveTicks;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (!EMTConfig.QUARK.alwaysShowUsageTicker) return;
        this.liveTicks = 50;
    }
}
