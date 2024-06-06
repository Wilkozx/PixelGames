package org.pixelgames.Minigames.ChatGames.Games;

import org.pixelgames.PixelGames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Unscramble {
    private static Unscramble instance = null;
    private static List<String> pokemonList = null;
    String scrambledWord = "";

    public void setScrambledWord(String scrambledWord) {
        this.scrambledWord = scrambledWord;
    }

    public String getScrambledWord() {
        ArrayList<Character> shuffledPokemonName = new ArrayList<>();
        ArrayList<Character> shuffledPokemonName2 = new ArrayList<>();
        Random random = new Random();

        for(char a : scrambledWord.toCharArray()) {
            int charPos = random.nextInt(10);
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

        if(pokemonName.toString().matches(scrambledWord)) {
            pokemonName.reverse();
        }

        return " " + pokemonName;
    }

    public void shuffleScramble() {
        setScramble();
    }

    public String getUnscrambledWord() {
        return scrambledWord;
    }

    public static void setScramble() {
        int randomIndex = new Random().nextInt(pokemonList.size());
        String pokemonName = pokemonList.get(randomIndex);
        Unscramble.getInstance().setScrambledWord(pokemonName.toLowerCase());
    }

    public static Unscramble getInstance() {
        if (instance == null) {
            instance = new Unscramble();
        }
        return instance;
    }

    public static void populateList() {
        try {
            InputStream inputStream = PixelGames.class.getResourceAsStream("/data/pokemonlist.txt");

            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                pokemonList = reader.lines().toList();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


}
