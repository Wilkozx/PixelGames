package org.pixelgames.Minigames.ChatGames;

import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ChatGameManager {
    public static void MinigamePicker(MinecraftServer server) {
        ChatListener.setMinecraftServer(server);
        try {
            ConfigBuilder config = new ConfigBuilder();
            ArrayList<Integer> minigames = config.getActiveMinigames();
            String preference = config.getSettingsPreference();

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            Runnable task = () -> {
                LogUtils.getLogger().info("[PixelGames] ChatGame initiated, selecting game based on preferences");

                if (Objects.equals(preference, "Random")) {
                    ChatGamesUtil.getRandomMinigame(minigames);
                }
                if (Objects.equals(preference, "Sequential")) {
                    ChatGamesUtil.getSequentialMinigame(minigames);
                }
            };
            executorService.scheduleAtFixedRate(task, 15, config.getSettingsFrequency(), TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Pixelgames config file not found.");
        }
    }

    public static void forceStartMinigame(MinecraftServer server) {
        ChatListener.setMinecraftServer(server);
        try {
            ConfigBuilder config = new ConfigBuilder();
            ArrayList<Integer> minigames = config.getActiveMinigames();
            String preference = config.getSettingsPreference();

            LogUtils.getLogger().info("[PixelGames] ChatGame force initiated, selecting game based on preferences");

            if (Objects.equals(preference, "Random")) {
                ChatGamesUtil.getRandomMinigame(minigames);
            }
            if (Objects.equals(preference, "Sequential")) {
                ChatGamesUtil.getSequentialMinigame(minigames);
            }
        } catch (Exception e) {
            System.out.println("Pixelgames config file not found.");
        }
    }

}



