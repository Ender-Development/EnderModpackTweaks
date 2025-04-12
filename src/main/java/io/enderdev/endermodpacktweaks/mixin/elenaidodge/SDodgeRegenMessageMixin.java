package io.enderdev.endermodpacktweaks.mixin.elenaidodge;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.elenai.elenaidodge2.capability.dodges.IDodges;
import com.elenai.elenaidodge2.network.message.SDodgeRegenMessage;
import com.llamalad7.mixinextras.sugar.Local;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SDodgeRegenMessage.Handler.class , remap = false)
public class SDodgeRegenMessageMixin {
    @Inject(method = "processMessage", at = @At("RETURN"))
    private void thirstOnStaminaRegen(SDodgeRegenMessage message, MessageContext ctx, CallbackInfo ci, @Local EntityPlayerMP player, @Local IDodges d) {
        if (!CfgTweaks.ELENAI_DODGE.enableSimpleDifficulty || !Loader.isModLoaded("simpledifficulty") || d.getDodges() >= 20) {
            return;
        }
        IThirstCapability thirst = SDCapabilities.getThirstData(player);
        if (thirst != null) {
            thirst.addThirstExhaustion((float) CfgTweaks.ELENAI_DODGE.thirstRegeneration);
        }
    }
}
