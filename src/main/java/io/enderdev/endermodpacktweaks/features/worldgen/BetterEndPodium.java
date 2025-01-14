package io.enderdev.endermodpacktweaks.features.worldgen;

import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BetterEndPodium extends WorldGen {
    private final boolean activePortal;
    private static final ResourceLocation portal_on = new ResourceLocation(EMTConfig.MINECRAFT.END_PODIUM.activePortalStructure);
    private static final ResourceLocation portal_off = new ResourceLocation(EMTConfig.MINECRAFT.END_PODIUM.portalStructure);

    public BetterEndPodium(boolean activePortalIn) {
        this.activePortal = activePortalIn;
    }

    @Override
    public boolean generate(@NotNull World worldIn, @NotNull Random rand, @NotNull BlockPos position) {
        return generateStructure(worldIn, position, activePortal ? portal_on : portal_off, false, -1);
    }
}
