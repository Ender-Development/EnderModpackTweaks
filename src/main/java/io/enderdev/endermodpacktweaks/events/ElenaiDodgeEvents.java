package io.enderdev.endermodpacktweaks.events;


import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.elenai.elenaidodge2.api.DodgeEvent;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ElenaiDodgeEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void addThirstOnDodge(DodgeEvent.ServerDodgeEvent event) {
        if (EMTConfig.ELENAI_DODGE.enableSimpleDifficulty && Loader.isModLoaded("simpledifficulty") && !event.isCanceled()) {
            IThirstCapability thirst = SDCapabilities.getThirstData(event.getPlayer());
            if (thirst != null) {
                thirst.addThirstExhaustion((float) EMTConfig.ELENAI_DODGE.thirst);
            }
        }
    }

    @SubscribeEvent
    public void cancelDodgeWhenThirsty(DodgeEvent.ServerDodgeEvent event) {
        if (EMTConfig.ELENAI_DODGE.enableSimpleDifficulty && Loader.isModLoaded("simpledifficulty") && !event.isCanceled()) {
            IThirstCapability thirst = SDCapabilities.getThirstData(event.getPlayer());
            if (thirst != null && thirst.getThirstLevel() < EMTConfig.ELENAI_DODGE.thirstThreshold) {
                event.setCanceled(true);
            }
        }
    }
}
