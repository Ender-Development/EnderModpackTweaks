package io.enderdev.endermodpacktweaks.mixin.redstonerepository;

import cofh.core.gui.GuiContainerCore;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import thundr.redstonerepository.gui.GuiFeeder;

@Mixin(value = GuiFeeder.class, remap = false)
public abstract class GuiFeederMixin extends GuiContainerCore {
    private GuiFeederMixin(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @WrapMethod(method = "drawGuiContainerBackgroundLayer", remap = true)
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY, Operation<Void> original) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("redstonerepository", "textures/gui/feeder.png"));
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)this.guiLeft, (float)this.guiTop, 0.0F);

        this.drawElements(partialTicks, false);
        this.drawTabs(partialTicks, false);

        GlStateManager.popMatrix();
    }
}
