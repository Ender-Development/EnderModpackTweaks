package io.enderdev.endermodpacktweaks.mixin.minecraftforge;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.features.startuptimer.HistroyHandler;
import io.enderdev.endermodpacktweaks.features.startuptimer.TimeHistory;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.client.SplashProgress;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@SuppressWarnings("all")
@Mixin(targets = { "net/minecraftforge/fml/client/SplashProgress$2" })
public abstract class SplashProgressMixin {
    @Shadow
    @Final
    private int textHeight2;

    @Shadow protected abstract void setColor(int color);

    @Unique
    private static FontRenderer enderModpackTweaks$fontRenderer = null;

    @Unique
    private static int enderModpackTweaks$fontColor = 0;

    @Inject(method = "run", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glDisable(I)V", ordinal = 1, remap = false, shift = At.Shift.AFTER), remap = false)
    private void injectStartupTime(CallbackInfo ci) {
        if (enderModpackTweaks$fontRenderer == null) {
            try {
                Field f = SplashProgress.class.getDeclaredField("fontRenderer");
                f.setAccessible(true);
                enderModpackTweaks$fontRenderer = (FontRenderer) f.get(null);
                f = SplashProgress.class.getDeclaredField("fontColor");
                f.setAccessible(true);
                enderModpackTweaks$fontColor = f.getInt(null);
            } catch (ReflectiveOperationException e) {
                EnderModpackTweaks.LOGGER.error(String.valueOf(e));
                return;
            }
        }
        GL11.glPushMatrix();
        setColor(enderModpackTweaks$fontColor);
        GL11.glTranslatef(320 - (float) Display.getWidth() / 2 + 4, 240 + (float) Display.getHeight() / 2 - textHeight2, 0);
        GL11.glScalef(2, 2, 1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        String renderString = enderModpackTweaks$getString();
        enderModpackTweaks$fontRenderer.drawString(renderString, 0, 0, 0x000000);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    /**
     * Get formatted timer + estimate string
     */
    @Unique
    private String enderModpackTweaks$getString(){
        long startupTime = System.currentTimeMillis() - HistroyHandler.startupInstant;
        if(HistroyHandler.done_time > 0) startupTime = HistroyHandler.done_time;

        long minutes = (startupTime / 1000) / 60;
        long seconds = (startupTime / 1000) % 60;

        String str = String.format("Startup: %sm%ss", minutes, seconds);

        if(TimeHistory.getEstimateTime() > 0){
            long ex_minutes = (TimeHistory.getEstimateTime() / 1000) / 60;
            long ex_seconds = (TimeHistory.getEstimateTime() / 1000) % 60;

            str += String.format(" / %sm%ss", ex_minutes, ex_seconds);
        }

        return str;
    }
}