package io.enderdev.endermodpacktweaks.events;

import com.codetaylor.mc.pyrotech.modules.tech.basic.block.BlockKilnPit;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PyrotechEvents {
    @SubscribeEvent
    public void onBlockActivated(PlayerInteractEvent.RightClickBlock event) {
        if (event.getItemStack().getItem() instanceof ItemFlintAndSteel && event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockKilnPit) {
            if (event.getItemStack().getItem() != Items.FLINT_AND_STEEL) {
                event.getItemStack().damageItem(1, event.getEntityPlayer());
            }
            event.getWorld().setBlockState(event.getPos().up(), Blocks.FIRE.getDefaultState(), 3);
        }
    }
}
