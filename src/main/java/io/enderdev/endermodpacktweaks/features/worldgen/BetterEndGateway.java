package io.enderdev.endermodpacktweaks.features.worldgen;

import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BetterEndGateway extends WorldGen {
    private static final ResourceLocation gateway = new ResourceLocation(EMTConfig.MINECRAFT.END_GATEWAY.gatewayStructure);

    @Override
    public boolean generate(@NotNull World worldIn, @NotNull Random rand, @NotNull BlockPos position) {
        return generateStructure(worldIn, position, gateway);
    }
}
