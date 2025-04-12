package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Mixin(WorldGenEndIsland.class)
public class WorldGenEndIslandMixin {
    @ModifyArg(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/WorldGenEndIsland;setBlockAndNotifyAdequately(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)V"), index = 2)
    private IBlockState modifyBlockState(IBlockState par3) {
        Block newBlock = Block.getBlockFromName(CfgMinecraft.END_ISLAND.endStone);
        return newBlock == null ? par3 : newBlock.getDefaultState();
    }

    @WrapOperation(method = "generate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0))
    private int modifySize(Random instance, int i, Operation<Integer> original) {
        return instance.nextInt(3) + CfgMinecraft.END_ISLAND.islandSize;
    }
}
