package io.enderdev.endermodpacktweaks.features.forcedresourcepack;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.settings.GameSettings;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ResourcePackHandler {
    private static List<String> currentPacks;
    private static final List<String> defaultPacks = Arrays.asList(CfgFeatures.FORCED_RESOURCEPACK.resourcepacks);

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final GameSettings settings = mc.gameSettings;
    private static final ResourcePackRepository resourcePackRepository = mc.getResourcePackRepository();

    @SuppressWarnings("deprecation")
    public static void loadResourcepacks() {
        settings.loadOptions();
        currentPacks = settings.resourcePacks;
        settings.resourcePacks.clear();

        addToGameSettings();
        addResourceRepositories();

        mc.scheduleResourcesRefresh();
    }

    private static void addToGameSettings() {
        if (!defaultPacks.isEmpty()) {
            settings.resourcePacks.addAll(defaultPacks);
        }
        if (!CfgFeatures.FORCED_RESOURCEPACK.remove) {
            settings.resourcePacks.addAll(currentPacks.stream().filter(pack -> !defaultPacks.contains(pack)).collect(Collectors.toList()));
        }
    }

    private static void addResourceRepositories() {
        resourcePackRepository.updateRepositoryEntriesAll();
        Map<String, ResourcePackRepository.Entry> entryMap = resourcePackRepository.getRepositoryEntriesAll().stream().collect(Collectors.toMap(ResourcePackRepository.Entry::getResourcePackName, Function.identity()));
        List<ResourcePackRepository.Entry> repositoryEntries = settings.resourcePacks.stream().map(entryMap::get).filter(Objects::nonNull).collect(Collectors.toList());
        resourcePackRepository.setRepositories(repositoryEntries);
    }
}
