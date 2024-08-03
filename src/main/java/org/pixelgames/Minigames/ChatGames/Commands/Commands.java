package org.pixelgames.Minigames.ChatGames.Commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import org.pixelgames.Minigames.ChatGames.ChatGameManager;

public class Commands {
    public void StartMinigame(CommandDispatcher<CommandSourceStack> dispatcher, String commandWord) {
        dispatcher.register(net.minecraft.commands.Commands.literal(commandWord).executes((command) -> {
            ChatGameManager.forceStartMinigame(command.getSource().getServer());
            return 1;
        }));
    }
}

