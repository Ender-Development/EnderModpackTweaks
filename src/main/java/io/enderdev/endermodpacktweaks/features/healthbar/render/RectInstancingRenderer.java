package io.enderdev.endermodpacktweaks.features.healthbar.render;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.render.Mesh;
import io.enderdev.endermodpacktweaks.render.renderer.MeshRenderer;
import io.enderdev.endermodpacktweaks.render.shader.Shader;
import io.enderdev.endermodpacktweaks.render.shader.ShaderLoadingUtils;
import io.enderdev.endermodpacktweaks.render.shader.ShaderProgram;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.util.Map;

/**
 * Only works for {@link HealthBarInstancingHelper}
 *
 * @see HealthBarInstancingHelper#renderRectHealthBars(Map, float, Vector3f, Vector2f)
 */
public class RectInstancingRenderer extends MeshRenderer {
    public static final float SIDE_LENGTH = 1f;
    public static final int INSTANCE_DATA_UNIT_SIZE = 8;

    private static ShaderProgram sharedShaderProgram = null;

    private final int maxInstance;

    public int getMaxInstance() {
        return maxInstance;
    }

    public int getInstanceDataLength() {
        return maxInstance * INSTANCE_DATA_UNIT_SIZE;
    }

    protected RectInstancingRenderer(int maxInstance) {
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

        float x = -SIDE_LENGTH / 2f;
        float y = -SIDE_LENGTH / 2f;
        float width = SIDE_LENGTH;
        float height = SIDE_LENGTH;

        float[] vertices = new float[] {
                x, y, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
                x + width, y, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
                x + width, y + height, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                x, y + height, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f
        };
        int[] indices = new int[] {
                0, 2, 1,
                0, 3, 2
        };

        mesh = new Mesh(vertices, indices);
        mesh.enableInstancing();
        mesh.setInstanceData(new float[getInstanceDataLength()]);
        mesh.setInstanceDataUnitSize(INSTANCE_DATA_UNIT_SIZE);
        mesh.setInstancePrimCount(0);
        mesh.setCustomInstancingLayout(new Mesh.IManageInstancingLayout() {
            @Override
            public void manage() {
                // pos offset
                GL20.glVertexAttribPointer(3, 3, GL11.GL_FLOAT, false, INSTANCE_DATA_UNIT_SIZE * Float.BYTES, 0);
                GL20.glEnableVertexAttribArray(3);
                GL33.glVertexAttribDivisor(3, 1);

                // hud scale
                GL20.glVertexAttribPointer(4, 2, GL11.GL_FLOAT, false, INSTANCE_DATA_UNIT_SIZE * Float.BYTES, 3 * Float.BYTES);
                GL20.glEnableVertexAttribArray(4);
                GL33.glVertexAttribDivisor(4, 1);

                // hud pos offset
                GL20.glVertexAttribPointer(5, 2, GL11.GL_FLOAT, false, INSTANCE_DATA_UNIT_SIZE * Float.BYTES, 5 * Float.BYTES);
                GL20.glEnableVertexAttribArray(5);
                GL33.glVertexAttribDivisor(5, 1);

                // hud color
                GL20.glVertexAttribPointer(6, 1, GL11.GL_FLOAT, false, INSTANCE_DATA_UNIT_SIZE * Float.BYTES, 7 * Float.BYTES);
                GL20.glEnableVertexAttribArray(6);
                GL33.glVertexAttribDivisor(6, 1);
            }
        });
        mesh.setup();

        init = true;
        return this;
    }
}
