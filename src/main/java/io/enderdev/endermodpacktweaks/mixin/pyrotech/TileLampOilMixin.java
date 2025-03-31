package io.enderdev.endermodpacktweaks.mixin.pyrotech;

import com.codetaylor.mc.pyrotech.modules.ignition.tile.TileLampOil;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TileLampOil.InteractionUseItemToActivate.class, remap = false)
public class TileLampOilMixin {
    @WrapMethod(method = "allowInteraction(Lcom/codetaylor/mc/pyrotech/modules/ignition/tile/TileLampOil;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumHand;Lnet/minecraft/util/EnumFacing;FFF)Z")
    private boolean allowInteraction(TileLampOil tile, net.minecraft.world.World world, net.minecraft.util.math.BlockPos hitPos, net.minecraft.block.state.IBlockState state, net.minecraft.entity.player.EntityPlayer player, net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing hitSide, float hitX, float hitY, float hitZ, com.llamalad7.mixinextras.injector.wrapoperation.Operation<Boolean> original) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.isEmpty() || tile.isActive()) {
            return false;
        }
        if (heldItem.getItem() instanceof ItemFlintAndSteel) {
            return true;
        } else {
            return original.call(tile, world, hitPos, state, player, hand, hitSide, hitX, hitY, hitZ);
        }
    }
}
