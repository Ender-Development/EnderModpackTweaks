package io.enderdev.endermodpacktweaks.mixin.simpledifficulty;

import com.charles445.simpledifficulty.api.SDPotions;
import com.charles445.simpledifficulty.client.gui.ThirstGui;
import com.charles445.simpledifficulty.config.ModConfig;
import com.charles445.simpledifficulty.util.RenderUtil;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.*;

import java.util.Random;

@Mixin(value = ThirstGui.class, remap = false)
public class ThirstGuiMixin {
    @Shadow
    private int updateCounter;

    @Final
    @Shadow
    private Random rand;

    //Position on the icons sheet
    @Shadow
    private static final int texturepos_X = 0;
    @Shadow
    private static final int texturepos_Y = 0;
    //Dimensions of the icon
    @Shadow
    private static final int textureWidth = 9;
    @Shadow
    private static final int textureHeight = 9;

    /**
     * @author _MasterEnderman_
     * @reason Tweak the position of the thirst bar
     */
    @Overwrite
    private void renderThirst(int width, int height, int thirst, float thirstSaturation) {
        GlStateManager.enableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f);

        int left = width / 2 + EMTConfig.SIMPLE_DIFFICULTY.thirstbarXOffset;
        int top = height + EMTConfig.SIMPLE_DIFFICULTY.thirstbarYOffset;

        for (int i = 0; i < 10; i++) {
            int halfIcon = i * 2 + 1;
            int x = left - i * 8;
            int y = top;

            int bgXOffset = 0;
            int xOffset = 0;

            if (Minecraft.getMinecraft().player.isPotionActive(SDPotions.thirsty)) {
                xOffset += (textureWidth * 4);
                bgXOffset = (textureWidth * 13);
            }

            if (thirstSaturation <= 0.0F && updateCounter % (thirst * 3 + 1) == 0) {
                y = top + (rand.nextInt(3) - 1);
            }

            RenderUtil.drawTexturedModalRect(x, y, texturepos_X + bgXOffset, texturepos_Y, textureWidth, textureHeight);

            if (halfIcon < thirst) //Full
            {
                RenderUtil.drawTexturedModalRect(x, y, texturepos_X + xOffset + (textureWidth * 4), texturepos_Y, textureWidth, textureHeight);
            } else if (halfIcon == thirst) //Half
            {
                RenderUtil.drawTexturedModalRect(x, y, texturepos_X + xOffset + (textureWidth * 5), texturepos_Y, textureWidth, textureHeight);
            }
        }

        int thirstSaturationInt = (int) thirstSaturation;
        if (thirstSaturationInt > 0) {
            if (ModConfig.client.drawThirstSaturation) {
                for (int i = 0; i < 10; i++) {
                    int halfIcon = i * 2 + 1;
                    int x = left - i * 8;
                    int y = top;


                    if (halfIcon < thirstSaturationInt) {
                        RenderUtil.drawTexturedModalRect(x, y, texturepos_X + (textureWidth * 14), texturepos_Y, textureWidth, textureHeight);
                    } else if (halfIcon == thirstSaturationInt) {
                        RenderUtil.drawTexturedModalRect(x, y, texturepos_X + (textureWidth * 15), texturepos_Y, textureWidth, textureHeight);
                    }
                }
            }
        }
        GlStateManager.disableBlend();
    }

}
