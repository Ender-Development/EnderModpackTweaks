package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.features.worldgen.BetterEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(ChunkGeneratorEnd.class)
public class ChunkGeneratorEndMixin {
    @WrapOperation(method = "populate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/WorldGenEndGateway;generate(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean populateEndGateway(WorldGenEndGateway instance, World flag1, Random flag2, BlockPos flag3, Operation<Boolean> original) {
        if (EMTConfig.MINECRAFT.DRAGON.disableGateway) {
            return false;
        }
        if (EMTConfig.MINECRAFT.END_GATEWAY.enable && EMTConfig.MINECRAFT.END_GATEWAY.replaceGateway) {
            return (new BetterEndGateway()).generate(flag1, new Random(), flag3);
        }
        return original.call(instance, flag1, flag2, flag3);
    }
}
