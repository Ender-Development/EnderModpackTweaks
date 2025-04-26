package io.enderdev.endermodpacktweaks.proxy;

import net.minecraftforge.fml.common.event.*;

public interface IProxy {
    void preInit(FMLPreInitializationEvent event);
    void init(FMLInitializationEvent event);
    void postInit(FMLPostInitializationEvent event);
    void construct(FMLConstructionEvent event) throws Exception;
    void onLoadComplete(FMLLoadCompleteEvent event);
    void serverStarting(FMLServerStartingEvent event);
    void serverStarted(FMLServerStartedEvent event);
}
