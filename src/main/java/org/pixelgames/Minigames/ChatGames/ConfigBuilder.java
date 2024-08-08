package org.pixelgames.Minigames.ChatGames;

import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.DumperOptions;
import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.Yaml;
import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.nodes.Tag;
import org.pixelgames.Minigames.ChatGames.RewardManager.RewardFunctions;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigBuilder {

    private final File configFile = new File("config/PixelGames/config.yaml");
    private final File rewardsFile = new File("config/PixelGames/rewards.yaml");

    public void buildDefaultConfig() {

        if(!new File("config/PixelGames").exists()) {
            new File("config/PixelGames").mkdirs();
        }

        if(configFile.exists()) {
            try {
                FileWriter writer = new FileWriter(configFile);
                // Add web docs link
                writer.write("# For more information on how to configure PixelGames, please visit: https://wilkozx.github.io/PixelGames\n");
                Map<String, Object> chatgameSettings = new HashMap<>();
                // Preferences
                Map<String, Object> preferences = new HashMap<>();
                preferences.put("Preference", "Random");
                preferences.put("Frequency", 300);
                preferences.put("Timeout", 180);
                chatgameSettings.put("ChatgameSettings", preferences);
                // Enabled Minigames
                Map<String, Object> enabledMinigames = new HashMap<>();
                enabledMinigames.put("Trivia", true);
                enabledMinigames.put("wordScramble", true);
                enabledMinigames.put("dotClicker", false);
                chatgameSettings.put("EnabledMinigames", enabledMinigames);
                // Customize Message Formats
                Map<String, Object> messageFormats = new HashMap<>();
                messageFormats.put("Prefix", "&l&f[&l&cPG&l&f] ");
                messageFormats.put("WinnerResponse", "%username% &l&ahas guessed the correct answer! ");
                messageFormats.put("TimeoutResponse", "&l&cUnfortunately, no one answered correctly. ");
                messageFormats.put("AnswerResponse", "&l&aThe correct answer was: &l&e%answer% ");
                chatgameSettings.put("MessageFormats", messageFormats);

                Yaml yaml = new Yaml();
                writer.write(yaml.dumpAs(chatgameSettings, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void buildRewardsConfig() {
        if(!rewardsFile.exists()) {
            try {
                rewardsFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (rewardsFile.exists()) {
            try {
                FileWriter writer = new FileWriter(rewardsFile);

                writer.write("# For more information on how to configure PixelGames, please visit: https://wilkozx.github.io/PixelGames\n");
                Map<String, Object> rewards = new HashMap<>();

                List<Map<String, Object>> triviaBundle = new ArrayList<>();
                Map<String, Object> triviaItem1 = new HashMap<>();
                triviaItem1.put("itemName", "pixelmon:poke_ball");
                triviaItem1.put("weighting", 0.6);
                triviaItem1.put("quantity", 32);
                triviaBundle.add(triviaItem1);
                Map<String, Object> triviaItem2 = new HashMap<>();
                triviaItem2.put("itemName", "pixelmon:great_ball");
                triviaItem2.put("weighting", 0.3);
                triviaItem2.put("quantity", 24);
                triviaBundle.add(triviaItem2);
                rewards.put("Trivia", triviaBundle);

                List<Map<String, Object>> unscrambleBundle = new ArrayList<>();
                Map<String, Object> unscrambleItem1 = new HashMap<>();
                unscrambleItem1.put("itemName", "pixelmon:ultra_ball");
                unscrambleItem1.put("weighting", 0.6);
                unscrambleItem1.put("quantity", 16);
                unscrambleBundle.add(unscrambleItem1);
                Map<String, Object> unscrambleItem2 = new HashMap<>();
                unscrambleItem2.put("itemName", "pixelmon:master_ball");
                unscrambleItem2.put("weighting", 0.4);
                unscrambleItem2.put("quantity", 1);
                unscrambleBundle.add(unscrambleItem2);
                rewards.put("Unscramble", unscrambleBundle);

                Yaml yaml = new Yaml();
                writer.write(yaml.dumpAs(rewards, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<Integer> getActiveMinigames() {
        try {
            Yaml yaml = new Yaml();
            FileReader reader = new FileReader(configFile);
            Map<String, Object> data = yaml.load(reader);
            Map<String, Object> enabledMinigames = (Map<String, Object>) data.get("EnabledMinigames");

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
            if (dotClicker) {
                minigames.add(2);
            }

            return minigames;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSettingsPreference() {
        try {
            Yaml yaml = new Yaml();
            FileReader reader = new FileReader(configFile);
            Map<String, Object> data = yaml.load(reader);
            Map<String, Object> preferenceData = (Map<String, Object>) data.get("ChatgameSettings");
            return (String) preferenceData.get("Preference");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSettingsFrequency() {
        try {
            Yaml yaml = new Yaml();
            FileReader reader = new FileReader(configFile);
            Map<String, Object> data = yaml.load(reader);
            Map<String, Object> preferenceData = (Map<String, Object>) data.get("ChatgameSettings");
            return (int) preferenceData.get("Frequency");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSettingsTimeout() {
        try {
            Yaml yaml = new Yaml();
            FileReader reader = new FileReader(configFile);
            Map<String, Object> data = yaml.load(reader);
            Map<String, Object> preferenceData = (Map<String, Object>) data.get("ChatgameSettings");
            return (int) preferenceData.get("Timeout");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> getMessageFormats() {
        try {
            Yaml yaml = new Yaml();
            FileReader reader = new FileReader(configFile);
            Map<String, Object> data = yaml.load(reader);
            return (Map<String, Object>) data.get("MessageFormats");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
