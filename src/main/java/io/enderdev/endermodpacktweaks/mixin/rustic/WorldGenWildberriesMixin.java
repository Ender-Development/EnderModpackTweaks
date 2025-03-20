package io.enderdev.endermodpacktweaks.mixin.rustic;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import rustic.common.world.WorldGenWildberries;

import java.util.*;

@Mixin(value = WorldGenWildberries.class, remap = false)
public abstract class WorldGenWildberriesMixin extends WorldGenerator {

    @Shadow
    public static List<BiomeDictionary.Type> biomeBlacklist;

    @WrapMethod(method = "generate", remap = true)
    public boolean generateWrap(World world, Random rand, BlockPos pos, Operation<Boolean> original) {
        if (EMTConfig.RUSTIC.overrideBerryBushBiomeBlacklist) {
            biomeBlacklist.clear();
            Biome biome = world.getBiome(pos);
            if (Arrays.stream(EMTConfig.RUSTIC.listBiomesBlacklist)
                    .map(BiomeDictionary.Type::getType)
                    .filter(Objects::nonNull)
                    .anyMatch(type -> BiomeDictionary.hasType(biome, type))) {
                return false;
            }
        }
        return original.call(world, rand, pos);
    }

    @WrapOperation(method = "generate", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", remap = false), remap = true)
    public int generateWrap(Random rand, int i, Operation<Boolean> original) {
        return rand.nextInt(EMTConfig.RUSTIC.maxWildberrySpread);
    }
}
