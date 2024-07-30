package me.mixces.ornithe_togglesprint.mixin;

import me.mixces.ornithe_togglesprint.config.ConfigOptions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsOptionsScreen.class)
public abstract class ControlsOptionsScreenMixin extends Screen {

	@Unique private OptionButtonWidget toggleSprint;

    @Inject(
		method = "init",
		at = @At(
			value = "TAIL"
		)
    )
    private void toggleSprint$addGuiButtons(CallbackInfo ci) {
		toggleSprint = new OptionButtonWidget(2004, width / 2 + 5, 42, "Sprint" + toggleSprint$getMode(ConfigOptions.toggleSprint));
        buttons.add(toggleSprint);
    }

    @Inject(
		method = "buttonClicked",
		at = @At(
			value = "HEAD"
		)
    )
    private void toggleSprint$onButtonPress(ButtonWidget button, CallbackInfo ci) {
        if (button.id == 2004) {
			ConfigOptions.toggleSprint = !ConfigOptions.toggleSprint;
			toggleSprint.message = "Sprint" + toggleSprint$getMode(ConfigOptions.toggleSprint);
        }
    }

	@Override
	public void removed() {
		ConfigOptions.INSTANCE.write();
		super.removed();
	}

	@Unique
	private String toggleSprint$getMode(boolean option) {
		return ": " + (option ? "Toggle" : "Hold");
	}

}
