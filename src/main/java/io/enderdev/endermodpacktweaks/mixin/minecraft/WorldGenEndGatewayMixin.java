package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldGenEndGateway.class)
public class WorldGenEndGatewayMixin {
    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;AIR:Lnet/minecraft/block/Block;"))
    private Block generateAir() {
        return Block.getBlockFromName(EMTConfig.MINECRAFT.END_GATEWAY.air);
    }

    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;BEDROCK:Lnet/minecraft/block/Block;"))
    private Block generateBedrock() {
        return Block.getBlockFromName(EMTConfig.MINECRAFT.END_GATEWAY.bedrock);
    }
}
