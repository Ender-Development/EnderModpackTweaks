package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.config.CfgFeatures;
import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
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
        if (CfgFeatures.SYNC_TIME.enable) {
            world.getWorldInfo().getGameRulesInstance().setOrCreateGameRule("doDaylightCycle", "false");
        }

        if (!CfgMinecraft.WORLD.enable) {
            return;
        }
        EnumDifficulty currentDifficulty = world.getDifficulty();
        EnumDifficulty forcedDifficulty = EnumDifficulty.byId(CfgMinecraft.WORLD.difficulty.getValue());
        if (currentDifficulty != forcedDifficulty && CfgMinecraft.WORLD.force) {
            world.getWorldInfo().setDifficulty(forcedDifficulty);
        }
        if (!world.getWorldInfo().isDifficultyLocked()) {
            world.getWorldInfo().setDifficultyLocked(CfgMinecraft.WORLD.lock);
        }
        if (CfgMinecraft.WORLD.force) {
            world.getWorldInfo().setGameType(GameType.getByID(CfgMinecraft.WORLD.gamemode.getValue()));
        }
        world.getWorldInfo().setAllowCommands(CfgMinecraft.WORLD.cheats);
        if (!world.getWorldInfo().isHardcoreModeEnabled() && (world.getWorldInfo().getGameType() == GameType.SURVIVAL || world.getWorldInfo().getGameType() == GameType.ADVENTURE)) {
            world.getWorldInfo().setHardcore(CfgMinecraft.WORLD.hardcore);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void worldSyncTime(TickEvent.WorldTickEvent event) {
        if (CfgFeatures.SYNC_TIME.enable) {
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
