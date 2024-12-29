package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.ConfigMain;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.WorldServer;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DragonFightManager.class)
public abstract class DragonFightManagerMixin {

    @Shadow
    protected abstract void generatePortal(boolean active);

    @Final
    @Shadow
    private WorldServer world;

    @Unique
    private boolean enderModpackTweaks$firstTime = true;

    @Shadow private boolean previouslyKilled;

    @Inject(method = "hasDragonBeenKilled", at = @At("HEAD"), cancellable = true)
    private void hasDragonBeenKilled(CallbackInfoReturnable<Boolean> cir) {
        if (!this.enderModpackTweaks$firstTime) {
            return;
        }
        enderModpackTweaks$firstTime = false;
        EnderModpackTweaks.LOGGER.info("Killing the dragon for the first time.");
        this.generatePortal(ConfigMain.DRAGON.createPortal);
        if (ConfigMain.DRAGON.dropEgg) {
            this.world.setBlockState(this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.getDefaultState());
        }
        cir.setReturnValue(true);
    }

    @Inject(method = "processDragonDeath", at = @At("RETURN"))
    private void processDragonDeath(EntityDragon dragon, CallbackInfo ci) {
        if (ConfigMain.DRAGON.multipleEgg && this.previouslyKilled) {
            this.world.setBlockState(this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.getDefaultState());
        }
    }
}
