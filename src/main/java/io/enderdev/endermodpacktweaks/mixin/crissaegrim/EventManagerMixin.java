package io.enderdev.endermodpacktweaks.mixin.crissaegrim;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import epicsquid.crissaegrim.EventManager;
import epicsquid.crissaegrim.RegistryManager;
import epicsquid.mysticallib.util.Util;
import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EventManager.class, remap = false)
public class EventManagerMixin {
    @WrapMethod(method = "onDropXP")
    public void onDropXP(LivingExperienceDropEvent event, Operation<Void> original) {
        EntityEntry entityEntry = EntityRegistry.getEntry(event.getEntity().getClass());
        ResourceLocation entityName = entityEntry != null ? entityEntry.getRegistryName() : null;

        if (entityName == null || EMTConfig.CRISSAEGRIM.disableRandomDrop) {
            return;
        }

        if (event.getAttackingPlayer() != null
                && event.getAttackingPlayer().world != null
                && event.getAttackingPlayer().getGameProfile() != null
                && !event.getAttackingPlayer().world.isRemote
                && !(event.getAttackingPlayer() instanceof FakePlayer)
                && entityName.equals(new ResourceLocation(EMTConfig.CRISSAEGRIM.specialMob))
                && Util.rand.nextFloat() < EMTConfig.CRISSAEGRIM.specialMobChance) {
            event.getAttackingPlayer().world.spawnEntity(new EntityItem(event.getEntity().world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, new ItemStack(RegistryManager.crissaegrim, 1)));
        }
    }
}
