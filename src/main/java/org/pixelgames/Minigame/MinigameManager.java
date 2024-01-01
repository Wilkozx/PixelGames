package org.pixelgames.Minigame;

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

public class MinigameManager {
    public static void MinigamePicker(MinecraftServer server) throws FileNotFoundException {
        ChatListener.setMinecraftServer(server);

        File config = new File("config/PixelGames/config.yaml");
        FileReader reader = new FileReader(config);
        Yaml yaml = new Yaml();

        Iterable<Object> documents = yaml.loadAll(reader); // update yaml import if possible
        Map<String, Boolean> configData = (Map<String, Boolean>) documents.iterator().next(); // Access the first document
        Map<String, String> preferenceData = (Map<String, String>) documents.iterator().next(); // Access the second document
        Map<String, Integer> frequencyData = (Map<String, Integer>) documents.iterator().next();

        Boolean trivia = configData.get("Trivia");
        Boolean wordScramble = configData.get("WordScramble");

        ArrayList<Integer> minigames = new ArrayList<>();

        if(trivia) {
            minigames.add(0);
        }
        if(wordScramble) {
            minigames.add(1);
        }

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            System.out.println("[PixelGames] Picking chat game...");
            if(preferenceData.containsValue("Random")) {
                MinigameUtil.getRandomMinigame(minigames);
            }
            if(preferenceData.containsValue("Sequential")) {
                MinigameUtil.getSequentialMinigame(minigames);
            }

        };
        executorService.scheduleAtFixedRate(task, 0, frequencyData.values().size(), TimeUnit.MINUTES);

    }

}



