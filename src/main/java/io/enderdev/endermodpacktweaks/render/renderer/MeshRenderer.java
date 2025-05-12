package io.enderdev.endermodpacktweaks.render.renderer;

import io.enderdev.endermodpacktweaks.render.IRenderer;
import io.enderdev.endermodpacktweaks.render.Mesh;
import io.enderdev.endermodpacktweaks.render.shader.ShaderProgram;

public class MeshRenderer implements IRenderer {
    public Mesh getMesh() {
        return mesh;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    protected Mesh mesh;
    protected ShaderProgram shaderProgram;

    public MeshRenderer(Mesh mesh, ShaderProgram shaderProgram) {
        this.mesh = mesh;
        this.shaderProgram = shaderProgram;
    }

    @Override
    public void render() {
        if (mesh == null || shaderProgram == null) return;
        if (!mesh.getSetup()) return;
        if (!shaderProgram.getSetup()) return;

        shaderProgram.use();
        mesh.render();
        shaderProgram.unuse();
    }
}
