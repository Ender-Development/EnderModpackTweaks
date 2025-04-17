package io.enderdev.endermodpacktweaks.features.noinventorycrafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
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

@SideOnly(Side.CLIENT)
public class InventoryHandler {
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiInventory) {
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
    }

    @SubscribeEvent
    public void renderBackground(GuiContainerEvent.DrawForeground event) {
        if (event.getGuiContainer() instanceof GuiInventory) {
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();

            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/items/barrier.png"));
            GlStateManager.color(1, 1, 1, 1);
            Gui.drawModalRectWithCustomSizedTexture(154, 28, 0, 0, 16, 16, 16, 16);

            GlStateManager.popMatrix();
        }
    }
}
