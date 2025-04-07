package io.enderdev.endermodpacktweaks.utils;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmtPotionHandler {
    private final List<EmtPotionData> listPotions = new ArrayList<>();

    private final String[] potionData;
    private final int lowerBound;
    private final int upperBound;

    public EmtPotionHandler(String[] potionData, int lowerBound, int upperBound) {
        this.potionData = potionData;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public void init() {
        Arrays.stream(potionData).map(line -> line.split(";")).forEach(data -> {
            if (data.length == 4
                    && Integer.parseInt(data[0]) <= Integer.parseInt(data[1])
                    && Integer.parseInt(data[0]) <= upperBound
                    && Integer.parseInt(data[0]) >= lowerBound
                    && Integer.parseInt(data[1]) <= upperBound
                    && Integer.parseInt(data[1]) >= lowerBound
            ) {
                try {
                    listPotions.add(new EmtPotionData(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                } catch (NumberFormatException e) {
                    EnderModpackTweaks.LOGGER.error(e);
                    EnderModpackTweaks.LOGGER.error("Error parsing potion data: {}", Arrays.toString(data));
                }
            }
        });
    }

    public void apply(EntityPlayer player, int value) {
        if (listPotions.isEmpty()) {
            return;
        }
        listPotions.forEach(potion -> {
            if (potion == null) {
                return;
            }
            if (value >= potion.getLowerBound() && value <= potion.getUpperBound() && !player.isPotionActive(potion.getPotion())) {
                player.addPotionEffect(new PotionEffect(potion.getPotion(), Integer.MAX_VALUE, potion.getAmplifier(), true, false));
                potion.setActive(true);
            }
            if (potion.isActive() && (value < potion.getLowerBound() || value > potion.getUpperBound())) {
                player.removePotionEffect(potion.getPotion());
                potion.setActive(false);
            }
        });
    }

    public void clear(EntityPlayer player) {
        listPotions.forEach(potion -> {
            if (potion.isActive()) {
                player.removePotionEffect(potion.getPotion());
                potion.setActive(false);
            }
        });
    }
}
