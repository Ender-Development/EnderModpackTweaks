package io.enderdev.endermodpacktweaks.mixin.taoism;

import com.jackiecrazi.taoism.client.InputEvents;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InputEvents.class, remap = false)
public class InputEventsMixin {
    @Inject(method = "doju", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;sendStatusMessage(Lnet/minecraft/util/text/ITextComponent;Z)V"), cancellable = true)
    private static void preventDoju(InputUpdateEvent e, CallbackInfo ci) {
        EntityPlayer player = e.getEntityPlayer();
        if (Loader.isModLoaded("gamestages") && player != null && !GameStageHelper.hasStage(player, CfgTweaks.TAOISM.unlockGameStage)) {
            String gamestage = CfgTweaks.GAME_STAGES.localizeRecipeStages ? I18n.format("emt.game_stages." + CfgTweaks.TAOISM.unlockGameStage) : CfgTweaks.TAOISM.unlockGameStage;
            player.sendStatusMessage(new TextComponentTranslation("endermodpacktweaks.taoism.combat_locked", gamestage), true);
            ci.cancel();
        }
    }
}
