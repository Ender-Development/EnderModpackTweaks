package io.enderdev.endermodpacktweaks.core;

import com.google.common.eventbus.EventBus;
import io.enderdev.endermodpacktweaks.Tags;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class EMTContainer extends DummyModContainer {
    public EMTContainer() {
        super(new ModMetadata());
        ModMetadata meta = this.getMetadata();
        meta.modId = Tags.MOD_ID + "-core";
        meta.name = Tags.MOD_NAME + " Core";
        meta.version = Tags.VERSION;
        meta.authorList.add("EnderDev");
        meta.description = "Core mod for Ender's Modpack Tweaks";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
}
