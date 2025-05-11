package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.EnumShapeType;
import io.enderdev.endermodpacktweaks.mixin.minecraft.WorldClientAccessor;
import io.enderdev.endermodpacktweaks.render.shader.ShaderProgram;
import io.enderdev.endermodpacktweaks.utils.EmtColor;
import io.enderdev.endermodpacktweaks.utils.EmtConfigHandler;
import io.enderdev.endermodpacktweaks.utils.EmtConfigParser;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.*;
import java.util.List;

public class HealthBarHandler {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    //<editor-fold desc="config">
    public final EmtConfigHandler<EmtConfigParser.ConfigItem> whitelist = new EmtConfigHandler<>(
            CfgFeatures.MOB_HEALTH_BAR.onlyRenderWithEquipment,
            EmtConfigParser.ConfigItem::new
    );
    public final EmtConfigHandler<EmtConfigParser.ConfigItemWithFloat> rangeModifiers = new EmtConfigHandler<>(
            CfgFeatures.MOB_HEALTH_BAR.distanceMultipliers,
            EmtConfigParser.ConfigItemWithFloat::new
    );
    //</editor-fold>

    //<editor-fold desc="key bind">
    private final KeyBinding key;
    private boolean down;
    private boolean shouldRender = true;

    public HealthBarHandler() {
        key = new KeyBinding("keybind.endermodpacktweaks.toggle", 0, "key.categories.misc");
        ClientRegistry.registerKeyBinding(key);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        boolean wasDown = down;
        down = key.isKeyDown();
        if (mc.inGameHasFocus && down && !wasDown) {
            shouldRender = !shouldRender;
        }
    }
    //</editor-fold>

    //<editor-fold desc="instancing">
    public boolean instancing = false;
    private RectInstancingRenderer rectBackgroundRenderer = null;
    private RectInstancingRenderer rectGraySpaceRenderer = null;
    //</editor-fold>

