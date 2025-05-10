package io.enderdev.endermodpacktweaks.features.healthbar;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.render.Mesh;
import io.enderdev.endermodpacktweaks.render.mesh2d.RectMesh;
import io.enderdev.endermodpacktweaks.render.renderer.MeshRenderer;
import io.enderdev.endermodpacktweaks.render.shader.Shader;
import io.enderdev.endermodpacktweaks.render.shader.ShaderLoadingUtils;
import io.enderdev.endermodpacktweaks.render.shader.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

public class RectInstancingRenderer extends MeshRenderer {
    public static final float SIDE_LENGTH = 10f;
    public static final int INSTANCE_DATA_UNIT_SIZE = 6;

    private static ShaderProgram sharedShaderProgram = null;

    private final int maxInstance;

    public int getMaxInstance() {
        return maxInstance;
    }

    public int getInstanceDataLength() {
        return maxInstance * INSTANCE_DATA_UNIT_SIZE;
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
        rectMesh.setInstanceData(new float[getInstanceDataLength()]);
        rectMesh.setInstanceDataUnitSize(INSTANCE_DATA_UNIT_SIZE);
        rectMesh.setInstancePrimCount(0);
        rectMesh.setCustomInstancingLayout(new Mesh.IManageInstancingLayout() {
            @Override
            public void manage() {
                GL20.glVertexAttribPointer(3, 3, GL11.GL_FLOAT, false, INSTANCE_DATA_UNIT_SIZE * Float.BYTES, 0);
                GL20.glEnableVertexAttribArray(3);
                GL33.glVertexAttribDivisor(3, 1);

                GL20.glVertexAttribPointer(4, 3, GL11.GL_FLOAT, false, INSTANCE_DATA_UNIT_SIZE * Float.BYTES, 3 * Float.BYTES);
                GL20.glEnableVertexAttribArray(4);
                GL33.glVertexAttribDivisor(4, 1);
            }
        });
        rectMesh.setup();

        mesh = rectMesh;

        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        rectMesh.setRect((resolution.getScaledWidth() - SIDE_LENGTH) / 2f, (resolution.getScaledHeight() - SIDE_LENGTH) / 2f, SIDE_LENGTH, SIDE_LENGTH).update();

        init = true;
        return this;
    }
}
