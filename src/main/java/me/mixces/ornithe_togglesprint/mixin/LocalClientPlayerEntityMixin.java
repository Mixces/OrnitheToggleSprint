package me.mixces.ornithe_togglesprint.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.mixces.ornithe_togglesprint.SprintHandler;
import me.mixces.ornithe_togglesprint.config.ConfigOptions;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalClientPlayerEntity.class)
public abstract class LocalClientPlayerEntityMixin {

	@ModifyExpressionValue(
		method = "tickAi",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/options/KeyBinding;isPressed()Z"
		)
	)
	private boolean toggleSprint$setSprintState(boolean original) {
		if (SprintHandler.shouldReset) {
			SprintHandler.shouldReset = false;
			return false;
		}
		return (ConfigOptions.toggleSprint && SprintHandler.shouldToggle) || original;
	}
}
