package io.enderdev.endermodpacktweaks.mixin.toolprogression;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import tyra314.toolprogression.item.ModItems;

@Mixin(value = ModItems.class, remap = false)
public class ModItemsMixin {
    @WrapMethod(method = "registerItems")
    private static void registerItems(RegistryEvent.Register<Item> event, Operation<Void> original) {
        if (Loader.isModLoaded("tconstruct")) {
            original.call(event);
        }
    }
}
