package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraft.block.Block;
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

    @Shadow
    private boolean previouslyKilled;

    @Shadow
    protected abstract void spawnNewGateway();

    @Inject(method = "hasDragonBeenKilled", at = @At("HEAD"), cancellable = true)
    private void hasDragonBeenKilled(CallbackInfoReturnable<Boolean> cir) {
        EnderModpackTweaks.LOGGER.debug("Checking if the dragon has been killed before.");
        if (!this.enderModpackTweaks$firstTime|| !EMTConfig.MINECRAFT.DRAGON.killDragon) {
            return;
        }
        enderModpackTweaks$firstTime = false;
        EnderModpackTweaks.LOGGER.info("Killing the dragon for the first time.");
        this.generatePortal(EMTConfig.MINECRAFT.DRAGON.createPortal);
        if (EMTConfig.MINECRAFT.DRAGON.dropEgg) {
            String eggBlock = EMTConfig.MINECRAFT.DRAGON.eggBlock;
            Block block = Block.getBlockFromName(eggBlock) == null ? Blocks.DRAGON_EGG : Block.getBlockFromName(eggBlock);
            this.world.setBlockState(this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION), block.getDefaultState());
            this.world.notifyNeighborsOfStateChange(world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION), block, true);
        }
        if (EMTConfig.MINECRAFT.DRAGON.createGateway) {
            EnderModpackTweaks.LOGGER.debug("Creating gateway for the first time.");
            spawnNewGateway();
        }
        cir.setReturnValue(true);
    }

    @Inject(method = "processDragonDeath", at = @At("RETURN"))
    private void processDragonDeath(EntityDragon dragon, CallbackInfo ci) {
        if (EMTConfig.MINECRAFT.DRAGON.multipleEgg && this.previouslyKilled) {
            this.world.setBlockState(this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.getDefaultState());
        }
    }

    @Inject(method = "spawnNewGateway", at = @At("HEAD"), cancellable = true)
    private void spawnNewGateway(CallbackInfo ci) {
        if (EMTConfig.MINECRAFT.DRAGON.disableGateway) {
            ci.cancel();
        }
    }

    @Inject(method = "generatePortal", at = @At("HEAD"), cancellable = true)
    private void generatePortal(boolean active, CallbackInfo ci) {
        if (EMTConfig.MINECRAFT.DRAGON.disablePortal) {
            ci.cancel();
        }
    }
}
