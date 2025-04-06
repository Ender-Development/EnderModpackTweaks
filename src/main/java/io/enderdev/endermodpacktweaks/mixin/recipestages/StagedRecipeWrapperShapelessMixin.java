package io.enderdev.endermodpacktweaks.mixin.recipestages;

import com.blamejared.recipestages.compat.StagedRecipeWrapperShapeless;
import com.blamejared.recipestages.recipes.RecipeStage;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Locale;

@Mixin(value = StagedRecipeWrapperShapeless.class, remap = false)
public class StagedRecipeWrapperShapelessMixin {
    @Shadow
    private RecipeStage recipe;

    @WrapOperation(method = "drawInfo", at = @At(value = "INVOKE", target="Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", remap = true), remap = false)
    public int drawString(FontRenderer instance, String text, int x, int y, int color, Operation<Integer> original) {
        if (!EMTConfig.GAME_STAGES.localizeRecipeStages) {
            return original.call(instance, text, x, y, color);
        }
        String localizedText = I18n.format("emt.game_stages." + recipe.getTier().toLowerCase(Locale.ROOT).trim());
        return instance.drawString(I18n.format("gui.rs.tip.stage", localizedText), x, y, color);
    }
}
