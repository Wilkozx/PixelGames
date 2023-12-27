package org.pixelgames.Minigame;

import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.Yaml;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MinigameManager {
    public static void MinigamePicker(MinecraftServer server) throws FileNotFoundException {
        File config = new File("config/PixelGames/config.yaml");
        FileReader reader = new FileReader(config);
        Yaml yaml = new Yaml();
        Map<String, Boolean> configData = yaml.load(reader); // update yaml import if possible

        Boolean trivia = configData.get("Trivia");;
        Boolean wordScramble = configData.get("WordScramble");

        System.out.println(trivia);
        System.out.println(wordScramble);

        ArrayList<Integer> minigames = new ArrayList<>();

        if(trivia) {
            minigames.add(0);
        }
        if(wordScramble) {
            minigames.add(1);
        }

        Random random = new Random();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            int number = random.nextInt(minigames.size());

            switch (number) {
                case 0:
                    server.sendSystemMessage(Component.literal("trivia"));
                    break;
                case 1:
                    server.sendSystemMessage(Component.literal("wordscramble"));
                    break;
            }



        };

        executorService.scheduleAtFixedRate(task, 0, 1, TimeUnit.MINUTES);








    }
}



