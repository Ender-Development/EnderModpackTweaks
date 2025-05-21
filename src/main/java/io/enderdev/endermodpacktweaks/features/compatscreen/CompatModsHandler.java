package io.enderdev.endermodpacktweaks.features.compatscreen;

import com.google.common.collect.ImmutableMap;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
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
            put("dragonmurder", () -> CfgMinecraft.DRAGON.enable);
            put("packcrashinfo", () -> CfgModpack.CRASH_INFO.enable);
            put("materialchanger", () -> CfgFeatures.MATERIAL_TWEAKER.enable);
            put("asaafp", () -> CfgTweaks.ASTRAL_SORCERY.enable);
            put("chunkomg", () -> CfgMinecraft.CLIENT.enable);
            put("hidenametags", () -> CfgMinecraft.CLIENT.enable);
            put("hideitemnames", () -> CfgMinecraft.CLIENT.enable);
            put("antifovchange", () -> CfgMinecraft.CLIENT.enable);
            put("icfluxnetworkenergylimitfix", () -> CfgTweaks.FLUX_NETWORKS.enable);
            put("neat", () -> CfgFeatures.MOB_HEALTH_BAR.enable);
            put("bonemealmod", () -> CfgFeatures.INSTANT_BONE_MEAL.enable);
            put("controlling", () -> CfgFeatures.IMPROVED_KEYBINDS.enable);
            put("witherproof", () -> CfgFeatures.BOSS_PROOF_BLOCKS.enable);
            put("packupdater", () -> CfgModpack.PACK_UPDATER.enable);
            put("itlt", () -> CfgModpack.CUSTOMIZATION.enable || CfgModpack.DEFAULT_SERVER.enable);
            put("startuptimer", () -> CfgModpack.STARTUP_TIMER.enable);
            put("modernsplash", () -> CfgModpack.STARTUP_TIMER.enable);
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
