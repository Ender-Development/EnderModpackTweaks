package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockEvents {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onPortalSpawnEvent(BlockEvent.PortalSpawnEvent event) {
        if (CfgMinecraft.NETHER_PORTAL.enable && !CfgMinecraft.NETHER_PORTAL.canBeCreated) {
            event.setCanceled(true);
        }
    }
}
