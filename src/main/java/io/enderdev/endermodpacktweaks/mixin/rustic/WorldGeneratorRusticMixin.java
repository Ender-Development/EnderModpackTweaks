package io.enderdev.endermodpacktweaks.mixin.rustic;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraft.world.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import rustic.common.world.WorldGeneratorRustic;

@Mixin(value = WorldGeneratorRustic.class, remap = false)
public class WorldGeneratorRusticMixin {
    @ModifyExpressionValue(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getWorldType()Lnet/minecraft/world/WorldType;"), remap = true)
    private WorldType modifyWorldType(WorldType original) {
        if (CfgTweaks.RUSTIC.enableWorldGenInFlat && original == WorldType.FLAT) {
            return WorldType.DEFAULT;
        }
        return original;
    }
}
