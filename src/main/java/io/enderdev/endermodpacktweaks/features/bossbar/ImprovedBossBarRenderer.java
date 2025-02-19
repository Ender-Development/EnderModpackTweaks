package io.enderdev.endermodpacktweaks.features.bossbar;

import com.google.gson.Gson;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.Tags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.BossInfo;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImprovedBossBarRenderer extends Gui {
    private final Minecraft mc;
    private final List<String> unknownBossMobs = new ArrayList<String>(){{
        add("INVALID");
        // https://www.curseforge.com/minecraft/mc-mods/unofficial-bosses-of-mass-destruction-dungeon
        add("da:ancient_fallen");
        add("da:flame_knight");
        add("da:great_wyrk");
    }};

    private static final ResourceLocation textureBarBackground = new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/background.png");

    public ImprovedBossBarRenderer(Minecraft mc) {
        this.mc = mc;
    }

    public int getOverlayHeight(BossInfo info) {
        String mob = getEntityFromBossInfo(info);
        BossType boss = BossType.getBossType(mob);
        return boss == null ? 0 : boss.overlayHeight;
    }

    public boolean render(int x, int y, BossInfo info) {
        String mob = getEntityFromBossInfo(info);
        BossType boss = BossType.getBossType(mob);
        if (boss == null) {
            if (!unknownBossMobs.contains(mob)) {
                unknownBossMobs.add(mob);
                EnderModpackTweaks.LOGGER.warn("Unknown boss mob: {}", mob);
            }
            return false;
        }
        ScaledResolution scaledresolution = new ScaledResolution(mc);
        GlStateManager.pushMatrix();
        int middleX = (scaledresolution.getScaledWidth() / 2) - (boss.overlayWidth / 2);
        renderBar(middleX + boss.barOffsetX, y + boss.barOffsetY, boss.barWidth, info, boss.bar);
        renderOverlay(middleX, y, boss.overlay);
        GlStateManager.popMatrix();
        return true;
    }

    private void renderBar(int x, int y, int barWidth, BossInfo info, ResourceLocation textureBarForeground) {
        mc.getTextureManager().bindTexture(textureBarBackground);
        drawScaledCustomSizeModalRect(x, y, 0, 0, 352, 10, barWidth, 5, 352, 10);

        int i = (int) Math.floor(info.getPercent() * barWidth);

        if (i > 0) {
            mc.getTextureManager().bindTexture(textureBarForeground);
            drawScaledCustomSizeModalRect(x, y, 0, 0, 352, 10, i, 5, 352, 10);
        }
    }

    private void renderOverlay(int x, int y, ResourceLocation overlay) {
        mc.getTextureManager().bindTexture(overlay);
        int overlayWidth = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
        int overlayHeight = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
        drawScaledCustomSizeModalRect(x, y, 0, 0, overlayWidth, overlayHeight, overlayWidth, overlayHeight, overlayWidth, overlayHeight);
    }

    private String getEntityFromBossInfo(BossInfo info) {
        ITextComponent name = info.getName();
        if (name instanceof TextComponentTranslation) {
            TextComponentTranslation translation = (TextComponentTranslation) name;
            if (translation.getKey().equals("entity.EnderDragon.name")) {
                return "minecraft:ender_dragon";
            }
        } else {
            HoverEvent hoverEvent = name.getStyle().getHoverEvent();
            if (hoverEvent != null) {
                // {id:"17935eb4-1711-45ff-8f82-7f8b3b47c5c2",type:"minecraft:wither",name:"Wither"}
                String componentText = name.getStyle().getHoverEvent().getValue().getUnformattedComponentText();
                StringJson jsonObject = new Gson().fromJson(componentText, StringJson.class);
                return jsonObject.type;
            }
        }
        return "INVALID";
    }

    private static class StringJson {
        private String id;
        private String type;
        private String name;
    }


    private enum BossType {
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
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/white_progress.png"),
                13, 20, 150, 190, 49),
        VOID_BLOSSOM("da:void_blossom",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/void_blossom.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/green_progress.png"),
                16, 8, 169, 185, 47),
        NIGHT_LICH("da:night_lich",
                new ResourceLocation(Tags.MOD_ID, "textures/gui/boss_bars/night_lich.png"),
                new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/blue_progress.png"),
                18, 22, 175, 219, 49);

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
    }
}
