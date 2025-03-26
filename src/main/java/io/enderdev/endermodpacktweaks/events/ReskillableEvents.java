package io.enderdev.endermodpacktweaks.events;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import codersafterdark.reskillable.client.gui.GuiSkills;
import codersafterdark.reskillable.client.gui.button.GuiButtonLevelUp;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.stream.IntStream;

public class ReskillableEvents {
    @SubscribeEvent
    public void beforeLevelUp(LevelUpEvent.Pre event) {
        if (!EMTConfig.RESKILLABLE.enable) {
            return;
        }
        if (playerDataCheck(event.getEntityPlayer())) {
            event.setCanceled(true);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!EMTConfig.RESKILLABLE.enable) {
            return;
        }
        if (event.getGui() instanceof GuiSkills) {
            for (GuiButton button : event.getButtonList()) {
                if (button instanceof GuiButtonLevelUp && playerDataCheck(event.getGui().mc.player)) {
                    button.enabled = false;
                }
            }
        }
    }

    private boolean playerDataCheck(EntityPlayer player) {
        PlayerData data = PlayerDataHandler.get(player);
        if (data == null) {
            return false;
        }
        int sum = data.getAllSkillInfo().stream().flatMapToInt(playerSkillInfo -> IntStream.of(playerSkillInfo.getLevel())).sum();
        return sum >= EMTConfig.RESKILLABLE.maxLevel && EMTConfig.RESKILLABLE.maxLevel > 0;
    }
}
