package io.enderdev.endermodpacktweaks.core;

import com.cleanroommc.assetmover.AssetMoverAPI;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.utils.EmtFile;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraftforge.fml.common.Loader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public final class EMTAssetMover {

    private EMTAssetMover() {
        throw new IllegalStateException("Utility class");
    }

    @SuppressWarnings("all")
    public static void getInternalAssets() {
        List<AssetLocation> assets = null;
        try {
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Loader.class.getResourceAsStream(String.format("/assets/%s/assetmover.json", Tags.MOD_ID))));
            assets = gson.fromJson(reader, new TypeToken<List<AssetLocation>>() {
            }.getType());
        } catch (Exception e) {
            EnderModpackTweaks.LOGGER.error("Failed to read assetmover.json", e);
        }
        acquireAssets(assets);
    }

    @SuppressWarnings("all")
    public static void getExternalAssets() {
        Map<String, InputStream> externalAssets = new HashMap<>();
        Arrays.stream(CfgModpack.CUSTOM_ASSETS.assetMoverJsonFiles).forEach(path -> {
            try {
                InputStream inputStream = EmtFile.getInputStream(path);
                externalAssets.put(path, inputStream);
            } catch (Exception e) {
                EnderModpackTweaks.LOGGER.error("Error loading JSON file from path: {}", path, e);
            }
        });
        externalAssets.forEach((path, inputStream) -> {
            List<AssetLocation> assets = null;
            try {
                Gson gson = new Gson();
                InputStreamReader reader = new InputStreamReader(inputStream);
                assets = gson.fromJson(reader, new TypeToken<List<AssetLocation>>() {}.getType());
            } catch (Exception e) {
                EnderModpackTweaks.LOGGER.error("Failed to read asset file: {}", path, e);
            }
            acquireAssets(assets);
        });
    }

    private static void acquireAssets(List<AssetLocation> assets) {
        if (assets == null || assets.isEmpty()) {
            EnderModpackTweaks.LOGGER.error("No assets found!");
            return;
        }
        EnderModpackTweaks.LOGGER.info("Parsed assets files. Found {} asset sources.", assets.size());
        assets.forEach(asset -> {
            Map<String, String> textureMap = new Object2ObjectOpenHashMap<>();
            asset.getTextures().forEach(texture -> {
                textureMap.put(texture.getOriginal(), texture.getNewPath());
            });

            if (asset.getSource().equals("minecraft") && asset.getVersion() != null) {
                try {
                    EnderModpackTweaks.LOGGER.info("Using Minecraft version: {}", asset.getVersion());
                    AssetMoverAPI.fromMinecraft(asset.getVersion(), textureMap);
                } catch (Exception e) {
                    EnderModpackTweaks.LOGGER.fatal("Failed to get assets for version {} with {}", asset.getVersion(), e);
                }
            } else if (asset.getSource().equals("curseforge") && asset.projectID != 0 && asset.fileID != 0) {
                try {
                    EnderModpackTweaks.LOGGER.info("Using CurseForge project ID: {}, file ID: {}", asset.getProjectID(), asset.getFileID());
                    AssetMoverAPI.fromCurseForgeMod(asset.getProjectID(), asset.getFileID(), textureMap);
                } catch (Exception e) {
                    EnderModpackTweaks.LOGGER.fatal("Failed to get assets for curseforge project {} from version {} with {}", asset.getProjectID(), asset.getFileID(), e);
                }
            } else if (asset.getSource().equals("url") && asset.getUrl() != null) {
                try {
                    EnderModpackTweaks.LOGGER.info("Using URL: {}", asset.getUrl());
                    AssetMoverAPI.fromUrlFile(asset.getUrl(), textureMap);
                } catch (Exception e) {
                    EnderModpackTweaks.LOGGER.fatal("Failed to get assets from URL {} with {}", asset.getUrl(), e);
                }
            } else {
                EnderModpackTweaks.LOGGER.error("Invalid asset source: {}", asset.getSource());
            }
        });
    }

    private static class AssetLocation {
        private String source;
        private String version;
        private String url;
        private int projectID;
        private int fileID;
        private List<Texture> textures;

        public String getSource() {
            return source;
        }

        public String getVersion() {
            return version;
        }

        public String getUrl() {
            return url;
        }

        public String getProjectID() {
            return "" + projectID;
        }

        public String getFileID() {
            return "" + fileID;
        }

        public List<Texture> getTextures() {
            return textures;
        }
    }

    private static class Texture {
        private String original;
        @SerializedName("new")
        private String newPath;

        public String getOriginal() {
            return original;
        }

        public String getNewPath() {
            return newPath;
        }
    }
}
