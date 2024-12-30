package io.enderdev.endermodpacktweaks.core;

import com.google.common.collect.ImmutableMap;
import io.enderdev.endermodpacktweaks.config.EMTConfigMinecraft;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

@IFMLLoadingPlugin.Name("EnderModpackTweaksCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class EMTLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {

    public static final boolean isClient = FMLLaunchHandler.side().isClient();

    private static final Map<String, BooleanSupplier> serversideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>() {
    });

    private static final Map<String, BooleanSupplier> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>() {
        {
            put("mixins.emt.minecraft.dragonfightmanager.json", () -> EMTConfigMinecraft.DRAGON.enable);
            put("mixins.emt.minecraft.endgateway.json", () -> EMTConfigMinecraft.END_GATEWAY.enable);
            put("mixins.emt.minecraft.endpodium.json", () -> EMTConfigMinecraft.END_PODIUM.enable);
        }
    });

    private static final Map<String, BooleanSupplier> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>() {
    });

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public List<String> getMixinConfigs() {
        List<String> configs = new ArrayList<>();
        if (isClient) {
            configs.addAll(clientsideMixinConfigs.keySet());
        } else {
            configs.addAll(serversideMixinConfigs.keySet());
        }
        configs.addAll(commonMixinConfigs.keySet());
        return configs;
    }

     @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        BooleanSupplier sidedSupplier = EMTLoadingPlugin.isClient ? clientsideMixinConfigs.get(mixinConfig) : null;
        BooleanSupplier commonSupplier = commonMixinConfigs.get(mixinConfig);
        return sidedSupplier != null ? sidedSupplier.getAsBoolean() : commonSupplier == null || commonSupplier.getAsBoolean();
    }
}
