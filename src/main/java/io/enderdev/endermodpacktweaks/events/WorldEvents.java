package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.EMTConfig;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onWorldLoad(WorldEvent.Load event) {
        if (!EMTConfig.MINECRAFT.WORLD.enable) {
            return;
        }
        World world = event.getWorld();
        EnumDifficulty currentDifficulty = world.getDifficulty();
        EnumDifficulty forcedDifficulty = EnumDifficulty.byId(EMTConfig.MINECRAFT.WORLD.DIFFICULTY.difficulty.getValue());
        if (currentDifficulty != forcedDifficulty && EMTConfig.MINECRAFT.WORLD.DIFFICULTY.force) {
            world.getWorldInfo().setDifficulty(forcedDifficulty);
        }
        if (!world.getWorldInfo().isDifficultyLocked()) {
            world.getWorldInfo().setDifficultyLocked(EMTConfig.MINECRAFT.WORLD.DIFFICULTY.lock);
        }
        if (EMTConfig.MINECRAFT.WORLD.GAMEMODE.force) {
            world.getWorldInfo().setGameType(GameType.getByID(EMTConfig.MINECRAFT.WORLD.GAMEMODE.gamemode.getValue()));
        }
        world.getWorldInfo().setAllowCommands(EMTConfig.MINECRAFT.WORLD.GAMEMODE.cheats);
        if (!world.getWorldInfo().isHardcoreModeEnabled() && (world.getWorldInfo().getGameType() == GameType.SURVIVAL || world.getWorldInfo().getGameType() == GameType.ADVENTURE)) {
            world.getWorldInfo().setHardcore(EMTConfig.MINECRAFT.WORLD.GAMEMODE.hardcore);
        }
    }
}
