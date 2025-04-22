package io.enderdev.endermodpacktweaks.mixin.arcaneworld;

import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import party.lemons.arcaneworld.block.tilentity.TileEntityRitualTable;

@Mixin(value = TileEntityRitualTable.class, remap = false)
public abstract class TileEntityRitualTableMixin {
    @Shadow
    public abstract TileEntityRitualTable.RitualState getState();

    @Shadow
    private TileEntityRitualTable.RitualState state;

    @Inject(method = "writeToNBT", at = @At("HEAD"), remap = true)
    private void injectWriteNBT(NBTTagCompound compound, CallbackInfoReturnable<NBTTagCompound> cir) {
        compound.setString("state", getState().toString());
    }

    @Inject(method = "readFromNBT", at = @At("HEAD"), remap = true)
    private void injectReadNBT(NBTTagCompound compound, CallbackInfo ci) {
        state = TileEntityRitualTable.RitualState.valueOf(compound.getString("state"));
    }
}
