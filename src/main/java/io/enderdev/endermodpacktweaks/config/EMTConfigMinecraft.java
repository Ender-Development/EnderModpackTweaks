package io.enderdev.endermodpacktweaks.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import io.enderdev.endermodpacktweaks.Tags;
import io.enderdev.endermodpacktweaks.config.minecraft.CfgClient;
import io.enderdev.endermodpacktweaks.config.minecraft.CfgDragon;
import io.enderdev.endermodpacktweaks.config.minecraft.CfgEndGateway;
import io.enderdev.endermodpacktweaks.config.minecraft.CfgEndPodium;
import net.minecraftforge.common.config.Config;

@Config(modid = Tags.MOD_ID, category = "minecraft")
@Config.LangKey("config.endermodpacktweaks.minecraft")
public class EMTConfigMinecraft {
    @Config.Name("Dragon Tweaks")
    @Config.LangKey("config.endermodpacktweaks.minecraft.dragon")
    public static final CfgDragon DRAGON = new CfgDragon();

    @Config.Name("Client Tweaks")
    @Config.LangKey("config.endermodpacktweaks.minecraft.client")
    public static final CfgClient CLIENT = new CfgClient();

    @Config.Name("End Gateway Tweaks")
    @Config.LangKey("config.endermodpacktweaks.minecraft.end_gateway")
    public static final CfgEndGateway END_GATEWAY = new CfgEndGateway();

    @Config.Name("End Portal Tweaks")
    @Config.LangKey("config.endermodpacktweaks.minecraft.end_portal")
    public static final CfgEndPodium END_PODIUM = new CfgEndPodium();

    static {
        ConfigAnytime.register(EMTConfigMinecraft.class);
    }
}
