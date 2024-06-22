package org.pixelgames.Minigames.ChatGames;

import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.DumperOptions;
import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.Yaml;
import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.nodes.Tag;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class ConfigBuilder {

    public void buildDefaultConfig() {

        if(!new File("config/PixelGames").exists()) {
            new File("config/PixelGames").mkdirs();
        }

        if(!new File("config/PixelGames/configtest.yaml").exists()) {
            try {
                new File("config/PixelGames/config.yaml").createNewFile();
                FileWriter writer = new FileWriter("config/PixelGames/config.yaml");

                Map<String, Object> chatgameSettings = new HashMap<>();
                // Preferences
                Map<String, Object> preferences = new HashMap<>();
                preferences.put("Preference", "Random");
                preferences.put("Frequency", 15);
                preferences.put("Timeout", 0.5);
                chatgameSettings.put("ChatgameSettings", preferences);
                // Enabled Minigames
                Map<String, Object> enabledMinigames = new HashMap<>();
                enabledMinigames.put("Trivia", true);
                enabledMinigames.put("wordScramble", true);
                enabledMinigames.put("dotClicker", false);
                chatgameSettings.put("EnabledMinigames", enabledMinigames);
                // Customize Message Formats
                Map<String, Object> messageFormats = new HashMap<>();
                messageFormats.put("Prefix", "&l&f[&cPG&f");
                messageFormats.put("WinnerResponse", "%username% %l%ahas guessed the correct answer! ");
                messageFormats.put("TimeoutResponse", "%l%cUnfortunately, no one answered correctly. ");
                messageFormats.put("AnswerResponse", "%l%aThe correct answer was: %answer% ");
                chatgameSettings.put("MessageFormats", messageFormats);

                Yaml yaml = new Yaml();
                writer.write(yaml.dumpAs(chatgameSettings, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
