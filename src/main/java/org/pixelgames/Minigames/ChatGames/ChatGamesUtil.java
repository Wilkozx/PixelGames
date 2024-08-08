package org.pixelgames.Minigames.ChatGames;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import org.pixelgames.Minigames.ChatGames.Games.Trivia;
import org.pixelgames.Minigames.ChatGames.Games.Unscramble;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatGamesUtil {

    private static int sequence = 0;
    private static final ConfigBuilder config = new ConfigBuilder();

    private static String latestMinigame = "";

    public static void getRandomMinigame(ArrayList<Integer> minigames) {
        Random random = new Random();
        int number = random.nextInt(minigames.size());
        ChatGamesUtil.runMinigame(number);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(ChatGamesUtil::stopMinigame, config.getSettingsTimeout(), TimeUnit.SECONDS);
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

    public static void stopMinigame() {
        if (ChatListener.isMonitoringChat()) {
            MinecraftServer server = ChatListener.getServer();
            switch (latestMinigame) {
                case "Trivia":
                    ChatListener.stopChatMonitoring();
                    try {
                        MessageWrapper messageWrapper = new MessageWrapper();
                        Component component = messageWrapper.getMessagePrefix();
                        component = component.copy().append(messageWrapper.getTimeoutResponse());
                        component = component.copy().append(messageWrapper.getAnswerResponse(Trivia.getInstance().getAnswer()));
                        String message = Component.Serializer.toJson(component);

                        server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "tellraw @a " + message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ChatListener.stopChatMonitoring();
                    break;
                case "Scramble":
                    try {
                        MessageWrapper messageWrapper = new MessageWrapper();
                        Component component = messageWrapper.getMessagePrefix();
                        component = component.copy().append(messageWrapper.getTimeoutResponse());
                        component = component.copy().append(messageWrapper.getAnswerResponse(Unscramble.getInstance().getUnscrambledWord()));
                        String message = Component.Serializer.toJson(component);

                        server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "tellraw @a " + message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ChatListener.stopChatMonitoring();
                    break;
            }
        }
    }

    public static void runMinigame(int number) {
        MinecraftServer server = ChatListener.getServer();
        switch (number) {
            case 0:
                try {
                    MessageWrapper messageWrapper = new MessageWrapper();
                    Component component = messageWrapper.getMessagePrefix();
                    component = component.copy().append(Component.literal(Trivia.getRandomTrivia()).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
                    String message = Component.Serializer.toJson(component);
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "tellraw @a " + message);

                    LogUtils.getLogger().info("[PixelGames] Trivia selected, chat monitoring started.");
                    LogUtils.getLogger().info("[PixelGames] Trivia Answer is " + Trivia.getInstance().getAnswer());
                    ChatListener.setToMatch(Trivia.getInstance().getAnswer());
                    ChatListener.startChatMonitoring();
                    latestMinigame = "Trivia";

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    Unscramble.getInstance().shuffleScramble();
                    MessageWrapper messageWrapper = new MessageWrapper();
                    Component component = messageWrapper.getMessagePrefix();
                    component = component.copy().append(Component.literal("Unscramble the word:").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
                    component = component.copy().append(Component.literal(Unscramble.getInstance().getScrambledWord()).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
                    String message = Component.Serializer.toJson(component);
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "tellraw @a " + message);

                    LogUtils.getLogger().info("[PixelGames] Scramble selected, chat monitoring started.");
                    LogUtils.getLogger().info("[PixelGames] Scramble Answer is " + Unscramble.getInstance().getUnscrambledWord().toLowerCase());
                    ChatListener.setToMatch(Unscramble.getInstance().getUnscrambledWord().toLowerCase());
                    ChatListener.startChatMonitoring();
                    latestMinigame = "Unscramble";

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {

                    Component component1 = Component.literal("%player% has clicked the button").withStyle(ChatFormatting.GRAY, ChatFormatting.BOLD);
                    String messageOutput = Component.Serializer.toJson(component1);

                    Component component = Component.literal("[").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
                    component = component.copy().append(Component.literal("ChatGames")
                            .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));
                    component = component.copy().append(Component.literal("]")
                            .withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD));
                    component = component.copy().append(Component.literal("Click the dot ")
                            .withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));
                    component = component.copy().append(Component.literal(".")
                                    .setStyle(Style.EMPTY.withClickEvent(
                                            new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tellraw @a " + messageOutput)
                                    )))
                            .withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);

                    LogUtils.getLogger().info("[PixelGames] dotClicker selected, onClickEvent started");
                    String message = Component.Serializer.toJson(component);
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "tellraw @a " + message);

                } catch (Exception ignore) {}
        }

    }

    public static String getCurrentMinigame() {
        return latestMinigame;
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

