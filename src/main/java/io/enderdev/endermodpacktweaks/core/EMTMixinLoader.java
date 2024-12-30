package io.enderdev.endermodpacktweaks.core;

import com.google.common.collect.ImmutableMap;
import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.config.EMTConfigMods;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EMTMixinLoader implements ILateMixinLoader {
    public static final Map<String, Boolean> modMixins = ImmutableMap.of(
            "perfectspawn", EMTConfigMods.PERFECT_SPAWN.enable
    );

    @Override
    public List<String> getMixinConfigs() {
        return modMixins.keySet().stream().filter(mod -> Loader.isModLoaded(mod) && modMixins.get(mod)).map(mod -> "mixins." + Tags.MOD_ID + "." + mod + ".json").collect(Collectors.toList());
    }
}
