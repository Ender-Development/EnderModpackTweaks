package io.enderdev.endermodpacktweaks.mixin.cases;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import ru.radviger.cases.client.gui.ScreenOpenCase;

@Mixin(value = ScreenOpenCase.Spin.class, remap = false)
public class ScreenOpenCaseSpinMixin {
    @WrapMethod(method = "draw")
    public void draw(int x, int y, Operation<Void> original) {
        // We don't want to draw the spin animation
    }
}
