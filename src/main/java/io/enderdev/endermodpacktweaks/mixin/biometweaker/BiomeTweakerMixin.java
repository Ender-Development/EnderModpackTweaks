package io.enderdev.endermodpacktweaks.mixin.biometweaker;

import me.superckl.biometweaker.BiomeTweaker;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;
import java.io.FilenameFilter;

@Mixin(value = BiomeTweaker.class, remap = false)
public class BiomeTweakerMixin {
	@Redirect(method = "parseScripts", at = @At(value = "INVOKE", target = "Ljava/io/File;listFiles(Ljava/io/FilenameFilter;)[Ljava/io/File;"))
	public File[] listFiles(File instance, FilenameFilter filenameFilter) {
		return FileUtils.listFiles(instance, new String[] {"cfg"}, true).toArray(new File[0]);
	}
}
