package io.enderdev.endermodpacktweaks.mixin.crissaegrim;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import epicsquid.crissaegrim.RegistryManager;
import epicsquid.crissaegrim.item.ItemCrissaegrim;
import epicsquid.mysticallib.network.PacketHandler;
import epicsquid.mysticallib.util.Util;
import io.enderdev.endermodpacktweaks.patches.mysticallib.ColorHandler;
import io.enderdev.endermodpacktweaks.patches.mysticallib.Effect;
import io.enderdev.endermodpacktweaks.patches.mysticallib.EffectCut;
import io.enderdev.endermodpacktweaks.patches.mysticallib.MessageEffect;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemCrissaegrim.class, remap = false)
public class ItemCrissaegrimMixin {
    @WrapMethod(method = "onUpdate")
    private void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected, Operation<Void> original) {
        if (selected && world.getCurrentMoonPhaseFactor() > 0.875F && !world.isDaytime()) {
            NBTTagCompound tag = stack.getSubCompound("crissaegrim");
            if (tag != null && tag.hasKey("durability") && !world.isRemote && tag.getInteger("durability") > 0) {
                tag.setInteger("durability", Math.max(0, tag.getInteger("durability") - 5));
                float offX = 0.75F * (float) Math.sin(Math.toRadians(-30.0F - entity.rotationYaw));
                float offZ = 0.75F * (float)Math.cos(Math.toRadians(-30.0F - entity.rotationYaw));
                Effect cut = (new EffectCut(world.provider.getDimension())).setSlashProperties(0.0F, 0.0F, 90.0F).setLife(20).setColor(ColorHandler.getColor()).setAdditive(true).setPosition(entity.posX + (double)(0.5F * (Util.rand.nextFloat() - 0.5F)) + (double)offX, entity.posY + (double)(0.5F * (Util.rand.nextFloat() - 0.5F)) + (double)(entity.height / 2.0F), entity.posZ + (double)(0.5F * (Util.rand.nextFloat() - 0.5F)) + (double)offZ);
                PacketHandler.INSTANCE.sendToAll(new MessageEffect(RegistryManager.FX_CUT, cut.write()));
            }
        }
    }
}
