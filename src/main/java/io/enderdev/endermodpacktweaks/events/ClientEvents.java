package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientEvents {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiInventory) {
            GuiInventory inv = (GuiInventory) event.getGui();
            ContainerPlayer container = (ContainerPlayer) inv.inventorySlots;
            for (int i = 0; i < container.inventorySlots.size(); i++) {
                Slot s = container.inventorySlots.get(i);

                if (s instanceof SlotCrafting) {
                    if (EMTConfig.MINECRAFT.CLIENT.disableInventoryCrafting) {
                        s.yPos = -1000;
                    } else {
                        s.yPos = 28;
                    }
                } else if (s.inventory instanceof InventoryCrafting) {
                    if (EMTConfig.MINECRAFT.CLIENT.disableInventoryCrafting) {
                        s.inventory.closeInventory(Minecraft.getMinecraft().player);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void renderBackground(GuiContainerEvent.DrawForeground event) {
        GuiContainer gui = event.getGuiContainer();
        if (EMTConfig.MINECRAFT.CLIENT.disableInventoryCrafting) {
            if (gui instanceof GuiInventory) {
                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();

                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/items/barrier.png"));
                GlStateManager.color(1, 1, 1, 1);
                Gui.drawModalRectWithCustomSizedTexture(154, 28, 0, 0, 16, 16, 16, 16);

                GlStateManager.popMatrix();
            }
        }
    }
}
