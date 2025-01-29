package io.enderdev.endermodpacktweaks.mixin.mysticallib;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import epicsquid.mysticallib.network.PacketHandler;
import io.enderdev.endermodpacktweaks.patches.mysticallib.MessageEffect;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = PacketHandler.class, remap = false)
public class PacketHandlerMixin {
    @Shadow
    public static SimpleNetworkWrapper INSTANCE;

    @WrapMethod(method = "registerMessages")
    private static void registerMessages(Operation<Void> original) {
        INSTANCE.registerMessage(MessageEffect.MessageHolder.class, MessageEffect.class, 1, Side.CLIENT);
    }
}
