package io.enderdev.endermodpacktweaks.mixin.recipestages;

import com.blamejared.recipestages.compat.StagedRecipeWrapperShaped;
import com.blamejared.recipestages.recipes.RecipeStage;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.time.StopWatch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Locale;

@Mixin(value = StagedRecipeWrapperShaped.class, remap = false)
public class StagedRecipeWrapperShapedMixin {
    @Shadow
    private RecipeStage recipe;

    @Unique
    private StopWatch enderModpackTweaks$stopWatch = null;

    @Unique
    private float enderModpackTweaks$deltaTime = 0f;

    @Unique
    private String enderModpackTweaks$localizedText = null;

    @Unique
    private float enderModpackTweaks$textWidth = 0f;

    @Unique
    private float enderModpackTweaks$xShift = 0f;

    @Unique
    private boolean enderModpackTweaks$needSliding;

    @Unique
    private float enderModpackTweaks$freezeTimer = 0f;

    @Unique
    private boolean enderModpackTweaks$onFreezeTiming = true;

    public float rectWidth = 100f;
    public float spareWidth = 20f;
    public float xShiftSpeed = 15f;
    public float freezeTime = 2f;
    public boolean onDemandSliding = false;

    @WrapOperation(method = "drawInfo", at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", remap = true), remap = false)
    public int drawString(FontRenderer instance, String text, int x, int y, int color, Operation<Integer> original) {
        if (!CfgTweaks.GAME_STAGES.localizeRecipeStages) {
            return original.call(instance, text, x + CfgTweaks.GAME_STAGES.recipeStagesTooltipXOffset, y + CfgTweaks.GAME_STAGES.recipeStagesTooltipYOffset, color);
        }

        if (enderModpackTweaks$stopWatch == null) {
            enderModpackTweaks$stopWatch = new StopWatch();
            enderModpackTweaks$stopWatch.start();
        } else {
            enderModpackTweaks$stopWatch.stop();
            enderModpackTweaks$deltaTime = (float) (enderModpackTweaks$stopWatch.getNanoTime() / 1e9d);
            enderModpackTweaks$stopWatch.reset();
            enderModpackTweaks$stopWatch.start();
        }

        // <https://github.com/tttsaurus/Ingame-Info-Reborn/blob/master/src/main/java/com/tttsaurus/ingameinfo/common/impl/gui/control/SlidingText.java>
        if ((!onDemandSliding || enderModpackTweaks$needSliding) && !enderModpackTweaks$onFreezeTiming) {
            enderModpackTweaks$xShift += (enderModpackTweaks$deltaTime * xShiftSpeed);
        }
        if (enderModpackTweaks$onFreezeTiming) {
            if (enderModpackTweaks$freezeTimer > freezeTime) {
                enderModpackTweaks$freezeTimer = 0;
                enderModpackTweaks$onFreezeTiming = false;
            }
            enderModpackTweaks$freezeTimer += enderModpackTweaks$deltaTime;
        }

        if (enderModpackTweaks$localizedText == null) {
            enderModpackTweaks$localizedText = I18n.format("emt.game_stages." + recipe.getTier().toLowerCase(Locale.ROOT).trim());
            enderModpackTweaks$localizedText = I18n.format("gui.rs.tip.stage", enderModpackTweaks$localizedText);
            enderModpackTweaks$textWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(enderModpackTweaks$localizedText);
            enderModpackTweaks$needSliding = enderModpackTweaks$textWidth > rectWidth;
        }

        x += CfgTweaks.GAME_STAGES.recipeStagesTooltipXOffset;
        y += CfgTweaks.GAME_STAGES.recipeStagesTooltipYOffset;
        if (!onDemandSliding || enderModpackTweaks$needSliding) {
            EmtRender.prepareStencilToWrite(1);
            EmtRender.drawRectStencilArea(x, y, rectWidth, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT);
            EmtRender.prepareStencilToRender(1);

            float firstX = x - enderModpackTweaks$xShift;
            if (firstX > x - enderModpackTweaks$textWidth) {
                EmtRender.renderText(enderModpackTweaks$localizedText, firstX, y, color);
            }
            if (enderModpackTweaks$xShift > spareWidth + enderModpackTweaks$textWidth - rectWidth) {
                float secondX = x + spareWidth + enderModpackTweaks$textWidth - enderModpackTweaks$xShift;
                if (secondX <= x) {
                    enderModpackTweaks$xShift = 0;
                    enderModpackTweaks$onFreezeTiming = true;
                    EmtRender.renderText(enderModpackTweaks$localizedText, x, y, color);
                } else {
                    EmtRender.renderText(enderModpackTweaks$localizedText, secondX, y, color);
                }
            }

            EmtRender.endStencil();
        } else {
            EmtRender.renderText(enderModpackTweaks$localizedText, x, y, 1f, color, true);
        }

        return 0;
    }
}
