package me.mixces.ornithe_togglesprint.handler;


import me.mixces.ornithe_togglesprint.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.ornithemc.osl.entrypoints.api.ModInitializer;
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents;

public class StateHandler implements ModInitializer {

	private final Config config;
	private final Minecraft minecraft;
	private static boolean sprintHeld, sneakHeld = false;

	public StateHandler() {
		this.minecraft = Minecraft.getInstance();
		this.config = Config.INSTANCE;
	}

	@Override
	public void init() {
		MinecraftClientEvents.TICK_END.register(client -> {
			if (!config.TOGGLE_SPRINT_ENABLED.get() || !config.MOD_ENABLED.get()) return;
			if (client.options.sprintKey.consumeClick()) {
				if (!sprintHeld) {
					config.SPRINT_STATE.set(!config.SPRINT_STATE.get());
				}
				sprintHeld = true;
			} else {
				sprintHeld = false;
			}
		});

		MinecraftClientEvents.TICK_END.register(client -> {
			if (!config.TOGGLE_SNEAK_ENABLED.get() || !config.MOD_ENABLED.get()) return;
			if (client.options.sneakKey.consumeClick()) {
				if (!sneakHeld) {
					config.SNEAK_STATE.set(!config.SNEAK_STATE.get());
				}
				sneakHeld = true;
			} else {
				sneakHeld = false;
			}
		});
	}

	public void drawText(float x, float y, boolean editing) {
		minecraft.textRenderer.draw(getText(editing), x, y, 0xFFFFFFFF, true);
	}

	public boolean isEmptyText() {
		return getText(false).isEmpty();
	}

	public String getText(boolean editing) {
		final String sprintToggledText = config.SPRINT_TOGGLED_TEXT.get();
		if (editing) {
			return sprintToggledText;
		}

		final LocalClientPlayerEntity player = minecraft.player;
		if (player.isSneaking() && player.abilities.flying) {
			return config.DESCENDING_TEXT.get();
		}
		if (player.abilities.flying) {
			return config.FLYING_TEXT.get();
		}
		if (player.hasVehicle()) {
			return config.RIDING_TEXT.get();
		}
		if (config.TOGGLE_SNEAK_ENABLED.get() && config.SNEAK_STATE.get()) {
			return config.SNEAKING_TOGGLED_TEXT.get();
		}
		if (player.isSneaking()) {
			return config.SNEAKING_VANILLA_TEXT.get();
		}
		if (config.TOGGLE_SPRINT_ENABLED.get() && config.SPRINT_STATE.get()) {
			return sprintToggledText;
		}
		if (player.isSprinting()) {
			return config.SPRINT_VANILLA_TEXT.get();
		}
		return "";
	}
}
