package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEvents {
    @SubscribeEvent
    public void cancelSleep(SleepingTimeCheckEvent event) {
        if (EMTConfig.MODPACK.SYNC_TIME.sleeping) {
            event.setResult(Event.Result.DENY);
        }
    }
}
