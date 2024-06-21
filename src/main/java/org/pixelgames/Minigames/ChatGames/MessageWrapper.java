package org.pixelgames.Minigames.ChatGames;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

public class MessageWrapper {
    private Component messagePrefix;

    public MessageWrapper() {
        this.messagePrefix = getMessagePrefix();
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

    public Component getAnswerResposne(String answer) {
        Component component = Component.literal("The correct answer was: ").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD);
        component = component.copy().append(Component.literal(answer).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        return component;
    }

}
