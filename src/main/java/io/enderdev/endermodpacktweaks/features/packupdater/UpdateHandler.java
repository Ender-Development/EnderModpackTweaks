package io.enderdev.endermodpacktweaks.features.packupdater;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UpdateHandler {
    private boolean flag;

    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu && Updater.INSTANCE.doesNeedUpdate() && !flag) {
            event.setGui(new UpdateGUI(event.getGui()));
            flag = true;
        }
    }
}
