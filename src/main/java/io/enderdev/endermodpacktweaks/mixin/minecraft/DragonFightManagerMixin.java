package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
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

import java.util.List;

@Mixin(DragonFightManager.class)
public abstract class DragonFightManagerMixin {

    @Shadow
    public abstract void generatePortal(boolean active);

    @Shadow
    protected abstract void spawnNewGateway();

    @Shadow
    public abstract void generateGateway(BlockPos pos);

    @Final
    @Shadow
    private WorldServer world;

    @Unique
    private boolean enderModpackTweaks$firstTime = true;

    @Shadow
    private boolean previouslyKilled;

    @Final
    @Shadow
    private List<Integer> gateways;

    @WrapMethod(method = "hasDragonBeenKilled")
    private boolean hasDragonBeenKilled(Operation<Boolean> original) {
        if (!this.enderModpackTweaks$firstTime || !EMTConfig.MINECRAFT.DRAGON.killDragon) {
            return false;
        }
        enderModpackTweaks$firstTime = false;
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
            this.spawnNewGateway();
        }
        return true;
    }

    @WrapMethod(method = "spawnNewGateway")
    private void spawnNewGateway(Operation<Void> original) {
        if (!this.gateways.isEmpty() && EMTConfig.MINECRAFT.END_GATEWAY.enable) {
            double radius = EMTConfig.MINECRAFT.END_GATEWAY.gatewayDistance;
            int height = EMTConfig.MINECRAFT.END_GATEWAY.gatewayHeight;
            int i = this.gateways.remove(this.gateways.size() - 1);
            int j = (int) (radius * Math.cos(2.0D * (-Math.PI + 0.15707963267948966D * (double) i)));
            int k = (int) (radius * Math.sin(2.0D * (-Math.PI + 0.15707963267948966D * (double) i)));
            this.generateGateway(new BlockPos(j, height, k));
        } else {
            original.call();
        }
    }

    @Inject(method = "processDragonDeath", at = @At("RETURN"))
    private void processDragonDeath(EntityDragon dragon, CallbackInfo ci) {
        if (EMTConfig.MINECRAFT.DRAGON.multipleEgg && this.previouslyKilled) {
            this.world.setBlockState(this.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.getDefaultState());
        }
    }
}
