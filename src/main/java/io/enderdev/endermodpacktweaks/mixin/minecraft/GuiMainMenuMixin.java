package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.google.common.util.concurrent.Runnables;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.features.credits.ImprovedCreditsGui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiWinGame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiMainMenu.class)
public class GuiMainMenuMixin {
    @WrapOperation(method = "mouseClicked", at = @At(value = "NEW", target = "(ZLjava/lang/Runnable;)Lnet/minecraft/client/gui/GuiWinGame;"))
    private GuiWinGame myCredits(boolean poemIn, Runnable onFinishedIn, Operation<GuiWinGame> original) {
        return CfgFeatures.CUSTOM_CREDITS.enable ? new ImprovedCreditsGui(false, Runnables.doNothing()) : original.call(poemIn, onFinishedIn);
    }

    @WrapOperation(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;drawModalRectWithCustomSizedTexture(IIFFIIFF)V"))
    private void drawEdition(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight, Operation<Void> original) {
        if (!CfgModpack.CUSTOMIZATION.removeJavaEdition) {
            original.call(x, y, u, v, width, height, textureWidth, textureHeight);
        }
    }
}
