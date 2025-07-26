package io.enderdev.endermodpacktweaks.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.Objects;

public final class EmtConfigParser {
    public static class ConfigItem {
        private String modid;
        private String item;
        private int meta;

        ConfigItem() {
        }

        public ConfigItem(String configString) {
            parseConfigString(configString);
            validateConfigItem();
        }

        public boolean compare(Object obj) {
            if (obj instanceof ItemStack) {
                return this.meta == -1 ? ((ItemStack) obj).getItem() == toItemStack().getItem() : ((ItemStack) obj).isItemEqual(this.toItemStack());
            }
            if (obj instanceof ConfigItem) {
                ConfigItem other = (ConfigItem) obj;
                return this.modid.equals(other.modid) && this.item.equals(other.item) && this.meta == other.meta;
            }
            return super.equals(obj);
        }

        public ItemStack toItemStack() {
            return new ItemStack(Objects.requireNonNull(Item.getByNameOrId(new ResourceLocation(modid, item).toString())), 1, meta);
        }

        void parseConfigString(String configString) {
            String[] parts = configString.split(":");
            if (parts.length == 3) {
                this.modid = parts[0];
                this.item = parts[1];
                this.meta = Integer.parseInt(parts[2]);
            } else if (parts.length == 2) {
                this.modid = parts[0];
                this.item = parts[1];
                this.meta = -1;
            } else {
                throw new IllegalArgumentException("Invalid config string format: " + configString);
            }
        }

        void validateConfigItem() {
            if (modid == null || item == null) {
                throw new IllegalArgumentException("Mod ID and item name cannot be null");
            }
            if (!Loader.isModLoaded(modid)) {
                throw new IllegalArgumentException("Mod ID is not loaded: " + modid);
            }
            // Check metadata value (the meta value can be -1 for any item)
            if (meta < 0 && meta != -1) {
                throw new IllegalArgumentException("Meta value cannot be negative");
            }
        }
    }

    public static class ConfigItemWithFloat extends ConfigItem {
        private final float value;

        public ConfigItemWithFloat(String configString) {
            String[] parts = configString.split(";");
            if (parts.length == 2) {
                parseConfigString(parts[0]);
                this.value = Float.parseFloat(parts[1]);
            } else {
                throw new IllegalArgumentException("Invalid config string format: " + configString);
            }
            validateConfigItem();
        }

        public float value() {
            return value;
        }
    }

    public static class ConfigItemWithInt extends ConfigItem {
        private final int value;

        public ConfigItemWithInt(String configString) {
            String[] parts = configString.split(";");
            if (parts.length == 2) {
                parseConfigString(parts[0]);
                this.value = Integer.parseInt(parts[1]);
            } else {
                throw new IllegalArgumentException("Invalid config string format: " + configString);
            }
            validateConfigItem();
        }

        public int value() {
            return value;
        }
    }

    public static class ConfigItemWithBoolean extends ConfigItem {
        private final boolean value;

        public ConfigItemWithBoolean(String configString) {
            String[] parts = configString.split(";");
            if (parts.length == 2) {
                parseConfigString(parts[0]);
                this.value = Boolean.parseBoolean(parts[1]);
            } else {
                throw new IllegalArgumentException("Invalid config string format: " + configString);
            }
            validateConfigItem();
        }

        public boolean value() {
            return value;
        }
    }
}
