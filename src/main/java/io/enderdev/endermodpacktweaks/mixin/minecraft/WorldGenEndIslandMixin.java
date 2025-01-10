package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldGenEndIsland.class)
public class WorldGenEndIslandMixin {
    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Blocks;END_STONE:Lnet/minecraft/block/Block;"))
    private Block replaceEndStone(@Local Block block) {
        Block block1 = Block.getBlockFromName(EMTConfig.MINECRAFT.END_ISLAND.endStone);
        return block1 == null || Loader.isModLoaded("nether_api")? Blocks.END_STONE : block1;
    }

    @ModifyVariable(method = "generate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", ordinal = 0))
    private int modifyRandomInt(int original) {
        return original + EMTConfig.MINECRAFT.END_ISLAND.islandSize;
    }
}
