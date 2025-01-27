package io.enderdev.endermodpacktweaks.features.bossbar;

import com.google.gson.Gson;
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

public class ImprovedBossBarRenderer extends Gui {
    private final Minecraft mc;
    private final boolean isResourcePackEnabled;

    private ResourceLocation textureOverlay;
    private ResourceLocation textureBarForeground;
    private static final ResourceLocation textureBarBackground = new ResourceLocation("minecraft", "textures/gui/sprites/boss_bar/white_background.png");

    public ImprovedBossBarRenderer(Minecraft mc, boolean isResourcePackEnabled) {
        this.mc = mc;
        this.isResourcePackEnabled = isResourcePackEnabled;
    }

    public int getOverlayHeight() {
        return 50;
    }

    public boolean isResourcePackEnabled() {
        return isResourcePackEnabled;
    }

    public boolean render(int x, int y, BossInfo info) {
        String mob = getEntityFromBossInfo(info);
        ScaledResolution scaledresolution = new ScaledResolution(mc);
        int barOffsetY;
        int barOffsetX;
        int barWidth;
        int overlayWidth;
        switch (mob) {
            case "minecraft:wither":
                textureOverlay = new ResourceLocation("minecraft", "textures/font/wither.png");
                textureBarForeground = new ResourceLocation("minecraft", "textures/gui/sprites/boss_bar/purple_progress.png");
                barOffsetY = 15;
                barOffsetX = 8;
                barWidth = 172;
                overlayWidth = 189;
                break;
            case "minecraft:ender_dragon":
                textureOverlay = new ResourceLocation("minecraft", "textures/font/enderdragon.png");
                textureBarForeground = new ResourceLocation("minecraft", "textures/gui/sprites/boss_bar/pink_progress.png");
                barOffsetY = 16;
                barOffsetX = 14;
                barWidth = 158;
                overlayWidth = 186;
                break;
            default:
                return false;
        }
        GlStateManager.pushMatrix();
        int middleX = (scaledresolution.getScaledWidth() / 2) - (overlayWidth / 2);
        renderBar(middleX + barOffsetX, y + barOffsetY, barWidth, info);
        renderOverlay(middleX, y, info);
        GlStateManager.popMatrix();
        return true;
    }

    private void renderBar(int x, int y, int barWidth, BossInfo info) {
        mc.getTextureManager().bindTexture(textureBarBackground);
        drawScaledCustomSizeModalRect(x, y, 16, 0, 332, 10, barWidth, 5, 364, 10);
        //drawModalRectWithCustomSizedTexture(scaledX, scaledY, 0, 0, 364, 10, 364, 10);

        int i = (int) Math.floor(info.getPercent() * barWidth);

        if (i > 0) {
            mc.getTextureManager().bindTexture(textureBarForeground);
            drawScaledCustomSizeModalRect(x, y, 16, 0, 332, 10, i, 5, 364, 10);
            //drawModalRectWithCustomSizedTexture(scaledX, scaledY, 0, 0, i, 10, 364, 10);
        }
    }

    private void renderOverlay(int x, int y, BossInfo info) {
        mc.getTextureManager().bindTexture(textureOverlay);
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
        return "";
    }

    private static class StringJson {
        private String id;
        private String type;
        private String name;
    }
}
