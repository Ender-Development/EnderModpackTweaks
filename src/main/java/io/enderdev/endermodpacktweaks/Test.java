package io.enderdev.endermodpacktweaks;

import io.enderdev.endermodpacktweaks.render.mesh2d.RoundedRectMesh;
import io.enderdev.endermodpacktweaks.render.renderer.Mesh2DRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class Test {
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        if (mesh2DRenderer == null) {
            RoundedRectMesh mesh = new RoundedRectMesh(3);
            mesh.setup();
            mesh.setCornerRadius(5f).setRect(50, 50, 30, 30).update();
            mesh2DRenderer = new Mesh2DRenderer(mesh);
            mesh2DRenderer.setUniform_InWorld(false);

            EnderModpackTweaks.LOGGER.info(mesh2DRenderer.getShaderProgram().getSetupDebugReport());
        }

        mesh2DRenderer.render();
    }

    static Mesh2DRenderer mesh2DRenderer = null;
}
