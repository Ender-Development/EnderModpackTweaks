package io.enderdev.endermodpacktweaks.core;

import com.google.common.collect.ImmutableMap;
import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;
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
            put("mixins/mixins.emt.minecraft.dragonfightmanager.json", () -> CfgMinecraft.DRAGON.enable);
            put("mixins/mixins.emt.minecraft.endgateway.json", () -> CfgMinecraft.END_GATEWAY.enable);
            put("mixins/mixins.emt.minecraft.endpodium.json", () -> CfgMinecraft.END_PODIUM.enable);
            put("mixins/mixins.emt.minecraft.netherportal.json", () -> CfgMinecraft.NETHER_PORTAL.enable);
            put("mixins/mixins.emt.minecraft.obsidianspike.json", () -> CfgMinecraft.OBSIDIAN_SPIKE.enable);
            put("mixins/mixins.emt.minecraft.endisland.json", () -> CfgMinecraft.END_ISLAND.enable);
            put("mixins/mixins.emt.minecraft.bossproof.json", () -> CfgFeatures.BOSS_PROOF_BLOCKS.enable);
            put("mixins/mixins.emt.minecraftforge.json", () -> CfgModpack.STARTUP_TIMER.enable);
            put("mixins/mixins.emt.minecraft.window.json", () -> CfgModpack.CUSTOMIZATION.enable);
            put("mixins/mixins.emt.minecraft.credits.json", () -> CfgFeatures.CUSTOM_CREDITS.enable);
        }
    });

    private static final Map<String, BooleanSupplier> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>() {
        {
            put("mixins/mixins.emt.minecraft.bossbar.json", () -> CfgFeatures.BOSS_BAR.enable);
            put("mixins/mixins.emt.minecraft.client.json", () -> true);
            put("mixins/mixins.emt.fancymenu.json", () -> true);
        }
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

    @SuppressWarnings("unchecked")
    @Override
    public void injectData(Map<String, Object> data) {
        try {
            Field f_transformerExceptions = LaunchClassLoader.class.getDeclaredField("transformerExceptions");
            f_transformerExceptions.setAccessible(true);
            Set<String> transformerExceptions = (Set<String>) f_transformerExceptions.get(Launch.classLoader);
            transformerExceptions.remove("tyra314.toolprogression");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return "io.enderdev.endermodpacktweaks.core.EMTTransformer";
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
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        BooleanSupplier sidedSupplier = EMTLoadingPlugin.isClient ? clientsideMixinConfigs.get(mixinConfig) : null;
        BooleanSupplier commonSupplier = commonMixinConfigs.get(mixinConfig);
        return sidedSupplier != null ? sidedSupplier.getAsBoolean() : commonSupplier == null || commonSupplier.getAsBoolean();
    }
}
