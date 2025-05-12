package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.EnumShapeType;
import io.enderdev.endermodpacktweaks.mixin.minecraft.WorldClientAccessor;
import io.enderdev.endermodpacktweaks.utils.EmtConfigHandler;
import io.enderdev.endermodpacktweaks.utils.EmtConfigParser;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
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
import org.lwjgl.util.vector.Vector3f;

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
    //</editor-fold>

    // render health bar for these entities
    private final List<EntityLivingBase> entities = new ArrayList<>();

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
            // instancing rect health bars
            if (CfgFeatures.MOB_HEALTH_BAR.shapeBackground == EnumShapeType.STRAIGHT) {
                if (HealthBarInstancingHelper.rectBackgroundRenderer == null) {
                    HealthBarInstancingHelper.rectBackgroundRenderer = (new RectInstancingRenderer(100)).init();
                }
                if (HealthBarInstancingHelper.rectGraySpaceRenderer == null) {
                    HealthBarInstancingHelper.rectGraySpaceRenderer = (new RectInstancingRenderer(100)).init();
                }
                HealthBarInstancingHelper.renderRectHealthBars(entities, partialTicks, cameraPos, EmtRender.getCameraRotationInRadian());
            }
        } else {
//            // optifine compat: disable shader program
//            int oldProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
//            if (oldProgram != 0) GL20.glUseProgram(0);
//            for (EntityLivingBase entity : entities) {
//                // fixed-func health bar rendering
//                HealthBarDirectRenderHelper.renderHealthBar(entity, partialTicks, instancing);
//            }
//            if (oldProgram != 0) GL20.glUseProgram(oldProgram);
        }

        // optifine compat: disable shader program
        int oldProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        if (oldProgram != 0) GL20.glUseProgram(0);
        for (EntityLivingBase entity : entities) {
            // fixed-func health bar rendering
            HealthBarDirectRenderHelper.renderHealthBar(entity, partialTicks, instancing);
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
