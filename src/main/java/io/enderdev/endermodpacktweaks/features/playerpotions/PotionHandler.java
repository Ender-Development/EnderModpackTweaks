package io.enderdev.endermodpacktweaks.features.playerpotions;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PotionHandler {
    private final List<PotionData> listPotions = new ArrayList<>();

    private final String[] potionData;
    private final int lowerBound;
    private final int upperBound;

    public PotionHandler(String[] potionData, int lowerBound, int upperBound) {
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
                    listPotions.add(new PotionData(data[2], Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3])));
                } catch (NumberFormatException e) {
                    EnderModpackTweaks.LOGGER.error(e);
                    EnderModpackTweaks.LOGGER.error("Error parsing potion data: {}", Arrays.toString(data));
                }
            }
        });
        listPotions.removeIf(Objects::isNull);
    }

    public void apply(EntityPlayer player, int value) {
        if (listPotions.isEmpty() || player.ticksExisted % CfgFeatures.PLAYER_EFFECTS.effectRefreshRate != 0) {
            return;
        }
        listPotions.forEach(potion -> updatePotionStatus(player, potion, value));
    }

    private void updatePotionStatus(EntityPlayer player, PotionData potionData, int value) {
        if (potionData == null || potionData.getPotion() == null) {
            return;
        }
        PotionEffect currentEffect = player.getActivePotionEffect(potionData.getPotion());
        boolean valueInBound = value >= potionData.getLowerBound() && value <= potionData.getUpperBound();
        if (!valueInBound && currentEffect != null && currentEffect.getDuration() <= CfgFeatures.PLAYER_EFFECTS.effectDuration) {
            player.removePotionEffect(potionData.getPotion());
        }
        if (valueInBound && (currentEffect == null || currentEffect.getDuration() <= CfgFeatures.PLAYER_EFFECTS.effectDuration)) {
            player.addPotionEffect(new PotionEffect(potionData.getPotion(), CfgFeatures.PLAYER_EFFECTS.effectDuration, potionData.getAmplifier(), true, false));
        }
    }
}
