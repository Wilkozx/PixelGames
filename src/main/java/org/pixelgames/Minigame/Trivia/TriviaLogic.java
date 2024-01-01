package org.pixelgames.Minigame.Trivia;

import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Random;

public class TriviaLogic {
    public static String testTrivia() {
        File triviaFile = new File("mods/PixelGames/trivia.yaml");
        FileReader fileReader;

        try {
            fileReader = new FileReader(triviaFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Yaml yaml = new Yaml();
        Map<String, String> triviaData = yaml.load(fileReader);

        Random random = new Random();
        int i = random.nextInt(triviaData.size());

        Map.Entry<String, String> randomEntry = triviaData.entrySet().stream().skip(i).findFirst().orElseThrow(() -> new IllegalStateException("Trivia Data Entry"));
        Trivia.getInstance().setQuestion(randomEntry.getKey());
        Trivia.getInstance().setAnswer(randomEntry.getValue());

        return " " + Trivia.getInstance().getQuestion();
    }
}
