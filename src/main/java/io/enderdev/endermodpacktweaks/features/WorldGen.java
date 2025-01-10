package io.enderdev.endermodpacktweaks.features;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class WorldGen {

    PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(true).setChunk((ChunkPos) null).setReplacedBlock((Block) null).setIgnoreStructureBlock(false);

    public abstract boolean generate(@NotNull World worldIn, @NotNull Random rand, @NotNull BlockPos position);

    boolean generateStructure(@NotNull World worldIn, @NotNull BlockPos position, @NotNull ResourceLocation structure, boolean centerY, int offsetY) {
        Template template = getTemplate(worldIn, structure);
        if (template == null) {
            EnderModpackTweaks.LOGGER.error("Structure {} not found!", structure);
            return false;
        } else {
            BlockPos templateCenter = getCenter(template);
            BlockPos location = position.add(-templateCenter.getX(), (centerY ? -templateCenter.getY() : 0) + offsetY, -templateCenter.getZ());
            template.addBlocksToWorldChunk(worldIn, location, placementsettings);
            return true;
        }
    }

    boolean generateStructure(@NotNull World worldIn, @NotNull BlockPos position, @NotNull ResourceLocation structure) {
        return generateStructure(worldIn, position, structure, true, 0);
    }

    private BlockPos getCenter(@NotNull Template template) {
        int centerY = Math.floorDiv(template.getSize().getY(), 2);
        int centerX = Math.floorDiv(template.getSize().getX(), 2);
        int centerZ = Math.floorDiv(template.getSize().getZ(), 2);
        return new BlockPos(centerX, centerY, centerZ);
    }

    private Template getTemplate(@NotNull World worldIn, @NotNull ResourceLocation structure) {
        MinecraftServer minecraftserver = worldIn.getMinecraftServer();
        WorldServer worldserver = (WorldServer) worldIn;
        TemplateManager templatemanager = worldserver.getStructureTemplateManager();
        return templatemanager.get(minecraftserver, structure);
    }
}
