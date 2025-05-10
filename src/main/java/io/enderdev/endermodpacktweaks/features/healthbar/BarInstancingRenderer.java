package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.render.mesh2d.RectMesh;
import io.enderdev.endermodpacktweaks.render.mesh2d.RoundedRectMesh;
import io.enderdev.endermodpacktweaks.render.renderer.MeshRenderer;
import io.enderdev.endermodpacktweaks.render.shader.Shader;
import io.enderdev.endermodpacktweaks.render.shader.ShaderLoadingUtils;
import io.enderdev.endermodpacktweaks.render.shader.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

public class BarInstancingRenderer extends MeshRenderer {
    private static ShaderProgram sharedShaderProgram = null;

    private final int maxInstance;

    public int getMaxInstance() {
        return maxInstance;
    }

    public BarInstancingRenderer(int maxInstance) {
        super(null, null);
        this.maxInstance = maxInstance;
    }

    public enum BarType {
        RECT,
        ROUNDED_RECT
    }

    private BarType barType = BarType.RECT;

    public BarType getBarType() {
        return barType;
    }
    public void setBarType(BarType barType) {
        this.barType = barType;
        if (barType == BarType.RECT) {
            mesh = rectMesh;
        } else if (barType == BarType.ROUNDED_RECT) {
            mesh = roundedRectMesh;
        }
    }

    private RoundedRectMesh roundedRectMesh;
    private RectMesh rectMesh;

    private boolean init = false;
    public BarInstancingRenderer init() {
        if (init) return this;

        if (sharedShaderProgram == null) {
            Shader frag = ShaderLoadingUtils.load("endermodpacktweaks:shaders/mesh2d_frag.glsl", Shader.ShaderType.FRAGMENT);
            Shader vertex = ShaderLoadingUtils.load("endermodpacktweaks:shaders/mesh2d_instancing_vertex.glsl", Shader.ShaderType.VERTEX);
            ShaderProgram program = new ShaderProgram(frag, vertex);
            program.setup();

            Matrix4f matrix4f = new Matrix4f();
            matrix4f.setIdentity();
            matrix4f.scale(new Vector3f(50, 50, 0));
            FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
            matrix4f.store(buffer);
            buffer.flip();

            program.use();
            program.setUniform("inWorld", true);
            program.setUniform("unprojectToWorld", true);
            program.setUniform("transformation", buffer);
            program.setUniform("targetWorldPos", 0, 0, 0);
            program.unuse();

            EnderModpackTweaks.LOGGER.info("Loading shaders for health bar instancing.");
            EnderModpackTweaks.LOGGER.info(program.getSetupDebugReport());

            sharedShaderProgram = program;
        }

        shaderProgram = sharedShaderProgram;

        RoundedRectMesh roundedRectMesh = new RoundedRectMesh(3);
        roundedRectMesh.enableInstancing();
        roundedRectMesh.setInstanceData(new float[3 * maxInstance]);
        roundedRectMesh.setInstanceDataUnitSize(3);
        roundedRectMesh.setInstancePrimCount(0);
        roundedRectMesh.setup();
        this.roundedRectMesh = roundedRectMesh;

        RectMesh rectMesh = new RectMesh();
        rectMesh.enableInstancing();
        rectMesh.setInstanceData(new float[3 * maxInstance]);
        rectMesh.setInstanceDataUnitSize(3);
        rectMesh.setInstancePrimCount(0);
        rectMesh.setup();
        this.rectMesh = rectMesh;

        mesh = rectMesh;

        // test
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        roundedRectMesh.setCornerRadius(5f).setRect((resolution.getScaledWidth() - 30) / 2f, (resolution.getScaledHeight() - 30) / 2f, 30, 30).update();
        rectMesh.setRect((resolution.getScaledWidth() - 30) / 2f, (resolution.getScaledHeight() - 30) / 2f, 30, 30).update();

        init = true;
        return this;
    }
}
