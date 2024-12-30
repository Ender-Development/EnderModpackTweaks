package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.config.EMTConfigMinecraft;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldGenEndPodium.class)
public class WorldGenEndPodiumMixin {
    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;AIR:Lnet/minecraft/block/Block;"))
    private Block generateAir() {
        return Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.air);
    }

    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;BEDROCK:Lnet/minecraft/block/Block;"))
    private Block generateBedrock() {
        return Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.bedrock);
    }

    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;END_STONE:Lnet/minecraft/block/Block;"))
    private Block generateEndStone() {
        return Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.endStone);
    }

    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;TORCH:Lnet/minecraft/block/Block;"))
    private Block generateTorch() {
        return Block.getBlockFromName(EMTConfigMinecraft.END_PODIUM.torch);
    }
}
