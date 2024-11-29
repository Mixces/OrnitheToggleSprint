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
	private TextFieldWidget sprintToggledTextField;
	private TextFieldWidget sprintVanillaTextField;
	private TextFieldWidget flyingTextField;
	private TextFieldWidget ridingTextField;
	private TextFieldWidget sneakingToggledTextField;
	private TextFieldWidget sneakingVanillaTextField;
	private TextFieldWidget descendingTextField;

	public TextCustomizationScreen(Screen parentScreen) {
		this.parentScreen = parentScreen;
		this.config = Config.INSTANCE;
	}

	@Override
	public void render(int mouseX, int mouseY, float tickDelta) {
		renderBackground();

		drawString(textRenderer, "Sprinting Toggle Text", width / 2 - 75, (int) (height * 0.2f) - 12, 0xA0A0A0);
		sprintToggledTextField.render();

		drawString(textRenderer, "Sprinting Vanilla Text", width / 2 - 2 - 150, (int) (height * 0.35f) - 12, 0xA0A0A0);
		sprintVanillaTextField.render();

		drawString(textRenderer, "Flying Text", width / 2 + 4, (int) (height * 0.35f) - 12, 0xA0A0A0);
		flyingTextField.render();

		drawString(textRenderer, "Riding Text", width / 2 - 2 - 150, (int) (height * 0.5f) - 12, 0xA0A0A0);
		ridingTextField.render();

		drawString(textRenderer, "Sneaking Toggle Text", width / 2 + 4, (int) (height * 0.5f) - 12, 0xA0A0A0);
		sneakingToggledTextField.render();

		drawString(textRenderer, "Sneaking Vanilla Text", width / 2 - 2 - 150, (int) (height * 0.65f) - 12, 0xA0A0A0);
		sneakingVanillaTextField.render();

		drawString(textRenderer, "Descending Text", width / 2 + 4, (int) (height * 0.65f) - 12, 0xA0A0A0);
		descendingTextField.render();

		super.render(mouseX, mouseY, tickDelta);
	}

	@Override
	protected void keyPressed(char chr, int key) {
		super.keyPressed(chr, key);

		sprintToggledTextField.keyPressed(chr, key);
		sprintVanillaTextField.keyPressed(chr, key);
		flyingTextField.keyPressed(chr, key);
		ridingTextField.keyPressed(chr, key);
		sneakingToggledTextField.keyPressed(chr, key);
		sneakingVanillaTextField.keyPressed(chr, key);
		descendingTextField.keyPressed(chr, key);

		if (key == 15) {
			cycleFocus();
		}
	}

	private void cycleFocus() {
		if (sprintToggledTextField.isFocused()) {
			sprintToggledTextField.setFocused(false);
			sprintVanillaTextField.setFocused(true);
		} else if (sprintVanillaTextField.isFocused()) {
			sprintVanillaTextField.setFocused(false);
			flyingTextField.setFocused(true);
		} else if (flyingTextField.isFocused()) {
			flyingTextField.setFocused(false);
			ridingTextField.setFocused(true);
		} else if (ridingTextField.isFocused()) {
			ridingTextField.setFocused(false);
			sneakingToggledTextField.setFocused(true);
		} else if (sneakingToggledTextField.isFocused()) {
			sneakingToggledTextField.setFocused(false);
			sneakingVanillaTextField.setFocused(true);
		} else if (sneakingVanillaTextField.isFocused()) {
			sneakingVanillaTextField.setFocused(false);
			descendingTextField.setFocused(true);
		} else {
			descendingTextField.setFocused(false);
			sprintToggledTextField.setFocused(true);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		sprintToggledTextField.mouseClicked(mouseX, mouseY, mouseButton);
		sprintVanillaTextField.mouseClicked(mouseX, mouseY, mouseButton);
		flyingTextField.mouseClicked(mouseX, mouseY, mouseButton);
		ridingTextField.mouseClicked(mouseX, mouseY, mouseButton);
		sneakingToggledTextField.mouseClicked(mouseX, mouseY, mouseButton);
		sneakingVanillaTextField.mouseClicked(mouseX, mouseY, mouseButton);
		descendingTextField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void buttonClicked(ButtonWidget button) {
		super.buttonClicked(button);
		if (button.id == 0) {
			config.SPRINT_TOGGLED_TEXT.set(sprintToggledTextField.getText());
			config.SPRINT_VANILLA_TEXT.set(sprintVanillaTextField.getText());
			config.FLYING_TEXT.set(flyingTextField.getText());
			config.RIDING_TEXT.set(ridingTextField.getText());
			config.SNEAKING_TOGGLED_TEXT.set(sneakingToggledTextField.getText());
			config.SNEAKING_VANILLA_TEXT.set(sneakingVanillaTextField.getText());
			config.DESCENDING_TEXT.set(descendingTextField.getText());
			minecraft.openScreen(parentScreen);
		}
	}

	@Override
	public void init() {
		super.init();
		Keyboard.enableRepeatEvents(true);
		buttons.clear();
		buttons.add(new ButtonWidget(0, width / 2 - 75, height - 27, 150, 20, "Save & Exit"));

		sprintToggledTextField = new TextFieldWidget(0, textRenderer, width / 2 - 75, (int) (height * 0.2f), 150, 20);
		sprintToggledTextField.setFocused(true);
		sprintToggledTextField.setText(config.SPRINT_TOGGLED_TEXT.get());

		sprintVanillaTextField = new TextFieldWidget(1, textRenderer, width / 2 - 2 - 150, (int) (height * 0.35), 150, 20);
		sprintVanillaTextField.setText(config.SPRINT_VANILLA_TEXT.get());

		flyingTextField = new TextFieldWidget(2, textRenderer, width / 2 + 4, (int) (height * 0.35), 150, 20);
		flyingTextField.setText(config.FLYING_TEXT.get());

		ridingTextField = new TextFieldWidget(3, textRenderer, width / 2 - 2 - 150, (int) (height * 0.5), 150, 20);
		ridingTextField.setText(config.RIDING_TEXT.get());

		sneakingToggledTextField = new TextFieldWidget(4, textRenderer, width / 2 + 4, (int) (height * 0.5), 150, 20);
		sneakingToggledTextField.setText(config.SNEAKING_TOGGLED_TEXT.get());

		sneakingVanillaTextField = new TextFieldWidget(5, textRenderer, width / 2 - 2 - 150, (int) (height * 0.65), 150, 20);
		sneakingVanillaTextField.setText(config.SNEAKING_VANILLA_TEXT.get());

		descendingTextField = new TextFieldWidget(6, textRenderer, width / 2 + 4, (int) (height * 0.65), 150, 20);
		descendingTextField.setText(config.DESCENDING_TEXT.get());
	}

	@Override
	public void tick() {
		super.tick();
		sprintToggledTextField.tick();
		sprintVanillaTextField.tick();
		flyingTextField.tick();
		ridingTextField.tick();
		sneakingToggledTextField.tick();
		sneakingVanillaTextField.tick();
		descendingTextField.tick();
	}

	@Override
	public void removed() {
		super.removed();
	}
}
