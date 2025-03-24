package io.enderdev.endermodpacktweaks.mixin.waila;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import mcp.mobius.waila.addons.core.HUDHandlerBlocks;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(value = HUDHandlerBlocks.class, remap = false)
public class HUDHandlerBlocksMixin {

    @Unique
    private Map<ItemStack, ItemStack> enderModpackTweaks$replacementMap = new HashMap<ItemStack, ItemStack>() {{
        Arrays.stream(EMTConfig.WAILA.overrideBlockName).forEach( s -> {
            String[] split = s.split(";");
            List<String[]> splitStrings = Arrays.stream(split).map(spl -> spl.split(":")).collect(Collectors.toList());
            if (splitStrings.size() == 2
                    && splitStrings.get(0).length == 3
                    && Arrays.stream(splitStrings.get(0)).allMatch(Objects::nonNull)
                    && splitStrings.get(1).length == 3
                    && Arrays.stream(splitStrings.get(1)).allMatch(Objects::nonNull)
            ) {
                ItemStack original = new ItemStack(Objects.requireNonNull(Item.getByNameOrId(splitStrings.get(0)[0] + ":" + splitStrings.get(0)[1])), 1, Integer.parseInt(splitStrings.get(0)[2]));
                ItemStack replacement = new ItemStack(Objects.requireNonNull(Item.getByNameOrId(splitStrings.get(1)[0] + ":" + splitStrings.get(1)[1])), 1, Integer.parseInt(splitStrings.get(1)[2]));
                if (!original.isEmpty() && !replacement.isEmpty()) {
                    put(original, replacement);
                }
            }
        });
    }};

    @Unique
    private ItemStack enderModpackTweaks$replaceItemStack(ItemStack itemStack) {
        return enderModpackTweaks$replacementMap.keySet().stream().filter(key -> ItemStack.areItemStacksEqual(key, itemStack)).findFirst().map(enderModpackTweaks$replacementMap::get).orElse(itemStack);
    }

    @WrapMethod(method = "getWailaHead")
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config, Operation<List<String>> original) {
        return original.call(enderModpackTweaks$replaceItemStack(itemStack), currenttip, accessor, config);
    }

    @WrapMethod(method = "getWailaBody")
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config, Operation<List<String>> original) {
        return original.call(enderModpackTweaks$replaceItemStack(itemStack), currenttip, accessor, config);
    }

    @WrapMethod(method = "getWailaTail")
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config, Operation<List<String>> original) {
        return original.call(enderModpackTweaks$replaceItemStack(itemStack), currenttip, accessor, config);
    }
}
