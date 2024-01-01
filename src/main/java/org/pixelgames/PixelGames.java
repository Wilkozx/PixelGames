package org.pixelgames;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.core.jmx.Server;
import org.pixelgames.Minigame.ChatListener;
import org.pixelgames.Minigame.MinigameManager;
import org.pixelgames.Minigame.MinigameUtil;
import org.pixelgames.Minigame.Trivia.Trivia;
import org.pixelgames.Minigame.Trivia.TriviaLogic;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
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

            File modFolder = new File("mods/PixelGames");
            if(!modFolder.exists()) {
                modFolder.mkdirs();
            }

            File trivia = new File("mods/PixelGames/trivia.yaml");
            if(!trivia.exists()) {
                trivia.createNewFile();
            }

        } catch (Exception ignore) {}
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) throws FileNotFoundException {
        LOGGER.info("[PixelGames] Thanks for installing PixelGames, please see to config files to adjust preferences");
        MinigameManager.MinigamePicker(event.getServer());
    }

    @SubscribeEvent
    public void onChatMessage(ServerChatEvent event) {
        Boolean isMonitoring = ChatListener.isMonitoringChat();
        if (isMonitoring) {
            String message = event.getMessage().getString().toLowerCase();
            message = MinigameUtil.isValidAnswer(message);

            if (message.contains(ChatListener.getToMatch())) {
                ChatListener.stopChatMonitoring();
                // Perform any additional actions after matching string is found
                System.out.println("[PixelGames] Answer found, chat monitoring stopped.");

                MinecraftServer server = ChatListener.getServer();
                System.out.println("[PixelGames] " + event.getUsername() + " has guessed the correct answer");
                Component component = Component.literal(event.getUsername()).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD);
                component = component.copy().append(Component.literal(" has guessed the correct answer!").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));

                String winnerMessage = Component.Serializer.toJson(component);
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> {
                    server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "tellraw @a " + winnerMessage);
                    executorService.shutdown();
                }, 5, TimeUnit.MILLISECONDS);

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
