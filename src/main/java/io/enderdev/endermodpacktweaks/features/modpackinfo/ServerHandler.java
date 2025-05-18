package io.enderdev.endermodpacktweaks.features.modpackinfo;

import io.enderdev.endermodpacktweaks.config.CfgModpack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ServerHandler {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static void addServer() {
        ServerList serverList = new ServerList(MC);
        final int c = serverList.countServers();
        boolean foundServer = false;
        for (int i = 0; i < c; i++) {
            ServerData data = serverList.getServerData(i);
            if (data.serverIP.equals(CfgModpack.DEFAULT_SERVER.serverIp)) {
                foundServer = true;
                break;
            }
        }
        if (!foundServer) {
            ServerData data = new ServerData(CfgModpack.DEFAULT_SERVER.serverName, CfgModpack.DEFAULT_SERVER.serverIp, false);
            serverList.addServerData(data);
            serverList.saveServerList();
        }
    }
}
