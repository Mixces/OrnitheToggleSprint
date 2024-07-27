package me.mixces.ornithetogglesprint;

import me.mixces.ornithetogglesprint.config.ConfigOptions;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.ornithemc.osl.entrypoints.api.ModInitializer;

public class OrintheToggleSprint implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("OrnitheToggleSprint");

	@Override
	public void init() {
		LOGGER.info("initializing example mod!");
		ConfigOptions.INSTANCE.preInit(FabricLoader.getInstance().getConfigDir().toFile());
	}

}
