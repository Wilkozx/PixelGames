package org.pixelgames;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.pixelgames.Minigames.ChatGames.*;
import org.pixelgames.Minigames.ChatGames.Games.Unscramble;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PixelGames.MODID)
public class PixelGames {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "pixelgames";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "pixelgames" namespace

    public PixelGames() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        try {
            // Some common setup code
            File configFolder = new File("config/PixelGames");
            if (!configFolder.exists()) {
                configFolder.mkdirs();
            }
            File config = new File("config/PixelGames/config.yaml");
            if (!config.exists()) {
                config.createNewFile();
            }

        } catch (Exception ignore) {}
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) throws IOException {
        LOGGER.info("[PixelGames] Thanks for installing PixelGames, please see to config/PixelGames to adjust preferences");
        Unscramble.populateList();
        ChatGameManager.MinigamePicker(event.getServer());
        new ConfigBuilder().buildDefaultConfig();
    }

    @SubscribeEvent
    public void onChatMessage(ServerChatEvent event) {
        Boolean isMonitoring = ChatListener.isMonitoringChat();
        if (isMonitoring) {
            String message = event.getMessage().getString().toLowerCase();
            message = ChatGamesUtil.isValidAnswer(message);

            if (message.matches(ChatListener.getToMatch())) {
                ChatListener.stopChatMonitoring();
                // Perform any additional actions after matching string is found
                System.out.println("[PixelGames] Answer found, chat monitoring stopped.");

                MinecraftServer server = ChatListener.getServer();
                System.out.println("[PixelGames] " + event.getUsername() + " has guessed the correct answer");

                MessageWrapper messageWrapper = new MessageWrapper();
                Component component = messageWrapper.getMessagePrefix();
                component = component.copy().append(messageWrapper.getWinnerResponse(event.getUsername()));
                String winnerMessage = Component.Serializer.toJson(component);

                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> {
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "tellraw @a " + winnerMessage);
                    executorService.shutdown();
                }, 50, TimeUnit.MILLISECONDS);

            }
        }

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
