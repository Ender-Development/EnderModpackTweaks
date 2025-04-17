package io.enderdev.endermodpacktweaks.mixin.cases;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import org.spongepowered.asm.mixin.Mixin;
import ru.radviger.cases.client.gui.ScreenOpenCase;

@Mixin(value = ScreenOpenCase.Spin.class, remap = false)
public class ScreenOpenCaseSpinMixin {
    @WrapMethod(method = "draw")
    public void draw(int x, int y, Operation<Void> original) {
        if (!CfgTweaks.CASES.disableAnimation) {
            original.call(x, y);
        }
    }
}
