package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.EnumShapeType;
import io.enderdev.endermodpacktweaks.utils.EmtColor;
import io.enderdev.endermodpacktweaks.utils.EmtConfigHandler;
import io.enderdev.endermodpacktweaks.utils.EmtConfigParser;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BarRenderer {
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

    public static final EmtConfigHandler<EmtConfigParser.ConfigItemWithFloat> rangeModifiers = new EmtConfigHandler<>(
            CfgFeatures.MOB_HEALTH_BAR.distanceMultipliers,
            EmtConfigParser.ConfigItemWithFloat::new
    );

    public static void renderHealthBar(EntityLivingBase passedEntity, float partialTicks, Entity viewPoint) {
        Stack<EntityLivingBase> ridingStack = new Stack<>();

        EntityLivingBase entity = passedEntity;
        ridingStack.push(entity);

        while (entity.getRidingEntity() != null && entity.getRidingEntity() instanceof EntityLivingBase) {
            entity = (EntityLivingBase) entity.getRidingEntity();
            ridingStack.push(entity);
        }

        Minecraft mc = Minecraft.getMinecraft();

        float pastTranslate = 0F;
        while (!ridingStack.isEmpty()) {
            entity = ridingStack.pop();
            boolean boss = !entity.isNonBoss();

            String entityID = EntityList.getEntityString(entity);
            if (Arrays.asList(CfgFeatures.MOB_HEALTH_BAR.mobBlacklist).contains(entityID)) {
                continue;
            }

            processing:
            {
                float maxDistance = CfgFeatures.MOB_HEALTH_BAR.maxDistance;
                if (CfgFeatures.MOB_HEALTH_BAR.distanceMultipliers.length != 0 && rangeModifiers.equipped(mc.player)) {
                    EmtConfigParser.ConfigItemWithFloat modifier = (EmtConfigParser.ConfigItemWithFloat) rangeModifiers.getEquipped(mc.player);
                    maxDistance *= modifier != null ? modifier.value() : 1F;
                }
                float distance = passedEntity.getDistance(viewPoint);
                if (distance > maxDistance || !passedEntity.canEntityBeSeen(viewPoint) || entity.isInvisible()) {
                    break processing;
                }
                if (!CfgFeatures.MOB_HEALTH_BAR.showOnBosses && boss) {
                    break processing;
                }
                if (!CfgFeatures.MOB_HEALTH_BAR.showOnPlayers && entity instanceof EntityPlayer) {
                    break processing;
                }

                double entityX = passedEntity.lastTickPosX + (passedEntity.posX - passedEntity.lastTickPosX) * partialTicks;
                double entityY = passedEntity.lastTickPosY + (passedEntity.posY - passedEntity.lastTickPosY) * partialTicks;
                double entityZ = passedEntity.lastTickPosZ + (passedEntity.posZ - passedEntity.lastTickPosZ) * partialTicks;

                float scale = 0.026666672F;
                float maxHealth = entity.getMaxHealth();
                float health = Math.min(maxHealth, entity.getHealth());

                if (maxHealth <= 0) {
                    break processing;
                }

                float percent = (int) ((health / maxHealth) * 100F);
                RenderManager renderManager = mc.getRenderManager();

                GlStateManager.pushMatrix();
                GlStateManager.translate((float) (entityX - renderManager.viewerPosX), (float) (entityY - renderManager.viewerPosY + passedEntity.height + CfgFeatures.MOB_HEALTH_BAR.heightAbove), (float) (entityZ - renderManager.viewerPosZ));
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                GlStateManager.scale(-scale, -scale, scale);
//                boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING); lighting is always `false` here when I tested and it takes up a surprising amount of time in Spark ~roz
//                GlStateManager.disableLighting();
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

                GlStateManager.translate(0F, pastTranslate, 0F);

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
                if (CfgFeatures.MOB_HEALTH_BAR.drawBackground) {
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
//                if (lighting) {
//                    GlStateManager.enableLighting();
//                }
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.popMatrix();

                pastTranslate -= bgHeight + barHeight + padding;
            }
        }
    }

    // Everything in this method randomly becomes null, pls ignore!
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

    public static Entity getEntityLookedAt(Entity e) {
        Entity foundEntity = null;

        final double finalDistance = 32;
        double distance = finalDistance;
        RayTraceResult pos = raycast(e, finalDistance);
        Vec3d positionVector = e.getPositionVector();
        if (e instanceof EntityPlayer) {
            positionVector = positionVector.add(0, e.getEyeHeight(), 0);
        }

        if (pos != null) {
            distance = pos.hitVec.distanceTo(positionVector);
        }

        Vec3d lookVector = e.getLookVec();
        Vec3d reachVector = positionVector.add(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance);

        Entity lookedEntity = null;
        List<Entity> entitiesInBoundingBox = e.getEntityWorld().getEntitiesWithinAABBExcludingEntity(e, e.getEntityBoundingBox().grow(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance).expand(1F, 1F, 1F));
        double minDistance = distance;

        for (Entity entity : entitiesInBoundingBox) {
            if (entity.canBeCollidedWith()) {
                float collisionBorderSize = entity.getCollisionBorderSize();
                AxisAlignedBB hitbox = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                RayTraceResult interceptPosition = hitbox.calculateIntercept(positionVector, reachVector);

                if (hitbox.contains(positionVector)) {
                    if (0.0D < minDistance || minDistance == 0.0D) {
                        lookedEntity = entity;
                        minDistance = 0.0D;
                    }
                } else if (interceptPosition != null) {
                    double distanceToEntity = positionVector.distanceTo(interceptPosition.hitVec);

                    if (distanceToEntity < minDistance || minDistance == 0.0D) {
                        lookedEntity = entity;
                        minDistance = distanceToEntity;
                    }
                }
            }

            if (lookedEntity != null && (minDistance < distance || pos == null)) {
                foundEntity = lookedEntity;
            }
        }

        return foundEntity;
    }

    public static RayTraceResult raycast(Entity e, double len) {
        Vec3d vec = new Vec3d(e.posX, e.posY, e.posZ);
        if (e instanceof EntityPlayer) {
            vec = vec.add(new Vec3d(0, e.getEyeHeight(), 0));
        }

        Vec3d look = e.getLookVec();
        return raycast(e.getEntityWorld(), vec, look, len);
    }

    public static RayTraceResult raycast(World world, Vec3d origin, Vec3d ray, double len) {
        Vec3d end = origin.add(ray.normalize().scale(len));
        return world.rayTraceBlocks(origin, end);
    }
}