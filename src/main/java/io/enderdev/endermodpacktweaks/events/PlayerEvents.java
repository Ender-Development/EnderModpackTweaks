package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.utils.EmtPotionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerEvents {
    public final EmtPotionHandler healthPotionHandler = new EmtPotionHandler(EMTConfig.MINECRAFT.PLAYER_EFFECTS.healthPotions, 0, 100);
    public final EmtPotionHandler hungerPotionHandler = new EmtPotionHandler(EMTConfig.MINECRAFT.PLAYER_EFFECTS.hungerPotions, 0, 20);

    @SubscribeEvent
    public void cancelSleep(SleepingTimeCheckEvent event) {
        if (EMTConfig.MODPACK.SYNC_TIME.enable && EMTConfig.MODPACK.SYNC_TIME.sleeping) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void potionPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!EMTConfig.MINECRAFT.PLAYER_EFFECTS.enable) {
            return;
        }

        EntityPlayer player = event.player;

        // Health
        int health = (int) ((player.getHealth() / player.getMaxHealth()) * 100);
        healthPotionHandler.apply(player, health);

        // Hunger
        int hunger = player.getFoodStats().getFoodLevel();
        hungerPotionHandler.apply(player, hunger);
    }

    @SubscribeEvent
    public void playerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayer player = event.player;
        healthPotionHandler.clear(player);
        hungerPotionHandler.clear(player);
    }
}
