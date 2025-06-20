package io.enderdev.endermodpacktweaks.mixin.arcaneworld;

import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.arcaneworld.block.tilentity.TileEntityLevitator;

@Mixin(value = TileEntityLevitator.class, remap = false)
public class TileEntityLevitatorMixin {
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void redstoneControl(CallbackInfo ci) {
        TileEntityLevitator levitator = (TileEntityLevitator) (Object) this;
        if (CfgTweaks.ARCANE_WORLD.redstoneControl && levitator.getWorld().getRedstonePowerFromNeighbors(levitator.getPos()) != 0) {
            ci.cancel();
        }
    }
}
