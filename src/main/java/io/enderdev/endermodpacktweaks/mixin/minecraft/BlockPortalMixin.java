package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockPortal.class)
public class BlockPortalMixin {
    @WrapMethod(method = "onEntityCollision")
    private void wrapCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn, Operation<Void> original) {
        if (!CfgMinecraft.NETHER_PORTAL.disallowTraverse) {
            original.call(worldIn, pos, state, entityIn);
        }
    }
}
