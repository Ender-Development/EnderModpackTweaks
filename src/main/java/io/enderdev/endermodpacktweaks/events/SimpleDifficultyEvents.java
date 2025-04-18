package io.enderdev.endermodpacktweaks.events;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class SimpleDifficultyEvents {
    @SubscribeEvent
    public void setThirstOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
        IThirstCapability thirstCapability = SDCapabilities.getThirstData(event.player);
        thirstCapability.setThirstLevel(CfgTweaks.SIMPLE_DIFFICULTY.respawnThirst);
        thirstCapability.setThirstSaturation(CfgTweaks.SIMPLE_DIFFICULTY.respawnThirstSaturation);
    }
}
