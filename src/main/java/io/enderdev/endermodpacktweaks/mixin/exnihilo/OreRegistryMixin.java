package io.enderdev.endermodpacktweaks.mixin.exnihilo;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import exnihilocreatio.registries.registries.OreRegistry;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = OreRegistry.class, remap = false)
public class OreRegistryMixin {
	@WrapMethod(method = "doRecipes")
	public void doRecipes(Operation<Void> original) {
		if(!CfgTweaks.EX_NIHILO.removeOreRecipes)
			original.call();
	}
}
