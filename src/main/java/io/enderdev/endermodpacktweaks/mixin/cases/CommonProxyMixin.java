package io.enderdev.endermodpacktweaks.mixin.cases;

import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import ru.radviger.cases.proxy.CommonProxy;

@Mixin(value = CommonProxy.class, remap = false)
public class CommonProxyMixin {
    @ModifyArg(method = "handleSpinStart", at = @At(value = "INVOKE", target = "Lru/radviger/cases/Cases;generateSpin(Ljava/util/Random;Ljava/util/Collection;I)Ljava/util/List;"), index = 2)
    private int modifySpinCount(int length) {
        return CfgTweaks.CASES.spinCount;
    }
}
