package io.enderdev.endermodpacktweaks.render.mesh2d;

import io.enderdev.endermodpacktweaks.render.Mesh;
import io.enderdev.endermodpacktweaks.render.ScaledRes2NdcUtils;

// under minecraft's scaled resolution coordinate system
public class RectMesh extends Mesh
{
    private float x;
    private float y;
    private float width;
    private float height;

    public RectMesh()
    {
        super(new float[8 * 4], new int[6]);
    }

    public RectMesh setRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public void update()
    {
        float[] vertices = new float[]
        {
            // positions                                                                          // texcoords  // normals
            ScaledRes2NdcUtils.toNdcX(x),          ScaledRes2NdcUtils.toNdcY(y),           0.0f,  0.0f, 0.0f,   0.0f, 0.0f, 1.0f,  // bottom-left
            ScaledRes2NdcUtils.toNdcX(x + width),  ScaledRes2NdcUtils.toNdcY(y),           0.0f,  1.0f, 0.0f,   0.0f, 0.0f, 1.0f,  // bottom-right
            ScaledRes2NdcUtils.toNdcX(x + width),  ScaledRes2NdcUtils.toNdcY(y + height),  0.0f,  1.0f, 1.0f,   0.0f, 0.0f, 1.0f,  // top-right
            ScaledRes2NdcUtils.toNdcX(x),          ScaledRes2NdcUtils.toNdcY(y + height),  0.0f,  0.0f, 1.0f,   0.0f, 0.0f, 1.0f   // top-left
        };
        int[] indices = new int[]
        {
            0, 2, 1,
            0, 3, 2
        };

        updateVerticesByBufferSubData(vertices);
        updateIndicesByBufferSubData(indices);
    }
}
