package io.enderdev.endermodpacktweaks.features.timesync;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TimeEventHandler {
    private int tickCounter = 0;

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void worldSyncTime(TickEvent.WorldTickEvent event) {
        tickCounter++;
        if (tickCounter < 100) {
            return;
        }
        int time = SyncTimeHandler.INSTANCE.getMappedTime();
        int days = (int) (event.world.getTotalWorldTime() / 24000);
        event.world.setWorldTime((long) time + (days * 24000L));
        tickCounter = 0;
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onWorldLoad(WorldEvent.Load event) {
        World world = event.getWorld();
        world.getWorldInfo().getGameRulesInstance().setOrCreateGameRule("doDaylightCycle", "false");
    }

    @SubscribeEvent
    public void cancelSleep(SleepingTimeCheckEvent event) {
        if (CfgFeatures.SYNC_TIME.enable && CfgFeatures.SYNC_TIME.sleeping) {
            event.setResult(Event.Result.DENY);
        }
    }
}
