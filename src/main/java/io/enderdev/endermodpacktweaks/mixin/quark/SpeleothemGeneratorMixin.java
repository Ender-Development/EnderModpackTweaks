package io.enderdev.endermodpacktweaks.mixin.quark;

import io.enderdev.endermodpacktweaks.EMTStore;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.quark.world.world.SpeleothemGenerator;

@Mixin(value = SpeleothemGenerator.class, remap = false)
public class SpeleothemGeneratorMixin {
    @Inject(method = "getSpeleothemType", at = @At("HEAD"), cancellable = true)
    private void getSpeleothemType(IBlockState state, CallbackInfoReturnable<Block> cir) {
        Block block = state.getBlock();
        if (block == Blocks.END_STONE) {
            cir.setReturnValue(EMTStore.Quark.endstone_speleothem);
        } else if (block == Blocks.OBSIDIAN) {
            cir.setReturnValue(EMTStore.Quark.obsidian_speleothem);
        }
    }
}
