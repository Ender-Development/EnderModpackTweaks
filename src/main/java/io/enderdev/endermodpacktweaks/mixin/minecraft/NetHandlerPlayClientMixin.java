package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.features.credits.ImprovedCreditsGui;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientMixin {
    @WrapOperation(method = "handleChangeGameState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(F)I"))
    private int alwaysShowCredits(float value, Operation<Integer> original) {
        return CfgFeatures.CUSTOM_CREDITS.alwaysShow ? 1 : original.call(value);
    }

    @WrapOperation(method = "handleChangeGameState", at = @At(value = "NEW", target = "(ZLjava/lang/Runnable;)Lnet/minecraft/client/gui/GuiWinGame;"))
    private GuiWinGame myCredits(boolean poemIn, Runnable onFinishedIn, Operation<GuiWinGame> original) {
        return new ImprovedCreditsGui(poemIn, onFinishedIn);
    }
}
