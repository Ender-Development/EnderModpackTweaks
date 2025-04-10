package io.enderdev.endermodpacktweaks.features.compatscreen;

import com.google.common.collect.ImmutableMap;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * <a href="https://github.com/ACGaming/UniversalTweaks/blob/main/src/main/java/mod/acgaming/universaltweaks/util/compat/UTObsoleteModsHandler.java">Inspired by Universal Tweaks</a>
 */
public class CompatModsHandler {
    private static final Map<String, BooleanSupplier> obsoleteModMap = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>() {
        {
            put("dragonmurder", () -> EMTConfig.MINECRAFT.DRAGON.enable);
            put("packcrashinfo", () -> EMTConfig.MODPACK.CRASH_INFO.enable);
            put("materialchanger", () -> EMTConfig.MODPACK.MATERIAL_TWEAKER.enable);
            put("asaafp", () -> EMTConfig.ASTRAL_SORCERY.enable);
            put("chunkomg", () -> EMTConfig.MINECRAFT.CLIENT.enable);
            put("hidenametags", () -> EMTConfig.MINECRAFT.CLIENT.enable);
            put("hideitemnames", () -> EMTConfig.MINECRAFT.CLIENT.enable);
            put("antifovchange", () -> EMTConfig.MINECRAFT.CLIENT.enable);
            put("icfluxnetworkenergylimitfix", () -> EMTConfig.FLUX_NETWORKS.enable);
            put("neat", () -> EMTConfig.MODPACK.MOB_HEALTH_BAR.enable);
            put("bonemealmod", () -> EMTConfig.MODPACK.INSTANT_BONE_MEAL.enable);
        }
    });

    private static List<String> obsoleteModsList;
    private static boolean hasShownObsoleteMods = false;

    public static boolean hasObsoleteModsMessage() {
        return !CompatModsHandler.hasShownObsoleteMods() && !getObsoleteModsList().isEmpty();
    }

    public static List<String> obsoleteModsMessage() {
        List<String> messages = new ArrayList<>();
        messages.add(new TextComponentTranslation("msg.endermodpacktweaks.obsoletemods.warning1").getFormattedText());
        messages.add(new TextComponentTranslation("msg.endermodpacktweaks.obsoletemods.warning2").getFormattedText());
        messages.add("");
        messages.addAll(getObsoleteModsList());
        messages.add("");
        messages.add(new TextComponentTranslation("msg.endermodpacktweaks.obsoletemods.warning3").getFormattedText());
        return messages;
    }

    private static List<String> getObsoleteModsList() {
        if (obsoleteModsList == null) {
            obsoleteModsList = generateObsoleteModsList();
        }
        return obsoleteModsList;
    }

    private static List<String> generateObsoleteModsList() {
        List<String> messages = new ArrayList<>();
        Map<String, ModContainer> modIdMap = Loader.instance().getIndexedModList();
        for (String modId : obsoleteModMap.keySet()) {
            if (Loader.isModLoaded(modId) && obsoleteModMap.get(modId).getAsBoolean()) {
                messages.add(modIdMap.get(modId).getName());
            }
        }
        return messages;
    }

    public static boolean hasShownObsoleteMods() {
        return hasShownObsoleteMods;
    }

    public static void setHasShownObsoleteMods(boolean value) {
        hasShownObsoleteMods = value;
    }
}
