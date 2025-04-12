package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import io.enderdev.endermodpacktweaks.features.worldgen.BetterEndPodium;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Mixin(WorldGenEndPodium.class)
public abstract class WorldGenEndPodiumMixin extends WorldGenerator {

    @Mutable
    @Final
    @Shadow
    private boolean activePortal;

    @WrapMethod(method = "generate")
    private boolean generate(World worldIn, Random rand, BlockPos position, Operation<Boolean> original) {
        activePortal = !CfgMinecraft.DRAGON.disablePortal && this.activePortal;
        if (CfgMinecraft.END_PODIUM.replacePortal) {
            BetterEndPodium betterEndPodium = new BetterEndPodium(activePortal);
            if (betterEndPodium.generate(worldIn, rand, position)) {
                return true;
            }
            EnderModpackTweaks.LOGGER.warn("Failed to generate the end portal, generating the default one.");
        }
        return original.call(worldIn, rand, position);
    }

    @ModifyArg(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/WorldGenEndPodium;setBlockAndNotifyAdequately(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)V"), index = 2)
    private IBlockState generateEndPodium(IBlockState iBlockState) {
        Block newBedrock = Block.getBlockFromName(CfgMinecraft.END_PODIUM.bedrock);
        Block newAir = Block.getBlockFromName(CfgMinecraft.END_PODIUM.air);
        Block newEndstone = Block.getBlockFromName(CfgMinecraft.END_PODIUM.endStone);
        Block newTorch = Block.getBlockFromName(CfgMinecraft.END_PODIUM.torch);

        if (iBlockState.getBlock() == Blocks.BEDROCK) {
            return newBedrock == null ? Blocks.BEDROCK.getDefaultState() : newBedrock.getDefaultState();
        } else if (iBlockState.getBlock() == Blocks.AIR) {
            return newAir == null ? Blocks.AIR.getDefaultState() : newAir.getDefaultState();
        } else if (iBlockState.getBlock() == Blocks.END_STONE) {
            return newEndstone == null ? Blocks.END_STONE.getDefaultState() : newEndstone.getDefaultState();
        } else if (iBlockState.getBlock() == Blocks.TORCH) {
            EnumFacing enumfacing = iBlockState.getValue(BlockTorch.FACING);
            if (newTorch instanceof BlockTorch) {
                return newTorch.getDefaultState().withProperty(BlockTorch.FACING, enumfacing);
            }
            return newTorch == null ? Blocks.TORCH.getDefaultState() : newTorch.getDefaultState();
        }

        return iBlockState;
    }
}
