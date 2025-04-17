package io.enderdev.endermodpacktweaks.features.dodgethirst;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.elenai.elenaidodge2.api.DodgeEvent;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DodgeHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void addThirstOnDodge(DodgeEvent.ServerDodgeEvent event) {
        if (!event.isCanceled()) {
            IThirstCapability thirst = SDCapabilities.getThirstData(event.getPlayer());
            if (thirst != null) {
                thirst.addThirstExhaustion((float) CfgTweaks.ELENAI_DODGE.thirst);
            }
        }
    }

    @SubscribeEvent
    public void cancelDodgeWhenThirsty(DodgeEvent.ServerDodgeEvent event) {
        if (!event.isCanceled()) {
            IThirstCapability thirst = SDCapabilities.getThirstData(event.getPlayer());
            if (thirst != null && thirst.getThirstLevel() < CfgTweaks.ELENAI_DODGE.thirstThreshold) {
                event.setCanceled(true);
            }
        }
    }
}
