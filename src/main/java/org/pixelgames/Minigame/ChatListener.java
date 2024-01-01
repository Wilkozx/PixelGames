package org.pixelgames.Minigame;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import org.pixelgames.PixelGames;

@Mod.EventBusSubscriber(modid = PixelGames.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChatListener {
    private static MinecraftServer server = null;
    private static ChatListener instance = null;
    private static boolean isMonitoringChat = false;
    private static String toMatch = "";

    public static void stopChatMonitoring() {
        isMonitoringChat = false;
    }

    public static void startChatMonitoring() {
        isMonitoringChat = true;
    }

    public static Boolean isMonitoringChat() {
        return isMonitoringChat;
    }

    public static String getToMatch() {
        return toMatch;
    }

    public static void setToMatch(String toMatch) {
        ChatListener.toMatch = toMatch;
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static void setMinecraftServer(MinecraftServer server) {
        ChatListener.server = server;
    }

}
