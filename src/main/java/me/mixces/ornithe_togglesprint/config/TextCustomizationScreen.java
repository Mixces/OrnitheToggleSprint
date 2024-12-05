package me.mixces.ornithe_togglesprint.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.ornithemc.osl.config.api.config.option.StringOption;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class TextCustomizationScreen extends Screen {

	private final Config config;
	private final Screen parentScreen;
	private final List<TextFieldWidget> textFields = new ArrayList<>();
	private final List<String> labels = new ArrayList<>();
	private int currentIndex = 0;

	public TextCustomizationScreen(Screen parentScreen) {
		this.parentScreen = parentScreen;
		this.config = Config.INSTANCE;
	}

	@Override
	public void render(int mouseX, int mouseY, float tickDelta) {
		renderBackground();

		TextFieldWidget currentField = textFields.get(currentIndex);
		drawString(textRenderer, labels.get(currentIndex), width / 2 - 75, height / 2 - 33, 0xA0A0A0);
		currentField.render();

		drawCenteredString(textRenderer, "Toggle Sprint", width / 2, 15, 0xFFFFFF);
		super.render(mouseX, mouseY, tickDelta);
	}

	@Override
	protected void keyPressed(char chr, int key) {
		super.keyPressed(chr, key);
		textFields.get(currentIndex).keyPressed(chr, key);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		textFields.get(currentIndex).mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void buttonClicked(ButtonWidget button) {
		super.buttonClicked(button);
		if (!button.active) {
			return;
		}
		switch (button.id) {
			case 0:
				cycle(-1);
				break;
			case 1:
				cycle(1);
				break;
			case 2:
				saveConfig();
				minecraft.openScreen(parentScreen);
				break;
		}
	}

	@Override
	public void init() {
		super.init();
		Keyboard.enableRepeatEvents(true);
		buttons.clear();

		addTextFieldWidget("Sprinting Toggle Text", config.SPRINT_TOGGLED_TEXT);
		addTextFieldWidget("Sprinting Vanilla Text", config.SPRINT_VANILLA_TEXT);
		addTextFieldWidget("Flying Text", config.FLYING_TEXT);
		addTextFieldWidget("Riding Text", config.RIDING_TEXT);
		addTextFieldWidget("Sneaking Toggle Text", config.SNEAKING_TOGGLED_TEXT);
		addTextFieldWidget("Sneaking Vanilla Text", config.SNEAKING_VANILLA_TEXT);
		addTextFieldWidget("Descending Text", config.DESCENDING_TEXT);

		buttons.add(new ButtonWidget(0, width / 2 - 125, height / 2 - 20, 40, 20, "<-"));
		buttons.add(new ButtonWidget(1, width / 2 + 85, height / 2 - 20, 40, 20, "->"));
		buttons.add(new ButtonWidget(2, width / 2 - 75, height - 27, 150, 20, "Save & Exit"));
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		super.resize(minecraft, width, height);
		/* for some reason this is required */
		for (TextFieldWidget textField : textFields) {
			textField.x = width / 2 - 75;
			textField.y = height / 2 - 20;
		}
	}

	@Override
	public void tick() {
		super.tick();
		textFields.get(currentIndex).tick();
	}

	@Override
	public void removed() {
		super.removed();
		saveConfig();
	}

	private void addTextFieldWidget(String label, StringOption option) {
		TextFieldWidget textField = new TextFieldWidget(3, textRenderer, width / 2 - 75, height / 2 - 20, 150, 20);
		textField.setText(option.get());
		textFields.add(textField);
		labels.add(label);
	}

	private void saveConfig() {
		config.SPRINT_TOGGLED_TEXT.set(textFields.get(0).getText());
		config.SPRINT_VANILLA_TEXT.set(textFields.get(1).getText());
		config.FLYING_TEXT.set(textFields.get(2).getText());
		config.RIDING_TEXT.set(textFields.get(3).getText());
		config.SNEAKING_TOGGLED_TEXT.set(textFields.get(4).getText());
		config.SNEAKING_VANILLA_TEXT.set(textFields.get(5).getText());
		config.DESCENDING_TEXT.set(textFields.get(6).getText());
	}

	private void cycle(int direction) {
		textFields.get(currentIndex).setFocused(false);
		currentIndex = (currentIndex + direction + textFields.size()) % textFields.size();
		textFields.get(currentIndex).setFocused(true);
	}
}
