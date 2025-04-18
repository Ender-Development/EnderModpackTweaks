package io.enderdev.endermodpacktweaks.features.playerpotions;

import com.elenai.elenaidodge2.api.FeathersHelper;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ElenaiDodgeHandler {
     public final PotionHandler staminaPotionHandler = new PotionHandler(CfgTweaks.ELENAI_DODGE.staminaPotions, 0, 20);

    @SubscribeEvent
    public void elenaiDodgePlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.getEntityWorld().isRemote) {
            return;
        }
        EntityPlayer player = event.player;
        int stamina = FeathersHelper.getFeatherLevel((EntityPlayerMP) player);
        staminaPotionHandler.apply(player, stamina);
    }
}
