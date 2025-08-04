package io.enderdev.endermodpacktweaks.features.healthbar.render;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.features.healthbar.HealthBarData;
import io.enderdev.endermodpacktweaks.features.healthbar.HealthBarHandler;
import io.enderdev.endermodpacktweaks.render.shader.ShaderProgram;
import io.enderdev.endermodpacktweaks.utils.EmtColor;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.Map;

public final class HealthBarInstancingHelper {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    private static final FloatBuffer FLOAT_BUFFER_16 = ByteBuffer.allocateDirect(16 << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();

    private static RectInstancingRenderer rectBackgroundRenderer = null;
    private static RectInstancingRenderer rectGraySpaceRenderer = null;
    private static RectInstancingRenderer rectHealthBarRenderer = null;

    // should have the same visual behavior as HealthBarDirectRenderHelper.renderHealthBar()
    public static void renderRectHealthBars(Map<EntityLivingBase, HealthBarData> entities, float partialTicks, Vector3f worldOffset, Vector2f cameraRot) {
        if (rectBackgroundRenderer == null) {
            rectBackgroundRenderer = (new RectInstancingRenderer(CfgFeatures.MOB_HEALTH_BAR.maxInstancingCount)).init();
        }
        if (rectGraySpaceRenderer == null) {
            rectGraySpaceRenderer = (new RectInstancingRenderer(CfgFeatures.MOB_HEALTH_BAR.maxInstancingCount)).init();
        }
        if (rectHealthBarRenderer == null) {
            rectHealthBarRenderer = (new RectInstancingRenderer(CfgFeatures.MOB_HEALTH_BAR.maxInstancingCount)).init();
        }

        int entityListLength =
                Math.min(rectHealthBarRenderer.getMaxInstance(),
                        Math.min(rectGraySpaceRenderer.getMaxInstance(),
                                Math.min(rectBackgroundRenderer.getMaxInstance(), entities.size())));

        float[] backgroundInstanceData = new float[entityListLength * RectInstancingRenderer.INSTANCE_DATA_UNIT_SIZE];
        float[] graySpaceInstanceData = new float[entityListLength * RectInstancingRenderer.INSTANCE_DATA_UNIT_SIZE];
        float[] healthBarInstanceData = new float[entityListLength * RectInstancingRenderer.INSTANCE_DATA_UNIT_SIZE];

        float padding = CfgFeatures.MOB_HEALTH_BAR.backgroundPadding;
        int bgHeight = CfgFeatures.MOB_HEALTH_BAR.backgroundHeight;
        int barHeight = CfgFeatures.MOB_HEALTH_BAR.barHeight;

        Iterator<Map.Entry<EntityLivingBase, HealthBarData>> iterator = entities.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext() && i < entityListLength) {
            Map.Entry<EntityLivingBase, HealthBarData> entry = iterator.next();

            EntityLivingBase entity = entry.getKey();
            HealthBarData healthBarData = entry.getValue();

            boolean boss = !entity.isNonBoss();

            float size = boss ? CfgFeatures.MOB_HEALTH_BAR.plateSizeBoss : CfgFeatures.MOB_HEALTH_BAR.plateSize;

            float s = 0.5F;
            float namel = MINECRAFT.fontRenderer.getStringWidth(healthBarData.name) * s;
            if (namel + 20 > size * 2) {
                size = namel / 2F + 10F;
            }

            float x = (float) (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks);
            float y = (float) (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks);
            float z = (float) (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks);

            y += entity.height + (float) CfgFeatures.MOB_HEALTH_BAR.heightAbove;
            y -= (float) healthBarData.ridingStackPos * (bgHeight + barHeight + padding);

            float ratio = HealthBarHandler.HEALTH_BAR_HUD_SCALE / RectInstancingRenderer.SIDE_LENGTH;

            // Background
            // xyz pos offset
            backgroundInstanceData[i * 8] = x;
            backgroundInstanceData[i * 8 + 1] = y;
            backgroundInstanceData[i * 8 + 2] = z;
            // hud scale
            backgroundInstanceData[i * 8 + 3] = (size * 2 + padding * 2) * ratio;
            backgroundInstanceData[i * 8 + 4] = (bgHeight * 2 + padding) * ratio;
            // hud offset
            backgroundInstanceData[i * 8 + 5] = 0f;
            backgroundInstanceData[i * 8 + 6] = -HealthBarHandler.HEALTH_BAR_HUD_SCALE;

            // Gray Space
            // xyz pos offset
            graySpaceInstanceData[i * 8] = x;
            graySpaceInstanceData[i * 8 + 1] = y;
            graySpaceInstanceData[i * 8 + 2] = z;
            // hud scale
            graySpaceInstanceData[i * 8 + 3] = size * 2 * ratio;
            graySpaceInstanceData[i * 8 + 4] = barHeight * ratio;
            // hud offset
            graySpaceInstanceData[i * 8 + 5] = 0f;
            graySpaceInstanceData[i * 8 + 6] = -HealthBarHandler.HEALTH_BAR_HUD_SCALE * 2;

            float maxHealth = entity.getMaxHealth();
            float health = Math.min(maxHealth, entity.getHealth());
            float healthSize = size * (health / maxHealth);

            // Health Bar
            // xyz pos offset
            healthBarInstanceData[i * 8] = x;
            healthBarInstanceData[i * 8 + 1] = y;
            healthBarInstanceData[i * 8 + 2] = z;
            // hud scale
            healthBarInstanceData[i * 8 + 3] = healthSize * 2 * ratio;
            healthBarInstanceData[i * 8 + 4] = barHeight * ratio;
            // hud offset
            healthBarInstanceData[i * 8 + 5] = (size - healthSize) * ratio;
            healthBarInstanceData[i * 8 + 6] = -HealthBarHandler.HEALTH_BAR_HUD_SCALE * 2;

            int r = 0;
            int g = 255;
            int b = 0;
            if (entity instanceof IMob) {
                r = 255;
                g = 0;
            }
            if (boss) {
                r = 128;
                g = 0;
                b = 128;
            }
            boolean useHue = !CfgFeatures.MOB_HEALTH_BAR.colorByType;
            if (useHue) {
                float hue = Math.max(0F, (health / maxHealth) / 3F - 0.07F);
                Color color = Color.getHSBColor(hue, 1F, 1F);
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
            }
            Color healthColor = new Color(r, g, b, CfgFeatures.MOB_HEALTH_BAR.healthBarAlpha);

            healthBarInstanceData[i * 8 + 7] = Float.intBitsToFloat(healthColor.getRGB());

            i++;
        }

        rectBackgroundRenderer.getMesh().setInstancePrimCount(entityListLength);
        rectBackgroundRenderer.getMesh().updateInstanceDataByBufferSubData(0, backgroundInstanceData);

        rectGraySpaceRenderer.getMesh().setInstancePrimCount(entityListLength);
        rectGraySpaceRenderer.getMesh().updateInstanceDataByBufferSubData(0, graySpaceInstanceData);

        rectHealthBarRenderer.getMesh().setInstancePrimCount(entityListLength);
        rectHealthBarRenderer.getMesh().updateInstanceDataByBufferSubData(0, healthBarInstanceData);

        // shader program is shared
        ShaderProgram program = rectBackgroundRenderer.getShaderProgram();

        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        matrix4f.rotate(-cameraRot.x, new Vector3f(0, 1, 0));
        matrix4f.rotate(cameraRot.y, new Vector3f(1, 0, 0));
        FLOAT_BUFFER_16.clear();
        matrix4f.store(FLOAT_BUFFER_16);
        FLOAT_BUFFER_16.flip();

        Color bgColor = EmtColor.parseColorFromHexString(CfgFeatures.MOB_HEALTH_BAR.backgroundColor);

        program.use();
        program.setUniform("modelView", EmtRender.getModelViewMatrix());
        program.setUniform("projection", EmtRender.getProjectionMatrix());
        program.setUniform("camPos", worldOffset.x, worldOffset.y, worldOffset.z);
        program.setUniform("transformation", FLOAT_BUFFER_16);
        program.setUniform("color", bgColor.getRed() / 255f, bgColor.getGreen() / 255f, bgColor.getBlue() / 255f, bgColor.getAlpha() / 255f);
        program.unuse();

        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        rectBackgroundRenderer.render();

        Color grayColor = EmtColor.parseColorFromHexString(CfgFeatures.MOB_HEALTH_BAR.graySpaceColor);

        program.use();
        program.setUniform("color", grayColor.getRed() / 255f, grayColor.getGreen() / 255f, grayColor.getBlue() / 255f, grayColor.getAlpha() / 255f);
        program.unuse();

        rectGraySpaceRenderer.render();

        rectHealthBarRenderer.render();

        // hybrid part: draw texts and icons without instancing

        iterator = entities.entrySet().iterator();
        i = 0;
        while (iterator.hasNext() && i < entityListLength) {
            Map.Entry<EntityLivingBase, HealthBarData> entry = iterator.next();

            EntityLivingBase entity = entry.getKey();
            HealthBarData healthBarData = entry.getValue();

            renderHealthBarInfoSeparately(entity, healthBarData, partialTicks, worldOffset, cameraRot);

            i++;
        }

        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    // Armor
    private static final ItemStack IRON_CHESTPLATE = new ItemStack(Items.IRON_CHESTPLATE);
    private static final ItemStack DIAMOND_CHESTPLATE = new ItemStack(Items.DIAMOND_CHESTPLATE);
    // Attributes
    private static final ItemStack SPIDER_EYE = new ItemStack(Items.SPIDER_EYE);
    private static final ItemStack ROTTEN_FLESH = new ItemStack(Items.ROTTEN_FLESH);
    private static final ItemStack TOTEM_OF_UNDYING = new ItemStack(Items.TOTEM_OF_UNDYING);
    private static final ItemStack SKULL = new ItemStack(Items.SKULL, 1, 4);
    // Boss
    private static final ItemStack BOSS_SKULL = new ItemStack(Items.SKULL);

    private static void renderHealthBarInfoSeparately(EntityLivingBase entity, HealthBarData healthBarData, float partialTicks, Vector3f cameraPos, Vector2f cameraRot) {
        String entityID = EntityList.getEntityString(entity);
        boolean boss = !entity.isNonBoss();

        float entityX = (float) (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks);
        float entityY = (float) (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks);
        float entityZ = (float) (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks);

        float maxHealth = entity.getMaxHealth();
        float health = Math.min(maxHealth, entity.getHealth());
        float percent = (int) ((health / maxHealth) * 100F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(entityX - cameraPos.x, entityY - cameraPos.y + entity.height + (float) CfgFeatures.MOB_HEALTH_BAR.heightAbove, entityZ - cameraPos.z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-(float) Math.toDegrees(cameraRot.x), 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float) Math.toDegrees(cameraRot.y), 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-HealthBarHandler.HEALTH_BAR_HUD_SCALE, -HealthBarHandler.HEALTH_BAR_HUD_SCALE, HealthBarHandler.HEALTH_BAR_HUD_SCALE);
        GlStateManager.disableTexture2D();

        float padding = CfgFeatures.MOB_HEALTH_BAR.backgroundPadding;
        int bgHeight = CfgFeatures.MOB_HEALTH_BAR.backgroundHeight;
        int barHeight = CfgFeatures.MOB_HEALTH_BAR.barHeight;
        float size = CfgFeatures.MOB_HEALTH_BAR.plateSize;

        ItemStack stack = null;

        if (entity instanceof IMob) {
            EnumCreatureAttribute attr = entity.getCreatureAttribute();
            switch (attr) {
                case ARTHROPOD:
                    stack = SPIDER_EYE;
                    break;
                case UNDEAD:
                    stack = ROTTEN_FLESH;
                    break;
                case ILLAGER:
                    stack = TOTEM_OF_UNDYING;
                    break;
                default:
                    stack = SKULL;
            }
        }

        if (boss) {
            stack = BOSS_SKULL;
            size = CfgFeatures.MOB_HEALTH_BAR.plateSizeBoss;
        }

        GlStateManager.translate(0F, -(float) healthBarData.ridingStackPos * (bgHeight + barHeight + padding), 0F);

        float s = 0.5F;
        float namel = MINECRAFT.fontRenderer.getStringWidth(healthBarData.name) * s;
        if (namel + 20 > size * 2) {
            size = namel / 2F + 10F;
        }

        GlStateManager.enableTexture2D();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-size, -4.5F, 0F);
        GlStateManager.scale(s, s, s);

        if (CfgFeatures.MOB_HEALTH_BAR.showName) {
            MINECRAFT.fontRenderer.drawString(healthBarData.name, 0, 0, 0xFFFFFF);
        }

        GlStateManager.pushMatrix();
        float s1 = 0.75F;
        GlStateManager.scale(s1, s1, s1);

        int h = CfgFeatures.MOB_HEALTH_BAR.hpTextHeight;
        String maxHpStr = TextFormatting.BOLD + "" + Math.round(maxHealth * 100.0) / 100.0;
        String hpStr = "" + Math.round(health * 100.0) / 100.0;
        String percStr = (int) percent + "%";

        if (maxHpStr.endsWith(".0")) {
            maxHpStr = maxHpStr.substring(0, maxHpStr.length() - 2);
        }
        if (hpStr.endsWith(".0")) {
            hpStr = hpStr.substring(0, hpStr.length() - 2);
        }

        if (CfgFeatures.MOB_HEALTH_BAR.showCurrentHP) {
            MINECRAFT.fontRenderer.drawString(hpStr, 2, h, 0xFFFFFF);
        }
        if (CfgFeatures.MOB_HEALTH_BAR.showMaxHP) {
            MINECRAFT.fontRenderer.drawString(maxHpStr, (int) (size / (s * s1) * 2) - 2 - MINECRAFT.fontRenderer.getStringWidth(maxHpStr), h, 0xFFFFFF);
        }
        if (CfgFeatures.MOB_HEALTH_BAR.showPercentage) {
            MINECRAFT.fontRenderer.drawString(percStr, (int) (size / (s * s1)) - MINECRAFT.fontRenderer.getStringWidth(percStr) / 2, h, 0xFFFFFFFF);
        }
        if (CfgFeatures.MOB_HEALTH_BAR.enableDebugInfo && MINECRAFT.gameSettings.showDebugInfo) {
            MINECRAFT.fontRenderer.drawString("ID: \"" + entityID + "\"", 0, h + 16, 0xFFFFFFFF);
        }
        GlStateManager.popMatrix();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int off = 0;

        s1 = 0.5F;
        GlStateManager.scale(s1, s1, s1);
        GlStateManager.translate(size / (s * s1) * 2 - 16, 0F, 0F);
        MINECRAFT.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (stack != null && CfgFeatures.MOB_HEALTH_BAR.showAttributes) {
            HealthBarDirectRenderHelper.renderIcon(off, 0, stack, 16, 16);
            off -= 16;
        }

        if (CfgFeatures.MOB_HEALTH_BAR.showArmor && entity.getTotalArmorValue() > 0) {
            int armor = entity.getTotalArmorValue();
            int ironArmor = armor % 5;
            int diamondArmor = armor / 5;
            if (!CfgFeatures.MOB_HEALTH_BAR.groupArmor) {
                ironArmor = armor;
                diamondArmor = 0;
            }

            for (int i = 0; i < ironArmor; i++) {
                HealthBarDirectRenderHelper.renderIcon(off, 0, IRON_CHESTPLATE, 16, 16);
                off -= 4;
            }
            for (int i = 0; i < diamondArmor; i++) {
                HealthBarDirectRenderHelper.renderIcon(off, 0, DIAMOND_CHESTPLATE, 16, 16);
                off -= 4;
            }
        }

        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
    }
}
