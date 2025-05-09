package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.EnumShapeType;
import io.enderdev.endermodpacktweaks.utils.EmtColor;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public final class HealthBarRenderHelper {
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

    public static void renderHealthBar(EntityLivingBase entity, float partialTicks, boolean instancing) {
        Minecraft mc = Minecraft.getMinecraft();

        String entityID = EntityList.getEntityString(entity);
        boolean boss = !entity.isNonBoss();

        double entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
        double entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;

        float scale = 0.026666672F;
        float maxHealth = entity.getMaxHealth();
        float health = Math.min(maxHealth, entity.getHealth());

        float percent = (int) ((health / maxHealth) * 100F);
        RenderManager renderManager = mc.getRenderManager();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) (entityX - renderManager.viewerPosX), (float) (entityY - renderManager.viewerPosY + entity.height + CfgFeatures.MOB_HEALTH_BAR.heightAbove), (float) (entityZ - renderManager.viewerPosZ));
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        float padding = CfgFeatures.MOB_HEALTH_BAR.backgroundPadding;
        int bgHeight = CfgFeatures.MOB_HEALTH_BAR.backgroundHeight;
        int barHeight = CfgFeatures.MOB_HEALTH_BAR.barHeight;
        float size = CfgFeatures.MOB_HEALTH_BAR.plateSize;

        int r = 0;
        int g = 255;
        int b = 0;

        ItemStack stack = null;

        if (entity instanceof IMob) {
            r = 255;
            g = 0;
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

        float s = 0.5F;
        String name = I18n.format(entity.getDisplayName().getFormattedText());

        if (entity instanceof EntityLiving && entity.hasCustomName()) {
            name = TextFormatting.ITALIC + entity.getCustomNameTag();
        } else if (entity instanceof EntityVillager) {
            name = I18n.format("entity.Villager.name");
        }

        float namel = mc.fontRenderer.getStringWidth(name) * s;
        if (namel + 20 > size * 2) {
            size = namel / 2F + 10F;
        }
        float healthSize = size * (health / maxHealth);

        // Background
        if (CfgFeatures.MOB_HEALTH_BAR.drawBackground && !instancing) {
            Color bgColor = EmtColor.parseColorFromHexString(CfgFeatures.MOB_HEALTH_BAR.backgroundColor);
            if (CfgFeatures.MOB_HEALTH_BAR.shapeBackground == EnumShapeType.STRAIGHT) {
                EmtRender.renderRect(-size - padding, -bgHeight, size * 2 + padding * 2, bgHeight * 2 + padding, bgColor);
            }
            if (CfgFeatures.MOB_HEALTH_BAR.shapeBackground == EnumShapeType.ROUND) {
                EmtRender.renderRoundedRect(-size - padding, -bgHeight, size * 2 + padding * 2, bgHeight * 2 + padding, CfgFeatures.MOB_HEALTH_BAR.backgroundRadius, bgColor);
            }
        }

        // Gray Space
        if (CfgFeatures.MOB_HEALTH_BAR.drawGraySpace) {
            Color grayColor = EmtColor.parseColorFromHexString(CfgFeatures.MOB_HEALTH_BAR.graySpaceColor);
            if (CfgFeatures.MOB_HEALTH_BAR.shapeBar == EnumShapeType.STRAIGHT) {
                EmtRender.renderRect(-size, 0, size * 2, barHeight, grayColor);
            }
            if (CfgFeatures.MOB_HEALTH_BAR.shapeBar == EnumShapeType.ROUND) {
                EmtRender.renderRoundedRect(-size, 0, size * 2, barHeight, CfgFeatures.MOB_HEALTH_BAR.barRadius, grayColor);
            }
        }

        // Health Bar
        if (CfgFeatures.MOB_HEALTH_BAR.drawHealthBar) {
            Color healthColor = new Color(r, g, b, CfgFeatures.MOB_HEALTH_BAR.healthBarAlpha);
            if (CfgFeatures.MOB_HEALTH_BAR.shapeBar == EnumShapeType.STRAIGHT) {
                EmtRender.renderRect(-size, 0, healthSize * 2, barHeight, healthColor);
            }
            if (CfgFeatures.MOB_HEALTH_BAR.shapeBar == EnumShapeType.ROUND) {
                EmtRender.renderRoundedRect(-size, 0, healthSize * 2, barHeight, CfgFeatures.MOB_HEALTH_BAR.barRadius, healthColor);
            }
        }

        GlStateManager.enableTexture2D();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-size, -4.5F, 0F);
        GlStateManager.scale(s, s, s);

        if (CfgFeatures.MOB_HEALTH_BAR.showName) {
            mc.fontRenderer.drawString(name, 0, 0, 0xFFFFFF);
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
            mc.fontRenderer.drawString(hpStr, 2, h, 0xFFFFFF);
        }
        if (CfgFeatures.MOB_HEALTH_BAR.showMaxHP) {
            mc.fontRenderer.drawString(maxHpStr, (int) (size / (s * s1) * 2) - 2 - mc.fontRenderer.getStringWidth(maxHpStr), h, 0xFFFFFF);
        }
        if (CfgFeatures.MOB_HEALTH_BAR.showPercentage) {
            mc.fontRenderer.drawString(percStr, (int) (size / (s * s1)) - mc.fontRenderer.getStringWidth(percStr) / 2, h, 0xFFFFFFFF);
        }
        if (CfgFeatures.MOB_HEALTH_BAR.enableDebugInfo && mc.gameSettings.showDebugInfo) {
            mc.fontRenderer.drawString("ID: \"" + entityID + "\"", 0, h + 16, 0xFFFFFFFF);
        }
        GlStateManager.popMatrix();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int off = 0;

        s1 = 0.5F;
        GlStateManager.scale(s1, s1, s1);
        GlStateManager.translate(size / (s * s1) * 2 - 16, 0F, 0F);
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (stack != null && CfgFeatures.MOB_HEALTH_BAR.showAttributes) {
            renderIcon(off, 0, stack, 16, 16);
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
                renderIcon(off, 0, IRON_CHESTPLATE, 16, 16);
                off -= 4;
            }
            for (int i = 0; i < diamondArmor; i++) {
                renderIcon(off, 0, DIAMOND_CHESTPLATE, 16, 16);
                off -= 4;
            }
        }

        GlStateManager.popMatrix();

        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    // Everything in this method randomly becomes null, pls ignore!
    @SuppressWarnings("all")
    private static void renderIcon(int vertexX, int vertexY, ItemStack stack, int intU, int intV) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        IBakedModel iBakedModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
        TextureAtlasSprite meow = iBakedModel.getParticleTexture();
        if (meow == null) {
            return;
        }

        String iconName = meow.getIconName();
        if (iconName == null) {
            return;
        }
        TextureAtlasSprite textureAtlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iconName);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(vertexX, vertexY + intV, 0.0D).tex(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxV()).endVertex();
        buffer.pos(vertexX + intU, vertexY + intV, 0.0D).tex(textureAtlasSprite.getMaxU(), textureAtlasSprite.getMaxV()).endVertex();
        buffer.pos(vertexX + intU, vertexY, 0.0D).tex(textureAtlasSprite.getMaxU(), textureAtlasSprite.getMinV()).endVertex();
        buffer.pos(vertexX, vertexY, 0.0D).tex(textureAtlasSprite.getMinU(), textureAtlasSprite.getMinV()).endVertex();
        tessellator.draw();
    }
}