package io.enderdev.endermodpacktweaks.mixin.pyrotech;

import com.codetaylor.mc.athenaeum.interaction.spi.ITileInteractable;
import com.codetaylor.mc.pyrotech.library.InteractionUseItemToActivateWorker;
import com.codetaylor.mc.pyrotech.library.spi.tile.ITileWorker;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = InteractionUseItemToActivateWorker.class, remap = false)
public class InteractionUseItemToActivateWorkerMixin<T extends TileEntity & ITileInteractable & ITileWorker> {
    @Shadow
    @Final
    private Item item;

    @WrapMethod(method = "allowInteraction")
    private boolean allowInteraction(T tile, World world, BlockPos hitPos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing hitSide, float hitX, float hitY, float hitZ, Operation<Boolean> original) {
        return item == Items.FLINT_AND_STEEL ? player.getHeldItem(hand).getItem() instanceof ItemFlintAndSteel : player.getHeldItem(hand).getItem() == item;
    }
}
