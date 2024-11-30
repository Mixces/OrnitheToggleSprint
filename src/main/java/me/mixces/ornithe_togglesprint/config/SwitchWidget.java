package me.mixces.ornithe_togglesprint.config;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.TextRenderer;
import net.ornithemc.osl.config.api.config.option.BooleanOption;

public class SwitchWidget extends ButtonWidget {

	public final BooleanOption option;

	public SwitchWidget(int id, int x, int y, Config config, BooleanOption option) {
		super(id, x, y, 28, 20, config.getToggleStateNo(option));
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

		int v = active ? (hovered ? 20 : 0) : -20;
		/* toggle */
		drawTexture(toggleX, y, 0, 66 + v, toggleWidth, boxHeight);
		drawTexture(toggleX, y + boxHeight, 0, 76 + v, toggleWidth, boxHeight);
		drawTexture(toggleX + toggleWidth, y, 200 - toggleWidth, 66 + v, toggleWidth, boxHeight);
		drawTexture(toggleX + toggleWidth, y + boxHeight, 200 - toggleWidth, 76 + v, toggleWidth, boxHeight);

		drawString(textRenderer, message, x - 80, y + height / 4, 0xFFFFFF);
	}
}
