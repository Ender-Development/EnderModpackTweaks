package io.enderdev.endermodpacktweaks.events;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerEvents {
    private final List<SDPotion> temperaturePotions = new ArrayList<>();
    private final List<SDPotion> thirstPotions = new ArrayList<>();
    private final List<SDPotion> healthPotions = new ArrayList<>();
    private final List<SDPotion> hungerPotions = new ArrayList<>();

    @SubscribeEvent
    public void cancelSleep(SleepingTimeCheckEvent event) {
        if (EMTConfig.MODPACK.SYNC_TIME.enable && EMTConfig.MODPACK.SYNC_TIME.sleeping) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void setThirstOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!EMTConfig.SIMPLE_DIFFICULTY.enable || !Loader.isModLoaded("simpledifficulty")) {
            return;
        }
        IThirstCapability thirstCapability = SDCapabilities.getThirstData(event.player);
        thirstCapability.setThirstLevel(EMTConfig.SIMPLE_DIFFICULTY.respawnThirst);
        thirstCapability.setThirstSaturation(EMTConfig.SIMPLE_DIFFICULTY.respawnThirstSaturation);
    }

    @SubscribeEvent
    public void simpleDifficultyPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!EMTConfig.SIMPLE_DIFFICULTY.enable || !Loader.isModLoaded("simpledifficulty")) {
            return;
        }

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
                        temperaturePotions.add(new SDPotion(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                    } catch (NumberFormatException e) {
                        EnderModpackTweaks.LOGGER.error(e);
                        EnderModpackTweaks.LOGGER.error("Error parsing temperature potion data: {}", Arrays.toString(data));
                    }
                }
            });
        }
        if (!temperaturePotions.isEmpty()) {
            temperaturePotions.forEach(potion -> {
                if (temp >= potion.lowerBound && temp <= potion.upperBound && !player.isPotionActive(potion.potion)) {
                    player.addPotionEffect(new PotionEffect(potion.potion, 20, potion.amplifier, true, false));
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
                        thirstPotions.add(new SDPotion(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                    } catch (NumberFormatException e) {
                        EnderModpackTweaks.LOGGER.error(e);
                        EnderModpackTweaks.LOGGER.error("Error parsing thirst potion data: {}", Arrays.toString(data));
                    }
                }
            });
        }
        if (!thirstPotions.isEmpty()) {
            thirstPotions.forEach(potion -> {
                if (thirst >= potion.lowerBound && thirst <= potion.upperBound && !player.isPotionActive(potion.potion)) {
                    player.addPotionEffect(new PotionEffect(potion.potion, 20, potion.amplifier, true, false));
                }
            });
        }
    }

    @SubscribeEvent
    public void potionPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!EMTConfig.MINECRAFT.PLAYER_EFFECTS.enable) {
            return;
        }

        EntityPlayer player = event.player;
        int health = (int) (player.getHealth() / player.getMaxHealth()) * 100;
        int hunger = player.getFoodStats().getFoodLevel();

        // Health
        if (healthPotions.isEmpty() && EMTConfig.MINECRAFT.PLAYER_EFFECTS.healthPotions.length != 0) {
            Arrays.stream(EMTConfig.MINECRAFT.PLAYER_EFFECTS.healthPotions).map(line -> line.split(";")).forEach(data -> {
                if (data.length == 4
                        && Integer.parseInt(data[0]) <= Integer.parseInt(data[1])
                        && Integer.parseInt(data[0]) <= 25
                        && Integer.parseInt(data[0]) >= 0
                        && Integer.parseInt(data[1]) <= 25
                        && Integer.parseInt(data[1]) >= 0
                ) {
                    try {
                        healthPotions.add(new SDPotion(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                    } catch (NumberFormatException e) {
                        EnderModpackTweaks.LOGGER.error(e);
                        EnderModpackTweaks.LOGGER.error("Error parsing health potion data: {}", Arrays.toString(data));
                    }
                }
            });
        }
        if (!healthPotions.isEmpty()) {
            healthPotions.forEach(potion -> {
                if (health >= potion.lowerBound && health <= potion.upperBound && !player.isPotionActive(potion.potion)) {
                    player.addPotionEffect(new PotionEffect(potion.potion, 20, potion.amplifier, true, false));
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
                        hungerPotions.add(new SDPotion(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                    } catch (NumberFormatException e) {
                        EnderModpackTweaks.LOGGER.error(e);
                        EnderModpackTweaks.LOGGER.error("Error parsing hunger potion data: {}", Arrays.toString(data));
                    }
                }
            });
        }
        if (!hungerPotions.isEmpty()) {
            hungerPotions.forEach(potion -> {
                if (hunger >= potion.lowerBound && hunger <= potion.upperBound && !player.isPotionActive(potion.potion)) {
                    player.addPotionEffect(new PotionEffect(potion.potion, 20, potion.amplifier, true, false));
                }
            });
        }
    }

    private static class SDPotion {
        Potion potion;
        int lowerBound;
        int upperBound;
        int amplifier;

        public SDPotion(String potion, int lowerBound, int upperBound, int amplifier) {
            this.potion = Potion.getPotionFromResourceLocation(potion);
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.amplifier = amplifier;
        }
    }
}
