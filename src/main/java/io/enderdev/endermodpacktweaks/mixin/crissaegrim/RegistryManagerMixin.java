package io.enderdev.endermodpacktweaks.mixin.crissaegrim;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import epicsquid.crissaegrim.RegistryManager;
import epicsquid.mysticallib.event.RegisterFXEvent;
import io.enderdev.endermodpacktweaks.patches.mysticallib.EffectCut;
import io.enderdev.endermodpacktweaks.patches.mysticallib.EffectManager;
import io.enderdev.endermodpacktweaks.patches.mysticallib.FXRegistry;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = RegistryManager.class, remap = false)
public class RegistryManagerMixin {
    @Shadow
    public static int FX_CUT;

    @WrapMethod(method = "registerFX")
    public void registerFX(RegisterFXEvent event, Operation<Void> original) {
        FX_CUT = FXRegistry.registerEffect(t -> {
            EffectCut slash = new EffectCut(Minecraft.getMinecraft().world.provider.getDimension());
            slash.read(t);
            EffectManager.addEffect(slash);
            return null;
        });
    }
}
