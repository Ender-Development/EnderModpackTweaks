package io.enderdev.endermodpacktweaks.mixin.waila;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import mcp.mobius.waila.addons.core.HUDHandlerBlocks;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(value = HUDHandlerBlocks.class, remap = false)
public class HUDHandlerBlocksMixin {

    @Unique
    private Map<ItemStack, ItemStack> enderModpackTweaks$replacementMap = new HashMap<ItemStack, ItemStack>() {{
        for(String line : CfgTweaks.WAILA.overrideBlockName) {
            // line: "mod:item:meta?;mod:item:meta?", like "minecraft:stone;minecraft:sand:1"
            String[] split = line.split(";");
            if(split.length != 2)
                continue;

            String[] original = split[0].split(":");
            String[] replacement = split[1].split(":");
            if((original.length != 2 && original.length != 3) || (replacement.length != 2 && replacement.length != 3))
                continue;

            Item originalItem = Item.getByNameOrId(original[0] + ":" + original[1]);
            Item replacementItem = Item.getByNameOrId(replacement[0] + ":" + replacement[1]);
            if(originalItem == null || replacementItem == null)
                continue;

            ItemStack originalStack = new ItemStack(originalItem, 1, original.length == 2? 0 : Integer.parseInt(original[2]));
            ItemStack replacementStack = new ItemStack(replacementItem, 1, replacement.length == 2? 0 : Integer.parseInt(replacement[2]));
            if(originalStack.isEmpty() || replacementStack.isEmpty()) // shouldn't happen but doesn't hurt to check
                continue;

            put(originalStack, replacementStack);
        }
    }};

    @Unique
    private ItemStack enderModpackTweaks$replaceItemStack(ItemStack itemStack) {
        for(Map.Entry<ItemStack, ItemStack> entry : enderModpackTweaks$replacementMap.entrySet())
            if(ItemStack.areItemsEqual(itemStack, entry.getKey()))
                return entry.getValue();
        return itemStack;
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
