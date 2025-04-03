package io.enderdev.endermodpacktweaks.mixin.itemstages;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.darkhax.bookshelf.util.PlayerUtils;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.itemstages.ConfigurationHandler;
import net.darkhax.itemstages.ItemStages;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Iterator;

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
                    String itemName = ItemStages.CUSTOM_NAMES.getOrDefault(event.getItemStack(), "Unfamiliar Item");
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
}
