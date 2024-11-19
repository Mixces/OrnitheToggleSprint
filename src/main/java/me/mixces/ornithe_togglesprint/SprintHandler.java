package me.mixces.ornithe_togglesprint;


import me.mixces.ornithe_togglesprint.config.Config;
import net.minecraft.client.Minecraft;
import net.ornithemc.osl.entrypoints.api.ModInitializer;
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents;

public class SprintHandler implements ModInitializer {

	public static boolean shouldToggle = false;

	@Override
	public void init() {
		MinecraftClientEvents.TICK_END.register(client -> {
			if (!Config.TOGGLE_SPRINT.get()) return;
			if (client.options.sprintKey.consumeClick()) {
				shouldToggle = !shouldToggle;
			}
		});
	}

	public static void drawText(Minecraft minecraft, float x, float y) {
		minecraft.textRenderer.draw(getText(), x, y, 0xFFFFFFFF, true);
	}

	public static String getText() {
		return "[Sprinting (" + (SprintHandler.shouldToggle ? "Toggled)]" : "Vanilla)]");
	}
}
