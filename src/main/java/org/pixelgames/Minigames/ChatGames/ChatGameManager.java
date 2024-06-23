package org.pixelgames.Minigames.ChatGames;

import com.mojang.logging.LogUtils;
import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.Yaml;
import net.minecraft.server.MinecraftServer;
import org.pixelgames.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
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

}



