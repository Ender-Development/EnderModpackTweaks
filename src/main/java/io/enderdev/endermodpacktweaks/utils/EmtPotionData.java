package io.enderdev.endermodpacktweaks.utils;

import net.minecraft.potion.Potion;

public class EmtPotionData {
    Potion potion;
    int lowerBound;
    int upperBound;
    int amplifier;

    public EmtPotionData(String potion, int lowerBound, int upperBound, int amplifier) {
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