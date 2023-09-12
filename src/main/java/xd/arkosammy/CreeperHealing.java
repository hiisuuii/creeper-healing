package xd.arkosammy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xd.arkosammy.events.AffectedBlock;
import xd.arkosammy.handlers.ExplosionHealerHandler;
import xd.arkosammy.util.Commands;
import xd.arkosammy.util.Config;
import xd.arkosammy.util.ExplosionEventsSerializer;
import java.io.IOException;

public class CreeperHealing implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("Creeper-Healing");
	public static final Config CONFIG = new Config();
	private static boolean healerHandlerLock;
	private static MinecraftServer serverInstance;

	@Override
	public void onInitialize() {

		//Initialize config
		try {
			initializeConfig();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		//Read the list of CreeperExplosionEvents stored in our file once the server has fully started
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {

			try {
				onServerStarting(server);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		});

		//Grab our current list of CreeperExplosionEvents and store it.
		//Update the config file with new values changed via commands
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {

			try {
				onServerStopping(server);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		});

		//Start listening for CreeperExplosionEvents in our list once we have read the config
		ServerTickEvents.END_SERVER_TICK.register(ExplosionHealerHandler::tickCreeperExplosions);

		//Register our commands
		CommandRegistrationCallback.EVENT.register(Commands::registerCommands);

		LOGGER.info("I will try my best to heal your creeper explosions :)");

	}

	private static void initializeConfig() throws IOException {

		//If the config file already exists, read the data from it
		if(!CONFIG.writeConfig()){

			CONFIG.readConfig();

			CONFIG.updateConfig();

			//Warn the user if these delays were set to 0 or fewer seconds
			if(Math.round(Math.max(CONFIG.getExplosionDelayRaw(), 0) * 20L) == 0) LOGGER.warn("Explosion heal delay set to a very low value in the config file. A value of 1 second will be used instead. Please set a valid value in the config file");
			if(Math.round(Math.max(CONFIG.getBlockPlacementDelayRaw(), 0) * 20L) == 0) LOGGER.warn("Block placement delay set to a very low value in the config file. A value of 1 second will be used instead. Please set a valid value in the config file");
			LOGGER.info("Applied custom configs");

		}

	}

	private static void onServerStarting(MinecraftServer server) throws IOException {

		//Capture the server instance
		serverInstance = server;

		//Read the contents of our scheduled-explosions.json file and add them to the list
		ExplosionEventsSerializer.reScheduleCreeperExplosionEvents(server);

		//We can now start listening for explosions in the list
		setHealerHandlerLock(true);

		//ExplosionHealerHandler.updateExplosionTimers();
		AffectedBlock.updateAffectedBlocksTimers();

	}

	private static void onServerStopping(MinecraftServer server) throws IOException {

		//Reset the flag
		setHealerHandlerLock(false);

		//Make a new ExplosionEventsSerializer object and pass the current list to the constructor, then store it
		ExplosionEventsSerializer explosionEventsSerializer = new ExplosionEventsSerializer(ExplosionHealerHandler.getExplosionEventList());
		explosionEventsSerializer.storeCreeperExplosionList(server);

		//Once we have stored the list, clear the current list from memory
		ExplosionHealerHandler.getExplosionEventList().clear();

		//Update the config by overriding the current values with new ones obtained via commands
		CONFIG.updateConfig();

	}

	//Return whether we are allowed to handle explosion events.
	// Security lock for avoiding any potential issues with concurrency
	public static boolean isExplosionHandlingUnlocked(){
		return healerHandlerLock;
	}

	public static void setHealerHandlerLock(boolean locked){
		healerHandlerLock = locked;
	}

	public static MinecraftServer getServerInstance(){
		return serverInstance;
	}

}