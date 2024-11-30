package me.mixces.ornithe_togglesprint.config;

import me.mixces.ornithe_togglesprint.handler.StateHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

public class ConfigScreen extends Screen {

	private final StateHandler hud;
	private final Config config;
	private final Screen parentScreen;
	/* hud editor properties */
	private int hudWidth;
	private final int hudHeight = 9;
	private final int hudBound = 4;
	/* mouse movement */
	private boolean isDragging = false;
	private int dragOffsetX;
	private int dragOffsetY;

	public ConfigScreen(Screen parentScreen) {
		this.parentScreen = parentScreen;
		this.config = Config.INSTANCE;
		this.hud = new StateHandler();
	}

	@Override
	public void render(int mouseX, int mouseY, float tickDelta) {
		renderBackground();
		if (isDragging) {
			drawGridlines();
			config.HUD_X.set(config.HUD_X.get() + (mouseX - dragOffsetX));
			config.HUD_Y.set(config.HUD_Y.get() + (mouseY - dragOffsetY));
		}
		dragOffsetX = mouseX;
		dragOffsetY = mouseY;

		if (config.ENABLE_MOD_ENABLED.get()) {
			int x = config.HUD_X.get();
			int y = config.HUD_Y.get();

			if (isMouseOver(mouseX, mouseY)) {
				drawSelectionBox(x, y);
			}
			drawOutlineBox(x, y);
			hud.drawText(x, y, true);
		}

		if (!isDragging) {
			super.render(mouseX, mouseY, tickDelta);
		}
	}

	@Override
	public void init() {
		super.init();
		hudWidth = textRenderer.getWidth(hud.getText(true));

		buttons.add(new SwitchWidget(0, width / 2 + 27, height / 2 - 48, config, config.ENABLE_MOD_ENABLED));
		buttons.add(new SwitchWidget(1, width / 2 + 27, height / 2 - 24, config, config.TOGGLE_SPRINT_ENABLED));
		buttons.add(new SwitchWidget(2, width / 2 + 27, height / 2, config, config.TOGGLE_SNEAK_ENABLED));
		buttons.add(new ButtonWidget(3, width / 2 - 75, height / 2 + 24, 150, 20, "Customize Text"));

		buttons.add(new ButtonWidget(4, width / 2 - 154, height - 27, 150, 20, "Reset Position"));
		buttons.add(new ButtonWidget(5, width / 2 + 4, height - 27, 150, 20, "Done"));
	}

	@Override
	protected void buttonClicked(ButtonWidget button) {
		super.buttonClicked(button);
		if (!button.active) {
			return;
		}
		switch (button.id) {
			case 0:
				config.ENABLE_MOD_ENABLED.set(!config.ENABLE_MOD_ENABLED.get());
				button.message = config.getToggleState(config.ENABLE_MOD_ENABLED);

				/* mark button as inactive if mod is disabled */
				boolean modEnabled = config.ENABLE_MOD_ENABLED.get();
				buttons.get(1).active = modEnabled;
				buttons.get(2).active = modEnabled;
				buttons.get(3).active = modEnabled;
				buttons.get(4).active = modEnabled;
				break;
			case 1:
				config.TOGGLE_SPRINT_ENABLED.set(!config.TOGGLE_SPRINT_ENABLED.get());
				button.message = config.getToggleState(config.TOGGLE_SPRINT_ENABLED);
				break;
			case 2:
				config.TOGGLE_SNEAK_ENABLED.set(!config.TOGGLE_SNEAK_ENABLED.get());
				button.message = config.getToggleState(config.TOGGLE_SNEAK_ENABLED);
				break;
			case 3:
				minecraft.openScreen(new TextCustomizationScreen(this));
				break;
			case 4:
				config.HUD_X.set(4);
				config.HUD_Y.set(height - 13);
				break;
			case 5:
				minecraft.openScreen(parentScreen);
				break;
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (!isDragging && mouseButton == 0 && isMouseOver(mouseX, mouseY)) {
			isDragging = true;
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		super.mouseReleased(mouseX, mouseY, mouseButton);
		isDragging = false;
	}

	@Override
	public void removed() {
		super.removed();
	}

	private void drawGridlines() {
		drawVerticalLine(width / 3, 0, height, 0x80FFFFFF);
		drawVerticalLine(width * 2 / 3, 0, height, 0x80FFFFFF);
		drawHorizontalLine(0, width, height / 3, 0x80FFFFFF);
		drawHorizontalLine(0, width, height * 2 / 3, 0x80FFFFFF);
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		int x = config.HUD_X.get();
		int y = config.HUD_Y.get();
		return mouseX >= x - hudBound && mouseY >= y - hudBound && mouseX < x + hudBound + hudWidth && mouseY < y + hudBound + hudHeight;
	}

	private void drawSelectionBox(int x, int y) {
		fill(x - hudBound, y - hudBound, x + hudWidth + hudBound, y + hudHeight + hudBound, 0x40FFFFFF);
	}

	private void drawOutlineBox(int x, int y) {
		drawHorizontalLine(x - hudBound, x + hudWidth + hudBound, y - hudBound, 0x80FFFFFF);
		drawVerticalLine(x - hudBound, y - hudBound, y + hudHeight + hudBound, 0x80FFFFFF);
		drawHorizontalLine(x - hudBound, x + hudWidth + hudBound, y + hudHeight + hudBound, 0x80FFFFFF);
		drawVerticalLine(x + hudWidth + hudBound, y - hudBound, y + hudHeight + hudBound, 0x80FFFFFF);
	}
}
