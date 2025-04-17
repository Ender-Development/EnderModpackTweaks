package io.enderdev.endermodpacktweaks.mixin.cases;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.radviger.cases.Cases;
import ru.radviger.cases.client.gui.ScreenOpenCase;
import ru.radviger.cases.client.gui.ScreenRewardResult;
import ru.radviger.cases.slot.Case;
import ru.radviger.cases.slot.SingleCaseSlot;

import java.util.Collections;

@Mixin(value = ScreenOpenCase.class, remap = false)
public class ScreenOpenCaseMixin {
    @Shadow
    private ScreenOpenCase.Spin spin;

    @Final
    @Shadow
    private Case entry;

    @WrapMethod(method = "update")
    public void update(Operation<Void> original) {
        if (spin != null) {
            SingleCaseSlot result = this.spin.getResultSlot();
            ItemStack item = result.getItemStack();
            Minecraft.getMinecraft().displayGuiScreen(new ScreenRewardResult(Collections.singletonList(item), this.entry.caseName, false, this.entry.getName(Cases.getCurrentLanguage())));
        }
    }
}
