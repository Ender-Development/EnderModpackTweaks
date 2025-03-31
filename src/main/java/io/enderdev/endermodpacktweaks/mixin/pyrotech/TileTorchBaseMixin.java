package io.enderdev.endermodpacktweaks.mixin.pyrotech;

import com.codetaylor.mc.pyrotech.modules.ignition.tile.spi.TileTorchBase;
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

@Mixin(value = TileTorchBase.InteractionUseItemToActivate.class, remap = false)
public class TileTorchBaseMixin {
    @WrapMethod(method = "allowInteraction(Lcom/codetaylor/mc/pyrotech/modules/ignition/tile/spi/TileTorchBase;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Z")
    private boolean allowInteraction(TileTorchBase tile, World world, BlockPos hitPos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing hitSide, float hitX, float hitY, float hitZ, Operation<Boolean> original) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.isEmpty()) {
            return false;
        }
        if (heldItem.getItem() instanceof ItemFlintAndSteel) {
            return true;
        } else {
            return original.call(tile, world, hitPos, state, player, hand, hitSide, hitX, hitY, hitZ);
        }
    }
}
