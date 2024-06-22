package org.pixelgames.Minigames.ChatGames;

import com.mojang.logging.LogUtils;
import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.Yaml;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ChatGameManager {
    public static void MinigamePicker(MinecraftServer server) throws FileNotFoundException {
        ChatListener.setMinecraftServer(server);

        File config = new File("config/PixelGames/config.yaml");
        FileReader reader = new FileReader(config);
        Yaml yaml = new Yaml();

        try {
            Map<String, Object> data = yaml.load(reader);
            Map<String, Object> enabledMinigames = (Map<String, Object>) data.get("EnabledMinigames");
            Map<String, Object> preferenceData = (Map<String, Object>) data.get("ChatgameSettings");

            Boolean trivia = (Boolean) enabledMinigames.get("Trivia");
            Boolean wordScramble = (Boolean) enabledMinigames.get("wordScramble");
            Boolean dotClicker = (Boolean) enabledMinigames.get("dotClicker");

            ArrayList<Integer> minigames = new ArrayList<>();

            if (trivia) {
                minigames.add(0);
            }
            if (wordScramble) {
                minigames.add(1);
            }

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

            Runnable task = () -> {
                LogUtils.getLogger().info("[PixelGames] ChatGame initiated, selecting game based on preferences");

                if (preferenceData.containsValue("Random")) {
                    ChatGamesUtil.getRandomMinigame(minigames);
                }
                if (preferenceData.containsValue("Sequential")) {
                    ChatGamesUtil.getSequentialMinigame(minigames);
                }
            };
            executorService.scheduleAtFixedRate(task, 15, 300, TimeUnit.SECONDS);

        } catch (Exception e) {
            System.out.println("Pixelgames config file not found.");
        }
    }

}



