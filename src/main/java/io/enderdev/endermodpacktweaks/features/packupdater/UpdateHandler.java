package io.enderdev.endermodpacktweaks.features.packupdater;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UpdateHandler {
    public static boolean showGUI = Updater.INSTANCE.doesNeedUpdate();

    @SubscribeEvent
    public void onOpenGui(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu && showGUI) {
            event.setGui(new UpdateGUI(event.getGui()));
        }
    }
}
