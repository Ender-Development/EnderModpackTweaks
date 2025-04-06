package io.enderdev.endermodpacktweaks.utils;

import net.minecraft.potion.Potion;

public class EmtPotionData {
    private final Potion potion;
    private final int lowerBound;
    private final int upperBound;
    private final int amplifier;
    private boolean state;

    public EmtPotionData(String potion, int lowerBound, int upperBound, int amplifier) {
        this.potion = Potion.getPotionFromResourceLocation(potion);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.amplifier = amplifier;
        this.state = false;
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

    public boolean isActive() {
        return state;
    }

    public void setActive(boolean active) {
        state = active;
    }
}