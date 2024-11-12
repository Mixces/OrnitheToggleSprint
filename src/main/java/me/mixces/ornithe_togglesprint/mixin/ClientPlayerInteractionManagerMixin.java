package me.mixces.ornithe_togglesprint.mixin;

import me.mixces.ornithe_togglesprint.SprintHandler;
import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

	@Inject(
		method = "attackEntity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/network/handler/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V",
			shift = At.Shift.AFTER
		)
	)
	private void toggleSprint$onAttackPacket(PlayerEntity player, Entity target, CallbackInfo ci) {
		SprintHandler.shouldReset = true;
	}
}
