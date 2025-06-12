package io.enderdev.endermodpacktweaks.mixin.noctrl;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.wynprice.noctrl.GuiControlsOverride;
import com.wynprice.noctrl.NoCtrl;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.features.keybinds.NoCtrlIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NoCtrl.class, remap = false)
public class NoCtrlMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void registerControllingCompat(FMLInitializationEvent event, CallbackInfo ci) {
        if (CfgFeatures.IMPROVED_KEYBINDS.enable) {
            MinecraftForge.EVENT_BUS.register(NoCtrlIntegration.class);
        }
    }

    @WrapMethod(method = "onGuiOpen")
    private static void onGuiOpen(GuiOpenEvent event, Operation<Void> original) {
        if (event.getGui() != null && event.getGui() instanceof GuiControls && !CfgFeatures.IMPROVED_KEYBINDS.enable) {
            event.setGui(new GuiControlsOverride(((GuiControls)event.getGui()).parentScreen, Minecraft.getMinecraft().gameSettings));
        }
    }
}
