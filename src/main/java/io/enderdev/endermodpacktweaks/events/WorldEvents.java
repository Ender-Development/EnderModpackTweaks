package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.features.timesync.SyncTimeHandler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WorldEvents {
    private int tickCounter = 0;

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onWorldLoad(WorldEvent.Load event) {
        World world = event.getWorld();
        if (EMTConfig.MODPACK.SYNC_TIME.enable) {
            world.getWorldInfo().getGameRulesInstance().setOrCreateGameRule("doDaylightCycle", "false");
        }

        if (!EMTConfig.MINECRAFT.WORLD.enable) {
            return;
        }
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

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void worldSyncTime(TickEvent.WorldTickEvent event) {
        if (EMTConfig.MODPACK.SYNC_TIME.enable) {
            tickCounter++;
            if (tickCounter < 100) {
                return;
            }
            int time = SyncTimeHandler.INSTANCE.getMappedTime();
            int days = (int) (event.world.getTotalWorldTime() / 24000);
            event.world.setWorldTime((long) time + (days * 24000L));
            tickCounter = 0;
        }
    }
}
