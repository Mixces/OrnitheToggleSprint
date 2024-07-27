package me.mixces.ornithetogglesprint.config;

public class ConfigOptions extends ConfigUtils {

    @ConfigButton(name = "toggleSprint")
    public static boolean toggleSprint = true;

    public static ConfigOptions INSTANCE = new ConfigOptions();

    public ConfigOptions() {
        super("togglesprint.json");
    }

}
