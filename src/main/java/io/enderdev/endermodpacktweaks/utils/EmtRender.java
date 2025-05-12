package io.enderdev.endermodpacktweaks.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.nio.FloatBuffer;
import java.util.function.Supplier;

public final class EmtRender {
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

        GlStateManager.pushMatrix();

        GlStateManager.disableCull();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

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

        TESSELLATOR.draw();

        GlStateManager.popMatrix();
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

    //<editor-fold desc="gl version">
    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/master/src/main/java/com/tttsaurus/ingameinfo/common/core/render/RenderHints.java>
    private static boolean glVersionParsed = false;
    private static int majorGlVersion = -1;
    private static int minorGlVersion = -1;
    private static String rawGlVersion = "";

    public static int getMajorGlVersion() {
        if (!glVersionParsed) parseGlVersion();
        return majorGlVersion;
    }

    public static int getMinorGlVersion() {
        if (!glVersionParsed) parseGlVersion();
        return minorGlVersion;
    }

    public static String getRawGlVersion() {
        if (!glVersionParsed) parseGlVersion();
        return rawGlVersion;
    }

    private static void parseGlVersion() {
        glVersionParsed = true;
        rawGlVersion = GL11.glGetString(GL11.GL_VERSION);

        if (rawGlVersion != null) {
            String[] parts = rawGlVersion.split("\\s+")[0].split("\\.");
            if (parts.length >= 2) {
                try {
                    majorGlVersion = Integer.parseInt(parts[0]);
                    minorGlVersion = Integer.parseInt(parts[1]);
                } catch (NumberFormatException ignored) {
                }
            }
        } else
            rawGlVersion = "";

        if (rawGlVersion.isEmpty() || majorGlVersion == -1 || minorGlVersion == -1)
            throw new RuntimeException("EmtRender.parseGlVersion() failed to parse GL version.");
    }
    //</editor-fold>

    //<editor-fold desc="active render info">
    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/master/src/main/java/com/tttsaurus/ingameinfo/common/core/render/RenderHints.java>
    // inspired by <https://github.com/Laike-Endaril/Fantastic-Lib/blob/669c3306bbebca9de1c3959e6dd4203b5b7215d4/src/main/java/com/fantasticsource/mctools/Render.java>
    private static boolean isActiveRenderInfoGettersInit = false;
    private static Supplier<FloatBuffer> modelViewMatrixGetter;
    private static Supplier<FloatBuffer> projectionMatrixGetter;

    public static FloatBuffer getModelViewMatrix() {
        if (!isActiveRenderInfoGettersInit) initActiveRenderInfoGetters();
        return modelViewMatrixGetter.get();
    }

    public static FloatBuffer getProjectionMatrix() {
        if (!isActiveRenderInfoGettersInit) initActiveRenderInfoGetters();
        return projectionMatrixGetter.get();
    }

    private static void initActiveRenderInfoGetters() {
        isActiveRenderInfoGettersInit = true;

        Field modelViewMatrixField = null;
        try {
            modelViewMatrixField = ActiveRenderInfo.class.getDeclaredField("MODELVIEW");
        } catch (Exception ignored) {
            try {
                modelViewMatrixField = ActiveRenderInfo.class.getDeclaredField("field_178812_b");
            } catch (Exception ignored2) {
            }
        }
        if (modelViewMatrixField == null)
            throw new RuntimeException("EmtRender.initActiveRenderInfoGetters() failed to find the getter of MODELVIEW.");

        Field projectionMatrixField = null;
        try {
            projectionMatrixField = ActiveRenderInfo.class.getDeclaredField("PROJECTION");
        } catch (Exception ignored) {
            try {
                projectionMatrixField = ActiveRenderInfo.class.getDeclaredField("field_178813_c");
            } catch (Exception ignored2) {
            }
        }
        if (projectionMatrixField == null)
            throw new RuntimeException("EmtRender.initActiveRenderInfoGetters() failed to find the getter of PROJECTION.");

        MethodHandles.Lookup lookup = MethodHandles.lookup();

        try {
            modelViewMatrixField.setAccessible(true);
            MethodHandle handle = lookup.unreflectGetter(modelViewMatrixField);
            modelViewMatrixGetter = () ->
            {
                try {
                    return (FloatBuffer) handle.invoke();
                } catch (Throwable e) {
                    return null;
                }
            };
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        try {
            projectionMatrixField.setAccessible(true);
            MethodHandle handle = lookup.unreflectGetter(projectionMatrixField);
            projectionMatrixGetter = () ->
            {
                try {
                    return (FloatBuffer) handle.invoke();
                } catch (Throwable e) {
                    return null;
                }
            };
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    //</editor-fold>

    //<editor-fold desc="partial ticks">
    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/master/src/main/java/com/tttsaurus/ingameinfo/common/core/render/RenderHints.java>
    // inspired by <https://github.com/Laike-Endaril/Fantastic-Lib/blob/669c3306bbebca9de1c3959e6dd4203b5b7215d4/src/main/java/com/fantasticsource/mctools/Render.java>
    private static boolean isPartialTickGetterInit = false;
    private static Supplier<Double> partialTickGetter;

    public static double getPartialTick() {
        if (!isPartialTickGetterInit) initPartialTickGetter();
        Minecraft minecraft = Minecraft.getMinecraft();
        return minecraft.isGamePaused() ? partialTickGetter.get() : minecraft.getRenderPartialTicks();
    }

    private static void initPartialTickGetter() {
        isPartialTickGetterInit = true;

        Field partialTickField = null;
        try {
            partialTickField = Minecraft.class.getDeclaredField("renderPartialTicksPaused");
        } catch (Exception ignored) {
            try {
                partialTickField = Minecraft.class.getDeclaredField("field_193996_ah");
            } catch (Exception ignored2) {
            }
        }
        if (partialTickField == null)
            throw new RuntimeException("EmtRender.initPartialTickGetter() failed to find the getter of renderPartialTicksPaused.");

        MethodHandles.Lookup lookup = MethodHandles.lookup();

        try {
            partialTickField.setAccessible(true);
            MethodHandle handle = lookup.unreflectGetter(partialTickField);
            partialTickGetter = () ->
            {
                try {
                    return (double) (float) handle.invoke(Minecraft.getMinecraft());
                } catch (Throwable e) {
                    return null;
                }
            };
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
    //</editor-fold>

    //<editor-fold desc="camera">
    // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/master/src/main/java/com/tttsaurus/ingameinfo/common/core/render/RenderHints.java>
    public static Vector3f getCameraPos() {
        double partialTick = getPartialTick();

        Entity viewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        if (viewEntity == null) return new Vector3f(0, 0, 0);

        double camX = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTick;
        double camY = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTick;
        double camZ = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTick;

        return new Vector3f((float) camX, (float) camY, (float) camZ);
    }

    public static Vector2f getCameraRotationInDegree() {
        Entity viewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        if (viewEntity == null) return new Vector2f(0, 0);
        return new Vector2f(viewEntity.rotationYaw, viewEntity.rotationPitch);
    }

    public static Vector2f getCameraRotationInRadian() {
        Entity viewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        if (viewEntity == null) return new Vector2f(0, 0);
        return new Vector2f((float) Math.toRadians(viewEntity.rotationYaw), (float) Math.toRadians(viewEntity.rotationPitch));
    }
    //</editor-fold>
}
