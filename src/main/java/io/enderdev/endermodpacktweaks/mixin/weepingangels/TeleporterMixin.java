package io.enderdev.endermodpacktweaks.mixin.weepingangels;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.swirtzly.angels.config.WAConfig;
import me.swirtzly.angels.utils.Teleporter;
import net.minecraft.entity.Entity;
import net.minecraft.world.DimensionType;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Arrays;
import java.util.Random;

@Mixin(value = Teleporter.class, remap = false)
public class TeleporterMixin {
    @WrapMethod(method = "getRandomDimension")
    private static DimensionType allowBlacklistingOverworld(Entity angel, Random rand, Operation<DimensionType> original) {
        if (WAConfig.teleport.angelDimTeleport) {
            int attempts = 0;
            while (attempts < DimensionType.class.getEnumConstants().length) {
                DimensionType dim = Teleporter.randomEnum(DimensionType.class, rand);
                if (dim != null && Arrays.stream(WAConfig.teleport.notAllowedDimensions).noneMatch(blacklistedDimensionId -> blacklistedDimensionId == dim.getId())) {
                    return dim;
                }
                attempts++;
            }
        }
        return angel.world.provider.getDimensionType();
    }
}
