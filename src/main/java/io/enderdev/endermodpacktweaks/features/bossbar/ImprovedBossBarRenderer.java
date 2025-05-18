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

import java.util.*;

public class ImprovedBossBarRenderer extends Gui {
    private final Minecraft mc;
    private final Map<String,String> unknownBossMobs = new HashMap<String, String>(){{
        put("INVALID", "INVALID");
    }};

    private static final ResourceLocation textureBarBackground = new ResourceLocation(Tags.MOD_ID, "textures/gui/bars/background.png");

    public ImprovedBossBarRenderer(Minecraft mc) {
        this.mc = mc;
    }

    public int getOverlayHeight(BossInfo info) {
        String mob = getEntityFromBossInfo(info);
        BossType boss = BossRegister.getBossType(mob);
        return boss == null ? 0 : boss.getOverlayHeight();
    }

    public boolean hasOverlay(String text) {
        return !unknownBossMobs.containsValue(text);
    }

    public boolean render(int x, int y, BossInfo info) {
        String mob = getEntityFromBossInfo(info);
        BossType boss = BossRegister.getBossType(mob);
        if (boss == null) {
            if (!unknownBossMobs.containsKey(mob)) {
                unknownBossMobs.put(mob, info.getName().getFormattedText());
                EnderModpackTweaks.LOGGER.warn("Unknown boss mob: {}, named {}", mob, info.getName().getFormattedText());
            }
            return false;
        }
        ScaledResolution scaledresolution = new ScaledResolution(mc);
        GlStateManager.pushMatrix();
        int middleX = (scaledresolution.getScaledWidth() / 2) - (boss.getOverlayWidth() / 2);
        renderBar(middleX + boss.getBarOffsetX(), y + boss.getBarOffsetY(), boss.getBarWidth(), info, boss.getBar());
        renderOverlay(middleX, y, boss.getOverlay());
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

    @SuppressWarnings("unused")
    private static class StringJson {
        private String id;
        private String type;
        private String name;
    }
}
