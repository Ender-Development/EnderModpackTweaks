package io.enderdev.endermodpacktweaks.mixin.minecraftforge;

import com.google.common.collect.ListMultimap;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EntityRegistry.class, remap = false)
public interface EntityRegistryAccessor {
    @Accessor("entityRegistrations")
    ListMultimap<ModContainer, EntityRegistry.EntityRegistration> getEntityRegistrations();
}
