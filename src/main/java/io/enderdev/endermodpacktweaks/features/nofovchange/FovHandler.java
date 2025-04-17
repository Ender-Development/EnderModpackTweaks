package io.enderdev.endermodpacktweaks.features.nofovchange;

import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FovHandler {
    @SubscribeEvent
    public void onFovUpdate(FOVUpdateEvent event) {
        event.setNewfov(1.0f);
    }
}
