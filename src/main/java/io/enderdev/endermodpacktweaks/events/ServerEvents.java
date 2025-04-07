package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.EMTConfig;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.utils.EmtSide;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ServerEvents {
    boolean flag = false;

    @SubscribeEvent
    public void onTickEnd(TickEvent.ServerTickEvent event) {
        if (!EMTConfig.MODPACK.SERVER_MESSAGE.enable || flag || !EmtSide.isDedicatedServer() || event.phase != TickEvent.Phase.END) {
            return;
        }
        flag = true;
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        StringBuilder motd = new StringBuilder();

        motd.append("=========================================================\n");
        motd.append(EMTConfig.MODPACK.SERVER_MESSAGE.serverName).append(" Server Successfully Started!\n");

        if (EMTConfig.MODPACK.modpackName.isEmpty()) {
            motd.append(" - Pack Name: ").append(EMTConfig.MODPACK.modpackName).append("\n");
        }
        if (!EMTConfig.MODPACK.modpackVersion.isEmpty()) {
            motd.append(" - Pack Version: ").append(EMTConfig.MODPACK.modpackVersion).append("\n");
        }
        if (!EMTConfig.MODPACK.modpackAuthor.isEmpty()) {
            motd.append(" - Pack Author: ").append(EMTConfig.MODPACK.modpackAuthor).append("\n");
        }

        motd.append(" - Port: ").append(server.getServerPort()).append("\n");
        motd.append("Players Can Now Join!\n");
        motd.append("=========================================================\n");

        EnderModpackTweaks.LOGGER.info(motd.toString());
    }
}
