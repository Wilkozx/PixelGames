package org.pixelgames.Minigame;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import org.pixelgames.Minigame.Trivia.Trivia;
import org.pixelgames.Minigame.Trivia.TriviaLogic;
import org.pixelgames.Minigame.Unscramble.Unscramble;

import java.util.ArrayList;
import java.util.Random;

public class MinigameUtil {

    private static int sequence = 0;

    public static void getRandomMinigame(ArrayList<Integer> minigames) {
        Random random = new Random();
        int number = random.nextInt(minigames.size());
        MinigameUtil.runMinigame(number);
    }

    public static void getSequentialMinigame(ArrayList<Integer> minigames) {
        if(sequence < minigames.size()) {
            runMinigame(sequence);
            sequence += 1;
        }

        if(sequence == minigames.size()) {
            sequence = 0;
        }
    }

    public static void runMinigame(int number) {
        MinecraftServer server = ChatListener.getServer();
        switch (number) {
            case 0:
                try {
                    Component component = Component.literal("[").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD);
                    component = component.copy().append(Component.literal("Trivia").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
                    component = component.copy().append(Component.literal("]").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
                    component = component.copy().append(Component.literal(TriviaLogic.testTrivia()).withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.GREEN));
                    String message = Component.Serializer.toJson(component);
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "tellraw @a " + message);

                    System.out.println("[PixelGames] Trivia selected, chat monitoring started.");
                    ChatListener.setToMatch(Trivia.getInstance().getAnswer());
                    ChatListener.startChatMonitoring();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    Unscramble.getInstance().shuffleScramble();
                    Component component = Component.literal("[").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD);
                    component = component.copy().append(Component.literal("unScramble").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
                    component = component.copy().append(Component.literal("]").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
                    component = component.copy().append(Component.literal(" Unscramble the word:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
                    component = component.copy().append(Component.literal(Unscramble.getInstance().getScrambledWord()).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
                    String message = Component.Serializer.toJson(component);
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "tellraw @a " + message);

                    System.out.println("[PixelGames] Scramble selected, chat monitoring started.");
                    ChatListener.setToMatch(Unscramble.getInstance().getUnscrambledWord().toLowerCase());
                    ChatListener.startChatMonitoring();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    public static String isValidAnswer(String answer) {
        if (answer.equals("1") || answer.equalsIgnoreCase("one")) {
            answer = "1";
        }
        if (answer.equals("2") || answer.equalsIgnoreCase("two")) {
            answer = "2";
        }
        if (answer.equals("3") || answer.equalsIgnoreCase("three")) {
            answer = "3";
        }
        if (answer.equals("4") || answer.equalsIgnoreCase("four")) {
            answer = "4";
        }
        if (answer.equals("5") || answer.equalsIgnoreCase("five")) {
            answer = "5";
        }
        if (answer.equals("6") || answer.equalsIgnoreCase("six")) {
            answer = "6";
        }
        if (answer.equals("7") || answer.equalsIgnoreCase("seven")) {
            answer = "7";
        }
        if (answer.equals("8") || answer.equalsIgnoreCase("eight")) {
            answer = "8";
        }
        if (answer.equals("9") || answer.equalsIgnoreCase("nine")) {
            answer = "9";
        }
        if (answer.equals("10") || answer.equalsIgnoreCase("ten")) {
            answer = "10";
        }
        return answer;
    }



}

