package io.enderdev.endermodpacktweaks.mixin.dshuds;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import io.enderdev.endermodpacktweaks.config.EnumListType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.orecruncher.dshuds.hud.PotionHUD;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mixin(value = PotionHUD.class, remap = false)
public class PotionHUDMixin {
    @WrapMethod(method = "skipDisplay")
    public boolean skipDisplay(PotionEffect effect, Operation<Boolean> original) {
        return original.call(effect) || enderModpackTweaks$listCheck(effect);
    }

    @Unique
    private boolean enderModpackTweaks$listCheck(PotionEffect effect) {
        List<Potion> potionList = Arrays.stream(CfgTweaks.DSHUDS.potionHUDHideList)
                .map(Potion::getPotionFromResourceLocation)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return (CfgTweaks.DSHUDS.potionHUDHideListType == EnumListType.BLACKLIST) == potionList.contains(effect.getPotion());
    }
}
