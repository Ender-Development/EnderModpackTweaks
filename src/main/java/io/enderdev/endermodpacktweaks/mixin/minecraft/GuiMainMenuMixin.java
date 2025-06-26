package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.google.common.util.concurrent.Runnables;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.features.credits.ImprovedCreditsGui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiWinGame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiMainMenu.class)
public class GuiMainMenuMixin {
    @WrapOperation(method = "mouseClicked", at = @At(value = "NEW", target = "(ZLjava/lang/Runnable;)Lnet/minecraft/client/gui/GuiWinGame;"))
    private GuiWinGame myCredits(boolean poemIn, Runnable onFinishedIn, Operation<GuiWinGame> original) {
        return new ImprovedCreditsGui(false, Runnables.doNothing());
    }
}
