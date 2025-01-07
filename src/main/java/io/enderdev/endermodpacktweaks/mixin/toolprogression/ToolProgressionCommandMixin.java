package io.enderdev.endermodpacktweaks.mixin.toolprogression;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tyra314.toolprogression.command.ToolProgressionCommand;

@Mixin(value = ToolProgressionCommand.class, remap = false)
public class ToolProgressionCommandMixin {
    @WrapOperation(method = "checkPermission", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V"))
    private void checkPermission(ICommandSender instance, ITextComponent component, Operation<Void> original) {
        // Stop sending the message
    }
}
