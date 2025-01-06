package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.config.EMTConfigMinecraft;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onWorldLoad(WorldEvent.Load event) {
        if (!EMTConfigMinecraft.WORLD.enable) {
            return;
        }
        World world = event.getWorld();
        EnumDifficulty currentDifficulty = world.getDifficulty();
        EnumDifficulty forcedDifficulty = EnumDifficulty.byId(EMTConfigMinecraft.WORLD.DIFFICULTY.difficulty);
        if (currentDifficulty != forcedDifficulty && EMTConfigMinecraft.WORLD.DIFFICULTY.force) {
            world.getWorldInfo().setDifficulty(forcedDifficulty);
        }
        if (!world.getWorldInfo().isDifficultyLocked()) {
            world.getWorldInfo().setDifficultyLocked(EMTConfigMinecraft.WORLD.DIFFICULTY.lock);
        }
        if (EMTConfigMinecraft.WORLD.GAMEMODE.force) {
            world.getWorldInfo().setGameType(GameType.getByID(EMTConfigMinecraft.WORLD.GAMEMODE.gamemode));
        }
        world.getWorldInfo().setAllowCommands(EMTConfigMinecraft.WORLD.GAMEMODE.cheats);
        if (!world.getWorldInfo().isHardcoreModeEnabled() && (world.getWorldInfo().getGameType() == GameType.SURVIVAL || world.getWorldInfo().getGameType() == GameType.ADVENTURE)) {
            world.getWorldInfo().setHardcore(EMTConfigMinecraft.WORLD.GAMEMODE.hardcore);
        }
    }
}
