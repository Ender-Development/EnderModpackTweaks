package io.enderdev.endermodpacktweaks.render.shader;

import io.enderdev.endermodpacktweaks.utils.EmtRlReader;

public final class ShaderLoadingUtils {
    public static Shader load(String rl, Shader.ShaderType shaderType) {
        String raw = EmtRlReader.read(rl, true);
        if (raw.isEmpty()) return null;
        return new Shader(rl, raw, shaderType);
    }
}
