package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.features.BetterEndGateway;
import io.enderdev.endermodpacktweaks.features.BetterEndPodium;
import net.minecraft.block.Block;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
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

import java.util.List;
import java.util.Random;

@Mixin(DragonFightManager.class)
public abstract class DragonFightManagerMixin {

    @Shadow
    protected abstract void generatePortal(boolean active);

    @Final
    @Shadow
    private WorldServer world;

    @Final
    @Shadow
    private List<Integer> gateways;

    @Unique
    private boolean enderModpackTweaks$firstTime = true;

    @Shadow
    private boolean previouslyKilled;

    @Shadow
    protected abstract void spawnNewGateway();

    @Shadow
    private BlockPos exitPortalLocation;

    @Inject(method = "hasDragonBeenKilled", at = @At("HEAD"), cancellable = true)
    private void hasDragonBeenKilled(CallbackInfoReturnable<Boolean> cir) {
        EnderModpackTweaks.LOGGER.debug("Checking if the dragon has been killed before.");
        if (!this.enderModpackTweaks$firstTime || !EMTConfig.MINECRAFT.DRAGON.killDragon) {
            return;
        }
        enderModpackTweaks$firstTime = false;
        EnderModpackTweaks.LOGGER.info("Killing the dragon for the first time.");
        EnderModpackTweaks.LOGGER.debug("Generating portal for the first time.");
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

    @WrapMethod(method = "spawnNewGateway")
    private void spawnNewGatewayWithConfig(Operation<Void> original) {
        if (EMTConfig.MINECRAFT.DRAGON.disableGateway) {
            return;
        }
        if (!this.gateways.isEmpty()) {
            int i = (Integer) this.gateways.remove(this.gateways.size() - 1);
            int j = (int) (EMTConfig.MINECRAFT.END_GATEWAY.gatewayDistance * Math.cos(2.0D * (-Math.PI + 0.15707963267948966D * (double) i)));
            int k = (int) (EMTConfig.MINECRAFT.END_GATEWAY.gatewayDistance * Math.sin(2.0D * (-Math.PI + 0.15707963267948966D * (double) i)));
            if (enderModpackTweaks$generateGateway(new BlockPos(j, EMTConfig.MINECRAFT.END_GATEWAY.gatewayHeight, k))) {
                return;
            }
            original.call();
        }
    }

    @WrapMethod(method = "generatePortal")
    private void generatePortalWithConfig(boolean active, Operation<Void> original) {
        if (EMTConfig.MINECRAFT.DRAGON.disablePortal) {
            return;
        }
        if (EMTConfig.MINECRAFT.END_PODIUM.enable && EMTConfig.MINECRAFT.END_PODIUM.replacePortal) {
            BetterEndPodium betterEndPodium = new BetterEndPodium(active);
            if (this.exitPortalLocation == null) {
                this.exitPortalLocation = this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION);
            }
            if (betterEndPodium.generate(world, new Random(), this.exitPortalLocation)) {
                return;
            }
        }
        original.call(active);
    }

    /**
     * @reason Replace the gateway with a custom one if the config is enabled.
     * If it can't find the template, it will generate the default gateway.
     */
    @WrapMethod(method = "generateGateway")
    private void generateGatewayReplace(BlockPos pos, Operation<Void> original) {
        if (EMTConfig.MINECRAFT.DRAGON.disableGateway) {
            return;
        }
        if (EMTConfig.MINECRAFT.END_GATEWAY.enable && EMTConfig.MINECRAFT.END_GATEWAY.replaceGateway) {
            if (enderModpackTweaks$generateGateway(pos)) {
                return;
            }
        }
        original.call(pos);
    }

    @Unique
    private boolean enderModpackTweaks$generateGateway(BlockPos pos) {
        this.world.playEvent(3000, pos, 0);
        return (new BetterEndGateway()).generate(this.world, new Random(), pos);
    }
}
