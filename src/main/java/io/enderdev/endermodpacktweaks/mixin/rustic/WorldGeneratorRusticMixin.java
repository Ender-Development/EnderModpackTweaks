package io.enderdev.endermodpacktweaks.mixin.rustic;

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
    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/world/WorldType;FLAT:Lnet/minecraft/world/WorldType;"), remap = true)
    private WorldType fixFlatWorldType(@Local(argsOnly = true) World world) {
        if (EMTConfig.RUSTIC.enableWorldGenInFlat && world.getWorldType() == WorldType.FLAT) {
            return WorldType.DEFAULT;
        }
        return world.getWorldType();
    }
}
