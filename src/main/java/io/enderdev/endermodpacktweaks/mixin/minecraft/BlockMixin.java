package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.utils.EmtOreDict;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Block.class)
public class BlockMixin {
    @Inject(method = "canEntityDestroy", at = @At("HEAD"), cancellable = true, remap = false)
    private void canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof EntityDragon)
            cir.setReturnValue(!EmtOreDict.hasOreDictionary(state, EmtOreDict.PROOF_ENDER_DRAGON));
        else if (entity instanceof EntityWither || entity instanceof EntityWitherSkull)
            cir.setReturnValue(!EmtOreDict.hasOreDictionary(state, EmtOreDict.PROOF_WITHER));
    }
}
