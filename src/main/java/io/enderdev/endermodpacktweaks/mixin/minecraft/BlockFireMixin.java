package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockFire.class)
public class BlockFireMixin {
    @Inject(method = "onBlockAdded", at = @At("HEAD"))
    private void onBlockAdded(World worldIn, BlockPos pos, IBlockState state, CallbackInfo ci) {
        if (CfgMinecraft.NETHER_PORTAL.canBeCreatedInEnd) {
            if (worldIn.provider.getDimension() == 1) {
                Blocks.PORTAL.trySpawnPortal(worldIn, pos);
            }
        }
    }
}
