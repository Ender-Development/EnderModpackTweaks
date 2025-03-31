package io.enderdev.endermodpacktweaks.mixin.pyrotech;

import com.codetaylor.mc.pyrotech.library.util.Util;
import com.codetaylor.mc.pyrotech.modules.tech.refractory.event.RightClickBlockEventHandler;
import com.codetaylor.mc.pyrotech.modules.tech.refractory.util.RefractoryIgnitionHelper;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RightClickBlockEventHandler.class, remap = false)
public class RightClickBlockEventHandlerMixin {
    @WrapMethod(method = "onRightClickBlockEvent")
    private void onRightclickBlockEvent(PlayerInteractEvent.RightClickBlock event, Operation<Void> original) {
        ItemStack itemStack = event.getItemStack();
        BlockPos pos = event.getPos();
        World world = event.getWorld();
        Item item = itemStack.getItem();
        if ((item instanceof ItemFlintAndSteel || item == Items.FIRE_CHARGE) && RefractoryIgnitionHelper.igniteBlocks(world, pos)) {
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, Util.RANDOM.nextFloat() * 0.4F + 0.8F);
            event.setUseItem(Event.Result.ALLOW);
        }
    }
}
