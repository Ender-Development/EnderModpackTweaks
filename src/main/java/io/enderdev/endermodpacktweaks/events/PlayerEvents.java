package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.utils.EmtPotionData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerEvents {

    private final List<EmtPotionData> healthPotions = new ArrayList<>();
    private final List<EmtPotionData> hungerPotions = new ArrayList<>();

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
        int health = (int) ((player.getHealth() / player.getMaxHealth()) * 100);
        int hunger = player.getFoodStats().getFoodLevel();

        // Health
        if (healthPotions.isEmpty() && EMTConfig.MINECRAFT.PLAYER_EFFECTS.healthPotions.length != 0) {
            Arrays.stream(EMTConfig.MINECRAFT.PLAYER_EFFECTS.healthPotions).map(line -> line.split(";")).forEach(data -> {
                if (data.length == 4
                        && Integer.parseInt(data[0]) <= Integer.parseInt(data[1])
                        && Integer.parseInt(data[0]) <= 100
                        && Integer.parseInt(data[0]) >= 0
                        && Integer.parseInt(data[1]) <= 100
                        && Integer.parseInt(data[1]) >= 0
                ) {
                    try {
                        healthPotions.add(new EmtPotionData(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                    } catch (NumberFormatException e) {
                        EnderModpackTweaks.LOGGER.error(e);
                        EnderModpackTweaks.LOGGER.error("Error parsing health potion data: {}", Arrays.toString(data));
                    }
                }
            });
        }
        if (!healthPotions.isEmpty()) {
            healthPotions.forEach(potion -> {
                if (health >= potion.getLowerBound() && health <= potion.getUpperBound() && !player.isPotionActive(potion.getPotion())) {
                    player.addPotionEffect(new PotionEffect(potion.getPotion(), 20, potion.getAmplifier(), true, false));
                }
            });
        }

        // Hunger
        if (hungerPotions.isEmpty() && EMTConfig.MINECRAFT.PLAYER_EFFECTS.hungerPotions.length != 0) {
            Arrays.stream(EMTConfig.MINECRAFT.PLAYER_EFFECTS.hungerPotions).map(line -> line.split(";")).forEach(data -> {
                if (data.length == 4
                        && Integer.parseInt(data[0]) <= Integer.parseInt(data[1])
                        && Integer.parseInt(data[0]) <= 20
                        && Integer.parseInt(data[0]) >= 0
                        && Integer.parseInt(data[1]) <= 20
                        && Integer.parseInt(data[1]) >= 0
                ) {
                    try {
                        hungerPotions.add(new EmtPotionData(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                    } catch (NumberFormatException e) {
                        EnderModpackTweaks.LOGGER.error(e);
                        EnderModpackTweaks.LOGGER.error("Error parsing hunger potion data: {}", Arrays.toString(data));
                    }
                }
            });
        }
        if (!hungerPotions.isEmpty()) {
            hungerPotions.forEach(potion -> {
                if (hunger >= potion.getLowerBound() && hunger <= potion.getUpperBound() && !player.isPotionActive(potion.getPotion())) {
                    player.addPotionEffect(new PotionEffect(potion.getPotion(), 20, potion.getAmplifier(), true, false));
                }
            });
        }
    }
}
