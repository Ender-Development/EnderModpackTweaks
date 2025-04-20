package io.enderdev.endermodpacktweaks.mixin.delivery;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.delivery.store.Store;
import party.lemons.delivery.store.Trades;

import java.util.ArrayList;

@Mixin(value = Trades.class, remap = false)
public class TradesMixin {
    @Inject(method = "getStore", at = @At("HEAD"))
    private static void getDefaultStore(String name, String profile, CallbackInfoReturnable<Store> cir) {
        if (name.equals("_store") && profile.equals("_default")) {
            if (Trades.trades.get(profile).isEmpty()) {
                Trades.trades.get(profile).put(new Store(name), new ArrayList<>());
            }
        }
    }
}
