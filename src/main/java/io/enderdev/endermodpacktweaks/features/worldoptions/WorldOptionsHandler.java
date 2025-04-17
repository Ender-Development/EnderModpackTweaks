package io.enderdev.endermodpacktweaks.features.worldoptions;

import io.enderdev.endermodpacktweaks.config.CfgMinecraft;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldOptionsHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onWorldLoad(WorldEvent.Load event) {
        World world = event.getWorld();

        EnumDifficulty currentDifficulty = world.getDifficulty();
        EnumDifficulty forcedDifficulty = EnumDifficulty.byId(CfgMinecraft.WORLD.difficulty.getValue());

        if (currentDifficulty != forcedDifficulty && CfgMinecraft.WORLD.forceDifficulty) {
            world.getWorldInfo().setDifficulty(forcedDifficulty);
        }

        if (!world.getWorldInfo().isDifficultyLocked()) {
            world.getWorldInfo().setDifficultyLocked(CfgMinecraft.WORLD.lock);
        }

        if (CfgMinecraft.WORLD.forceGamemode) {
            world.getWorldInfo().setGameType(GameType.getByID(CfgMinecraft.WORLD.gamemode.getValue()));
        }

        world.getWorldInfo().setAllowCommands(CfgMinecraft.WORLD.cheats);

        if (!world.getWorldInfo().isHardcoreModeEnabled() && (world.getWorldInfo().getGameType() == GameType.SURVIVAL || world.getWorldInfo().getGameType() == GameType.ADVENTURE)) {
            world.getWorldInfo().setHardcore(CfgMinecraft.WORLD.hardcore);
        }
    }
}
