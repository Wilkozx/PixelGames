package org.pixelgames.Minigames.ChatGames.Commands;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.pixelgames.PixelGames;

@Mod.EventBusSubscriber(modid = PixelGames.MODID)
public class CommandInit extends Event {
        @SubscribeEvent
        public static void onCommandsRegister(RegisterCommandsEvent event) {
            Commands commands = new Commands();
            commands.StartMinigame(event.getDispatcher(), "startminigame");
        }

}
