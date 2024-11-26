package me.mixces.ornithe_togglesprint.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import me.mixces.ornithe_togglesprint.handler.SprintHandler;
import me.mixces.ornithe_togglesprint.config.Config;
import me.mixces.ornithe_togglesprint.config.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.resource.manager.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Shadow
	private Minecraft minecraft;

	@Unique
	private SprintHandler hud;

	@Inject(
		method = "<init>",
		at = @At(
			value = "TAIL"
		)
	)
	private void toggleSprint$initSprintHud(Minecraft minecraft, ResourceManager resourceManager, CallbackInfo ci) {
		this.hud = new SprintHandler();
	}

	@Inject(
		method = "render(FJ)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GameGui;render(F)V",
			shift = At.Shift.AFTER
		)
	)
	private void toggleSprint$showStateGui(float tickDelta, long startTime, CallbackInfo ci) {
		if (Config.INSTANCE.ENABLED.get() && !minecraft.options.debugEnabled && !(minecraft.screen instanceof ConfigScreen)) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(770, 771, 1, 0);
			hud.drawText(Config.INSTANCE.HUD_X.get(), Config.INSTANCE.HUD_Y.get(), false);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
}
