package io.enderdev.endermodpacktweaks.events;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BoneMealEvents {
    @SubscribeEvent
    public void onBoneMealUse(BonemealEvent event) {
        if (event.getWorld().isRemote) {
            return;
        }

        IBlockState blockState = event.getBlock();
        Block block = blockState.getBlock();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        if (block instanceof BlockCrops) {
            world.setBlockState(pos, ((BlockCrops) block).withAge(((BlockCrops) block).getMaxAge()), 2);
        } else if (block instanceof BlockStem) {
            world.setBlockState(pos, blockState.withProperty(BlockStem.AGE, 7), 2);
        } else if (block instanceof BlockSapling) {
            world.setBlockToAir(pos);
            ((BlockSapling) block).generateTree(world, pos, blockState, world.rand);
        } else {
            if (!(block instanceof IGrowable)) {
                return;
            }
            ((IGrowable) block).grow(world, world.rand, pos, blockState);
        }

        event.getEntityPlayer().swingArm(EnumHand.MAIN_HAND);
        if (!event.getEntityPlayer().capabilities.isCreativeMode) {
            event.getStack().shrink(1);
        }

        //event.setCanceled(true);
    }
}
