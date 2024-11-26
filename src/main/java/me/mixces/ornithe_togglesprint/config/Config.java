package me.mixces.ornithe_togglesprint.config;

import net.minecraft.text.Formatting;
import net.ornithemc.osl.config.api.ConfigScope;
import net.ornithemc.osl.config.api.LoadingPhase;
import net.ornithemc.osl.config.api.config.BaseConfig;
import net.ornithemc.osl.config.api.config.option.BooleanOption;
import net.ornithemc.osl.config.api.config.option.IntegerOption;
import net.ornithemc.osl.config.api.config.option.StringOption;
import net.ornithemc.osl.config.api.serdes.FileSerializerType;
import net.ornithemc.osl.config.api.serdes.SerializerTypes;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Config extends BaseConfig {

	public static Config INSTANCE = new Config();

	/* Position */
	public final IntegerOption HUD_X = new IntegerOption("hudX", null, 0);
	public final IntegerOption HUD_Y = new IntegerOption("hudY", null, 0);

	/* Properties */
	public final BooleanOption MOD_ENABLED = new BooleanOption("modEnabled", null, true);
	public final BooleanOption TOGGLE_SPRINT_ENABLED = new BooleanOption("toggleSprintEnabled", null, true);
	public final BooleanOption TOGGLE_SNEAK_ENABLED = new BooleanOption("toggleSneakEnabled", null, false);
	public final BooleanOption SPRINT_STATE = new BooleanOption("sprintState", null, false);
	public final BooleanOption SNEAK_STATE = new BooleanOption("sneakState", null, false);

	/* Text */
	public final StringOption SPRINT_TOGGLED_TEXT = new StringOption("sprintToggledText", null, "[Sprinting (Toggled)]");
	public final StringOption SPRINT_VANILLA_TEXT = new StringOption("sprintVanillaText", null, "[Sprinting (Vanilla)]");
	public final StringOption FLYING_TEXT = new StringOption("flyingText", null, "[Flying]");
	public final StringOption SNEAKING_TOGGLED_TEXT = new StringOption("sneakingToggledText", null, "[Sneaking (Toggled)]");
	public final StringOption SNEAKING_VANILLA_TEXT = new StringOption("sneakingVanillaText", null, "[Sneaking (Vanilla)]");

	public String getToggleState(BooleanOption option) {

		String displayName = option.getName()
			.replaceAll("Enabled", "")
			.replaceAll("([a-z])([A-Z])", "$1 $2")
			.trim();

		displayName = Arrays
			.stream(displayName.split(" "))
			.map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
			.collect(Collectors.joining(" "));

		return displayName + ": " + (option.get() ? Formatting.GREEN + "Enabled" : Formatting.RED + "Disabled");
	}

	@Override
	public String getNamespace() {
		return "";
	}

	@Override
	public String getName() {
		return "ToggleSprint";
	}

	@Override
	public String getSaveName() {
		return "togglesprint.json";
	}

	@Override
	public ConfigScope getScope() {
		return ConfigScope.GLOBAL;
	}

	@Override
	public LoadingPhase getLoadingPhase() {
		return LoadingPhase.READY;
	}

	@Override
	public FileSerializerType<?> getType() {
		return SerializerTypes.JSON;
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public void init() {
		registerOptions(
			"ToggleSprint",
			HUD_X,
			HUD_Y,
			MOD_ENABLED,
			TOGGLE_SPRINT_ENABLED,
			TOGGLE_SNEAK_ENABLED,
			SPRINT_STATE,
			SNEAK_STATE,
			SPRINT_TOGGLED_TEXT,
			SPRINT_VANILLA_TEXT,
			FLYING_TEXT,
			SNEAKING_TOGGLED_TEXT,
			SNEAKING_VANILLA_TEXT
		);
	}
}
