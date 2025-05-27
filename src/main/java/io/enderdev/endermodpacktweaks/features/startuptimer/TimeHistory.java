package io.enderdev.endermodpacktweaks.features.startuptimer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import net.minecraft.launchwrapper.Launch;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TimeHistory {
    private static final File configFile = new File(Launch.minecraftHome, "time.history");

    public static long getEstimateTime() {
        try {
            if (!configFile.exists()) configFile.createNewFile();

            JsonReader jr = new JsonReader(new FileReader(configFile));
            JsonElement jp = new JsonParser().parse(jr);
            if (jp.isJsonObject()) {
                JsonObject obj = jp.getAsJsonObject();
                if (obj.has("times") && obj.get("times").isJsonArray()) {
                    JsonArray arr = obj.get("times").getAsJsonArray();
                    if (arr.size() > 0) {
                        long sum = 0;
                        for (int i = 0; i < arr.size(); i++) {
                            sum += arr.get(i).getAsLong();
                        }
                        sum /= arr.size();

                        return sum;
                    }
                }
            }
            jr.close();
        } catch (IOException e) {
            EnderModpackTweaks.LOGGER.error(String.valueOf(e));
        }
        return CfgModpack.STARTUP_TIMER.defaultTime;
    }

    public static void saveHistory(long startupTime) {
        try {
            if (!configFile.exists()) configFile.createNewFile();

            long[] times = new long[0];
            JsonReader jr = new JsonReader(new FileReader(configFile));
            JsonElement jp = new JsonParser().parse(jr);

            if (jp.isJsonObject()) {
                JsonObject obj = jp.getAsJsonObject();
                if (obj.has("times") && obj.get("times").isJsonArray()) {
                    JsonArray arr = obj.get("times").getAsJsonArray();
                    if (arr.size() > 0) {
                        times = new long[arr.size()];
                        for (int i = 0; i < arr.size(); i++) {
                            times[i] = arr.get(i).getAsLong();
                        }
                    }
                }
            }
            jr.close();

            JsonWriter jw = getJsonWriter(startupTime, times);
            jw.close();

        } catch (IOException e) {
            EnderModpackTweaks.LOGGER.error(String.valueOf(e));
        }
    }

    private static @NotNull JsonWriter getJsonWriter(long startupTime, long[] times) throws IOException {
        JsonWriter jw = new JsonWriter(new FileWriter(configFile));
        jw.setIndent("  ");
        jw.beginObject();

        jw.name("times");
        jw.beginArray();
        if (times.length > CfgModpack.STARTUP_TIMER.sizeHistory) {
            for (int i = times.length - CfgModpack.STARTUP_TIMER.sizeHistory - 1; i < times.length; i++) {
                jw.value(times[i]);
            }
        } else {
            for (long time : times) {
                jw.value(time);
            }
        }
        jw.value(startupTime);
        jw.endArray();

        jw.endObject();
        return jw;
    }
}
