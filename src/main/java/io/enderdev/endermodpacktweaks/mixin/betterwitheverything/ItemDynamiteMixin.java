package io.enderdev.endermodpacktweaks.mixin.betterwitheverything;

import betterwithmods.common.items.ItemDynamite;
import betterwithmods.util.InvUtils;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ItemDynamite.class, remap = false)
public class ItemDynamiteMixin {
    @WrapMethod(method = "getActivator")
    private ItemStack checkForAllFlintAndSteel(EntityPlayer player, EnumHand hand, Operation<ItemStack> original) {
        if (ItemDynamite.needsOffhand) {
            ItemStack otherHand = player.getHeldItem(InvUtils.otherHand(hand));
            return (!otherHand.isEmpty() && otherHand.getItem() instanceof ItemFlintAndSteel) ? otherHand : ItemStack.EMPTY;
        }
        return enderModpackTweaks$checkInventoryForFlintAndSteel(InvUtils.getPlayerInventory(player, null));
    }

    @Unique
    private ItemStack enderModpackTweaks$checkInventoryForFlintAndSteel(IItemHandler inventory) {
        for(int slot = 0; slot < inventory.getSlots(); ++slot) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemFlintAndSteel) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
