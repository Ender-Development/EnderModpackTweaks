package io.enderdev.endermodpacktweaks.utils;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EmtConfigHandler<T extends EmtConfigParser.ConfigItem> {
    private final List<T> configItems = new ArrayList<>();
    private final String[] configData;
    private final Function<String, T> parser;

    public EmtConfigHandler(String[] configData, Function<String, T> parser) {
        this.configData = configData;
        this.parser = parser;
    }

    public EmtConfigHandler<T> init() {
        for (String line : configData) {
            try {
                T entry = parser.apply(line);
                configItems.add(entry);
            } catch (Exception e) {
                EnderModpackTweaks.LOGGER.error("Error parsing config data: {}", line, e);
            }
        }
        return this;
    }

    /**
     * Check if the list contains the given item stack.
     * @param stack The item stack to check.
     * @return True if the list contains the item stack, false otherwise.
     */
    public boolean contains(ItemStack stack) {
        return configItems.stream().anyMatch(item -> item.compare(stack));
    }

    /**
     * Check if the player has any of the items in the list equipped.
     * @param player The player to check.
     * @return True if the player has any of the items equipped, false otherwise.
     */
    public boolean equipped(EntityPlayer player) {
        for (ItemStack itemStack : player.getEquipmentAndArmor()) {
            if (contains(itemStack)) {
                return true;
            }
        }
        return false;
    }

    public EmtConfigParser.ConfigItem getEquipped(EntityPlayer player) {
        for (ItemStack itemStack : player.getEquipmentAndArmor()) {
            get(itemStack);
        }
        return null;
    }

    public EmtConfigParser.ConfigItem get(ItemStack stack) {
        for (T item : configItems) {
            if (item.compare(stack)) {
                return item;
            }
        }
        return null;
    }
}
