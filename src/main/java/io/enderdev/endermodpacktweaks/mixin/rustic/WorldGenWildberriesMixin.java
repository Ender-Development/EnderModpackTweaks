package io.enderdev.endermodpacktweaks.mixin.rustic;

import io.enderdev.endermodpacktweaks.config.EMTConfigMods;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.*;
import rustic.common.Config;
import rustic.common.world.WorldGenWildberries;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(value = WorldGenWildberries.class, remap = false)
public abstract class WorldGenWildberriesMixin extends WorldGenerator {

    @Shadow
    public static List<BiomeDictionary.Type> biomeBlacklist;

    @Shadow
    protected abstract boolean generateBush(World world, Random rand, BlockPos pos);

    /**
     * @author _MasterEnderman_
     * @reason Override Rustic's biomes blacklist
     */
    @Overwrite
    public boolean generate(World world, Random rand, BlockPos pos) {
        Biome biome = world.getBiome(pos);
        List<BiomeDictionary.Type> listBiomesBlacklist = Arrays.stream(EMTConfigMods.RUSTIC.listBiomesBlacklist.clone())
                .map(BiomeDictionary.Type::getType).collect(Collectors.toList());

        if (EMTConfigMods.RUSTIC.overrideBerryBushBiomeBlacklist) {
            if (listBiomesBlacklist.stream().anyMatch(type -> BiomeDictionary.hasType(biome, type))) {
                return false;
            }
        } else {
            if (biomeBlacklist.stream().anyMatch(type -> BiomeDictionary.hasType(biome, type))) {
                return false;
            }
        }

        boolean ret = false;
        for (int i = 0; i < Config.MAX_WILDBERRY_ATTEMPTS; i++) {
            int x = pos.getX() + rand.nextInt(EMTConfigMods.RUSTIC.maxWildberrySpread) - rand.nextInt(EMTConfigMods.RUSTIC.maxWildberrySpread);
            int z = pos.getZ() + rand.nextInt(EMTConfigMods.RUSTIC.maxWildberrySpread) - rand.nextInt(EMTConfigMods.RUSTIC.maxWildberrySpread);
            BlockPos genPos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

            if (generateBush(world, rand, genPos)) {
                ret = true;
            }
        }

        return ret;
    }
}
