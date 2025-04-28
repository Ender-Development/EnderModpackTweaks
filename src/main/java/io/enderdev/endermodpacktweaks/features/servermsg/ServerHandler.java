package io.enderdev.endermodpacktweaks.features.servermsg;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.utils.EmtSide;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;

public class ServerHandler {
    public static void serverStarted() {
        // FMLServerStartedEvent gets called from MinecraftServer, which both IntegratedServer and DedicatedServer extend, so this check is probably necessary
        if (!EmtSide.isDedicatedServer()) {
            return;
        }

        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        EnderModpackTweaks.LOGGER.info("=========================================================");
        EnderModpackTweaks.LOGGER.info("{} Server Successfully Started!", CfgModpack.SERVER_MESSAGE.serverName);

        if (!CfgModpack.MODPACK.modpackName.isEmpty()) {
            EnderModpackTweaks.LOGGER.info(" - Pack Name: {}", CfgModpack.MODPACK.modpackName);
        }
        if (!CfgModpack.MODPACK.modpackVersion.isEmpty()) {
            EnderModpackTweaks.LOGGER.info(" - Pack Version: {}", CfgModpack.MODPACK.modpackVersion);
        }
        if (!CfgModpack.MODPACK.modpackAuthor.isEmpty()) {
            EnderModpackTweaks.LOGGER.info(" - Pack Author: {}", CfgModpack.MODPACK.modpackAuthor);
        }

        EnderModpackTweaks.LOGGER.info(" - Port: {}", server.getServerPort());
        EnderModpackTweaks.LOGGER.info("Players Can Now Join!");
        EnderModpackTweaks.LOGGER.info("=========================================================");
    }
}
