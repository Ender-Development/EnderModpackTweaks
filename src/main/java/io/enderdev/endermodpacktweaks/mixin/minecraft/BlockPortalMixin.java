package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockPortal.class)
public class BlockPortalMixin {
    /**
     * @author _MasterEnderman_
     * @reason Disallow Entities to enter Nether Portals
     */
    @Overwrite
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (EMTConfig.MINECRAFT.NETHER_PORTAL.disallowTraverse) {
            return;
        }
        if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
        {
            entityIn.setPortal(pos);
        }
    }
}
