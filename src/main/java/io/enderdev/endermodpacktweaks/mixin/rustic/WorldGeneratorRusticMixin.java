package io.enderdev.endermodpacktweaks.mixin.rustic;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rustic.common.world.WorldGeneratorRustic;

@Mixin(value = WorldGeneratorRustic.class, remap = false)
public class WorldGeneratorRusticMixin {
    @ModifyExpressionValue(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getWorldType()Lnet/minecraft/world/WorldType;"), remap = true)
    private WorldType modifyWorldType(WorldType original) {
        if (EMTConfig.RUSTIC.enableWorldGenInFlat && original == WorldType.FLAT) {
            return WorldType.DEFAULT;
        }
        return original;
    }
}
