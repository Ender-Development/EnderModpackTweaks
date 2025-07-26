package io.enderdev.endermodpacktweaks.events;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import codersafterdark.reskillable.client.gui.GuiSkillInfo;
import codersafterdark.reskillable.client.gui.GuiSkills;
import codersafterdark.reskillable.client.gui.button.GuiButtonLevelUp;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.stream.IntStream;

public class ReskillableEvents {
    @SubscribeEvent
    public void beforeLevelUp(LevelUpEvent.Pre event) {
        if (playerDataCheck(event.getEntityPlayer())) {
            event.setCanceled(true);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiSkills) {
            for (GuiButton button : event.getButtonList()) {
                if (button instanceof GuiButtonLevelUp && playerDataCheck(event.getGui().mc.player)) {
                    button.enabled = false;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiRender(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof GuiSkillInfo) {
            for (GuiButton button : event.getGui().buttonList) {
                if (button instanceof GuiButtonLevelUp
                        && event.getMouseX() >= button.x
                        && event.getMouseX() <= button.x + button.width
                        && event.getMouseY() >= button.y
                        && event.getMouseY() <= button.y + button.height
                ) {
                    EntityPlayer player = event.getGui().mc.player;
                    if (playerDataCheck(player)) {
                        event.getGui().drawHoveringText(I18n.format("endermodpacktweaks.reskillable.level_max", CfgTweaks.RESKILLABLE.maxLevel), event.getMouseX(), event.getMouseY());
                    } else if (CfgTweaks.RESKILLABLE.maxLevel > 0) {
                        event.getGui().drawHoveringText(I18n.format("endermodpacktweaks.reskillable.level_left", CfgTweaks.RESKILLABLE.maxLevel - getSum(player)), event.getMouseX(), event.getMouseY());
                    }
                }
            }
        }
    }

    private boolean playerDataCheck(EntityPlayer player) {
        int sum = getSum(player);
        if (sum == 0) {
            return false;
        }
        return sum >= CfgTweaks.RESKILLABLE.maxLevel && CfgTweaks.RESKILLABLE.maxLevel > 0;
    }

    private int getSum(EntityPlayer player) {
        PlayerData data = PlayerDataHandler.get(player);
        if (data == null) {
            return 0;
        }
        return data.getAllSkillInfo().stream().flatMapToInt(playerSkillInfo -> IntStream.of(playerSkillInfo.getLevel())).sum();
    }
}
