package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.render.shader.ShaderProgram;
import io.enderdev.endermodpacktweaks.utils.EmtColor;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

public final class HealthBarInstancingHelper {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    protected static RectInstancingRenderer rectBackgroundRenderer = null;
    protected static RectInstancingRenderer rectGraySpaceRenderer = null;

    private static final FloatBuffer floatBuffer16 = ByteBuffer.allocateDirect(16 << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();

    // should have the same visual behavior as HealthBarRenderHelper.renderHealthBar()
    public static void renderRectHealthBars(List<EntityLivingBase> entities, float partialTicks, Vector3f cameraPos, Vector2f cameraRot) {
        int entityListLength = Math.min(rectGraySpaceRenderer.getMaxInstance(), Math.min(rectBackgroundRenderer.getMaxInstance(), entities.size()));

        float[] backgroundInstanceData = new float[rectBackgroundRenderer.getInstanceDataLength()];
        float[] graySpaceInstanceData = new float[rectGraySpaceRenderer.getInstanceDataLength()];

        for (int i = 0; i < entityListLength; i++) {
            Entity entity = entities.get(i);

            float size = CfgFeatures.MOB_HEALTH_BAR.plateSize;

            float s = 0.5F;
            String name = I18n.format(entity.getDisplayName().getFormattedText());

            if (entity instanceof EntityLiving && entity.hasCustomName()) {
                name = TextFormatting.ITALIC + entity.getCustomNameTag();
            } else if (entity instanceof EntityVillager) {
                name = I18n.format("entity.Villager.name");
            }

            float namel = MINECRAFT.fontRenderer.getStringWidth(name) * s;
            if (namel + 20 > size * 2) {
                size = namel / 2F + 10F;
            }

            float ratio = HealthBarDirectRenderHelper.HUD_SCALE / RectInstancingRenderer.SIDE_LENGTH;

            float backgroundWidth = size * 2 + CfgFeatures.MOB_HEALTH_BAR.backgroundPadding * 2;
            float backgroundHeight = CfgFeatures.MOB_HEALTH_BAR.backgroundHeight * 2 + CfgFeatures.MOB_HEALTH_BAR.backgroundPadding;

            // xyz pos offset
            backgroundInstanceData[i * 6] = (float) (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks);
            backgroundInstanceData[i * 6 + 1] = (float) (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) + entity.height + (float) CfgFeatures.MOB_HEALTH_BAR.heightAbove;
            backgroundInstanceData[i * 6 + 2] = (float) (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks);
            // scale
            backgroundInstanceData[i * 6 + 3] = backgroundWidth * ratio;
            backgroundInstanceData[i * 6 + 4] = backgroundHeight * ratio;
            // height
            backgroundInstanceData[i * 6 + 5] = -HealthBarDirectRenderHelper.HUD_SCALE;

            float graySpaceWidth = size * 2;
            float graySpaceHeight = CfgFeatures.MOB_HEALTH_BAR.barHeight;

            // xyz pos offset
            graySpaceInstanceData[i * 6] = backgroundInstanceData[i * 6];
            graySpaceInstanceData[i * 6 + 1] = backgroundInstanceData[i * 6 + 1];
            graySpaceInstanceData[i * 6 + 2] = backgroundInstanceData[i * 6 + 2];
            // scale
            graySpaceInstanceData[i * 6 + 3] = graySpaceWidth * ratio;
            graySpaceInstanceData[i * 6 + 4] = graySpaceHeight * ratio;
            // height
            graySpaceInstanceData[i * 6 + 5] = -HealthBarDirectRenderHelper.HUD_SCALE * 2;
        }

        rectBackgroundRenderer.getMesh().setInstancePrimCount(entityListLength);
        rectBackgroundRenderer.getMesh().updateInstanceDataByBufferSubData(backgroundInstanceData);

        rectGraySpaceRenderer.getMesh().setInstancePrimCount(entityListLength);
        rectGraySpaceRenderer.getMesh().updateInstanceDataByBufferSubData(graySpaceInstanceData);

        // shader program is shared
        ShaderProgram program = rectBackgroundRenderer.getShaderProgram();

        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        matrix4f.rotate(-cameraRot.x, new Vector3f(0, 1, 0));
        matrix4f.rotate(cameraRot.y, new Vector3f(1, 0, 0));
        floatBuffer16.clear();
        matrix4f.store(floatBuffer16);
        floatBuffer16.flip();

        Color bgColor = EmtColor.parseColorFromHexString(CfgFeatures.MOB_HEALTH_BAR.backgroundColor);

        program.use();
        program.setUniform("modelView", EmtRender.getModelViewMatrix());
        program.setUniform("projection", EmtRender.getProjectionMatrix());
        program.setUniform("camPos", cameraPos.x, cameraPos.y, cameraPos.z);
        program.setUniform("transformation", floatBuffer16);
        program.setUniform("color", bgColor.getRed() / 255f, bgColor.getGreen() / 255f, bgColor.getBlue() / 255f, bgColor.getAlpha() / 255f);
        program.unuse();

        GlStateManager.enableBlend();
        GlStateManager.disableDepth();

        rectBackgroundRenderer.render();

        Color grayColor = EmtColor.parseColorFromHexString(CfgFeatures.MOB_HEALTH_BAR.graySpaceColor);

        program.use();
        program.setUniform("color", grayColor.getRed() / 255f, grayColor.getGreen() / 255f, grayColor.getBlue() / 255f, grayColor.getAlpha() / 255f);
        program.unuse();

        rectGraySpaceRenderer.render();
    }
}
