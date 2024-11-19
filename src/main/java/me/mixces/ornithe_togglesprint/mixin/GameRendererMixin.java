package me.mixces.ornithe_togglesprint.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import me.mixces.ornithe_togglesprint.SprintHandler;
import me.mixces.ornithe_togglesprint.config.Config;
import me.mixces.ornithe_togglesprint.config.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Shadow
	private Minecraft minecraft;

	@Inject(
		method = "render(FJ)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GameGui;render(F)V",
			shift = At.Shift.AFTER
		)
	)
	private void toggleSprint$showStateGui(float tickDelta, long startTime, CallbackInfo ci) {
		//TODO: fix holding down sprint key bugging out hud
		if (!minecraft.options.debugEnabled && !(minecraft.screen instanceof ConfigScreen)) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(770, 771, 1, 0);
			SprintHandler.drawText(minecraft, Config.HUD_X.get(), Config.HUD_Y.get());
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
}
