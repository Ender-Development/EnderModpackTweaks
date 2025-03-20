package io.enderdev.endermodpacktweaks.features.compatscreen;

import io.enderdev.endermodpacktweaks.Tags;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * <a href="https://github.com/ACGaming/UniversalTweaks/blob/main/src/main/java/mod/acgaming/universaltweaks/util/compat/UTCompatScreenHandler.java">Inspired by Universal Tweaks</a>
 */
@Mod.EventBusSubscriber(modid = Tags.MOD_ID, value = Side.CLIENT)
public class CompatScreenHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void displayCompatScreens(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu && CompatModsHandler.hasObsoleteModsMessage()) {
            event.setGui(new CompatScreen(CompatModsHandler.obsoleteModsMessage()));
            CompatModsHandler.setHasShownObsoleteMods(true);
        }
    }
}
