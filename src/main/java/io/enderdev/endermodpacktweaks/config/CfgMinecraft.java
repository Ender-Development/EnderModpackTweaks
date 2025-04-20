package io.enderdev.endermodpacktweaks.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import io.enderdev.endermodpacktweaks.Tags;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MOD_ID, name = Tags.CFG_FOLDER + Tags.CFG_MINECRAFT, category = "")
public class CfgMinecraft {
    @Config.Name("client")
    @Config.LangKey("cfg.endermodpacktweaks.minecraft.client")
    @Config.Comment("Disable various Client elements.")
    public static final Client CLIENT = new Client();

    public static class Client {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Client Tweaks")
        @Config.Comment("Enable tweaks for the client.")
        public boolean enable = false;

        @Config.Name("[02] Disable Inventory Crafting")
        @Config.Comment({
                "This tweak disables the crafting grid in the player inventory.",
                "Useful for modpacks that want to make the player use a crafting table",
                "or want to realize crafting in a different way."
        })
        public boolean disableInventoryCrafting = false;

        @Config.RequiresWorldRestart
        @Config.Name("[03] Disable Item Names")
        @Config.Comment("This tweak disables the rendering of item names above the hotbar.")
        public boolean disableItemNames = false;

        @Config.RequiresMcRestart
        @Config.Name("[04] Max Render Distance")
        @Config.Comment({
                "Set the maximum render distance of the client.",
                "I'm not responsible for any performance issues this may cause.",
                "nor if your pc goes up in flames. (The Minecraft default is 16)"
        })
        @Config.RangeInt(min = 2, max = 4096)
        public int maxRenderDistance = 32;

        @Config.Name("[05] Hide Name Tags")
        @Config.Comment("Hide the name tags of entities.")
        public boolean hideNameTags = false;

        @Config.Name("[06] Disable Auto Jump")
        @Config.Comment("Disable the auto jump feature. It never should have been added in the first place.")
        public boolean disableAutoJump = false;

        @Config.Name("[07] Additional Master Volume")
        @Config.Comment("Adds an additional master volume slider to the main options menu.")
        public boolean additionalMasterVolume = false;

        @Config.Name("[08] Hide Potion Icons")
        @Config.Comment("Hide the potion icons in the top right corner.")
        public boolean hidePotionIcons = false;

        @Config.Name("[09] Hide Crosshair")
        @Config.Comment("Hide the crosshair.")
        public boolean hideCrosshair = false;

        @Config.Name("[10] Hide Armor Bar")
        @Config.Comment("Hide the armor bar.")
        public boolean hideArmorBar = false;

        @Config.Name("[11] Hide Health Bar")
        @Config.Comment("Hide the health bar.")
        public boolean hideHealthBar = false;

        @Config.Name("[12] Hide Hunger Bar")
        @Config.Comment("Hide the hunger bar.")
        public boolean hideHungerBar = false;

        @Config.Name("[13] Hide Experience Bar")
        @Config.Comment("Hide the experience bar.")
        public boolean hideExperienceBar = false;

        @Config.Name("[14] Hide Air Bar")
        @Config.Comment("Hide the air bar.")
        public boolean hideAirBar = false;

        @Config.Name("[15] Disable FOV Change")
        @Config.Comment("Disable the FOV change.")
        public boolean disableFovChange = false;
    }

    @Config.Name("dragon")
    @Config.LangKey("cfg.endermodpacktweaks.minecraft.dragon")
    @Config.Comment("Tweaks for the initial Dragon Fight.")
    public static final Dragon DRAGON = new Dragon();

    public static class Dragon {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Dragon Tweaks")
        @Config.Comment("Allow you to customize the Ender Dragon Fight Mechanic.")
        public boolean enable = false;

        @Config.Name("[02] Kill Dragon")
        @Config.Comment({
                "This tweak kills the first dragon when the player enters the end for the first time.",
                "Useful for modpacks that want to make the dragon fight non-free."
        })
        public boolean killDragon = false;

        @Config.Name("[03] Drop Dragon Egg")
        @Config.Comment("Should the auto killed dragon drop the dragon egg?")
        public boolean dropEgg = false;

        @Config.Name("[04] Replace the first Dragon Egg")
        @Config.Comment("Replace the dragon egg block with another block.")
        public String eggBlock = "minecraft:dragon_egg";

        @Config.Name("[05] Create End Portal")
        @Config.Comment("Should the auto killed dragon create the end portal back to the overworld?")
        public boolean createPortal = false;

        @Config.Name("[06] Create End Gateway")
        @Config.Comment("Should the auto killed dragon create an end gateway?")
        public boolean createGateway = false;

        @Config.Name("[07] Multiple Dragon Egg")
        @Config.Comment("Should every dragon drop an egg?")
        public boolean multipleEgg = false;

        @Config.Name("[08] Disable End Portal")
        @Config.Comment("Should spawning the end portal when killing the dragon be disabled?")
        public boolean disablePortal = false;

