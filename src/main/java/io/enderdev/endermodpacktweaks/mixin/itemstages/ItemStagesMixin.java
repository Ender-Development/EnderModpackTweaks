package io.enderdev.endermodpacktweaks.mixin.itemstages;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.darkhax.bookshelf.util.PlayerUtils;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.itemstages.ConfigurationHandler;
import net.darkhax.itemstages.ItemStages;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;
import java.util.Locale;

@Mixin(value = ItemStages.class, remap = false)
public class ItemStagesMixin {
    @WrapMethod(method = "onTooltip")
    private void wrapOnTooltip(ItemTooltipEvent event, Operation<Void> original) {
        if (!EMTConfig.GAME_STAGES.disableTooltip) {
            original.call(event);
        } else {
            EntityPlayerSP player = PlayerUtils.getClientPlayerSP();
            if (player != null) {
                String itemsStage = ItemStages.getStage(event.getItemStack());
                if (itemsStage != null && !GameStageHelper.hasStage(player, itemsStage) && ConfigurationHandler.changeRestrictionTooltip) {
                    String itemName = ItemStages.CUSTOM_NAMES.getOrDefault(event.getItemStack(), I18n.format("endermodspacktweaks.gamestages.unknown_item"));
                    event.getToolTip().clear();
                    event.getToolTip().add(TextFormatting.WHITE + itemName);
                }

                for (String tipStage : ItemStages.tooltipStages.keySet()) {
                    if (!GameStageHelper.hasStage(player, tipStage)) {
                        Iterator<String> iterator = event.getToolTip().iterator();

                        while (iterator.hasNext()) {
                            String tooltipLine = iterator.next();

                            for (String restricted : ItemStages.tooltipStages.get(tipStage)) {
                                if (tooltipLine.startsWith(restricted)) {
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @WrapOperation(method = "onTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/I18n;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", remap = true, ordinal = 1), remap = false)
    private String wrapOnTooltipFormat(String key, Object[] args, Operation<String> original) {
        String localizedKey = I18n.format("emt.game_stages." + args[0].toString().toLowerCase(Locale.ROOT).trim());
        return original.call(key, EMTConfig.GAME_STAGES.localizeRecipeStages ? new Object[]{localizedKey + TextFormatting.RED} : args);
    }

    @WrapOperation(method = "onTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/I18n;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", remap = true, ordinal = 3), remap = false)
    private String wrapOnTooltipFormat2(String key, Object[] args, Operation<String> original) {
        String localizedKey = I18n.format("emt.game_stages." + args[0].toString().toLowerCase(Locale.ROOT).trim());
        return original.call(key, EMTConfig.GAME_STAGES.localizeRecipeStages ? new Object[]{localizedKey + TextFormatting.RED} : args);
    }
}
