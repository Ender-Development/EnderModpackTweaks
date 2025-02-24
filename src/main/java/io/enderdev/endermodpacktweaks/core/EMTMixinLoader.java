package io.enderdev.endermodpacktweaks.core;

import com.google.common.collect.ImmutableMap;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class EMTMixinLoader implements ILateMixinLoader {
    private static final Map<String, BooleanSupplier> mixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>(){
        {
            put("mixins.emt.perfectspawn.json", () -> Loader.isModLoaded("perfectspawn") && EMTConfig.PERFECT_SPAWN.enable);
            put("mixins.emt.pyrotech.json", () -> Loader.isModLoaded("pyrotech") && EMTConfig.PYROTECH.enable);
            put("mixins.emt.rustic.json", () -> Loader.isModLoaded("rustic") && EMTConfig.RUSTIC.enable);
            put("mixins.emt.defaultworldgenerator.json", () -> Loader.isModLoaded("defaultworldgenerator-port") && EMTConfig.DEFAULT_WORLD_GENERATOR.enable);
            put("mixins.emt.simpledifficulty.json", () -> Loader.isModLoaded("simpledifficulty") && EMTConfig.SIMPLE_DIFFICULTY.enable);
            put("mixins.emt.itemphysic.json", () -> Loader.isModLoaded("itemphysic") && EMTConfig.ITEM_PHYSICS.enable);
            put("mixins.emt.toolprogression.json", () -> Loader.isModLoaded("toolprogression") && EMTConfig.TOOL_PROGRESSION.enable);
            put("mixins.emt.firstaid.json", () -> Loader.isModLoaded("firstaid") && EMTConfig.FIRST_AID.enable);
            put("mixins.emt.darkutils.json", () -> Loader.isModLoaded("darkutils") && EMTConfig.DARK_UTILS.enable);
            put("mixins.emt.quark.json", () -> Loader.isModLoaded("quark") && EMTConfig.QUARK.enable);
            put("mixins.emt.betterend.json", () -> Loader.isModLoaded("betterendforge") && EMTConfig.BETTER_END.enable);
            put("mixins.emt.lbm.json", () -> Loader.isModLoaded("lbm") && EMTConfig.LBM.enable);
            put("mixins.emt.bpopener.json", () -> Loader.isModLoaded("bpopener") && EMTConfig.BP_OPENER.enable);
            put("mixins.emt.crissaegrim.json", () -> Loader.isModLoaded("crissaegrim") && EMTConfig.CRISSAEGRIM.enable);
            put("mixins.emt.mysticallib.json", () -> Loader.isModLoaded("mysticallib") && EMTConfig.CRISSAEGRIM.enable);
            put("mixins.emt.astralsorcery.json", () -> Loader.isModLoaded("astralsorcery") && EMTConfig.ASTRAL_SORCERY.enable);
            put("mixins.emt.fluxnetworks.json", () -> Loader.isModLoaded("fluxnetworks") && EMTConfig.FLUX_NETWORKS.enable);
        }
    });

    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<>(mixinConfigs.keySet());
    }

    @Override
    public boolean shouldMixinConfigQueue(String config) {
        return mixinConfigs.get(config).getAsBoolean();
    }
}
