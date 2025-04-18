package io.enderdev.endermodpacktweaks.features.playerpotions;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VanillaHandler {
    public final PotionHandler healthPotionHandler = new PotionHandler(CfgFeatures.PLAYER_EFFECTS.healthPotions, 0, 100);
    public final PotionHandler hungerPotionHandler = new PotionHandler(CfgFeatures.PLAYER_EFFECTS.hungerPotions, 0, 20);

    @SubscribeEvent
    public void potionPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.getEntityWorld().isRemote) {
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
}
