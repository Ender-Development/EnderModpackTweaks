package io.enderdev.endermodpacktweaks.mixin.firstaid;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import ichttt.mods.firstaid.common.network.MessageConfiguration;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MessageConfiguration.Handler.class, remap = false)
public class MessageConfigurationMixin {
    @ModifyExpressionValue(method = "lambda$onMessage$0", at = @At(value = "FIELD", target = "Lichttt/mods/firstaid/api/damagesystem/AbstractPlayerDamageModel;hasTutorial:Z", ordinal = 0))
    private static boolean hasTutorial(boolean hasTutorial) {
        return CfgTweaks.FIRST_AID.disableTutorial || hasTutorial;
    }
}
