package me.mixces.ornithe_togglesprint.config;

import net.ornithemc.osl.config.api.ConfigScope;
import net.ornithemc.osl.config.api.LoadingPhase;
import net.ornithemc.osl.config.api.config.BaseConfig;
import net.ornithemc.osl.config.api.config.option.BooleanOption;
import net.ornithemc.osl.config.api.config.option.IntegerOption;
import net.ornithemc.osl.config.api.serdes.FileSerializerType;
import net.ornithemc.osl.config.api.serdes.SerializerTypes;

public class Config extends BaseConfig {

	/* Position */
	public static final IntegerOption HUD_X = new IntegerOption("hudX", null, 0);
	public static final IntegerOption HUD_Y = new IntegerOption("hudY", null, 0);

	/* Properties */
	public static final BooleanOption TOGGLE_SPRINT = new BooleanOption("toggleSprint", null, false);

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
			TOGGLE_SPRINT
		);
	}
}
