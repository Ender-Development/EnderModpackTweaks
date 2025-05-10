package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.mixin.minecraft.WorldClientAccessor;
import io.enderdev.endermodpacktweaks.render.mesh2d.RectMesh;
import io.enderdev.endermodpacktweaks.utils.EmtConfigHandler;
import io.enderdev.endermodpacktweaks.utils.EmtConfigParser;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.*;

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

    //<editor-fold desc="instancing">
    public boolean instancing = false;
    private ScaledResolution resolution = null;
    private RectInstancingRenderer rectBackgroundRenderer = null;
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

    //<editor-fold desc="instancing methods">
    private void initAndUpdateInstancingRenderer() {
        if (rectBackgroundRenderer == null) {
            rectBackgroundRenderer = (new RectInstancingRenderer(100)).init();
        }

        ScaledResolution newRes = new ScaledResolution(MINECRAFT);
        if (resolution == null) {
            resolution = newRes;
        } else if (resolution.getScaledWidth() != newRes.getScaledWidth() ||
                resolution.getScaledHeight() != newRes.getScaledHeight() ||
                resolution.getScaleFactor() != newRes.getScaleFactor()) {
            resolution = newRes;

            // update mesh
            RectMesh mesh = ((RectMesh) rectBackgroundRenderer.getMesh());
            mesh.setRect((resolution.getScaledWidth() - RectInstancingRenderer.SIDE_LENGTH) / 2f, (resolution.getScaledHeight() - RectInstancingRenderer.SIDE_LENGTH) / 2f, RectInstancingRenderer.SIDE_LENGTH, RectInstancingRenderer.SIDE_LENGTH).update();
        }
    }

    private final FloatBuffer floatBuffer16 = ByteBuffer.allocateDirect(16 << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
    // having the same visual behavior as HealthBarRenderHelper.renderHealthBar()
    private void healthBarRectBackgroundInstancing(List<EntityLivingBase> entities, float partialTicks, Vector3f cameraPos, Vector2f cameraRot) {
        if (rectBackgroundRenderer != null) {
            int entityListLength = Math.min(rectBackgroundRenderer.getMaxInstance(), entities.size());
            float[] instanceData = new float[rectBackgroundRenderer.getInstanceDataLength()];
            for (int i = 0; i < entityListLength; i++) {
                Entity entity = entities.get(i);
                instanceData[i * 6] = (float) (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks);
                instanceData[i * 6 + 1] = (float) (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) + entity.height + (float) CfgFeatures.MOB_HEALTH_BAR.heightAbove;
                instanceData[i * 6 + 2] = (float) (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks);
            }

            rectBackgroundRenderer.getMesh().setInstancePrimCount(entityListLength);
            rectBackgroundRenderer.getMesh().updateInstanceDataByBufferSubData(instanceData);

            GlStateManager.enableBlend();

            Matrix4f matrix4f = new Matrix4f();
            matrix4f.setIdentity();
            matrix4f.scale(new Vector3f(5, 5, 5));
            // plus PI to not trigger culling
            matrix4f.rotate(-cameraRot.x + (float) Math.PI, new Vector3f(0, 1, 0));
            matrix4f.rotate(-cameraRot.y, new Vector3f(1, 0, 0));
            floatBuffer16.clear();
            matrix4f.store(floatBuffer16);
            floatBuffer16.flip();

            rectBackgroundRenderer.getShaderProgram().use();
            rectBackgroundRenderer.getShaderProgram().setUniform("modelView", EmtRender.getModelViewMatrix());
            rectBackgroundRenderer.getShaderProgram().setUniform("projection", EmtRender.getProjectionMatrix());
            rectBackgroundRenderer.getShaderProgram().setUniform("camPos", cameraPos.x, cameraPos.y, cameraPos.z);
            rectBackgroundRenderer.getShaderProgram().setUniform("screenWidthHeightRatio", resolution.getScaledWidth_double() / resolution.getScaledHeight_double());
            rectBackgroundRenderer.getShaderProgram().setUniform("transformation", floatBuffer16);
            rectBackgroundRenderer.getShaderProgram().unuse();

            rectBackgroundRenderer.render();
        }
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
            initAndUpdateInstancingRenderer();
            // render rect background for all health bars
            healthBarRectBackgroundInstancing(entities, partialTicks, cameraPos, EmtRender.getCameraRotationInRadian());
        }

        // optifine compat: disable shader program
        int oldProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        if (oldProgram != 0) GL20.glUseProgram(0);
        for (EntityLivingBase entity : entities) {
            // fixed-func health bar rendering
            HealthBarRenderHelper.renderHealthBar(entity, partialTicks, false);
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
