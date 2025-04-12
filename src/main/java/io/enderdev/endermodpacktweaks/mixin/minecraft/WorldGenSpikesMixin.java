package io.enderdev.endermodpacktweaks.mixin.minecraft;

import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldGenSpikes.class)
public class WorldGenSpikesMixin {
    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;OBSIDIAN:Lnet/minecraft/block/Block;"))
    private Block redirectObsidian() {
        Block block = Block.getBlockFromName(CfgMinecraft.OBSIDIAN_SPIKE.obsidian);
        return block == null ? Blocks.OBSIDIAN : block;
    }

    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;AIR:Lnet/minecraft/block/Block;"))
    private Block redirectAir() {
        Block block = Block.getBlockFromName(CfgMinecraft.OBSIDIAN_SPIKE.air);
        return block == null ? Blocks.AIR : block;
    }

    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;IRON_BARS:Lnet/minecraft/block/Block;"))
    private Block redirectIronBars() {
        Block block = Block.getBlockFromName(CfgMinecraft.OBSIDIAN_SPIKE.ironBars);
        return block == null ? Blocks.IRON_BARS : block;
    }
}
