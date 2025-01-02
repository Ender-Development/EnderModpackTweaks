package io.enderdev.endermodpacktweaks.mixin.rustic;

import io.enderdev.endermodpacktweaks.config.EMTConfigMods;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rustic.common.world.WorldGeneratorRustic;

import java.util.Random;

@Mixin(value = WorldGeneratorRustic.class, remap = false)
public class WorldGeneratorRusticMixin {
    @Unique
    World enderModpackTweaks$world;

    @Inject(method = "generate", at = @At("HEAD"))
    private void setWorld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, CallbackInfo ci) {
        enderModpackTweaks$world = world;
    }

    @Redirect(method = "generate", at = @At(value = "FIELD", target = "Lnet/minecraft/world/WorldType;FLAT:Lnet/minecraft/world/WorldType;"))
    private WorldType fixFlatWorldType() {
        return EMTConfigMods.RUSTIC.enableWorldGenInFlat && enderModpackTweaks$world.getWorldType() == WorldType.FLAT ? WorldType.DEFAULT : enderModpackTweaks$world.getWorldType();
    }
}
