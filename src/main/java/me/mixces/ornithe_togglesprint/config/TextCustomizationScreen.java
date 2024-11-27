package me.mixces.ornithe_togglesprint.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.lwjgl.input.Keyboard;

/*
			SPRINT_TOGGLED_TEXT,
			SPRINT_VANILLA_TEXT,
			FLYING_TEXT,
			RIDING_TEXT,
			SNEAKING_TOGGLED_TEXT,
			SNEAKING_VANILLA_TEXT,
			DESCENDING_TEXT
 */

/**
 * Based off of
 * @see net.minecraft.client.gui.screen.menu.AddServerScreen
 */
public class TextCustomizationScreen extends Screen {

	private final Config config;
	private final Screen parentScreen;
	private TextFieldWidget textField;

	public TextCustomizationScreen(Screen parentScreen) {
		this.parentScreen = parentScreen;
		this.config = Config.INSTANCE;
	}

	@Override
	public void render(int mouseX, int mouseY, float tickDelta) {
		renderBackground();
		drawString(textRenderer, "Sprinting Toggle Text", width / 2 - 100, 53, 0xA0A0A0);
		textField.render();
		super.render(mouseX, mouseY, tickDelta);
	}

	@Override
	protected void keyPressed(char chr, int key) {
		super.keyPressed(chr, key);
		textField.keyPressed(chr, key);
		if (key == 15) {
			textField.setFocused(!textField.isFocused());
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		textField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void buttonClicked(ButtonWidget button) {
		super.buttonClicked(button);
		if (button.id == 0) {
			config.SPRINT_TOGGLED_TEXT.set(textField.getText());
			minecraft.openScreen(parentScreen);
		}
	}

	@Override
	public void init() {
		super.init();
		Keyboard.enableRepeatEvents(true);
		buttons.clear();
		buttons.add(new ButtonWidget(0, width / 2 - 100, height / 4 + 96 + 18, "Done"));
		textField = new TextFieldWidget(0, textRenderer, width / 2 - 100, 66, 200, 20);
		textField.setFocused(true);
		textField.setText(config.SPRINT_TOGGLED_TEXT.get());
	}

	@Override
	public void tick() {
		super.tick();
		textField.tick();
	}

	@Override
	public void removed() {
		super.removed();
	}
}
