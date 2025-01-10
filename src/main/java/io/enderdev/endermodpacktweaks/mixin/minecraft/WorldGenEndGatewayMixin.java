package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.features.BetterEndGateway;
import io.enderdev.endermodpacktweaks.features.BetterEndPodium;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(WorldGenEndGateway.class)
public class WorldGenEndGatewayMixin {
    @ModifyArg(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/state/IBlockState;"))
    private Block generateEndGateway(Block block) {
        Block newBedrock = Block.getBlockFromName(EMTConfig.MINECRAFT.END_GATEWAY.bedrock);
        Block newAir = Block.getBlockFromName(EMTConfig.MINECRAFT.END_GATEWAY.air);
        if (block == Blocks.BEDROCK) {
            return newBedrock == null ? Blocks.BEDROCK : newBedrock;
        } else if (block == Blocks.AIR) {
            return newAir == null ? Blocks.AIR : newAir;
        }
        return block;
    }

    @WrapMethod(method = "generate")
    private boolean generate(World worldIn, Random rand, BlockPos position, Operation<Boolean> original, @Local boolean flag) {
        if (EMTConfig.MINECRAFT.END_GATEWAY.replaceGateway) {
            BetterEndGateway betterEndPodium = new BetterEndGateway();
            if (betterEndPodium.generate(worldIn, rand, position)) {
                return true;
            }
            EnderModpackTweaks.LOGGER.warn("Failed to generate the end gateway, generating the default one.");
        }
        return original.call(worldIn, rand, position);
    }
}
