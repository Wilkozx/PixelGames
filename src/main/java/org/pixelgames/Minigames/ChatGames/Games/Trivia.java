package org.pixelgames.Minigames.ChatGames.Games;

import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.Yaml;
import org.pixelgames.PixelGames;

import java.io.InputStream;
import java.util.Map;
import java.util.Random;

public class Trivia {
    private static Trivia instance = null;
    static Map<String, String> triviaMap = null;
    String question = "";
    String answer = "";

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public static Trivia getInstance() {
        if (instance == null) {
            instance = new Trivia();
        }
        return instance;
    }

    public static String getRandomTrivia() {
        InputStream inputStream = PixelGames.class.getResourceAsStream("/data/trivia.yaml");
        Yaml yaml = new Yaml();
        Map<String, String> triviaData = yaml.load(inputStream);

        Random random = new Random();
        int i = random.nextInt(triviaData.size());

        Map.Entry<String, String> randomEntry = triviaData.entrySet().stream().skip(i).findFirst().orElseThrow(() -> new IllegalStateException("Trivia Data Entry"));
        Trivia.getInstance().setQuestion(randomEntry.getKey());
        Trivia.getInstance().setAnswer(randomEntry.getValue());

        return Trivia.getInstance().getQuestion();
    }

}
