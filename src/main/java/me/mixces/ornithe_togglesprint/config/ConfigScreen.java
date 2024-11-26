package me.mixces.ornithe_togglesprint.config;

import me.mixces.ornithe_togglesprint.handler.SprintHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.ornithemc.osl.config.api.ConfigManager;

public class ConfigScreen extends Screen {

	private final SprintHandler hud;
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
		this.hud = new SprintHandler();
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

		if (config.ENABLED.get()) {
			int x = config.HUD_X.get();
			int y = config.HUD_Y.get();

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

		buttons.add(new ButtonWidget(0, width / 2 - 75, height - 75, 150, 20, config.getToggleState()));
		buttons.add(new ButtonWidget(1, width / 2 - 75, height - 51, 150, 20, "Reset Position"));
		buttons.add(new ButtonWidget(2, width / 2 - 75, height - 27, 150, 20, "Done"));
	}

	@Override
	protected void buttonClicked(ButtonWidget button) {
		super.buttonClicked(button);
		if (!button.active) {
			return;
		}
		switch (button.id) {
			case 0:
				config.ENABLED.set(!config.ENABLED.get());
				button.message = config.getToggleState();
				break;
			case 1:
				config.HUD_X.set(4);
				config.HUD_Y.set(height - 13);
				break;
			case 2:
				ConfigManager.save(Config.INSTANCE);
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
		ConfigManager.save(Config.INSTANCE);
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

	private void drawOutlineBox(int x, int y) {
		drawHorizontalLine(x - hudBound, x + hudWidth + hudBound, y - hudBound, 0x80FFFFFF);
		drawVerticalLine(x - hudBound, y - hudBound, y + hudHeight + hudBound, 0x80FFFFFF);
		drawHorizontalLine(x - hudBound, x + hudWidth + hudBound, y + hudHeight + hudBound, 0x80FFFFFF);
		drawVerticalLine(x + hudWidth + hudBound, y - hudBound, y + hudHeight + hudBound, 0x80FFFFFF);
	}
}