        @Config.Name("[09] Disable End Gateway")
        @Config.Comment("Should spawning the end gateway when killing the dragon be disabled?")
        public boolean disableGateway = false;
    }

    @Config.Name("end_gateway")
    @Config.LangKey("cfg.endermodpacktweaks.minecraft.end_gateway")
    @Config.Comment("Tweak the End Gateway to your liking.")
    public static final EndGateway END_GATEWAY = new EndGateway();

    public static class EndGateway {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable End Gateway Tweaks")
        @Config.Comment({
                "This tweak allows you to customize the End Gateway.",
                "You can change the blocks that make up the End Gateway."
        })
        public boolean enable = false;

        @Config.Name("[02] Replace Air")
        @Config.Comment("Replace the air block in the End Gateway.")
        public String air = "minecraft:air";

        @Config.Name("[03] Replace Bedrock")
        @Config.Comment("Replace the bedrock block in the End Gateway.")
        public String bedrock = "minecraft:bedrock";

        @Config.Name("[04] Replace End Gateway")
        @Config.Comment("Replace the end gateway with a new structure.")
        public boolean replaceGateway = false;

        @Config.Name("[05] End Gateway Structure")
        @Config.Comment("The structure that replaces the end gateway.")
        public String gatewayStructure = "endermodpacktweaks:end_gateway";

        @Config.Name("[06] End Gateway Distance from End Portal")
        @Config.Comment("The distance the end gateway is placed from the end portal.")
        @Config.RangeDouble(min = 96.0, max = 256.0)
        public double gatewayDistance = 96.0;

        @Config.Name("[07] End Gateway Height")
        @Config.Comment("The height the end gateway is placed at.")
        @Config.RangeInt(min = 0, max = 255)
        public int gatewayHeight = 75;
    }

    @Config.Name("end_island")
    @Config.LangKey("cfg.endermodpacktweaks.minecraft.end_island")
    @Config.Comment("Tweak the End Island to your liking.")
    public static final EndIsland END_ISLAND = new EndIsland();

    public static class EndIsland {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable End Island Tweaks")
        @Config.Comment({
                "This tweak allows you to customize the End Island.",
                "You can change the blocks that make up the End Island."
        })
        public boolean enable = false;

        @Config.Name("[02] Island Size")
        @Config.Comment("Increase the size of the End Island.")
        @Config.RangeInt(min = 0, max = 32)
        public int islandSize = 4;

        @Config.Name("[03] Replace End Stone")
        @Config.Comment("Replace the end stone block in the End Island.")
        public String endStone = "minecraft:end_stone";
    }

    @Config.Name("end_portal")
    @Config.LangKey("cfg.endermodpacktweaks.minecraft.end_portal")
    @Config.Comment("Tweak the End Portal to your liking.")
    public static final EndPodium END_PODIUM = new EndPodium();

    public static class EndPodium {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable End Podium Tweaks")
        @Config.Comment({
                "This tweak allows you to customize the End Portal.",
                "You can change the blocks that make up the End Portal."
        })
        public boolean enable = false;

        @Config.Name("[02] Replace Bedrock")
        @Config.Comment({
                "Replace the bedrock block in the End Portal.",
                "Replacing the bedrock makes it impossible to respawn the Ender Dragon."
        })
        public String bedrock = "minecraft:bedrock";

        @Config.Name("[03] Replace End Stone")
        @Config.Comment("Replace the end stone block in the End Portal.")
        public String endStone = "minecraft:end_stone";

        @Config.Name("[04] Replace Torch")
        @Config.Comment("Replace the torch block in the End Portal.")
        public String torch = "minecraft:torch";

        @Config.Name("[05] Replace Air")
        @Config.Comment("Replace the air block in the End Portal.")
        public String air = "minecraft:air";

        @Config.Name("[06] Replace End Portal")
        @Config.Comment("Replace the end portal with a new structure.")
        public boolean replacePortal = false;

        @Config.Name("[07] End Portal Structure (Inactive)")
        @Config.Comment("The structure that replaces the inactive end portal.")
        public String portalStructure = "endermodpacktweaks:end_portal";

        @Config.Name("[08] End Portal Structure (Active)")
        @Config.Comment("The structure that replaces the active end portal.")
        public String activePortalStructure = "endermodpacktweaks:end_portal_active";
    }

    @Config.Name("obsidian_spike")
    @Config.LangKey("cfg.endermodpacktweaks.minecraft.obsidian_spike")
    @Config.Comment("Tweak the Obsidian Spikes in the End.")
    public static final ObsidianSpike OBSIDIAN_SPIKE = new ObsidianSpike();

    public static class ObsidianSpike {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Obsidian Spike Tweaks")
        @Config.Comment({
                "This tweak allows you to customize the Obsidian Spike.",
                "Obsidian Spikes are the large Pillars of Obsidian that generate in the End.",
                "You can change the blocks that make up the Obsidian Spike."
        })
        public boolean enable = false;

