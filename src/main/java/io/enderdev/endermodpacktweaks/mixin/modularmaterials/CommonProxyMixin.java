package io.enderdev.endermodpacktweaks.mixin.modularmaterials;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import glowredman.modularmaterials.proxy.CommonProxy;
import glowredman.modularmaterials.util.MaterialHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CommonProxy.class, remap = false)
public class CommonProxyMixin {
    @Inject(method = "preInit", at = @At("RETURN"))
    private void initOreDict(FMLPreInitializationEvent event, CallbackInfo ci) {
        MaterialHandler.addOreDictTags();
    }

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lglowredman/modularmaterials/util/MaterialHandler;addOreDictTags()V"))
    private void noOreDict(Operation<Void> original) {
        // NO-OP
    }
}
