package io.enderdev.endermodpacktweaks.features.nooverlay;

import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class OverlayHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        Map<RenderGameOverlayEvent.ElementType, Boolean> hideElements = new HashMap<RenderGameOverlayEvent.ElementType, Boolean>() {
            {
                put(RenderGameOverlayEvent.ElementType.HEALTH, CfgMinecraft.CLIENT.hideHealthBar);
                put(RenderGameOverlayEvent.ElementType.FOOD, CfgMinecraft.CLIENT.hideHungerBar);
                put(RenderGameOverlayEvent.ElementType.EXPERIENCE, CfgMinecraft.CLIENT.hideExperienceBar);
                put(RenderGameOverlayEvent.ElementType.AIR, CfgMinecraft.CLIENT.hideAirBar);
                put(RenderGameOverlayEvent.ElementType.ARMOR, CfgMinecraft.CLIENT.hideArmorBar);
                put(RenderGameOverlayEvent.ElementType.CROSSHAIRS, CfgMinecraft.CLIENT.hideCrosshair);
                put(RenderGameOverlayEvent.ElementType.POTION_ICONS, CfgMinecraft.CLIENT.hidePotionIcons);
            }
        };
        if (hideElements.containsKey(event.getType())) {
            event.setCanceled(hideElements.get(event.getType()));
        }
    }
}
