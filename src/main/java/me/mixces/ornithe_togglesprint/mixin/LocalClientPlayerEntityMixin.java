package me.mixces.ornithe_togglesprint.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.mixces.ornithe_togglesprint.config.Config;
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
		return (Config.INSTANCE.TOGGLE_SPRINT_ENABLED.get() && Config.INSTANCE.ENABLE_MOD_ENABLED.get() && Config.INSTANCE.SPRINT_STATE.get()) || original;
	}
}
