package io.enderdev.endermodpacktweaks.events;

import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.config.CfgModpack;
import io.enderdev.endermodpacktweaks.utils.EmtSide;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;

public class ServerEvents {
    boolean flag = false;

    @SubscribeEvent
    public void onTickEnd(TickEvent.ServerTickEvent event) {
        if (!CfgModpack.SERVER_MESSAGE.enable || flag || !EmtSide.isDedicatedServer() || event.phase != TickEvent.Phase.END) {
            return;
        }
        flag = true;
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        StringBuilder motd = new StringBuilder();

        motd.append("=========================================================\n");
        motd.append(CfgModpack.SERVER_MESSAGE.serverName).append(" Server Successfully Started!\n");

        if (!CfgModpack.MODPACK.modpackName.isEmpty()) {
            motd.append(" - Pack Name: ").append(CfgModpack.MODPACK.modpackName).append("\n");
        }
        if (!CfgModpack.MODPACK.modpackVersion.isEmpty()) {
            motd.append(" - Pack Version: ").append(CfgModpack.MODPACK.modpackVersion).append("\n");
        }
        if (!CfgModpack.MODPACK.modpackAuthor.isEmpty()) {
            motd.append(" - Pack Author: ").append(CfgModpack.MODPACK.modpackAuthor).append("\n");
        }

        motd.append(" - Port: ").append(server.getServerPort()).append("\n");
        motd.append("Players Can Now Join!\n");
        motd.append("=========================================================\n");

        Arrays.stream(motd.toString().split("\n")).forEach(EnderModpackTweaks.LOGGER::info);
    }
}
