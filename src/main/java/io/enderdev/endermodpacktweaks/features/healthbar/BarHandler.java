package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.mixin.minecraft.WorldClientAccessor;
import io.enderdev.endermodpacktweaks.utils.EmtConfigHandler;
import io.enderdev.endermodpacktweaks.utils.EmtConfigParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class BarHandler {
    public final EmtConfigHandler<EmtConfigParser.ConfigItem> whitelist = new EmtConfigHandler<>(
            CfgFeatures.MOB_HEALTH_BAR.onlyRenderWithEquipment,
            EmtConfigParser.ConfigItem::new
    );

    private final KeyBinding key;
    private boolean down;

    private boolean shouldRender = true;

    public BarHandler() {
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

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if ((!CfgFeatures.MOB_HEALTH_BAR.renderInF1 && !Minecraft.isGuiEnabled()) || !shouldRender) {
            return;
        }

        if (CfgFeatures.MOB_HEALTH_BAR.onlyRenderWithEquipment.length != 0 && !whitelist.equipped(mc.player)) {
            return;
        }

        Entity cameraEntity = mc.getRenderViewEntity();

        if (cameraEntity == null || !cameraEntity.isEntityAlive()) {
            return;
        }
        BlockPos renderingVector = cameraEntity.getPosition();
        Frustum frustum = new Frustum();

        float partialTicks = event.getPartialTicks();
        double viewX = cameraEntity.lastTickPosX + (cameraEntity.posX - cameraEntity.lastTickPosX) * partialTicks;
        double viewY = cameraEntity.lastTickPosY + (cameraEntity.posY - cameraEntity.lastTickPosY) * partialTicks;
        double viewZ = cameraEntity.lastTickPosZ + (cameraEntity.posZ - cameraEntity.lastTickPosZ) * partialTicks;
        frustum.setPosition(viewX, viewY, viewZ);

        if (CfgFeatures.MOB_HEALTH_BAR.showOnlyFocused) {
            Entity focused = BarRenderer.getEntityLookedAt(mc.player);
            if (focused instanceof EntityLivingBase && focused.isEntityAlive()) {
                renderHealthBar((EntityLivingBase) focused, partialTicks, cameraEntity);
            }
        } else {
            for (Entity entity : ((WorldClientAccessor) Minecraft.getMinecraft().world).getEntityList()) {
                if (entity instanceof EntityLivingBase
                        && entity != mc.player
                        && entity.isInRangeToRender3d(renderingVector.getX(), renderingVector.getY(), renderingVector.getZ())
                        && (entity.ignoreFrustumCheck || frustum.isBoundingBoxInFrustum(entity.getEntityBoundingBox()))
                        && entity.isEntityAlive()
                        && entity.getRecursivePassengers().isEmpty()) {
                    renderHealthBar((EntityLivingBase) entity, partialTicks, cameraEntity);
                }
            }
        }
    }

    /**
     * Renders the health bar for the given entity. This also contains a patch when having an optifine shader active.
     *
     * @param entity       The entity to render the health bar for.
     * @param partialTicks The partial ticks for rendering.
     * @param cameraEntity The camera entity.
     */
    private void renderHealthBar(EntityLivingBase entity, float partialTicks, Entity cameraEntity) {
        int oldProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        if (oldProgram != 0) {
            GL20.glUseProgram(0);
        }

        BarRenderer.renderHealthBar(entity, partialTicks, cameraEntity);

        if (oldProgram != 0) {
            GL20.glUseProgram(oldProgram);
        }
    }
}