        @Config.Name("[02] Replace Obsidian")
        @Config.Comment("Replace the obsidian block in the Obsidian Spike.")
        public String obsidian = "minecraft:obsidian";

        @Config.Name("[03] Replace Air")
        @Config.Comment("Replace the air block in the Obsidian Spike.")
        public String air = "minecraft:air";

        @Config.Name("[04] Replace Iron Bars")
        @Config.Comment("Replace the iron bars block in the Obsidian Spike.")
        public String ironBars = "minecraft:iron_bars";

        @Config.Name("[05] Replace Obsidian Spike")
        @Config.Comment({
                "Replace the obsidian spike with a new structure.",
                "!!! NOT YET IMPLEMENTED !!!"
        })
        public boolean replaceSpike = false;

        @Config.Name("[06] Obsidian Spike Structure")
        @Config.Comment({
                "The structure that replaces the obsidian spike.",
                "!!! NOT YET IMPLEMENTED !!!"
        })
        public String spikeStructure = "endermodpacktweaks:obsidian_spike";

        @Config.Name("[07] Obsidian Spike Distance from End Portal")
        @Config.Comment("The distance the obsidian spike is placed from the end portal.")
        @Config.RangeDouble(min = 42.0, max = 128.0)
        public double spikeDistance = 42.0;

        @Config.Name("[08] Obsidian Spike Base Height")
        @Config.Comment("The base height of the obsidian spike.")
        @Config.RangeInt(min = 10, max = 255)
        public int spikeHeight = 76;

        @Config.Name("[09] Obsidian Spike Base Radius")
        @Config.Comment("The base radius of the obsidian spike.")
        @Config.RangeInt(min = 2, max = 10)
        public int spikeRadius = 2;

        @Config.Name("[10] Obsidian Spike Count")
        @Config.Comment("The number of obsidian spikes around the end portal.")
        @Config.RangeInt(min = 1, max = 32)
        public int spikeCount = 10;

        @Config.Name("[11] Obsidian Spike Guard")
        @Config.Comment("Should the obsidian spikes be always guarded?")
        public boolean alwaysGuarded = false;

        @Config.Name("[12] Obsidian Spike Guard Chance")
        @Config.Comment({
                "The 1 in x chance that the obsidian spikes are guarded.",
                "Set to 0 to disable guarded spikes altogether."
        })
        @Config.RangeInt(min = 0, max = 100)
        public int guardChance = 5;
    }

    @Config.Name("nether_portal")
    @Config.LangKey("cfg.endermodpacktweaks.minecraft.nether_portal")
    @Config.Comment("Tweak the Nether Portal to your liking.")
    public static final NetherPortal NETHER_PORTAL = new NetherPortal();

    public static class NetherPortal {
        @Config.RequiresMcRestart
        @Config.Name("[01] Nether Portal Tweaks")
        @Config.Comment("Enable Nether Portal Tweaks")
        public boolean enable = false;

        @Config.Name("[02] Nether Portal Creation")
        @Config.Comment("Allow Nether Portal Creation")
        public boolean canBeCreated = true;

        @Config.Name("[03] End Nether Portal")
        @Config.Comment("Allow Nether Portal Creation in the End")
        public boolean canBeCreatedInEnd = false;

        @Config.Name("[04] No Entity Traverse")
        @Config.Comment("Disallow Entities to enter Nether Portals")
        public boolean disallowTraverse = false;
    }

    @Config.Name("world")
    @Config.LangKey("cfg.endermodpacktweaks.minecraft.world")
    @Config.Comment("Tweaks for world generation and world settings.")
    public static final World WORLD = new World();

    public static class World {
        @Config.Name("[01] Enable World Tweaks")
        @Config.Comment("Enable world tweaks")
        public boolean enable = false;

        @Config.Name("[02] Force Difficulty")
        @Config.Comment("Force the difficulty to a specific value")
        public boolean forceDifficulty = false;

        @Config.Name("[03] Forced Difficulty")
        @Config.Comment("The difficulty the world should be forced to")
        public EnumDifficulty difficulty = EnumDifficulty.NORMAL;

        @Config.Name("[04] Lock Difficulty")
        @Config.Comment("Prevent players from changing the difficulty")
        public boolean lock = false;

        @Config.Name("[05] Force Gamemode")
        @Config.Comment("Force the gamemode to a specific value")
        public boolean forceGamemode = false;

        @Config.Name("[06] Forced Gamemode")
        @Config.Comment("The gamemode the world should be forced to")
        public EnumGamemode gamemode = EnumGamemode.SURVIVAL;

        @Config.Name("[07] Allow Cheats")
        @Config.Comment("Allow cheats in the world")
        public boolean cheats = true;

        @Config.Name("[08] Hardcore Mode")
        @Config.Comment("Enable hardcore mode")
        public boolean hardcore = false;
    }

    @Mod.EventBusSubscriber(modid = Tags.MOD_ID)
    public static class ConfigEventHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Tags.MOD_ID)) {
                ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }

    static {
        ConfigAnytime.register(CfgMinecraft.class);
    }
}
