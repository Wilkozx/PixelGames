package org.pixelgames.Minigame.Unscramble;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Unscramble {
    private static Unscramble instance = null;
    String scrambledWord = "";

    public void setScrambledWord(String scrambledWord) {
        this.scrambledWord = scrambledWord;
    }

    public String getScrambledWord() {
        ArrayList<Character> shuffledPokemonName = new ArrayList<>();
        ArrayList<Character> shuffledPokemonName2 = new ArrayList<>();
        Random random = new Random();

        for(char a : scrambledWord.toCharArray()) {
            int charPos = random.nextInt(scrambledWord.length());
            if (charPos % 2 == 0) {
                shuffledPokemonName.add(a);
            } else {
                shuffledPokemonName2.add(a);
            }

        }
        shuffledPokemonName.addAll(shuffledPokemonName2);

        StringBuilder pokemonName = new StringBuilder();
        for(char a : shuffledPokemonName) {
            pokemonName.append(a);
        }

        int randomNumber = random.nextInt(scrambledWord.length());
        if (randomNumber % 3 == 0) {
            pokemonName.reverse();
        }

        return " " + pokemonName;
    }

    public void shuffleScramble() throws IOException {
        UnscrambleLogic.Unscrambler();
    }

    public String getUnscrambledWord() {
        return scrambledWord;
    }

    public static Unscramble getInstance() {
        if (instance == null) {
            instance = new Unscramble();
        }
        return instance;
    }

}
