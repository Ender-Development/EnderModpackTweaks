package io.enderdev.endermodpacktweaks;

import io.enderdev.endermodpacktweaks.render.mesh2d.RoundedRectMesh;
import io.enderdev.endermodpacktweaks.render.renderer.MeshRenderer;
import io.enderdev.endermodpacktweaks.render.shader.Shader;
import io.enderdev.endermodpacktweaks.render.shader.ShaderLoadingUtils;
import io.enderdev.endermodpacktweaks.render.shader.ShaderProgram;
import io.enderdev.endermodpacktweaks.utils.EmtRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

public final class Test {
    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        if (meshRenderer == null) {
            RoundedRectMesh mesh = new RoundedRectMesh(3);
            mesh.enableInstancing();
            mesh.setInstanceData(new float[]{0, 0, 0, 0, 1, 0});
            mesh.setInstanceDataUnitSize(3);
            mesh.setInstancePrimCount(2);
            mesh.setup();

            ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
            mesh.setCornerRadius(5f).setRect((resolution.getScaledWidth() - 30) / 2f, (resolution.getScaledHeight() - 30) / 2f, 30, 30).update();

            Shader frag = ShaderLoadingUtils.load("endermodpacktweaks:shaders/mesh2d_frag.glsl", Shader.ShaderType.FRAGMENT);
            Shader vertex = ShaderLoadingUtils.load("endermodpacktweaks:shaders/mesh2d_instancing_vertex.glsl", Shader.ShaderType.VERTEX);
            ShaderProgram program = new ShaderProgram(frag, vertex);
            program.setup();

            meshRenderer = new MeshRenderer(mesh, program);

            Matrix4f matrix4f = new Matrix4f();
            matrix4f.setIdentity();
            matrix4f.scale(new Vector3f(50, 50, 50));
            FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
            matrix4f.store(buffer);
            buffer.flip();

            program.use();
            program.setUniform("inWorld", true);
            program.setUniform("unprojectToWorld", true);
            program.setUniform("transformation", buffer);
            program.setUniform("targetWorldPos", 0, 100, 0);
            program.unuse();

            EnderModpackTweaks.LOGGER.info(program.getSetupDebugReport());
        }

        GlStateManager.enableBlend();

        meshRenderer.getShaderProgram().use();
        meshRenderer.getShaderProgram().setUniform("modelView", EmtRender.getModelViewMatrix());
        meshRenderer.getShaderProgram().setUniform("projection", EmtRender.getProjectionMatrix());
        meshRenderer.getShaderProgram().setUniform("camPos", EmtRender.getCameraPos().x, EmtRender.getCameraPos().y, EmtRender.getCameraPos().z);
        meshRenderer.getShaderProgram().unuse();

        meshRenderer.render();
    }

    static MeshRenderer meshRenderer = null;
}
