package io.enderdev.endermodpacktweaks.features.playerpotions;

import net.minecraft.potion.Potion;

public class PotionData {
    private final Potion potion;
    private final int lowerBound;
    private final int upperBound;
    private final int amplifier;

    public PotionData(String potion, int lowerBound, int upperBound, int amplifier) {
        this.potion = Potion.getPotionFromResourceLocation(potion);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.amplifier = amplifier;
    }

    public Potion getPotion() {
        return potion;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public int getAmplifier() {
        return amplifier;
    }
}