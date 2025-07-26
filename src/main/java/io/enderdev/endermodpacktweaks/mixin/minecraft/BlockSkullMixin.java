package io.enderdev.endermodpacktweaks.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraft.block.BlockSkull;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockSkull.class)
public class BlockSkullMixin {
    @WrapMethod(method = "checkWitherSpawn")
    private void emt$checkWitherSpawn(World worldIn, BlockPos pos, TileEntitySkull te, Operation<Void> original) {
        if (CfgMinecraft.WITHER.disableBuilding) return;
        original.call(worldIn, pos, te);
    }
}
