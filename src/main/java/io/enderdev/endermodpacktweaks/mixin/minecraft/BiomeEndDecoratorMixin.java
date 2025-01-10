package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(targets = "net.minecraft.world.biome.BiomeEndDecorator$SpikeCacheLoader")
public class BiomeEndDecoratorMixin {
    @Unique
    private int enderModpackTweaks$pillarCount = EMTConfig.MINECRAFT.OBSIDIAN_SPIKE.spikeCount;

    @Unique
    private double enderModpackTweaks$pillarRadius = EMTConfig.MINECRAFT.OBSIDIAN_SPIKE.spikeDistance;

    @Unique
    private int enderModpackTweaks$baseHeight = EMTConfig.MINECRAFT.OBSIDIAN_SPIKE.spikeHeight;

    @Unique
    private int enderModpackTweaks$baseRadius = EMTConfig.MINECRAFT.OBSIDIAN_SPIKE.spikeRadius;

    @Unique
    private boolean enderModpackTweaks$alwaysGuarded = EMTConfig.MINECRAFT.OBSIDIAN_SPIKE.alwaysGuarded;

    @Unique
    private int enderModpackTweaks$guradChance = EMTConfig.MINECRAFT.OBSIDIAN_SPIKE.guardChance;

    @WrapMethod(method = "load(Ljava/lang/Long;)[Lnet/minecraft/world/gen/feature/WorldGenSpikes$EndSpike;")
    private WorldGenSpikes.EndSpike[] load(Long p_load_1_, Operation<WorldGenSpikes.EndSpike[]> original) throws Exception {
        if (EMTConfig.MINECRAFT.OBSIDIAN_SPIKE.enable) {
            Random random = new Random(p_load_1_);
            List<Integer> list = Lists.newArrayList(ContiguousSet.create(Range.closedOpen(0, enderModpackTweaks$pillarCount), DiscreteDomain.integers()));
            Collections.shuffle(list, random);
            WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = new WorldGenSpikes.EndSpike[enderModpackTweaks$pillarCount];

            for (int i = 0; i < enderModpackTweaks$pillarCount; ++i) {
                int j = (int) (enderModpackTweaks$pillarRadius * Math.cos(2.0D * (-Math.PI + (Math.PI / (double) enderModpackTweaks$pillarCount) * (double) i)));
                int k = (int) (enderModpackTweaks$pillarRadius * Math.sin(2.0D * (-Math.PI + (Math.PI / (double) enderModpackTweaks$pillarCount) * (double) i)));
                int l = (Integer) list.get(i);
                // check if betterendforge is loaded and limit the radius from 2 to 5 so it still replaces all spikes
                int i1 = Loader.isModLoaded("betterendforge") ?
                        random.nextInt(4) + 2 :
                        enderModpackTweaks$baseRadius + random.nextInt(l / 3 + 1);
                int j1 = enderModpackTweaks$baseHeight + random.nextInt(l * 3 + 1);

                boolean flag;
                if (enderModpackTweaks$guradChance < 1) {
                    flag = false;
                }
                else {
                    flag = random.nextInt(enderModpackTweaks$guradChance) == 0 || enderModpackTweaks$alwaysGuarded;
                }

                // centerX centerZ radius height guarded
                aworldgenspikes$endspike[i] = new WorldGenSpikes.EndSpike(j, k, i1, j1, flag);
            }

            return aworldgenspikes$endspike;
        } else {
            return original.call(p_load_1_);
        }
    }
}
