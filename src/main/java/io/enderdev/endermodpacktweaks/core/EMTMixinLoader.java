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
