package io.enderdev.endermodpacktweaks.mixin.perfectspawn;

import lumien.perfectspawn.PerfectSpawn;
import lumien.perfectspawn.config.PSConfig;
import lumien.perfectspawn.config.PSConfigHandler;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.IOException;

@Mixin(value = PSConfigHandler.class, remap = false)
public abstract class PSConfigHandlerMixin {
    @Shadow
    PSConfig activeConfig;

    @Shadow
    protected abstract PSConfig loadConfig(File file);

    /**
     * @author _MasterEnderman_
     * @reason The mod is as dead as it can be, so I just override it to generate the config in an appropriate place.
     */
    @Overwrite
    public void reloadConfig() throws IOException {
        PerfectSpawn.debug("Reloading PerfectSpawn config");
        File configFile = new File(Loader.instance().getConfigDir(), "perfectspawn.json");
        if (configFile.isFile()) {
            activeConfig = loadConfig(configFile);
        } else {
            PerfectSpawn.debug("Config file not found, generating new one");
            configFile.createNewFile();
        }
    }
}
