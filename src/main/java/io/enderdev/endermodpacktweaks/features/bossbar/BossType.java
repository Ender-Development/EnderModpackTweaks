package io.enderdev.endermodpacktweaks.features.bossbar;

import io.enderdev.endermodpacktweaks.Tags;
import net.minecraft.util.ResourceLocation;

public class BossType {
    private final String entity;
    private final ResourceLocation overlay;
    private final ResourceLocation bar;
    private final int barOffsetY;
    private final int barOffsetX;
    private final int barWidth;
    private final int overlayWidth;
    private final int overlayHeight;

    public BossType(String entity, String overlay, String bar, int barOffsetY, int barOffsetX, int barWidth, int overlayWidth, int overlayHeight) {
        this.entity = entity;
        this.overlay = new ResourceLocation(Tags.MOD_ID, String.format("textures/gui/boss_bars/%s.png", overlay));
        this.bar = new ResourceLocation(Tags.MOD_ID, String.format("textures/gui/bars/%s.png", bar));
        this.barOffsetY = barOffsetY;
        this.barOffsetX = barOffsetX;
        this.barWidth = barWidth;
        this.overlayWidth = overlayWidth;
        this.overlayHeight = overlayHeight;
    }

    public String getEntity() {
        return entity;
    }

    public ResourceLocation getOverlay() {
        return overlay;
    }

    public ResourceLocation getBar() {
        return bar;
    }

    public int getBarOffsetY() {
        return barOffsetY;
    }

    public int getBarOffsetX() {
        return barOffsetX;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public int getOverlayWidth() {
        return overlayWidth;
    }

    public int getOverlayHeight() {
        return overlayHeight;
    }
}
