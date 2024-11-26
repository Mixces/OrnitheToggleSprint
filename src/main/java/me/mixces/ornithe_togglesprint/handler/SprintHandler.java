package me.mixces.ornithe_togglesprint.handler;


import me.mixces.ornithe_togglesprint.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.ornithemc.osl.entrypoints.api.ModInitializer;
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents;

public class SprintHandler implements ModInitializer {

	private final Config config;
	private final Minecraft minecraft;
	private static boolean sprintHeld = false;

	public SprintHandler() {
		this.minecraft = Minecraft.getInstance();
		this.config = Config.INSTANCE;
	}

	@Override
	public void init() {
		MinecraftClientEvents.TICK_END.register(client -> {
			if (!config.ENABLED.get()) return;
			if (client.options.sprintKey.consumeClick()) {
				if (!sprintHeld) {
					config.STATE.set(!config.STATE.get());
				}
				sprintHeld = true;
			} else {
				sprintHeld = false;
			}
		});
	}

	public void drawText(float x, float y, boolean editing) {
		minecraft.textRenderer.draw(getText(editing), x, y, 0xFFFFFFFF, true);
	}

	public String getText(boolean editing) {
		final String sprintToggledText = config.SPRINT_TOGGLED_TEXT.get();
		if (editing) {
			return sprintToggledText;
		}

		final LocalClientPlayerEntity player = minecraft.player;
		if (player.abilities.flying) {
			return config.FLYING_TEXT.get();
		}
		if (player.isSneaking()) {
			return config.SNEAKING_TEXT.get();
		}
		if (config.STATE.get()) {
			return sprintToggledText;
		}
		if (player.isSprinting()) {
			return config.SPRINT_VANILLA_TEXT.get();
		}
		return "";
	}
}
