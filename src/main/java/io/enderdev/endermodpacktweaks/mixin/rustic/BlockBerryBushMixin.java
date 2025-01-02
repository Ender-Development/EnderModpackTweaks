package io.enderdev.endermodpacktweaks.mixin.rustic;

import com.google.common.collect.ImmutableList;
import io.enderdev.endermodpacktweaks.config.EMTConfigMods;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rustic.common.blocks.crops.BlockBerryBush;

import java.util.Arrays;
import java.util.Objects;

@Mixin(value = BlockBerryBush.class, remap = false)
public abstract class BlockBerryBushMixin {
    @Shadow protected abstract boolean canSustainBush(IBlockState state);

    @Unique
    @Final
    private static final ImmutableList<Block> enderModpackTweaks$validBlocks = Arrays.stream(EMTConfigMods.RUSTIC.listBerryBushBlocks)
            .map(Block::getBlockFromName)
            .filter(Objects::nonNull)
            .collect(ImmutableList.toImmutableList());

    @Inject(method = "canSustainBush", at = @At("HEAD"), cancellable = true)
    private void canSustainBushMixin(IBlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (EMTConfigMods.RUSTIC.overrideBerryBushPlacement) {
            cir.setReturnValue(enderModpackTweaks$validBlocks.contains(state.getBlock()));
            cir.cancel();
        }
    }

    @Redirect(method = "canPlaceBlockAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;canSustainPlant(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraftforge/common/IPlantable;)Z"))
    private boolean canPlaceBlockAtMixin(Block block, IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side, IPlantable plantable) {
        if (EMTConfigMods.RUSTIC.overrideBerryBushPlacement) {
            return canSustainBush(state);
        }
        return block.canSustainPlant(state, world, pos, side, plantable);
    }

    @Redirect(method = "canBlockStay", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;canSustainPlant(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraftforge/common/IPlantable;)Z"))
    private boolean canBlockStayMixin(Block block, IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side, IPlantable plantable) {
        if (EMTConfigMods.RUSTIC.overrideBerryBushPlacement) {
            return canSustainBush(state);
        }
        return block.canSustainPlant(state, world, pos, side, plantable);
    }
}
