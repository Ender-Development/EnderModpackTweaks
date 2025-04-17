package io.enderdev.endermodpacktweaks.features.playerpotions;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.SDPotions;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import io.enderdev.endermodpacktweaks.utils.EmtPotionHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SimpleDifficultyHandler {
    public final EmtPotionHandler temperaturePotionHandler = new EmtPotionHandler(CfgTweaks.SIMPLE_DIFFICULTY.temperaturePotions, 0, 25);
    public final EmtPotionHandler thirstPotionHandler = new EmtPotionHandler(CfgTweaks.SIMPLE_DIFFICULTY.thirstPotions, 0, 20);

    @SubscribeEvent
    public void setThirstOnRespawn(PlayerEvent.PlayerRespawnEvent event) {
        IThirstCapability thirstCapability = SDCapabilities.getThirstData(event.player);
        thirstCapability.setThirstLevel(CfgTweaks.SIMPLE_DIFFICULTY.respawnThirst);
        thirstCapability.setThirstSaturation(CfgTweaks.SIMPLE_DIFFICULTY.respawnThirstSaturation);
    }

    @SubscribeEvent
    public void simpleDifficultyPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;

        // Temperature
        int temp = SDCapabilities.getTemperatureData(player).getTemperatureLevel();
        boolean resistCold = player.isPotionActive(SDPotions.cold_resist) && temp <= CfgTweaks.SIMPLE_DIFFICULTY.coldResistanceUpperLimit;
        boolean resistHeat = player.isPotionActive(SDPotions.heat_resist) && temp >= CfgTweaks.SIMPLE_DIFFICULTY.heatResistanceLowerLimit;
        if (!resistCold && !resistHeat) {
            temperaturePotionHandler.apply(player, temp);
        }

        // Thirst
        int thirst = SDCapabilities.getThirstData(player).getThirstLevel();
        thirstPotionHandler.apply(player, thirst);
    }

    @SubscribeEvent
    public void playerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        EntityPlayer player = event.player;
        temperaturePotionHandler.clear(player);
        thirstPotionHandler.clear(player);
    }
}
