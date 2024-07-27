package me.mixces.ornithetogglesprint;


import me.mixces.ornithetogglesprint.config.ConfigOptions;
import net.ornithemc.osl.entrypoints.api.ModInitializer;
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents;

public class SprintHandler implements ModInitializer {

	public static boolean shouldToggle = false;

	@Override
	public void init() {
		MinecraftClientEvents.TICK_END.register(client -> {
			if (!ConfigOptions.toggleSprint) return;
			if (client.options.sprintKey.consumeClick()) {
				shouldToggle = !shouldToggle;
			}
		});
	}

}
