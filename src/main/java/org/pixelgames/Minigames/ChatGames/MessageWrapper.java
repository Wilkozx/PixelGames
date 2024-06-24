package org.pixelgames.Minigames.ChatGames;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import org.antlr.v4.misc.OrderedHashMap;

import java.util.Map;

public class MessageWrapper {
    private Component messagePrefix;
    private ConfigBuilder config = new ConfigBuilder();

    public MessageWrapper() {
        this.messagePrefix = getMessagePrefix();
        translateFormatToComponent((String) config.getMessageFormats().get("Prefix"));
    }

    public Component translateFormatToComponent(String format) {
        Map<String, String> message = new OrderedHashMap<>();
        StringBuilder word = new StringBuilder();
        StringBuilder styles = new StringBuilder();

        for (int i = 0; i < format.length(); i++) {
            if (format.charAt(i) == '&') {
                if(!word.isEmpty()) {
                    message.put(word.toString(), styles.toString());
                    word = new StringBuilder();
                    styles = new StringBuilder();
                }
                i++;
                styles.append(format.charAt(i));
            } else {
                word.append(format.charAt(i));
            }
        }
        message.put(word.toString(), styles.toString());

        // TODO: Implement the rest of the method
        MutableComponent component;
        Component finalComponent = Component.literal("");
        for (Map.Entry<String, String> entry : message.entrySet()) {
            component = Component.literal(entry.getKey());
            for (char i : entry.getValue().toCharArray()) {
                switch (i) {
                    case '0' -> component.withStyle(ChatFormatting.BLACK);
                    case '1' -> component.withStyle(ChatFormatting.DARK_BLUE);
                    case '2' -> component.withStyle(ChatFormatting.DARK_GREEN);
                    case '3' -> component.withStyle(ChatFormatting.DARK_AQUA);
                    case '4' -> component.withStyle(ChatFormatting.DARK_RED);
                    case '5' -> component.withStyle(ChatFormatting.DARK_PURPLE);
                    case '6' -> component.withStyle(ChatFormatting.GOLD);
                    case '7' -> component.withStyle(ChatFormatting.GRAY);
                    case '8' -> component.withStyle(ChatFormatting.DARK_GRAY);
                    case '9' -> component.withStyle(ChatFormatting.BLUE);
                    case 'a' -> component.withStyle(ChatFormatting.GREEN);
                    case 'b' -> component.withStyle(ChatFormatting.AQUA);
                    case 'c' -> component.withStyle(ChatFormatting.RED);
                    case 'd' -> component.withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 'e' -> component.withStyle(ChatFormatting.YELLOW);
                    case 'f' -> component.withStyle(ChatFormatting.WHITE);
                    case 'l' -> component.withStyle(ChatFormatting.BOLD);
                    case 'm' -> component.withStyle(ChatFormatting.STRIKETHROUGH);
                    case 'n' -> component.withStyle(ChatFormatting.UNDERLINE);
                    case 'o' -> component.withStyle(ChatFormatting.ITALIC);
                    case 'r' -> component.withStyle(ChatFormatting.RESET);
                    default -> {
                    }
                }
            }
            finalComponent = finalComponent.copy().append(component);
        }
        return finalComponent;
    }

    public Component getMessagePrefix() {
        return translateFormatToComponent((String) config.getMessageFormats().get("Prefix"));
    }

    public Component getTimeoutResponse() {
        return translateFormatToComponent((String) config.getMessageFormats().get("TimeoutResponse"));
    }

    public Component getWinnerResponse(String username) {
        return translateFormatToComponent((String) config.getMessageFormats().get("WinnerResponse"));
    }

    public Component getAnswerResponse(String answer) {
        return translateFormatToComponent((String) config.getMessageFormats().get("AnswerResponse"));
    }

}
