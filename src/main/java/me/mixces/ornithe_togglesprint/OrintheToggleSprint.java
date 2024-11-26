package me.mixces.ornithe_togglesprint;

import me.mixces.ornithe_togglesprint.config.Config;
import net.ornithemc.osl.config.api.ConfigManager;

import net.ornithemc.osl.entrypoints.api.ModInitializer;

public class OrintheToggleSprint implements ModInitializer {

	@Override
	public void init() {
		ConfigManager.register(Config.INSTANCE);
	}
}
