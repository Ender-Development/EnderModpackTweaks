package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.render.mesh2d.RectMesh;
import io.enderdev.endermodpacktweaks.render.renderer.MeshRenderer;
import io.enderdev.endermodpacktweaks.render.shader.Shader;
import io.enderdev.endermodpacktweaks.render.shader.ShaderLoadingUtils;
import io.enderdev.endermodpacktweaks.render.shader.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

public class RectInstancingRenderer extends MeshRenderer {
    public static final float SIDE_LENGTH = 10f;

    private static ShaderProgram sharedShaderProgram = null;

    private final int maxInstance;

    public int getMaxInstance() {
        return maxInstance;
    }

    public RectInstancingRenderer(int maxInstance) {
        super(null, null);
        this.maxInstance = maxInstance;
    }

    private boolean init = false;
    public RectInstancingRenderer init() {
        if (init) return this;

        if (sharedShaderProgram == null) {
            Shader frag = ShaderLoadingUtils.load("endermodpacktweaks:shaders/mesh2d_frag.glsl", Shader.ShaderType.FRAGMENT);
            Shader vertex = ShaderLoadingUtils.load("endermodpacktweaks:shaders/mesh2d_instancing_vertex.glsl", Shader.ShaderType.VERTEX);
            ShaderProgram program = new ShaderProgram(frag, vertex);
            program.setup();

            Matrix4f matrix4f = new Matrix4f();
            matrix4f.setIdentity();
            FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
            matrix4f.store(buffer);
            buffer.flip();

            program.use();
            program.setUniform("inWorld", true);
            program.setUniform("transformation", buffer);
            program.setUniform("targetWorldPos", 0, 0, 0);
            program.unuse();

            EnderModpackTweaks.LOGGER.info("Loading shaders for rect instancing.");
            EnderModpackTweaks.LOGGER.info(program.getSetupDebugReport());

            sharedShaderProgram = program;
        }

        shaderProgram = sharedShaderProgram;

        RectMesh rectMesh = new RectMesh();
        rectMesh.enableInstancing();
        rectMesh.setInstanceData(new float[3 * maxInstance]);
        rectMesh.setInstanceDataUnitSize(3);
        rectMesh.setInstancePrimCount(0);
        rectMesh.setup();

        mesh = rectMesh;

        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        rectMesh.setRect((resolution.getScaledWidth() - SIDE_LENGTH) / 2f, (resolution.getScaledHeight() - SIDE_LENGTH) / 2f, SIDE_LENGTH, SIDE_LENGTH).update();

        init = true;
        return this;
    }
}
