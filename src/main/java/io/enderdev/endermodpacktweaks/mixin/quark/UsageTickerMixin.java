package io.enderdev.endermodpacktweaks.mixin.quark;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.client.feature.UsageTicker;

@Mixin(value = UsageTicker.TickerElement.class, remap = false)
public class UsageTickerMixin {
    @Shadow
    public int liveTicks;

    @Shadow
    @Final
    public EntityEquipmentSlot slot;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (!EMTConfig.QUARK.alwaysShowUsageTicker) {
            return;
        }
        this.liveTicks = 50;
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0, remap = true))
    private void render(float x, float y, float z, Operation<Void> original) {
        if (EMTConfig.QUARK.alwaysShowUsageTicker) {
            if (this.slot.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
                y += EMTConfig.QUARK.armorYOffset;
            } else {
                y += EMTConfig.QUARK.itemYOffset;
            }
            GlStateManager.translate(x, y, z);
        }
        original.call(x, y, z);
    }
}
