package io.enderdev.endermodpacktweaks.mixin.pyrotech;

import com.codetaylor.mc.pyrotech.modules.tech.basic.block.BlockKilnPit;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockKilnPit.class, remap = false)
public class BlockKilnPitMixin {
    @WrapMethod(method = "onBlockActivated")
    private boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, Operation<Boolean> original) {
        ItemStack heldItem = player.getHeldItemMainhand();
        return !(heldItem.getItem() instanceof ItemFlintAndSteel) && original.call(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }
}
