package io.enderdev.endermodpacktweaks.mixin.pyrotech;

import com.codetaylor.mc.pyrotech.modules.core.block.BlockRock;
import com.codetaylor.mc.pyrotech.modules.worldgen.feature.WorldGenRocks;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import io.enderdev.endermodpacktweaks.config.CfgTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Random;

@Mixin(value = WorldGenRocks.class, remap = false)
public class WorldGenRocksMixin {

    @Unique
    private List<Integer> enderModpackTweaks$rockTypeWeight = ImmutableList.of(
            CfgTweaks.PYROTECH.stone,
            CfgTweaks.PYROTECH.granite,
            CfgTweaks.PYROTECH.diorite,
            CfgTweaks.PYROTECH.andesite,
            CfgTweaks.PYROTECH.dirt,
            CfgTweaks.PYROTECH.sand,
            CfgTweaks.PYROTECH.sandstone,
            CfgTweaks.PYROTECH.wood_chips,
            CfgTweaks.PYROTECH.limestone,
            CfgTweaks.PYROTECH.sand_red,
            CfgTweaks.PYROTECH.sandstone_red,
            CfgTweaks.PYROTECH.mud
    );

    @Redirect(method = "lambda$generate$0", at = @At(value = "FIELD", target = "Lcom/codetaylor/mc/pyrotech/modules/core/block/BlockRock$EnumType;STONE:Lcom/codetaylor/mc/pyrotech/modules/core/block/BlockRock$EnumType;"))
    private BlockRock.EnumType modifyRockType(@Local(argsOnly = true) Random random) {
        if (!CfgTweaks.PYROTECH.randomRocks) {
            return BlockRock.EnumType.STONE;
        }
        int totalWeight = 0;
        for (int weight : enderModpackTweaks$rockTypeWeight) {
            totalWeight += weight;
        }

        int randomValue = random.nextInt(totalWeight);

        int cumulativeSum = 0;
        for (int i = 0; i < enderModpackTweaks$rockTypeWeight.size(); i++) {
            cumulativeSum += enderModpackTweaks$rockTypeWeight.get(i);
            if (randomValue < cumulativeSum) {
                return BlockRock.EnumType.values()[i];
            }
        }
        return BlockRock.EnumType.STONE;
    }
}
