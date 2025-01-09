package io.enderdev.endermodpacktweaks.mixin.itemphysic;

import com.creativemd.itemphysic.EventHandler;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(value = EventHandler.class, remap = false)
public class EventHandlerMixin {
    @WrapOperation(method = "renderTickFull", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
    private boolean renderTickFull(List instance, Object displayName, Operation<Boolean> original, @Local EntityItem item) {
        if (EMTConfig.ITEM_PHYSICS.improveTooltip) {
            return instance.add(String.format("%s%s%s", item.getItem().getRarity().getColor(), I18n.format("endermodpacktweaks.itemphysic.render_tooltip", item.getItem().getCount(), item.getItem().getDisplayName()), TextFormatting.RESET));
        }
        return original.call(instance, displayName);
    }

    @WrapMethod(method = "getReachDistance")
    private static double getReachDistance(EntityPlayer player, Operation<Double> original) {
        if (EMTConfig.ITEM_PHYSICS.reachDistance) {
            return player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        }
        return original.call(player);
    }

}
