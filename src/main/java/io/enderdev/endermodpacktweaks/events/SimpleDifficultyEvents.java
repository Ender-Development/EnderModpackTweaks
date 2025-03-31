package io.enderdev.endermodpacktweaks.events;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.utils.EmtPotionData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleDifficultyEvents {
    private final List<EmtPotionData> temperaturePotions = new ArrayList<>();
    private final List<EmtPotionData> thirstPotions = new ArrayList<>();

    @SubscribeEvent
    public void setThirstOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
        IThirstCapability thirstCapability = SDCapabilities.getThirstData(event.player);
        thirstCapability.setThirstLevel(EMTConfig.SIMPLE_DIFFICULTY.respawnThirst);
        thirstCapability.setThirstSaturation(EMTConfig.SIMPLE_DIFFICULTY.respawnThirstSaturation);
    }

    @SubscribeEvent
    public void simpleDifficultyPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        int thirst = SDCapabilities.getThirstData(player).getThirstLevel();
        int temp = SDCapabilities.getTemperatureData(player).getTemperatureLevel();

        // Temperature
        if (temperaturePotions.isEmpty() && EMTConfig.SIMPLE_DIFFICULTY.temperaturePotions.length != 0) {
            Arrays.stream(EMTConfig.SIMPLE_DIFFICULTY.temperaturePotions).map(line -> line.split(";")).forEach(data -> {
                if (data.length == 4
                        && Integer.parseInt(data[0]) <= Integer.parseInt(data[1])
                        && Integer.parseInt(data[0]) <= 25
                        && Integer.parseInt(data[0]) >= 0
                        && Integer.parseInt(data[1]) <= 25
                        && Integer.parseInt(data[1]) >= 0
                ) {
                    try {
                        temperaturePotions.add(new EmtPotionData(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                    } catch (NumberFormatException e) {
                        EnderModpackTweaks.LOGGER.error(e);
                        EnderModpackTweaks.LOGGER.error("Error parsing temperature potion data: {}", Arrays.toString(data));
                    }
                }
            });
        }
        if (!temperaturePotions.isEmpty()) {
            temperaturePotions.forEach(potion -> {
                if (temp >= potion.getLowerBound() && temp <= potion.getUpperBound() && !player.isPotionActive(potion.getPotion())) {
                    player.addPotionEffect(new PotionEffect(potion.getPotion(), 20, potion.getAmplifier(), true, false));
                }
            });
        }

        // Thirst
        if (thirstPotions.isEmpty() && EMTConfig.SIMPLE_DIFFICULTY.thirstPotions.length != 0) {
            Arrays.stream(EMTConfig.SIMPLE_DIFFICULTY.thirstPotions).map(line -> line.split(";")).forEach(data -> {
                if (data.length == 4
                        && Integer.parseInt(data[0]) <= Integer.parseInt(data[1])
                        && Integer.parseInt(data[0]) <= 20
                        && Integer.parseInt(data[0]) >= 0
                        && Integer.parseInt(data[1]) <= 20
                        && Integer.parseInt(data[1]) >= 0
                ) {
                    try {
                        thirstPotions.add(new EmtPotionData(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                    } catch (NumberFormatException e) {
                        EnderModpackTweaks.LOGGER.error(e);
                        EnderModpackTweaks.LOGGER.error("Error parsing thirst potion data: {}", Arrays.toString(data));
                    }
                }
            });
        }
        if (!thirstPotions.isEmpty()) {
            thirstPotions.forEach(potion -> {
                if (thirst >= potion.getLowerBound() && thirst <= potion.getUpperBound() && !player.isPotionActive(potion.getPotion())) {
                    player.addPotionEffect(new PotionEffect(potion.getPotion(), 20, potion.getAmplifier(), true, false));
                }
            });
        }
    }
}
