package org.pixelgames.Minigame.Unscramble;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class UnscrambleLogic {
    public static void Unscrambler() throws IOException {

        File pokemonList = new File("mods/PixelGames/pokemonlist.txt");

        RandomAccessFile file;
        try {
            file = new RandomAccessFile(pokemonList, "r");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Random random = new Random();
        int number = random.nextInt((int) pokemonList.length());
        file.seek(number);
        file.readLine();

        String pokemonName = file.readLine();


        file.close();
        Unscramble.getInstance().setScrambledWord(pokemonName);
    }

}
