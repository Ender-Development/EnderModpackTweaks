package io.enderdev.endermodpacktweaks.mixin.fluxnetworks;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sonar.fluxnetworks.common.tileentity.energy.TileIC2Energy;

@Mixin(value = TileIC2Energy.class, remap = false)
public class TileFluxIC2Mixin {
    @ModifyReturnValue(method = "getSinkTier", at = @At("RETURN"))
    private int getSinkTier(int tier) {
        return CfgTweaks.FLUX_NETWORKS.fixIC2EnergyLimit ? Integer.MAX_VALUE : CfgTweaks.FLUX_NETWORKS.ic2SinkTier;
    }

    @ModifyReturnValue(method = "getSourceTier", at = @At("RETURN"))
    private int getSourceTier(int tier) {
        return CfgTweaks.FLUX_NETWORKS.fixIC2EnergyLimit ? Integer.MAX_VALUE : CfgTweaks.FLUX_NETWORKS.ic2SourceTier;
    }
}
