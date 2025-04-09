package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.mixin.minecraft.WorldClientAccessor;
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

public class BarHandler {

    private final KeyBinding key;
    private boolean down;

    private boolean shouldRender = true;

    public BarHandler() {
        key = new KeyBinding("emt.keybind.toggle", 0, "key.categories.misc");
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

        if ((!EMTConfig.MODPACK.MOB_HEALTH_BAR.renderInF1 && !Minecraft.isGuiEnabled()) || !shouldRender) {
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

        if (EMTConfig.MODPACK.MOB_HEALTH_BAR.showOnlyFocused) {
            Entity focused = BarRenderer.getEntityLookedAt(mc.player);
            if (focused instanceof EntityLivingBase && focused.isEntityAlive()) {
                BarRenderer.renderHealthBar((EntityLivingBase) focused, partialTicks, cameraEntity);
            }
        } else {
            for (Entity entity : ((WorldClientAccessor) Minecraft.getMinecraft().world).getEntityList()) {
                if (entity instanceof EntityLivingBase
                        && entity != mc.player
                        && entity.isInRangeToRender3d(renderingVector.getX(), renderingVector.getY(), renderingVector.getZ())
                        && (entity.ignoreFrustumCheck || frustum.isBoundingBoxInFrustum(entity.getEntityBoundingBox()))
                        && entity.isEntityAlive()
                        && entity.getRecursivePassengers().isEmpty()) {
                    BarRenderer.renderHealthBar((EntityLivingBase) entity, partialTicks, cameraEntity);
                }
            }
        }
    }
}
