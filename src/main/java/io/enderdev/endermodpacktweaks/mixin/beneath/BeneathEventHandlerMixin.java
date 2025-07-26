package io.enderdev.endermodpacktweaks.mixin.beneath;

import com.shinoow.beneath.common.handler.BeneathEventHandler;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import io.enderdev.endermodpacktweaks.utils.EmtConfigHandler;
import io.enderdev.endermodpacktweaks.utils.EmtConfigParser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BeneathEventHandler.class, remap = false)
public class BeneathEventHandlerMixin {
    @Unique
    private static final EmtConfigHandler<EmtConfigParser.ConfigItem> BENEATH_CONFIG_HANDLER = new EmtConfigHandler<>(
            CfgTweaks.BENEATH.darknessImmunityItems,
            EmtConfigParser.ConfigItem::new
    );

    @Inject(method = "darkness", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LivingEvent$LivingUpdateEvent;getEntityLiving()Lnet/minecraft/entity/EntityLivingBase;", ordinal = 3), cancellable = true)
    private void darkness(LivingEvent.LivingUpdateEvent event, CallbackInfo ci) {
        // Cancel the darkness effect if the player is in creative mode
        if (CfgTweaks.BENEATH.disableDarknessEffect) {
            ci.cancel();
        }
        EntityPlayer player = (EntityPlayer)event.getEntityLiving();
        if (enderModpackTweaks$getBeneathConfigHandler().equipped(player)) {
            // Cancel the darkness effect if the player has any of the configured items equipped
            ci.cancel();
        }
    }

    @Unique
    private EmtConfigHandler<EmtConfigParser.ConfigItem> enderModpackTweaks$getBeneathConfigHandler() {
        if (!BENEATH_CONFIG_HANDLER.isInitialized()) {
            BENEATH_CONFIG_HANDLER.init();
        }
        return BENEATH_CONFIG_HANDLER;
    }
}
