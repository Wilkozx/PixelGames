package org.pixelgames.Minigames.ChatGames;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
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
        System.out.println(message);

        // TODO: Implement the rest of the method
        
        return null;
    }

    public Component getMessagePrefix() {
        Component component = Component.literal("[").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD);
        component = component.copy().append(Component.literal("PG").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
        component = component.copy().append(Component.literal("] ").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD));
        component = component.copy().withStyle(component.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Wilkozx/PixelGames")));
        component = component.copy().withStyle(component.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click here to go to the PixelGames GitHub page").withStyle(ChatFormatting.GRAY))));
        return component;
    }

    public Component getUnfortunateResponse() {
        Component component = Component.literal("Unfortunately, no one answered correctly. ").withStyle(ChatFormatting.RED);
        return component;
    }

    public Component getWinnerResponse(String username) {
        Component component = Component.literal(username).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD);
        component = component.copy().append(Component.literal(" has guessed the correct answer! ").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        return component;
    }

    public Component getAnswerResponse(String answer) {
        Component component = Component.literal("The correct answer was: ").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD);
        component = component.copy().append(Component.literal(answer).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        return component;
    }

}
