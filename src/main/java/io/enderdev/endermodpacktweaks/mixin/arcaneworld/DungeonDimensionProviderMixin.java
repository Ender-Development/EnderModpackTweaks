package io.enderdev.endermodpacktweaks.mixin.arcaneworld;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import party.lemons.arcaneworld.gen.dungeon.dimension.DungeonDimensionProvider;

import java.util.Objects;

@Mixin(value = DungeonDimensionProvider.class, remap = false)
public class DungeonDimensionProviderMixin {
    @Unique
    private BiomeProvider emt$BiomeProvider;

    @WrapMethod(method = "getBiomeProvider", remap = true)
    private BiomeProvider wrapBiomeProvider(Operation<BiomeProvider> original) {
        if (emt$BiomeProvider == null) {
            for (Biome biome : BiomeDictionary.getBiomes(BiomeDictionary.Type.VOID)) {
                if (Objects.requireNonNull(biome.getRegistryName()).toString().equals("arcaneworld:arcane_dungeon")) {
                    emt$BiomeProvider = new BiomeProviderSingle(biome);
                }
            }
        }

        return emt$BiomeProvider;
    }
}
