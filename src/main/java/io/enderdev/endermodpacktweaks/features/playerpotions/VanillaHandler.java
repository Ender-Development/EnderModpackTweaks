package io.enderdev.endermodpacktweaks.features.playerpotions;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.utils.EmtPotionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VanillaHandler {
    public final EmtPotionHandler healthPotionHandler = new EmtPotionHandler(CfgFeatures.PLAYER_EFFECTS.healthPotions, 0, 100);
    public final EmtPotionHandler hungerPotionHandler = new EmtPotionHandler(CfgFeatures.PLAYER_EFFECTS.hungerPotions, 0, 20);

    @SubscribeEvent
    public void cancelSleep(SleepingTimeCheckEvent event) {
        if (CfgFeatures.SYNC_TIME.enable && CfgFeatures.SYNC_TIME.sleeping) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void potionPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!CfgFeatures.PLAYER_EFFECTS.enable) {
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
