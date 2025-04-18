package io.enderdev.endermodpacktweaks.features.playerpotions;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.SDPotions;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SimpleDifficultyHandler {
    public final PotionHandler temperaturePotionHandler = new PotionHandler(CfgTweaks.SIMPLE_DIFFICULTY.temperaturePotions, 0, 25);
    public final PotionHandler thirstPotionHandler = new PotionHandler(CfgTweaks.SIMPLE_DIFFICULTY.thirstPotions, 0, 20);

    @SubscribeEvent
    public void simpleDifficultyPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.getEntityWorld().isRemote) {
            return;
        }
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
}
