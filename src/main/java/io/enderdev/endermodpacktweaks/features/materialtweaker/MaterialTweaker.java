package io.enderdev.endermodpacktweaks.features.materialtweaker;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;
import java.util.Arrays;

@SuppressWarnings("deprecation")
public class MaterialTweaker {
    private static final int ARMOR_PROTECTION_INDEX = 5;
    private static final int ARMOR_TOUGHNESS_INDEX = 6;
    private static final int TOOL_EFFICIENCY_INDEX = 1;
    private static final int TOOL_DAMAGE_INDEX = 2;
    private static final int TOOL_ATTACK_SPEED_INDEX = 3;
    private static final int HOE_ATTACK_SPEED_INDEX = 0;
    private static final int SWORD_DAMAGE_INDEX = 0;
    private static final int MATERIAL_HARVEST_LEVEL_INDEX = 5;
    private static final int MATERIAL_ENCHANTABILITY_INDEX = 9;
    private static final int ARMOR_MATERIAL_ENCHANTABILITY_INDEX = 8;

    public static final MaterialTweaker INSTANCE = new MaterialTweaker();

    private MaterialTweaker() {
    }

    public void load() {
        Arrays.stream(EMTConfig.MODPACK.MATERIAL_TWEAKER.stacksize).forEach(this::setStacksize);
        Arrays.stream(EMTConfig.MODPACK.MATERIAL_TWEAKER.durability).forEach(this::setDurability);
        Arrays.stream(EMTConfig.MODPACK.MATERIAL_TWEAKER.harvestLevel).forEach(this::setHarvestLevel);
        Arrays.stream(EMTConfig.MODPACK.MATERIAL_TWEAKER.enchantability).forEach(this::setEnchantability);
        Arrays.stream(EMTConfig.MODPACK.MATERIAL_TWEAKER.efficiency).forEach(this::setEfficiency);
        Arrays.stream(EMTConfig.MODPACK.MATERIAL_TWEAKER.attackDamage).forEach(this::setAttackDamage);
        Arrays.stream(EMTConfig.MODPACK.MATERIAL_TWEAKER.attackSpeed).forEach(this::setAttackSpeed);
        Arrays.stream(EMTConfig.MODPACK.MATERIAL_TWEAKER.armorProtection).forEach(this::setArmorProtection);
        Arrays.stream(EMTConfig.MODPACK.MATERIAL_TWEAKER.armorToughness).forEach(this::setArmorToughness);
    }

    private void setStacksize(String input) {
        Entry entry = new Entry(input);
        if (entry.invalid()) {
            EnderModpackTweaks.LOGGER.error("Invalid input, when editing stacksize: {}", input);
            return;
        }
        entry.item().setMaxStackSize(entry.i());
    }

    private void setDurability(String input) {
        Entry entry = new Entry(input);
        if (entry.invalid()) {
            EnderModpackTweaks.LOGGER.error("Invalid input, when editing durability: {}", input);
            return;
        }
        entry.item().setMaxDamage(entry.i());
    }

    private void setHarvestLevel(String input) {
        Entry entry = new Entry(input);
        if (entry.invalid()) {
            EnderModpackTweaks.LOGGER.error("Invalid input, when editing harvest level: {}", input);
            return;
        }
        Item.ToolMaterial toolMaterial = entry.getObjectFromClass(ItemTool.class, Item.ToolMaterial.class);
        if (toolMaterial == null) {
            toolMaterial = entry.getObjectFromClass(ItemSword.class, Item.ToolMaterial.class);
        }
        if (toolMaterial != null) {
            ObfuscationReflectionHelper.setPrivateValue(Item.ToolMaterial.class, toolMaterial, entry.i(), MATERIAL_HARVEST_LEVEL_INDEX);
        } else {
            EnderModpackTweaks.LOGGER.info("Currently not supported: Setting harvest level for {}", entry.item().getRegistryName());
        }
    }

    private void setEnchantability(String input) {
        Entry entry = new Entry(input);
        if (entry.invalid()) {
            EnderModpackTweaks.LOGGER.error("Invalid input, when editing enchantability: {}", input);
            return;
        }
        Item.ToolMaterial toolMaterial = entry.getObjectFromClass(ItemTool.class, Item.ToolMaterial.class);
        ItemArmor.ArmorMaterial armorMaterial = entry.getObjectFromClass(ItemArmor.class, ItemArmor.ArmorMaterial.class);
        if (toolMaterial == null) {
            toolMaterial = entry.getObjectFromClass(ItemSword.class, Item.ToolMaterial.class);
        }
        if (armorMaterial != null) {
            ObfuscationReflectionHelper.setPrivateValue(ItemArmor.ArmorMaterial.class, armorMaterial, entry.i(), ARMOR_MATERIAL_ENCHANTABILITY_INDEX);
        } else {
            EnderModpackTweaks.LOGGER.info("Currently not supported: Setting enchantability for {}", entry.item().getRegistryName());
        }
        if (toolMaterial != null) {
            ObfuscationReflectionHelper.setPrivateValue(Item.ToolMaterial.class, toolMaterial, entry.i(), MATERIAL_ENCHANTABILITY_INDEX);
        } else {
            EnderModpackTweaks.LOGGER.info("Currently not supported: Setting enchantability for {}", entry.item().getRegistryName());
        }
    }

