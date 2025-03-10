package io.enderdev.endermodpacktweaks.mixin.enderstorage;

import codechicken.enderstorage.tile.TileEnderTank;
import codechicken.lib.packet.PacketCustom;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TileEnderTank.class, remap = false)
public class TileEnderTankMixin {
    @WrapMethod(method = "sync")
    public void sync(PacketCustom packet, Operation<Void> original) {
        TileEnderTank tank = (TileEnderTank) (Object) this;
        if (packet.getType() == 5) {
            int x;
            try {
                x = packet.readInt();
            } catch (Exception e) {
                x = -1;
            }
            FluidStack fluidStack;
            try {
                fluidStack = packet.readFluidStack();
            } catch (IndexOutOfBoundsException e) {
                //TODO: YES, THIS BREAKS THE TANKS, BUT IT'S A TEMPORARY FIX
                return;
            }
            tank.liquid_state.sync(fluidStack, x, x);
        } else if (packet.getType() == 6) {
            tank.pressure_state.a_pressure = packet.readBoolean();
        }
    }
}
