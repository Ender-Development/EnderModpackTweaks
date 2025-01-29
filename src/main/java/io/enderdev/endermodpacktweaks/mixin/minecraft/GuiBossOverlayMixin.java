package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.features.bossbar.ImprovedBossBarRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.world.BossInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GuiBossOverlay.class)
public class GuiBossOverlayMixin {
    @Shadow
    @Final
    private Minecraft client;

    @Unique
    public ImprovedBossBarRenderer enderModpackTweaks$improvedBossBarRenderer;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initMixin(Minecraft clientIn, CallbackInfo ci) {
        boolean check = client.getResourcePackRepository().getRepositoryEntriesAll().stream().anyMatch(entry -> entry.getResourcePackName().contains("Enhanced Boss Bars"));
        enderModpackTweaks$improvedBossBarRenderer = new ImprovedBossBarRenderer(client, check);
    }

    @WrapOperation(method = "renderBossHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int renderBossHealthWrap(FontRenderer instance, String text, float x, float y, int color, Operation<Integer> original) {
        return enderModpackTweaks$improvedBossBarRenderer.isResourcePackEnabled() ? 0 : original.call(instance, text, x, y, color);
    }

    @ModifyArgs(method = "renderBossHealth", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;bossBarRenderPre(Lnet/minecraft/client/gui/ScaledResolution;Lnet/minecraft/client/gui/BossInfoClient;III)Lnet/minecraftforge/client/event/RenderGameOverlayEvent$BossInfo;", remap = false))
    private void renderBossHealthModifyArgs(Args args) {
        if (enderModpackTweaks$improvedBossBarRenderer.isResourcePackEnabled()) {
            int overlayHeight = enderModpackTweaks$improvedBossBarRenderer.getOverlayHeight(args.get(1));
            args.set(4, overlayHeight == 0 ? args.get(4) : overlayHeight);
        }
    }

    @WrapMethod(method = "render")
    private void renderWrap(int x, int y, BossInfo info, Operation<Void> original) {
        if (enderModpackTweaks$improvedBossBarRenderer.isResourcePackEnabled() && enderModpackTweaks$improvedBossBarRenderer.render(x, y, info)) {
            return;
        }
        original.call(x, y, info);
    }
}
