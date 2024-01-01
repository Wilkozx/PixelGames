package org.pixelgames.Minigame.Trivia;

public class Trivia {
    private static Trivia instance = null;
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

}