    private void setEfficiency(String input) {
        Entry entry = new Entry(input);
        if (entry.invalid()) {
            EnderModpackTweaks.LOGGER.error("Invalid input, when editing efficiency: {}", input);
            return;
        }
        if (entry.item() instanceof ItemTool) {
            ObfuscationReflectionHelper.setPrivateValue(ItemTool.class, (ItemTool) entry.item(), entry.f(), TOOL_EFFICIENCY_INDEX);
        } else {
            EnderModpackTweaks.LOGGER.info("Currently not supported: Setting efficiency for {}", entry.item().getRegistryName());
        }
    }

    private void setAttackDamage(String input) {
        Entry entry = new Entry(input);
        if (entry.invalid()) {
            EnderModpackTweaks.LOGGER.error("Invalid input, when editing attack damage: {}", input);
            return;
        }
        if (entry.item() instanceof ItemSword) {
            ObfuscationReflectionHelper.setPrivateValue(ItemSword.class, (ItemSword) entry.item(), entry.f() - 1, SWORD_DAMAGE_INDEX);
        } else if (entry.item() instanceof ItemTool) {
            ObfuscationReflectionHelper.setPrivateValue(ItemTool.class, (ItemTool) entry.item(), entry.f() - 1, TOOL_DAMAGE_INDEX);
        } else {
            EnderModpackTweaks.LOGGER.info("Currently not supported: Setting attack damage for {}", entry.item().getRegistryName());
        }
    }

    private void setAttackSpeed(String input) {
        Entry entry = new Entry(input);
        if (entry.invalid()) {
            EnderModpackTweaks.LOGGER.error("Invalid input, when editing attack speed: {}", input);
            return;
        }
        if (entry.item() instanceof ItemSword) {
            EnderModpackTweaks.LOGGER.info("Currently not supported: Setting attack speed for swords");
        } else if (entry.item() instanceof ItemTool) {
            ObfuscationReflectionHelper.setPrivateValue(ItemTool.class, (ItemTool) entry.item(), entry.f() - 4, TOOL_ATTACK_SPEED_INDEX);
        } else if (entry.item() instanceof ItemHoe) {
            ObfuscationReflectionHelper.setPrivateValue(ItemHoe.class, (ItemHoe) entry.item(), entry.f(), HOE_ATTACK_SPEED_INDEX);
        } else {
            EnderModpackTweaks.LOGGER.info("Currently not supported: Setting attack speed for {}", entry.item().getRegistryName());
        }
    }

    private void setArmorProtection(String input) {
        Entry entry = new Entry(input);
        if (entry.invalid()) {
            EnderModpackTweaks.LOGGER.error("Invalid input, when editing armor protection: {}", input);
            return;
        }
        if (entry.item() instanceof ItemArmor) {
            ObfuscationReflectionHelper.setPrivateValue(ItemArmor.class, (ItemArmor) entry.item(), entry.i(), ARMOR_PROTECTION_INDEX);
        } else {
            EnderModpackTweaks.LOGGER.info("Currently not supported: Setting armor protection for {}", entry.item().getRegistryName());
        }
    }

    private void setArmorToughness(String input) {
        Entry entry = new Entry(input);
        if (entry.invalid()) {
            EnderModpackTweaks.LOGGER.error("Invalid input, when editing armor toughness: {}", input);
            return;
        }
        if (entry.item() instanceof ItemArmor) {
            ObfuscationReflectionHelper.setPrivateValue(ItemArmor.class, (ItemArmor) entry.item(), entry.f(), ARMOR_TOUGHNESS_INDEX);
        } else {
            EnderModpackTweaks.LOGGER.info("Currently not supported: Setting armor toughness for {}", entry.item().getRegistryName());
        }
    }

    private static class Entry {
        private final ItemStack input;
        private final float value;

        public Entry(String input) {
            this.input = getItemStack(input);
            this.value = getValue(input);
        }

        public ItemStack stack() {
            return input;
        }

        public Item item() {
            return input.getItem();
        }

        public int i() {
            return (int) value;
        }

        public float f() {
            return value;
        }

        public boolean invalid() {
            return input.isEmpty() || value <= 0;
        }

        public <T> T getObjectFromClass(Class fromClazz, Class<T> clazz) {

            if (!fromClazz.isInstance(this.item())) {
                return null;
            }

            try {
                for (Field field : fromClazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object object = field.get(this.item());
                    if (clazz.isInstance(object)) {
                        return (T) object;
                    }
                }
            } catch (Exception e) {
                EnderModpackTweaks.LOGGER.error("Error getting object from class: {}", e.toString());
            }
            return null;
        }

        private ItemStack getItemStack(String input) {
            String[] splits = input.trim().split(";");
            if (splits.length != 2) {
                return ItemStack.EMPTY;
            }
            int meta = 0;
            String[] itemSplits = splits[0].split(":");
            if (itemSplits.length == 3) {
                meta = Integer.parseInt(itemSplits[2]);
            }
            Item item = Item.getByNameOrId(new ResourceLocation(itemSplits[0], itemSplits[1]).toString());
            if (item == null) {
                return ItemStack.EMPTY;
            }
            return new ItemStack(item, 1, meta);
        }

        private float getValue(String input) {
            try {
                return Float.parseFloat(input.split(";")[1]);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }
}
