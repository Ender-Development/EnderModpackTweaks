package io.enderdev.endermodpacktweaks.mixin.reskillable;

import codersafterdark.reskillable.api.skill.Skill;
import codersafterdark.reskillable.client.gui.GuiSkills;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;

@Mixin(value = GuiSkills.class, remap = false)
public class GuiSkillsMixin {
    @Shadow
    private List<Skill> enabledSkills;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        enabledSkills.sort(Comparator.comparing(Skill::getName));
    }
}
