package io.enderdev.endermodpacktweaks.mixin.darkutils;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.darkhax.darkutils.features.vector.BlockVectorPlate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = BlockVectorPlate.class, remap = false)
public class BlockVectorPlateMixin {
    @Shadow
    @Final
    public static AxisAlignedBB BOUNDS;

    @Inject(method = "onEntityCollision", at = @At("HEAD"), remap = true, cancellable = true)
    public void onEntityCollisionMixin(World world, BlockPos pos, IBlockState state, Entity entity, CallbackInfo ci) {
        if (!(entity instanceof EntityItem) && EMTConfig.DARK_UTILS.vectorPlateItemOnly) {
            ci.cancel();
        }
    }

    @Inject(method = "onEntityCollision", at = @At("RETURN"), remap = true)
    public void onEntityCollisionMixin(World world, BlockPos pos, IBlockState state, Entity entity, CallbackInfo ci, @Local EnumFacing direction) {
        if (!world.isRemote && entity instanceof EntityItem) {
            ItemStack stack = ((EntityItem) entity).getItem();
            List<EnumFacing> faces = new ArrayList<>(2);
            if (EMTConfig.DARK_UTILS.vectorPlatesInsertFront) {
                faces.add(direction);
            }
            if (EMTConfig.DARK_UTILS.vectorPlatesInsertBelow) {
                faces.add(EnumFacing.DOWN);
            }
            for (EnumFacing facing : faces) {
                TileEntity tile = world.getTileEntity(pos.offset(facing));
                if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite())) {
                    IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
                    if (handler != null) {
                        ItemStack remaining = ItemHandlerHelper.insertItemStacked(handler, stack, false);
                        ((EntityItem) entity).setItem(remaining);
                        if (remaining.isEmpty()) {
                            entity.setDead();
                        }
                    }
                }
            }
        }
    }

    @ModifyReturnValue(method = "getCollisionBoundingBox", at = @At("RETURN"), remap = true)
    public AxisAlignedBB getCollisionBoundingBoxMixin(AxisAlignedBB original) {
        return EMTConfig.DARK_UTILS.overrideVectorPlateCollisionBox ? BOUNDS : original;
    }
}
