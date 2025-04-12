package io.enderdev.endermodpacktweaks.mixin.potioncore;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tmtravlr.potioncore.PotionCoreEventHandlerClient;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PotionCoreEventHandlerClient.class, remap = false)
public class PotionCoreEventHandlerClientMixin {

    @WrapOperation(method = "renderOverlaysPre", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", remap = true), remap = false)
    private static void fixRightRender(GuiIngame instance, int x, int y, int tX, int tY, int w, int h, Operation<Void> original) {
        original.call(instance, x, y + CfgTweaks.POTION_CORE.renderOffset, tX, tY, w, h);
    }

    @WrapOperation(method = "renderOverlaysPre", at = @At(value = "INVOKE", target = "Lcom/tmtravlr/potioncore/PotionCoreEventHandlerClient;drawArmorOverlayRectangle(DDDDDDD)V"))
    private static void fixLeftRender(double x, double y, double z, double uStart, double vStart, double width, double height, Operation<Void> original) {
        original.call(x, y + CfgTweaks.POTION_CORE.renderOffset, z, uStart, vStart, width, height);
    }

    @WrapOperation(method = "renderOverlaysPost", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", remap = true), remap = false)
    private static void fixRightRenderPost(GuiIngame instance, int x, int y, int tX, int tY, int w, int h, Operation<Void> original) {
        original.call(instance, x, y + CfgTweaks.POTION_CORE.renderOffset, tX, tY, w, h);
    }

    @WrapOperation(method = "renderOverlaysPost", at = @At(value = "INVOKE", target = "Lcom/tmtravlr/potioncore/PotionCoreEventHandlerClient;drawArmorOverlayRectangle(DDDDDDD)V"))
    private static void fixLeftRenderPost(double x, double y, double z, double uStart, double vStart, double width, double height, Operation<Void> original) {
        original.call(x, y + CfgTweaks.POTION_CORE.renderOffset, z, uStart, vStart, width, height);
    }
}
