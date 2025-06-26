package io.enderdev.endermodpacktweaks.mixin.custommainmenu;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.features.credits.ImprovedCreditsGui;
import lumien.custommainmenu.lib.actions.ActionOpenGUI;
import net.minecraft.client.gui.GuiWinGame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ActionOpenGUI.class, remap = false)
public class ActionOpenGUIMixin {
    @WrapOperation(method = "perform", at = @At(value = "NEW", target = "(ZLjava/lang/Runnable;)Lnet/minecraft/client/gui/GuiWinGame;"))
    private GuiWinGame myCredits(boolean poemIn, Runnable onFinishedIn, Operation<GuiWinGame> original) {
        return CfgFeatures.CUSTOM_CREDITS.enable ? new ImprovedCreditsGui(poemIn, onFinishedIn) : original.call(poemIn, onFinishedIn);
    }
}
