package io.enderdev.endermodpacktweaks.mixin.firstaid;

import ichttt.mods.firstaid.common.CapProvider;
import ichttt.mods.firstaid.common.EventHandler;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EventHandler.class, remap = false)
public class EventHandlerMixin {
    @Inject(method = "onLogin", at = @At(value = "INVOKE", target = "Lichttt/mods/firstaid/common/network/MessageConfiguration;<init>(Lichttt/mods/firstaid/api/damagesystem/AbstractPlayerDamageModel;Z)V", shift = At.Shift.AFTER))
    private static void onLoginInject(PlayerEvent.PlayerLoggedInEvent event, CallbackInfo ci) {
        if (EMTConfig.FIRST_AID.disableTutorial) {
            CapProvider.tutorialDone.add(event.player.getName());
        }
    }
}