    //<editor-fold desc="render background by instancing">
    private final FloatBuffer floatBuffer16 = ByteBuffer.allocateDirect(16 << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();

    // should have the same visual behavior as HealthBarRenderHelper.renderHealthBar()
    private void rectHealthBarInstancing(List<EntityLivingBase> entities, float partialTicks, Vector3f cameraPos, Vector2f cameraRot) {
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

            float ratio = HealthBarRenderHelper.HUD_SCALE / RectInstancingRenderer.SIDE_LENGTH;

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
            backgroundInstanceData[i * 6 + 5] = -HealthBarRenderHelper.HUD_SCALE;

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
            graySpaceInstanceData[i * 6 + 5] = -HealthBarRenderHelper.HUD_SCALE * 2;
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
    //</editor-fold>

    private final Frustum frustum = new Frustum();

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        // early escape
        if (MINECRAFT.player == null) return;
        if ((!CfgFeatures.MOB_HEALTH_BAR.renderInF1 && !Minecraft.isGuiEnabled()) || !shouldRender) return;
        if (CfgFeatures.MOB_HEALTH_BAR.onlyRenderWithEquipment.length != 0 && !whitelist.equipped(MINECRAFT.player)) return;
        Entity cameraEntity = MINECRAFT.getRenderViewEntity();
        if (cameraEntity == null || !cameraEntity.isEntityAlive()) return;

        BlockPos cameraBlockPos = cameraEntity.getPosition();
        Vector3f cameraPos = EmtRender.getCameraPos();
        float partialTicks = (float) EmtRender.getPartialTick();

        // render health bar for these entities
        List<EntityLivingBase> entities = new ArrayList<>();

        // collect entities
        if (CfgFeatures.MOB_HEALTH_BAR.showOnlyFocused) {
            Entity focused = getEntityLookedAt(MINECRAFT.player);
            if (focused instanceof EntityLivingBase && focused.isEntityAlive()) {
                collectHealthBarEntities(entities, (EntityLivingBase) focused, cameraEntity);
            }
        } else {
            frustum.setPosition(cameraPos.x, cameraPos.y, cameraPos.z);
            for (Entity entity : ((WorldClientAccessor) MINECRAFT.world).getEntityList()) {
                if (entity instanceof EntityLivingBase
                        && entity != MINECRAFT.player
                        && entity.isInRangeToRender3d(cameraBlockPos.getX(), cameraBlockPos.getY(), cameraBlockPos.getZ())
                        && (entity.ignoreFrustumCheck || frustum.isBoundingBoxInFrustum(entity.getEntityBoundingBox()))
                        && entity.isEntityAlive()
                        && entity.getRecursivePassengers().isEmpty()) {
                    collectHealthBarEntities(entities, (EntityLivingBase) entity, cameraEntity);
                }
            }
        }

        if (instancing) {
            // instancing health bars
            if (CfgFeatures.MOB_HEALTH_BAR.shapeBackground == EnumShapeType.STRAIGHT) {
                if (rectBackgroundRenderer == null) {
                    rectBackgroundRenderer = (new RectInstancingRenderer(100)).init();
                }
                if (rectGraySpaceRenderer == null) {
                    rectGraySpaceRenderer = (new RectInstancingRenderer(100)).init();
                }
                rectHealthBarInstancing(entities, partialTicks, cameraPos, EmtRender.getCameraRotationInRadian());
            }
        }

        // optifine compat: disable shader program
        int oldProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        if (oldProgram != 0) GL20.glUseProgram(0);
        for (EntityLivingBase entity : entities) {
            // fixed-func health bar rendering
            HealthBarRenderHelper.renderHealthBar(entity, partialTicks, instancing);
        }
        if (oldProgram != 0) GL20.glUseProgram(oldProgram);
    }

    //<editor-fold desc="helpers">
    private void collectHealthBarEntities(List<EntityLivingBase> entities, EntityLivingBase entity, Entity viewPoint) {
        Stack<EntityLivingBase> ridingStack = new Stack<>();
        ridingStack.push(entity);

        while (entity.getRidingEntity() != null && entity.getRidingEntity() instanceof EntityLivingBase) {
            entity = (EntityLivingBase) entity.getRidingEntity();
            ridingStack.push(entity);
        }

        Minecraft mc = Minecraft.getMinecraft();

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
                float distance = entity.getDistance(viewPoint);
                if (distance > maxDistance || !entity.canEntityBeSeen(viewPoint) || entity.isInvisible()) {
                    break processing;
                }
                if (!CfgFeatures.MOB_HEALTH_BAR.showOnBosses && boss) {
                    break processing;
                }
                if (!CfgFeatures.MOB_HEALTH_BAR.showOnPlayers && entity instanceof EntityPlayer) {
                    break processing;
                }

                if (entity.getMaxHealth() <= 0) {
                    break processing;
                }

                entities.add(entity);
            }
        }
    }

    private Entity getEntityLookedAt(Entity e) {
        Entity foundEntity = null;

        double finalDistance = CfgFeatures.MOB_HEALTH_BAR.maxDistance;
        if (CfgFeatures.MOB_HEALTH_BAR.distanceMultipliers.length != 0 && rangeModifiers.equipped((EntityPlayer) e)) {
            EmtConfigParser.ConfigItemWithFloat modifier = (EmtConfigParser.ConfigItemWithFloat) rangeModifiers.getEquipped((EntityPlayer) e);
            finalDistance *= modifier != null ? modifier.value() : 1F;
        }
        double distance = finalDistance;
        RayTraceResult pos = rayCast(e, finalDistance);
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

    private static RayTraceResult rayCast(Entity e, double len) {
        Vec3d vec = new Vec3d(e.posX, e.posY, e.posZ);
        if (e instanceof EntityPlayer) {
            vec = vec.add(new Vec3d(0, e.getEyeHeight(), 0));
        }

        Vec3d look = e.getLookVec();
        return rayCast(e.getEntityWorld(), vec, look, len);
    }

    private static RayTraceResult rayCast(World world, Vec3d origin, Vec3d ray, double len) {
        Vec3d end = origin.add(ray.normalize().scale(len));
        return world.rayTraceBlocks(origin, end);
    }
    //</editor-fold>
}
