package io.enderdev.endermodpacktweaks.mixin.morph;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import me.ichun.mods.morph.api.ability.Ability;
import me.ichun.mods.morph.api.ability.type.AbilityFlightFlap;
import me.ichun.mods.morph.api.ability.type.AbilityFly;
import me.ichun.mods.morph.common.handler.AbilityHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;

@Mixin(value = AbilityHandler.class, remap = false)
public class AbilityHandlerMixin {
    @WrapOperation(method = "getEntityAbilities", at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;add(Ljava/lang/Object;)Z"))
    private boolean emt$overrideFlight(ArrayList<Ability> instance, Object e, Operation<Boolean> original) {
        if (CfgTweaks.MORPH.betterFlight) {
            Ability ability = (Ability) e;
            if (ability instanceof AbilityFlightFlap) {
                return instance.add(new AbilityFly());
            }
        }
        return original.call(instance, e);
    }
}
