package io.enderdev.endermodpacktweaks.mixin.itemphysic;

import com.creativemd.itemphysic.EventHandler;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EventHandler.class, remap = false)
public class EventHandlerMixin {
    @WrapMethod(method = "getReachDistance")
    private static double getReachDistance(EntityPlayer player, Operation<Double> original) {
        if (CfgTweaks.ITEM_PHYSICS.reachDistance) {
            return player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        }
        return original.call(player);
    }

}
