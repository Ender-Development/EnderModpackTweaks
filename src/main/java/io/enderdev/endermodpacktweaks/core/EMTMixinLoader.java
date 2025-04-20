package io.enderdev.endermodpacktweaks.core;

import codechicken.enderstorage.EnderStorage;
import com.google.common.collect.ImmutableMap;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class EMTMixinLoader implements ILateMixinLoader {
    private static final Map<String, BooleanSupplier> MIXIN_CONFIGS = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>(){
        {
            put("mixins/mixins.emt.perfectspawn.json", () -> Loader.isModLoaded("perfectspawn") && CfgTweaks.PERFECT_SPAWN.enable);
            put("mixins/mixins.emt.pyrotech.json", () -> Loader.isModLoaded("pyrotech") && CfgTweaks.PYROTECH.enable);
            put("mixins/mixins.emt.rustic.json", () -> Loader.isModLoaded("rustic") && CfgTweaks.RUSTIC.enable);
            put("mixins/mixins.emt.defaultworldgenerator.json", () -> Loader.isModLoaded("defaultworldgenerator-port") && CfgTweaks.DEFAULT_WORLD_GENERATOR.enable);
            put("mixins/mixins.emt.simpledifficulty.json", () -> Loader.isModLoaded("simpledifficulty") && CfgTweaks.SIMPLE_DIFFICULTY.enable);
            put("mixins/mixins.emt.itemphysic.json", () -> Loader.isModLoaded("itemphysic") && CfgTweaks.ITEM_PHYSICS.enable);
            put("mixins/mixins.emt.toolprogression.json", () -> Loader.isModLoaded("toolprogression") && CfgTweaks.TOOL_PROGRESSION.enable);
            put("mixins/mixins.emt.firstaid.json", () -> Loader.isModLoaded("firstaid") && CfgTweaks.FIRST_AID.enable);
            put("mixins/mixins.emt.darkutils.json", () -> Loader.isModLoaded("darkutils") && CfgTweaks.DARK_UTILS.enable);
            put("mixins/mixins.emt.quark.json", () -> Loader.isModLoaded("quark") && CfgTweaks.QUARK.enable);
            put("mixins/mixins.emt.betterend.json", () -> Loader.isModLoaded("betterendforge") && CfgTweaks.BETTER_END.enable);
            put("mixins/mixins.emt.lbm.json", () -> Loader.isModLoaded("lbm") && CfgTweaks.LBM.enable);
            put("mixins/mixins.emt.bpopener.json", () -> Loader.isModLoaded("bpopener") && CfgTweaks.BP_OPENER.enable);
            put("mixins/mixins.emt.crissaegrim.json", () -> Loader.isModLoaded("crissaegrim") && CfgTweaks.CRISSAEGRIM.enable);
            put("mixins/mixins.emt.mysticallib.json", () -> Loader.isModLoaded("mysticallib") && CfgTweaks.CRISSAEGRIM.enable);
            put("mixins/mixins.emt.astralsorcery.json", () -> Loader.isModLoaded("astralsorcery") && CfgTweaks.ASTRAL_SORCERY.enable);
            put("mixins/mixins.emt.fluxnetworks.json", () -> Loader.isModLoaded("fluxnetworks") && CfgTweaks.FLUX_NETWORKS.enable);
            put("mixins/mixins.emt.mbtool.json", () -> Loader.isModLoaded("mbtool") && CfgTweaks.MULTI_BUILDER_TOOL.enable);
            put("mixins/mixins.emt.enderstorage.json", () -> Loader.isModLoaded("enderstorage") && CfgTweaks.ENDER_STORAGE.enable && EnderStorage.VERSION.equals("2.6.3"));
            put("mixins/mixins.emt.reskillable.json", () -> Loader.isModLoaded("reskillable") && CfgTweaks.RESKILLABLE.enable);
            put("mixins/mixins.emt.elenaidodge.json", () -> Loader.isModLoaded("elenaidodge2") && CfgTweaks.ELENAI_DODGE.enable);
            put("mixins/mixins.emt.dshuds.json", () -> Loader.isModLoaded("dshuds") && CfgTweaks.DSHUDS.enable);
            put("mixins/mixins.emt.potioncore.json", () -> Loader.isModLoaded("potioncore") && CfgTweaks.POTION_CORE.enable);
            put("mixins/mixins.emt.waila.json", () -> Loader.isModLoaded("waila") && CfgTweaks.WAILA.enable);
            put("mixins/mixins.emt.pickletweaks.json", () -> Loader.isModLoaded("pickletweaks") && CfgTweaks.PICKLE_TWEAKS.enable);
            put("mixins/mixins.emt.matteroverdrive.json", () -> Loader.isModLoaded("matteroverdrive") && CfgTweaks.MATTER_OVERDRIVE.enable);
            put("mixins/mixins.emt.itemstages.json", () -> Loader.isModLoaded("itemstages") && CfgTweaks.GAME_STAGES.enable);
            put("mixins/mixins.emt.recipestages.json", () -> Loader.isModLoaded("recipestages") && CfgTweaks.GAME_STAGES.enable);
            put("mixins/mixins.emt.cases.json", () -> Loader.isModLoaded("cases") && CfgTweaks.CASES.enable);
            put("mixins/mixins.emt.scalinghealth.json", () -> Loader.isModLoaded("scalinghealth") && CfgTweaks.SCALING_HEALTH.enable);
            put("mixins/mixins.emt.delivery.json", () -> Loader.isModLoaded("delivery") && CfgTweaks.DELIVERY.enable);
        }
    });

    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<>(MIXIN_CONFIGS.keySet());
    }

    @Override
    public boolean shouldMixinConfigQueue(String config) {
        return MIXIN_CONFIGS.get(config).getAsBoolean();
    }
}
