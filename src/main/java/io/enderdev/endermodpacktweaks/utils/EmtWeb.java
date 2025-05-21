package io.enderdev.endermodpacktweaks.utils;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public final class EmtWeb {
    public static void openUrl(String url) {
        try {
            URI uri = new URI(url);
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, uri);
        } catch (URISyntaxException uriSyntaxException) {
            Throwable throwable = uriSyntaxException.getCause();
            EnderModpackTweaks.LOGGER.error("Couldn't open link: {}", throwable == null ? "<UNKNOWN>" : throwable.getMessage());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException otherException) {
            EnderModpackTweaks.LOGGER.error(String.valueOf(otherException));
        }
    }

    public static String readUrl(String urlString) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null) reader.close();
        }
    }
}
