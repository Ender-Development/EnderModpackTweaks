package io.enderdev.endermodpacktweaks.features.timesync;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TimeEventHandler {
    private int tickCounter = 0;

    @SubscribeEvent
    public void worldSyncTime(TickEvent.WorldTickEvent event) {
        if (++tickCounter != 100) {
            return;
        }
        int time = SyncTimeHandler.INSTANCE.getMappedTime();
        long worldTime = event.world.getTotalWorldTime();
        long dayTime = worldTime - worldTime % 24000L;
        event.world.setWorldTime(dayTime + time);
        tickCounter = 0;
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        World world = event.getWorld();
        world.getWorldInfo().getGameRulesInstance().setOrCreateGameRule("doDaylightCycle", "false");
    }

    @SubscribeEvent
    public void cancelSleep(SleepingTimeCheckEvent event) {
        if (CfgFeatures.SYNC_TIME.sleeping) {
            event.setResult(Event.Result.DENY);
        }
    }
}
