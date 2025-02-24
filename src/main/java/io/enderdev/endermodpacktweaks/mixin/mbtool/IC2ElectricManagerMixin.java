package io.enderdev.endermodpacktweaks.mixin.mbtool;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import igentuman.mbtool.ModConfig;
import igentuman.mbtool.common.item.IC2ElectricManager;
import igentuman.mbtool.common.item.ItemMultiBuilder;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = IC2ElectricManager.class, remap = false)
public class IC2ElectricManagerMixin {
    @Unique
    private double enderModpackTweaks$maxTransfer = ModConfig.general.mbtool_energy_capacity * 0.01;

    @WrapMethod(method = "charge")
    public double chargeWrap(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate, Operation<Double> original) {
        if (itemStack.getItem() instanceof ItemMultiBuilder) {
            ItemMultiBuilder item = (ItemMultiBuilder) itemStack.getItem();
            if (amount > item.getMaxCharge(itemStack)) {
                amount = item.getMaxCharge(itemStack);
            }

            float energy = (float) amount * 0.25F;
            float rejectedElectricity = Math.max(item.getElectricityStored(itemStack) + energy - (float) item.getMaxElectricityStored(itemStack), 0.0F);
            float energyToReceive = energy - rejectedElectricity;
            if (!ignoreTransferLimit && (double) energyToReceive > enderModpackTweaks$maxTransfer) {
                energyToReceive = (float) enderModpackTweaks$maxTransfer;
                ;
            }

            if (!simulate) {
                item.setElectricity(itemStack, item.getElectricityStored(itemStack) + energyToReceive);
            }

            return energyToReceive / 0.25F;
        } else {
            return 0.0F;
        }
    }

    @WrapMethod(method = "discharge")
    public double discharge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate, Operation<Double> original) {
        if (itemStack.getItem() instanceof ItemMultiBuilder) {
            ItemMultiBuilder item = (ItemMultiBuilder) itemStack.getItem();
            float energy = (float) amount / 4.0F;
            float energyToTransfer = Math.min(item.getElectricityStored(itemStack), energy);
            if (!ignoreTransferLimit) {
                energyToTransfer = (float) Math.min(energyToTransfer, enderModpackTweaks$maxTransfer);
            }

            if (!simulate) {
                item.setElectricity(itemStack, item.getElectricityStored(itemStack) - energyToTransfer);
            }

            return energyToTransfer * 4.0F;
        } else {
            return 0.0F;
        }
    }
}
