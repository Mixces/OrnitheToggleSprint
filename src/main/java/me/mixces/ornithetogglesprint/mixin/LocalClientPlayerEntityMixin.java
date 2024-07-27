package me.mixces.ornithetogglesprint.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.mixces.ornithetogglesprint.SprintHandler;
import me.mixces.ornithetogglesprint.config.ConfigOptions;
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
		return (ConfigOptions.toggleSprint && SprintHandler.shouldToggle) || original;
	}

}
