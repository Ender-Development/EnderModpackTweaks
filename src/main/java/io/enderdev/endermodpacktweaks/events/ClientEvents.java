package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ClientEvents implements GuiPageButtonList.GuiResponder, GuiSlider.FormatHelper {

    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (CfgMinecraft.CLIENT.disableInventoryCrafting && event.getGui() instanceof GuiInventory) {
            GuiInventory inv = (GuiInventory) event.getGui();
            ContainerPlayer container = (ContainerPlayer) inv.inventorySlots;
            for (int i = 0; i < container.inventorySlots.size(); i++) {
                Slot s = container.inventorySlots.get(i);

                if (s instanceof SlotCrafting) {
                    s.yPos = -1000;
                } else if (s.inventory instanceof InventoryCrafting) {
                    s.inventory.closeInventory(Minecraft.getMinecraft().player);
                }
            }
        }

        if (CfgMinecraft.CLIENT.disableAutoJump && event.getGui() instanceof GuiControls) {
            for (GuiButton button : event.getButtonList()) {
                if (button instanceof GuiOptionButton && ((GuiOptionButton) button).getOption() == GameSettings.Options.AUTO_JUMP) {
                    button.enabled = false;
                }
            }
        }

        if (CfgMinecraft.CLIENT.additionalMasterVolume && event.getGui() instanceof GuiOptions) {
            int x = 0;
            int y = 0;
            for (GuiButton guiButton : event.getButtonList()) {
                if (guiButton instanceof GuiOptionSlider) {
                    x = guiButton.x;
                    y = guiButton.y;
                }
            }
            event.getButtonList().add(new GuiSlider(this, -999, x, y + 27, SoundCategory.MASTER.getName(), 0f, 1f, Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER), this));
        }
    }

    @SubscribeEvent
    public void renderBackground(GuiContainerEvent.DrawForeground event) {
        if (CfgMinecraft.CLIENT.disableInventoryCrafting && event.getGuiContainer() instanceof GuiInventory) {
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();

            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/items/barrier.png"));
            GlStateManager.color(1, 1, 1, 1);
            Gui.drawModalRectWithCustomSizedTexture(154, 28, 0, 0, 16, 16, 16, 16);

            GlStateManager.popMatrix();
        }
    }

    @SubscribeEvent
    public void onRenderLivingSpecialsPre(RenderLivingEvent.Specials.Pre event) {
        event.setCanceled(CfgMinecraft.CLIENT.hideNameTags);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (CfgMinecraft.CLIENT.disableAutoJump && event.phase == TickEvent.Phase.END) {
            Minecraft.getMinecraft().gameSettings.autoJump = false;
        }
    }

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

    @SubscribeEvent
    public void onFovUpdate(FOVUpdateEvent event) {
        if (CfgMinecraft.CLIENT.disableFovChange) {
            event.setNewfov(1.0f);
        }
    }

    @Override
    public void setEntryValue(int id, boolean value) {
    }

    @Override
    public void setEntryValue(int id, float value) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.gameSettings.setSoundLevel(SoundCategory.MASTER, value);
        mc.gameSettings.saveOptions();
    }

    @Override
    public void setEntryValue(int id, String value) {
    }

    @NotNull
    @Override
    public String getText(int id, String name, float value) {
        float volume = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER);
        String displayVolume = volume == 0f ? I18n.format("options.off") : (int) (volume * 100f) + "%";
        return I18n.format("soundCategory." + SoundCategory.MASTER.getName()) + ": " + displayVolume;
    }
}
