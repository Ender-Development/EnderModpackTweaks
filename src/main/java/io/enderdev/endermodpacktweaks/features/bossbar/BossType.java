package io.enderdev.endermodpacktweaks.features.bossbar;

import io.enderdev.endermodpacktweaks.Tags;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public enum BossType {

        WITHER("minecraft:wither",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/wither.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/purple_progress.png"),
                15, 10, 169, 189, 48),
        ENDER_DRAGON("minecraft:ender_dragon",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/ender_dragon.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/pink_progress.png"),
                15, 12, 162, 186, 41),
        FERROUS_WROUGHTNAUT("mowziesmobs:ferrous_wroughtnaut",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/ferrous_wroughtnaut.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/red_progress.png"),
                17, 11, 163, 190, 54),
        FROSTMAW("mowziesmobs:frostmaw",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/frostmaw.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/ice_progress.png"),
                13, 20, 150, 190, 49),
        VOID_BLOSSOM("da:void_blossom",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/void_blossom.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/green_progress.png"),
                16, 8, 169, 185, 47),
        NIGHT_LICH("da:night_lich",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/night_lich.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/blue_progress.png"),
                18, 22, 175, 219, 49),
        GREAT_WYRK("da:great_wyrk",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/ancient_wyrk.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/ice_progress.png"),
                15, 13, 172, 198, 50),
    FLAME_KNIGHT("da:flame_knight",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/flame_knight.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/red_progress.png"),
                33, 21, 167, 200, 56);

        private final String entity;
        private final ResourceLocation overlay;
        private final ResourceLocation bar;
        private final int barOffsetY;
        private final int barOffsetX;
        private final int barWidth;
        private final int overlayWidth;
        private final int overlayHeight;

        BossType(String entity, ResourceLocation overlay, ResourceLocation bar, int barOffsetY, int barOffsetX, int barWidth, int overlayWidth, int overlayHeight) {
            this.entity = entity;
            this.overlay = overlay;
            this.bar = bar;
            this.barOffsetY = barOffsetY;
            this.barOffsetX = barOffsetX;
            this.barWidth = barWidth;
            this.overlayWidth = overlayWidth;
            this.overlayHeight = overlayHeight;
        }

        public static BossType getBossType(String entity) {
            return Arrays.stream(values()).filter(bossType -> bossType.entity.equals(entity)).findFirst().orElse(null);
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
