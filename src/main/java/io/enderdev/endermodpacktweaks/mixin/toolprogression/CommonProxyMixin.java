package io.enderdev.endermodpacktweaks.mixin.toolprogression;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tyra314.toolprogression.proxy.CommonProxy;

@Mixin(value = CommonProxy.class, remap = false)
public class CommonProxyMixin {
    @ModifyArg(method = "preInit", at = @At(value = "INVOKE", target = "Ljava/io/File;<init>(Ljava/lang/String;Ljava/lang/String;)V"), index = 1)
    private String modifyConfigPath(String path) {
        switch (path) {
            case "tool_progression/block_overwrites.cfg":
                return "tool_progression/overwrites/block.cfg";
            case "tool_progression/tool_overwrites.cfg":
                return "tool_progression/overwrites/tool.cfg";
            case "tool_progression/materials_overwrites.cfg":
                return "tool_progression/overwrites/materials.cfg";
            case "tool_progression/blocks.cfg":
                return "tool_progression/output/blocks.cfg";
            case "tool_progression/tools.cfg":
                return "tool_progression/output/tools.cfg";
            case "tool_progression/materials.cfg":
                return "tool_progression/output/materials.cfg";
            default:
                return path;
        }
    }
}
