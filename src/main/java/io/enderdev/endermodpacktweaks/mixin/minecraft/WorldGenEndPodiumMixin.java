package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.config.EMTConfigMinecraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorldGenEndPodium.class)
public abstract class WorldGenEndPodiumMixin extends WorldGenerator {

    @Final
    @Shadow
    private boolean activePortal;

    /**
     * @author _MasterEnderman_
     * @reason In over 10 years of playing this game I never encountered a single mod that tweaked the end pedestal.
     * Yes now there are some, but only for versions after 1.12.2. so I do not care enough to fix all of this with multiple mixins.
     */
    @Overwrite
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(new BlockPos(position.getX() - 4, position.getY() - 1, position.getZ() - 4), new BlockPos(position.getX() + 4, position.getY() + 32, position.getZ() + 4))) {
            double d0 = blockpos$mutableblockpos.getDistance(position.getX(), blockpos$mutableblockpos.getY(), position.getZ());

            if (d0 <= 3.5D) {
                if (blockpos$mutableblockpos.getY() < position.getY()) {
                    if (d0 <= 2.5D) {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.bedrock).getDefaultState());
                    } else if (blockpos$mutableblockpos.getY() < position.getY()) {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.endStone).getDefaultState());
                    }
                } else if (blockpos$mutableblockpos.getY() > position.getY()) {
                    this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.air).getDefaultState());
                } else if (d0 > 2.5D) {
                    this.setBlockAndNotifyAdequately(worldIn, blockpos$mutableblockpos, Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.bedrock).getDefaultState());
                } else if (activePortal) {
                    this.setBlockAndNotifyAdequately(worldIn, new BlockPos(blockpos$mutableblockpos), Blocks.END_PORTAL.getDefaultState());
                } else {
                    this.setBlockAndNotifyAdequately(worldIn, new BlockPos(blockpos$mutableblockpos), Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.air).getDefaultState());
                }
            }
        }

        for (int i = 0; i < 4; ++i) {
            this.setBlockAndNotifyAdequately(worldIn, position.up(i), Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.bedrock).getDefaultState());
        }

        BlockPos blockpos = position.up(2);

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            if (Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.torch) instanceof BlockTorch) {
                this.setBlockAndNotifyAdequately(worldIn, blockpos.offset(enumfacing), Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.torch).getDefaultState().withProperty(BlockTorch.FACING, enumfacing));
            } else {
                this.setBlockAndNotifyAdequately(worldIn, blockpos.offset(enumfacing), Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.torch).getDefaultState());
            }
        }

        return true;
    }
}
