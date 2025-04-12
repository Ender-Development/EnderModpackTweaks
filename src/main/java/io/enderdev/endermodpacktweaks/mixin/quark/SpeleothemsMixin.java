package io.enderdev.endermodpacktweaks.mixin.quark;

import io.enderdev.endermodpacktweaks.EMTStore;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.arl.recipe.RecipeHandler;
import vazkii.arl.util.ProxyRegistry;
import vazkii.quark.world.block.BlockSpeleothem;
import vazkii.quark.world.feature.Speleothems;

@Mixin(value = Speleothems.class, remap = false)
public class SpeleothemsMixin {

    @Inject(method = "preInit", at = @At("HEAD"))
    private void preInit(CallbackInfo ci) {
        if (CfgTweaks.QUARK.enableEndSpeleothems) {
            EMTStore.Quark.endstone_speleothem = new BlockSpeleothem("endstone");
            EMTStore.Quark.endstone_speleothem.setHardness(3.0F);
            EMTStore.Quark.endstone_speleothem.setResistance(15.0F);
        }
        if (CfgTweaks.QUARK.enableObsidianSpeleothems) {
            EMTStore.Quark.obsidian_speleothem = new BlockSpeleothem("obsidian");
            EMTStore.Quark.obsidian_speleothem.setHardness(50.0F);
            EMTStore.Quark.obsidian_speleothem.setResistance(2000.0F);
            EMTStore.Quark.obsidian_speleothem.setHarvestLevel("pickaxe", 3);
        }
    }

    @Inject(method = "postPreInit", at = @At("HEAD"))
    private void postPreInit(CallbackInfo ci) {
        if (CfgTweaks.QUARK.enableEndSpeleothems) {
            RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(EMTStore.Quark.endstone_speleothem, 6), "S", "S", "S", 'S', "endstone");
        }
        if (CfgTweaks.QUARK.enableObsidianSpeleothems) {
            RecipeHandler.addOreDictRecipe(ProxyRegistry.newStack(EMTStore.Quark.obsidian_speleothem, 6), "S", "S", "S", 'S', "obsidian");
        }
    }
}
