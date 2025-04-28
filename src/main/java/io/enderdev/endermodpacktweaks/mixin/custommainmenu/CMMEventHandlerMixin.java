package io.enderdev.endermodpacktweaks.mixin.custommainmenu;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.enderdev.endermodpacktweaks.EnderModpackTweaks;
import io.enderdev.endermodpacktweaks.features.modpackinfo.SlideButton;
import lumien.custommainmenu.gui.GuiCustom;
import lumien.custommainmenu.gui.GuiCustomButton;
import lumien.custommainmenu.gui.GuiCustomWrappedButton;
import lumien.custommainmenu.gui.GuiFakeMain;
import lumien.custommainmenu.handler.CMMEventHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Iterator;

@Mixin(value = CMMEventHandler.class, remap = false)
public class CMMEventHandlerMixin {
    @Shadow
    GuiCustom actualGui;

    @WrapMethod(method = "initGuiPost")
    private void addBackButtons(GuiScreenEvent.InitGuiEvent.Post event, Operation<Void> original) {
        if (event.getGui() instanceof GuiFakeMain) {
            HashMap<Integer, GuiButton> removedButtons = new HashMap<>();
            Iterator<GuiButton> iterator = event.getButtonList().iterator();

            while(iterator.hasNext()) {
                GuiButton o = iterator.next();
                if (!(o instanceof GuiCustomButton)) {
                    iterator.remove();
                    removedButtons.put(o.id, o);
                    if (o.id == 666 && Loader.isModLoaded("OpenEye")) {
                        EnderModpackTweaks.LOGGER.debug("Found OpenEye button, use a wrapped button to config this. ({})", o.id);
                    } else if (o.id == 404 && Loader.isModLoaded("VersionChecker")) {
                        EnderModpackTweaks.LOGGER.debug("Found VersionChecker button, use a wrapped button to config this. ({})", o.id);
                    } else {
                        EnderModpackTweaks.LOGGER.debug("Found unsupported button, use a wrapped button to config this. ({})", o.id);
                    }
                }
            }

            for(GuiButton o : actualGui.getButtonList()) {
                if (o instanceof GuiCustomWrappedButton) {
                    GuiCustomWrappedButton b = (GuiCustomWrappedButton)o;
                    EnderModpackTweaks.LOGGER.debug("Initiating Wrapped Button {} with {}", b.wrappedButtonID, removedButtons.get(b.wrappedButtonID));
                    b.init(removedButtons.get(b.wrappedButtonID));
                }
            }

            removedButtons.forEach(((integer, guiButton) -> {
                if (guiButton instanceof SlideButton) {
                    guiButton.x += actualGui.width;
                    guiButton.y += actualGui.height / 2;
                    actualGui.buttonList.add(guiButton);
                }
            }));
        }
    }
}
