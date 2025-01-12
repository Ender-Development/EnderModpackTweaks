package io.enderdev.endermodpacktweaks.mixin.lbm;

import com.asx.lbm.client.Resources;
import com.asx.lbm.client.event.RenderBloodOverlayEvent;
import com.asx.lbm.common.PotionHandler;
import com.asx.lbm.common.capabilities.IBleedableCapability;
import com.asx.mdx.client.ClientGame;
import com.asx.mdx.client.render.Draw;
import com.asx.mdx.client.render.OpenGL;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static com.asx.lbm.client.event.RenderBloodOverlayEvent.drawVerticalProgressBar;

@Mixin(value = RenderBloodOverlayEvent.class, remap = false)
public class RenderBloodOverlayEventMixin {
    @Shadow
    private static long lastBloodBarActivationTime;

    @WrapMethod(method = "render")
    public void renderOverlay(RenderGameOverlayEvent.Pre event, Operation<Void> original) {
        IBleedableCapability.Bleedable bleedable = (IBleedableCapability.Bleedable) ClientGame.instance.minecraft().player.getCapability(IBleedableCapability.Provider.CAPABILITY, (EnumFacing)null);
        if (!ClientGame.instance.minecraft().player.capabilities.isCreativeMode
                && bleedable != null
                && (System.currentTimeMillis() - lastBloodBarActivationTime <= 5000L
                || EMTConfig.LBM.alwaysShowBloodOverlay
                || ClientGame.instance.minecraft().player.isPotionActive(PotionHandler.LIGHT_BLEED)
                || ClientGame.instance.minecraft().player.isPotionActive(PotionHandler.HEAVY_BLEED))) {
            if (bleedable.getMaxBloodCount() > 0 && bleedable.getBloodCount() > 0) {
                int width = EMTConfig.LBM.bloodWidth;
                int height = EMTConfig.LBM.bloodHeight;
                int posX = (event.getResolution().getScaledWidth() / 2 + 93) + EMTConfig.LBM.bloodXOffset;
                int posY =( event.getResolution().getScaledHeight() - height) - EMTConfig.LBM.bloodYOffset;
                int posX2 = posX - 1 + EMTConfig.LBM.bloodIconXOffset;
                int posY2 = posY - 9 - EMTConfig.LBM.bloodIconYOffset;
                drawVerticalProgressBar(bleedable.getBloodCount(), bleedable.getMaxBloodCount(), posX, posY, width, height, EMTConfig.LBM.bloodForegroundColor, EMTConfig.LBM.bloodBackgroundColor);
                OpenGL.enableBlend();
                Draw.drawResource(Resources.BLOOD_DROP, posX2, posY2, 8, 8);
            }

            OpenGL.color(1.0F, 1.0F, 1.0F, 1.0F);
            OpenGL.enableBlend();
            Draw.bindTexture(Gui.ICONS);
        }
    }
}
