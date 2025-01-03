package io.enderdev.endermodpacktweaks.mixin.pyrotech;

import com.codetaylor.mc.pyrotech.modules.core.block.BlockRock;
import com.codetaylor.mc.pyrotech.modules.worldgen.feature.WorldGenRocks;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import io.enderdev.endermodpacktweaks.config.EMTConfigMods;
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
            EMTConfigMods.PYROTECH.rock_stone,
            EMTConfigMods.PYROTECH.rock_granite,
            EMTConfigMods.PYROTECH.rock_diorite,
            EMTConfigMods.PYROTECH.rock_andesite,
            EMTConfigMods.PYROTECH.rock_dirt,
            EMTConfigMods.PYROTECH.rock_sand,
            EMTConfigMods.PYROTECH.rock_sandstone,
            EMTConfigMods.PYROTECH.rock_wood_chips,
            EMTConfigMods.PYROTECH.rock_limestone,
            EMTConfigMods.PYROTECH.rock_sand_red,
            EMTConfigMods.PYROTECH.rock_sandstone_red,
            EMTConfigMods.PYROTECH.rock_mud
    );

    @Redirect(method = "lambda$generate$0", at = @At(value = "FIELD", target = "Lcom/codetaylor/mc/pyrotech/modules/core/block/BlockRock$EnumType;STONE:Lcom/codetaylor/mc/pyrotech/modules/core/block/BlockRock$EnumType;"))
    private BlockRock.EnumType modifyRockType(@Local(argsOnly = true) Random random) {
        if (!EMTConfigMods.PYROTECH.randomRocks) {
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
