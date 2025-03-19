package io.enderdev.endermodpacktweaks.core;

import com.cleanroommc.assetmover.AssetMoverAPI;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.Tags;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EMTAssetMover {

    private EMTAssetMover() {
        throw new IllegalStateException("Utility class");
    }

    public static void getAssets() {
        List<AssetLocation> assets = null;
        try {
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(Loader.class.getResourceAsStream(String.format("/assets/%s/assetmover.json", Tags.MOD_ID))));
            assets = gson.fromJson(reader, new TypeToken<List<AssetLocation>>() {}.getType());
        } catch (Exception e) {
            EnderModpackTweaks.LOGGER.error("Failed to read assetmover.json", e);
        }
        if (assets == null) {
            EnderModpackTweaks.LOGGER.error("Failed to parse assetmover.json");
            return;
        }
        EnderModpackTweaks.LOGGER.info("Parsed assetmover.json");
        assets.forEach(asset -> {
            Map<String, String> textureMap = new Object2ObjectOpenHashMap<>();
            asset.getTextures().forEach(texture -> {
                textureMap.put(texture.getOriginal(), texture.getNewPath());
            });
            EnderModpackTweaks.LOGGER.info("Moving assets for {}", asset.getProject());
            try {
                AssetMoverAPI.fromCurseForgeMod(asset.getProjectID(), asset.getFileID(), textureMap);
            } catch (Exception e) {
                EnderModpackTweaks.LOGGER.fatal("Failed to move assets for {} with {}", asset.getProject(), e);
            }
            EnderModpackTweaks.LOGGER.info("Finished moving assets for {}", asset.getProject());
        });
    }

    private static class AssetLocation {
        private String project;
        private int projectID;
        private int fileID;
        private List<Texture> textures;

        public String getProject () {
            return project;
        }

        public String getProjectID () {
            return "" + projectID;
        }

        public String getFileID () {
            return "" + fileID;
        }

        public List<Texture> getTextures () {
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
