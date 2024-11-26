package me.mixces.ornithe_togglesprint.mixin;

import me.mixces.ornithe_togglesprint.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.player.input.GameInput;
import net.minecraft.client.player.input.PlayerInput;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameInput.class)
public class GameInputMixin extends PlayerInput {

	@Shadow
	@Final
	private GameOptions options;

	@Inject(
		method = "tick",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.GETFIELD,
			target = "Lnet/minecraft/client/player/input/GameInput;sneaking:Z"
		)
	)
	private void toggleSprint$setSneakState(CallbackInfo ci) {
		if (Config.INSTANCE.TOGGLE_SNEAK_ENABLED.get() && Config.INSTANCE.MOD_ENABLED.get() && Minecraft.getInstance().screen == null) {
			sneaking = Config.INSTANCE.SNEAK_STATE.get() || options.sneakKey.isPressed();
		}
	}
}
