package io.enderdev.endermodpacktweaks.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class EmtRender {
    private static final Tessellator TESSELLATOR = Tessellator.getInstance();
    private static final BufferBuilder BUFFER_BUILDER = TESSELLATOR.getBuffer();
    private static final FontRenderer FONT_RENDERER = Minecraft.getMinecraft().fontRenderer;

    public static void renderRect(float x, float y, float width, float height, Color color) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 0);

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        BUFFER_BUILDER.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        BUFFER_BUILDER.pos(x + width, y + height, 0).endVertex();
        BUFFER_BUILDER.pos(x + width, y, 0).endVertex();
        BUFFER_BUILDER.pos(x, y, 0).endVertex();
        BUFFER_BUILDER.pos(x, y + height, 0).endVertex();
        TESSELLATOR.draw();

        GlStateManager.popMatrix();
    }

    public static void renderText(String text, float x, float y, int color) {
        renderText(text, x, y, 1f, color, false);
    }

    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/4e79541b1f77ecf44f7d3718fd32555ca498d1cb/src/main/java/com/tttsaurus/ingameinfo/common/api/render/RenderUtils.java>
    public static void renderText(String text, float x, float y, float scale, int color, boolean shadow) {
        GlStateManager.disableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.scale(scale, scale, 0);
        FONT_RENDERER.drawString(text, 0, 0, color, shadow);
        GlStateManager.popMatrix();
    }

    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/4e79541b1f77ecf44f7d3718fd32555ca498d1cb/src/main/java/com/tttsaurus/ingameinfo/common/api/render/RenderUtils.java>
    public static void renderRoundedRect(float x, float y, float width, float height, float radius, Color color) {
        int segments = Math.max(3, (int) (radius / 2f));
        float a = (float) color.getAlpha() / 255.0F;
        float r = (float) color.getRed() / 255.0F;
        float g = (float) color.getGreen() / 255.0F;
        float b = (float) color.getBlue() / 255.0F;

        GlStateManager.disableCull();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(r, g, b, a);

        BUFFER_BUILDER.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);

        addArcVertices(x + width - radius, y + radius, radius, 0, 90, segments);

        BUFFER_BUILDER.pos(x + width, y + radius, 0).endVertex();
        BUFFER_BUILDER.pos(x + width, y + height - radius, 0).endVertex();

        addArcVertices(x + width - radius, y + height - radius, radius, 90, 180, segments);

        BUFFER_BUILDER.pos(x + width - radius, y + height, 0).endVertex();
        BUFFER_BUILDER.pos(x + radius, y + height, 0).endVertex();

        addArcVertices(x + radius, y + height - radius, radius, 180, 270, segments);

        BUFFER_BUILDER.pos(x, y + height - radius, 0).endVertex();
        BUFFER_BUILDER.pos(x, y + radius, 0).endVertex();

        addArcVertices(x + radius, y + radius, radius, 270, 360, segments);

        BUFFER_BUILDER.pos(x + radius, y, 0).endVertex();
        BUFFER_BUILDER.pos(x + width - radius, y, 0).endVertex();
    }

    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/4e79541b1f77ecf44f7d3718fd32555ca498d1cb/src/main/java/com/tttsaurus/ingameinfo/common/api/render/RenderUtils.java>
    private static void addArcVertices(float cx, float cy, float radius, float startAngle, float endAngle, int segments) {
        startAngle -= 90;
        endAngle -= 90;
        float x = (float) (cx + Math.cos(Math.toRadians(startAngle)) * radius);
        float y = (float) (cy + Math.sin(Math.toRadians(startAngle)) * radius);
        for (int i = 1; i <= segments; i++) {
            float angle = (float) Math.toRadians(startAngle + (endAngle - startAngle) * i / segments);
            float dx = (float) (cx + Math.cos(angle) * radius);
            float dy = (float) (cy + Math.sin(angle) * radius);
            BUFFER_BUILDER.pos(x, y, 0).endVertex();
            BUFFER_BUILDER.pos(dx, dy, 0).endVertex();
            x = dx;
            y = dy;
        }
    }

    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/4e79541b1f77ecf44f7d3718fd32555ca498d1cb/src/main/java/com/tttsaurus/ingameinfo/common/api/render/RenderUtils.java>
    public static void prepareStencilToWrite(int stencilValue) {
        if (!Minecraft.getMinecraft().getFramebuffer().isStencilEnabled())
            Minecraft.getMinecraft().getFramebuffer().enableStencil();

        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();

        GL11.glEnable(GL11.GL_STENCIL_TEST);

        GlStateManager.depthMask(false);
        GlStateManager.colorMask(false, false, false, false);
        GL11.glStencilFunc(GL11.GL_ALWAYS, stencilValue, 0xFF);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);

        // mask area
        GL11.glStencilMask(0xFF);
    }

    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/4e79541b1f77ecf44f7d3718fd32555ca498d1cb/src/main/java/com/tttsaurus/ingameinfo/common/api/render/RenderUtils.java>
    public static void prepareStencilToRender(int stencilValue) {
        GL11.glStencilMask(0x00);

        GlStateManager.depthMask(true);
        GlStateManager.colorMask(true, true, true, true);
        GL11.glStencilFunc(GL11.GL_EQUAL, stencilValue, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
    }

    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/4e79541b1f77ecf44f7d3718fd32555ca498d1cb/src/main/java/com/tttsaurus/ingameinfo/common/api/render/RenderUtils.java>
    public static void endStencil() {
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/4e79541b1f77ecf44f7d3718fd32555ca498d1cb/src/main/java/com/tttsaurus/ingameinfo/common/api/render/RenderUtils.java>
    public static void drawRectStencilArea(float x, float y, float width, float height) {
        BUFFER_BUILDER.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        BUFFER_BUILDER.pos(x, y, 0d).endVertex();
        BUFFER_BUILDER.pos(x + width, y, 0d).endVertex();
        BUFFER_BUILDER.pos(x + width, y + height, 0d).endVertex();
        BUFFER_BUILDER.pos(x, y + height, 0d).endVertex();
        TESSELLATOR.draw();
    }
}
