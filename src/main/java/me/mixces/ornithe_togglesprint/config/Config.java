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

public class Config extends BaseConfig {

	public static Config INSTANCE = new Config();

	/* Position */
	public final IntegerOption HUD_X = new IntegerOption("hudX", null, 0);
	public final IntegerOption HUD_Y = new IntegerOption("hudY", null, 0);

	/* Properties */
	public final BooleanOption ENABLED = new BooleanOption("enabled", null, false);
	public final BooleanOption STATE = new BooleanOption("state", null, false);

	/* Text */
	public final StringOption SPRINT_TOGGLED_TEXT = new StringOption("sprintToggledText", null, "[Sprinting (Toggled)]");
	public final StringOption SPRINT_VANILLA_TEXT = new StringOption("sprintVanillaText", null, "[Sprinting (Vanilla)]");
	public final StringOption FLYING_TEXT = new StringOption("flyingText", null, "[Flying]");
	public final StringOption SNEAKING_TEXT = new StringOption("sneakingText", null, "[Sneaking]");

	public String getToggleState() {
		return "Mod: " + (ENABLED.get() ? Formatting.GREEN + "Enabled" : Formatting.RED + "Disabled");
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
			ENABLED,
			STATE,
			SPRINT_TOGGLED_TEXT,
			SPRINT_VANILLA_TEXT,
			FLYING_TEXT,
			SNEAKING_TEXT
		);
	}
}
