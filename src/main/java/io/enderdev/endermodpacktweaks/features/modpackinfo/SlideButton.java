package io.enderdev.endermodpacktweaks.features.modpackinfo;

import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.utils.EmtTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SlideButton extends GuiButton {
    private static final ResourceLocation SLIDE_BUTTON_TEXTURE = new ResourceLocation(Tags.MOD_ID, "textures/gui/slidebutton.png");
    private static final ResourceLocation SLIDE_BUTTON_ICON_TEXTURE = new ResourceLocation(Tags.MOD_ID, "textures/gui/icons.png");

    private final EmtTime time = new EmtTime(45);
    public final static int CORNER_WIDTH = 26;

    private int iconX;
    private int iconY;

    private final float xOpened;
    private float xOffset;

    private boolean finishedAnimation;

    public SlideButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.xOffset = this.width - CORNER_WIDTH;
        this.xOpened = this.xOffset;
    }

    public SlideButton(int buttonId, String buttonText) {
        this(buttonId, 0, 0, 100, 20, buttonText);
    }

    public SlideButton setIcon(int indexX, int indexY) {
        this.iconX = indexX * 32;
        this.iconY = indexY * 32;
        return this;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!this.visible) {
            return;
        }
        this.hovered = mouseX >= this.x + xOffset && mouseY >= this.y && mouseX < this.x + this.width + xOffset && mouseY < this.y + this.height;
        int i = this.getHoverState(this.hovered);

        if (i == 0) {
            return;
        }

        update();

        FontRenderer fontRenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(SLIDE_BUTTON_TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        this.drawTexturedModalRect(this.x + xOffset, this.y, 0, (i - 1) * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.x + this.width / 2f + xOffset, this.y, 200 - this.width / 2, (i - 1) * 20, this.width / 2, this.height);

        this.mouseDragged(mc, mouseX, mouseY);
        int j = 14737632;

        if (packedFGColour != 0) {
            j = packedFGColour;
        } else if (!this.enabled) {
            j = 10526880;
        } else if (this.hovered) {
            j = 16777120;
        }

        this.drawString(fontRenderer, this.displayString, this.x + 25 + (int) xOffset, this.y + (this.height - 8) / 2, j);

        mc.getTextureManager().bindTexture(SLIDE_BUTTON_ICON_TEXTURE);
        GlStateManager.scale(0.5, 0.5, 1);
        drawModalRectWithCustomSizedTexture((this.x + 7 + xOffset) * 2, (this.y + 2) * 2, iconX, iconY, 32, 32, 128, 128);
        GlStateManager.scale(2, 2, 1);
    }

    /**
     * Method copied from Gui#drawModalRectWithCustomSizedTexture, except all arguments are floats/doubles instead of requiring ints
     */
    private static void drawModalRectWithCustomSizedTexture(double x, double y, double u, double v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, (y + height), 0.0D).tex((u * f), ((v + height) * f1)).endVertex();
        bufferbuilder.pos((x + width), (y + height), 0.0D).tex(((u + width) * f), ((v + height) * f1)).endVertex();
        bufferbuilder.pos((x + width), y, 0.0D).tex(((u + width) * f), (v * f1)).endVertex();
        bufferbuilder.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
        tessellator.draw();
    }

    private void update() {
        time.update();
        if (time.getDeltaTime() <= 0) {
            return;
        }
        float distance = (float) (10.0 * time.getDeltaTime());
        if (hovered && !finishedAnimation) {
            if (xOffset <= distance) {
                xOffset = 0;
                finishedAnimation = true;
            } else {
                xOffset -= distance;
            }
        } else if (!hovered) {
            if (xOffset >= xOpened - distance) {
                xOffset = xOpened;
                finishedAnimation = false;
            } else {
                xOffset += distance;
            }
        }
        time.decreaseDeltaTime(1);
    }
}
