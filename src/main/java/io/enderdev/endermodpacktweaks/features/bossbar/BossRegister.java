package io.enderdev.endermodpacktweaks.features.bossbar;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.core.EMTAssetMover;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraftforge.fml.common.Loader;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BossRegister {
    private static final List<BossBar> BOSS_BARS = new ArrayList<>();

    @Nullable
    public static BossBar getBossBar(String entity) {
        return BOSS_BARS.stream().filter(bar -> bar.name.equals(entity)).findFirst().orElse(null);
    }

    public static void init() {
        List<BossBar> bars = null;
        try {
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Loader.class.getResourceAsStream(String.format("/assets/%s/bossbar.json", Tags.MOD_ID))));
            bars = gson.fromJson(reader, new TypeToken<List<BossBar>>() {
            }.getType());
        } catch (Exception e) {
            EnderModpackTweaks.LOGGER.error("Failed to read bossbar.json", e);
        }
        if (bars == null) return;
        BOSS_BARS.addAll(bars);
    }

    public static class BossBar {
        String name;
        @SerializedName("default")
        Boolean standard;
        int width;
        int height;
        BossTexture overlay;
        BossTexture bar;
        BossTexture background;

        public void draw(BossInfo info, int x, int y) {
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int center = (scaledresolution.getScaledWidth() / 2) - (width / 2);

            GlStateManager.pushMatrix();
            if (background != null) background.draw(center, y, 1f, 1f);
            if (bar != null) bar.draw(center, y, info.getPercent(), 1f);
            if (overlay != null) overlay.draw(center, y, 1f, 1f);
            GlStateManager.popMatrix();
        }
    }

    private static class BossTexture {
        private String texture;
        private Offset offset;
        private Offset limit;

        public ResourceLocation getTexture() {
            return new ResourceLocation(texture);
        }

        /**
         * Load and draw the specified texture
         * @param x x position of the texture, offset still applies
         * @param y y position of the texture, offset still applies
         * @param xScale what percent of the texture to draw
         * @param yScale what percent of the texture to draw
         */
        public void draw(int x, int y, float xScale, float yScale) {
            if (xScale <= 0f) return;
            int offsetX = offset == null ? 0 : offset.x;
            int offsetY = offset == null ? 0 : offset.y;

            Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture());
            int textureWidth = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
            int textureHeight = GlStateManager.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);

            int scaledWidth = xScale >= 1f ? textureWidth : (int) Math.floor(textureWidth * xScale);
            int scaledHeight = yScale >= 1f ? textureHeight : (int) Math.floor(textureHeight * yScale);

            int limitWidth = limit == null ? scaledWidth : Math.min(xScale >= 1f ? limit.x : (int) Math.floor(limit.x * xScale), scaledWidth);
            int limitHeight = limit == null ? scaledHeight : Math.min(yScale >= 1f ? limit.y : (int) Math.floor(limit.y * yScale), scaledHeight);

            Gui.drawScaledCustomSizeModalRect(x + offsetX, y + offsetY, 0, 0, scaledWidth, textureHeight, limitWidth, limitHeight, textureWidth, textureHeight);
        }
    }

    private static class Offset {
        int x;
        int y;
    }
}
