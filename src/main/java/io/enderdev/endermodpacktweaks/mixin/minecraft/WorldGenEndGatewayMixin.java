package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.features.BetterEndGateway;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Mixin(WorldGenEndGateway.class)
public class WorldGenEndGatewayMixin {
    @WrapMethod(method = "generate")
    private boolean generate(World worldIn, Random rand, BlockPos position, Operation<Boolean> original) {
        if (EMTConfig.MINECRAFT.DRAGON.disableGateway) {
            return false;
        }
        if (EMTConfig.MINECRAFT.END_GATEWAY.replaceGateway) {
            BetterEndGateway betterEndGateway = new BetterEndGateway();
            if (betterEndGateway.generate(worldIn, rand, position)) {
                return true;
            }
            EnderModpackTweaks.LOGGER.warn("Failed to generate the end gateway, generating the default one.");
        }
        return original.call(worldIn, rand, position);
    }

    @ModifyArg(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/WorldGenEndGateway;setBlockAndNotifyAdequately(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)V"), index = 2)
    private IBlockState generateEndGateway(IBlockState block) {
        Block newBedrock = Block.getBlockFromName(EMTConfig.MINECRAFT.END_GATEWAY.bedrock);
        Block newAir = Block.getBlockFromName(EMTConfig.MINECRAFT.END_GATEWAY.air);
        if (block.getBlock() == Blocks.BEDROCK) {
            return newBedrock == null ? Blocks.BEDROCK.getDefaultState() : newBedrock.getDefaultState();
        } else if (block.getBlock() == Blocks.AIR) {
            return newAir == null ? Blocks.AIR.getDefaultState() : newAir.getDefaultState();
        }
        return block;
    }
}
