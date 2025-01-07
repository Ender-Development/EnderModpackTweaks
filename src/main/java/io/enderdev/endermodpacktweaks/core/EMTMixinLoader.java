package io.enderdev.endermodpacktweaks.core;

import com.google.common.collect.ImmutableMap;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EMTMixinLoader implements ILateMixinLoader {
    public static final Map<String, Boolean> modMixins = ImmutableMap.of(
            "perfectspawn", EMTConfig.PERFECT_SPAWN.enable,
            "pyrotech", EMTConfig.PYROTECH.enable,
            "rustic", EMTConfig.RUSTIC.enable,
            "defaultworldgenerator-port", EMTConfig.DEFAULT_WORLD_GENERATOR.enable,
            "simpledifficulty", EMTConfig.SIMPLE_DIFFICULTY.enable
    );

    @Override
    public List<String> getMixinConfigs() {
        return modMixins.keySet().stream().filter(mod -> Loader.isModLoaded(mod) && modMixins.get(mod)).map(mod -> "mixins.emt." + mod + ".json").collect(Collectors.toList());
    }
}
