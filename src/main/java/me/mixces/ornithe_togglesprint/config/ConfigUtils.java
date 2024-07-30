package me.mixces.ornithe_togglesprint.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class ConfigUtils {

    private JsonObject configData;
    private final Field[] configFields = ConfigOptions.class.getDeclaredFields();
    private final Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
    private File configFile;
    private final String configFileName;

    public ConfigUtils(String configFile) {
        configFileName = configFile;
    }

    public void init(File file) {
        configFile = new File(file, configFileName);
        load();
        write();
        Runtime.getRuntime().addShutdownHook(new Thread(this::write));
    }

    private void load() {
        try {
            if (!configFile.exists()) {
                return;
            }
            String fileContent = FileUtils.readFileToString(configFile);
            configData = fileContent.isEmpty() ? new JsonObject() : JsonParser.parseString(fileContent).getAsJsonObject();
            for (Field field : configFields) {
                if (field.isAnnotationPresent(ConfigButton.class)) {
                    ConfigButton configButton = field.getAnnotation(ConfigButton.class);
                    if (configData.has(configButton.name())) {
                        field.setBoolean(null, configData.get(configButton.name()).getAsBoolean());
                    }
                }
            }
        } catch (IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write() {
        try {
            configData = new JsonObject();
            for (Field field : configFields) {
                if (field.isAnnotationPresent(ConfigButton.class)) {
                    ConfigButton configButton = field.getAnnotation(ConfigButton.class);
                    configData.addProperty(configButton.name(), (boolean) field.get(null));
                }
            }
            FileUtils.writeStringToFile(configFile, gsonBuilder.toJson(configData));
        } catch (IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
