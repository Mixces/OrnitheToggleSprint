package me.mixces.ornithe_togglesprint.config;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.TextRenderer;
import net.ornithemc.osl.config.api.config.option.BooleanOption;

public class SwitchWidget extends ButtonWidget {

	public final BooleanOption option;

	public SwitchWidget(int id, int x, int y, Config config, BooleanOption option) {
		super(id, x, y, 28, 20, config.getToggleState(option));
		this.option = option;
	}

	@Override
	public void render(Minecraft minecraft, int mouseX, int mouseY) {
		int boxWidth = width / 2;
		int boxHeight = height / 2;
		int toggleWidth = 4;

		TextRenderer textRenderer = minecraft.textRenderer;
		minecraft.getTextureManager().bind(WIDGETS_LOCATION);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		/* background */
		drawTexture(x, y + 2, 0, 46, boxWidth, boxHeight);
		drawTexture(x, y + 8, 0, 56, boxWidth, boxHeight);
		drawTexture(x + boxWidth, y + 2, 200 - boxWidth, 46, boxWidth, boxHeight);
		drawTexture(x + boxWidth, y + 8, 200 - boxWidth, 66 - boxHeight, boxWidth, boxHeight);

		int toggleX = option.get() ? x + boxWidth * 2 - toggleWidth * 2 : x;
		hovered = mouseX >= toggleX && mouseY >= y && mouseX < toggleX + boxWidth && mouseY < y + boxHeight * 2;
		boolean buttonHovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
		int labelColor = active ? (buttonHovered ? 0xFFFFA0 : 0xE0E0E0) : 0xA0A0A0;
		int booleanColor = option.get() ? active ? 0x00FF00 : 0xA0A0A0 : active ? 0xFF0000 : 0xA0A0A0;
		int switchHighlight = active ? (hovered ? 20 : 0) : -20;

		/* toggle */
		drawTexture(toggleX, y, 0, 66 + switchHighlight, toggleWidth, boxHeight);
		drawTexture(toggleX, y + boxHeight, 0, 76 + switchHighlight, toggleWidth, boxHeight);
		drawTexture(toggleX + toggleWidth, y, 200 - toggleWidth, 66 + switchHighlight, toggleWidth, boxHeight);
		drawTexture(toggleX + toggleWidth, y + boxHeight, 200 - toggleWidth, 76 + switchHighlight, toggleWidth, boxHeight);

		drawString(textRenderer, option.get() ? "On" : "Off", x + (option.get() ? 5 : 9), y + 6, booleanColor);

		drawString(textRenderer, message, x - 80, y + height / 4 + 1, labelColor);
	}
}
