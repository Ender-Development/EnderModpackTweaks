package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockPumpkin.class)
public class BlockPumpkinMixin {
    @Inject(method = "trySpawnGolem", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPumpkin;getGolemPattern()Lnet/minecraft/block/state/pattern/BlockPattern;", ordinal = 0), cancellable = true)
    private void preventIronGolem(World worldIn, BlockPos pos, CallbackInfo ci) {
        if (CfgMinecraft.GOLEM.disableIronGolem) {
            // Cancel the method to prevent the iron golem from spawning
            ci.cancel();
        }
    }

    @Inject(method = "trySpawnGolem", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPumpkin;getSnowmanPattern()Lnet/minecraft/block/state/pattern/BlockPattern;", ordinal = 1), cancellable = true)
    private void preventSnowGolem(World worldIn, BlockPos pos, CallbackInfo ci) {
        if (CfgMinecraft.GOLEM.disableSnowGolem) {
            // Cancel the method to prevent the snow golem from spawning
            ci.cancel();
        }
    }
}
