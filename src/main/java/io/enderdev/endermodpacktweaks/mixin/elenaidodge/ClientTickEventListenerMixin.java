package io.enderdev.endermodpacktweaks.mixin.elenaidodge;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.elenai.elenaidodge2.event.ClientTickEventListener;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientTickEventListener.class, remap = false)
public class ClientTickEventListenerMixin {
    @Shadow
    public static int regen;

    @Inject(method = "onPlayerClientTick", at = @At(value = "INVOKE", target = "Lcom/elenai/elenaidodge2/util/Utils;tanEnabled(Lnet/minecraft/client/entity/EntityPlayerSP;)Z", ordinal = 0), cancellable = true)
    private void disableStaminaRegen(TickEvent.ClientTickEvent event, CallbackInfo ci, @Local EntityPlayerSP player) {
        if (EMTConfig.ELENAI_DODGE.enableSimpleDifficulty && regen > 0 && Loader.isModLoaded("simpledifficulty")) {
            IThirstCapability thirst = SDCapabilities.getThirstData(player);
            if (thirst != null && thirst.getThirstLevel() < EMTConfig.ELENAI_DODGE.dodgeRegeneration) {
                ci.cancel();
            }
        }
    }

    @ModifyExpressionValue(method = "onPlayerClientTick", at = @At(value = "FIELD", target = "Lcom/elenai/elenaidodge2/util/ClientStorage;regenModifier:I"))
    private int modifyStaminaRegenModifier(int value, @Local EntityPlayerSP player) {
        if (EMTConfig.ELENAI_DODGE.enableSimpleDifficulty && Loader.isModLoaded("simpledifficulty")) {
            IThirstCapability thirst = SDCapabilities.getThirstData(player);
            if (thirst != null && thirst.isThirsty()) {
                return value + ((20 - thirst.getThirstLevel()) * EMTConfig.ELENAI_DODGE.dodgeRegenerationRate);
            }
        }
        return value;
    }
}
