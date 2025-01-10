package io.enderdev.endermodpacktweaks;

import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MOD_ID, name = Tags.MOD_ID, category = Tags.MOD_ID)
public class EMTConfig {

    public enum Difficulty {
        PEACEFUL(0),
        EASY(1),
        NORMAL(2),
        HARD(3);

        private final int value;

        Difficulty(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Gamemode {
        SURVIVAL(0),
        CREATIVE(1),
        ADVENTURE(2),
        SPECTATOR(3);

        private final int value;

        Gamemode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    @Config.Name("Minecraft")
    @Config.LangKey("config.endermodpacktweaks.minecraft")
    public static final Minecraft MINECRAFT = new Minecraft();

    public static class Minecraft {
        @Config.Name("Client Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.client")
        public final Client CLIENT = new Client();

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
        }

        @Config.Name("Dragon Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.dragon")
        public final Dragon DRAGON = new Dragon();

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

        @Config.Name("End Gateway Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.end_gateway")
        public final EndGateway END_GATEWAY = new EndGateway();

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

        @Config.Name("End Island Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.end_island")
        public final EndIsland END_ISLAND = new EndIsland();

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
            @Config.RangeInt(min = 0, max = 8)
            public int islandSize = 0;

            @Config.Name("[03] Replace End Stone")
            @Config.Comment("Replace the end stone block in the End Island.")
            public String endStone = "minecraft:end_stone";
        }

        @Config.Name("End Portal Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.end_portal")
        public final EndPodium END_PODIUM = new EndPodium();

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

        @Config.Name("Obsidian Spike Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.obsidian_spike")
        public final ObsidianSpike OBSIDIAN_SPIKE = new ObsidianSpike();

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
            @Config.Comment("Replace the obsidian spike with a new structure.")
            public boolean replaceSpike = false;

            @Config.Name("[06] Obsidian Spike Structure")
            @Config.Comment("The structure that replaces the obsidian spike.")
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
                    "Set to 0 to disable the guard chance."
            })
            @Config.RangeInt(min = 0, max = 100)
            public int guardChance = 5;
        }

        @Config.Name("Nether Portal Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.nether_portal")
        public final NetherPortal NETHER_PORTAL = new NetherPortal();

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

        @Config.Name("World Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.world")
        public final World WORLD = new World();

        public static class World {
            @Config.Name("[01] Enable World Tweaks")
            @Config.Comment("Enable world tweaks")
            public boolean enable = true;

            @Config.Name("[02] Difficulty")
            @Config.LangKey("config.endermodpacktweaks.minecraft.world.difficulty")
            public final DifficultyCategory DIFFICULTY = new DifficultyCategory();

            @Config.Name("[03] Gamemode")
            @Config.LangKey("config.endermodpacktweaks.minecraft.world.gamemode")
            public final GamemodeCategory GAMEMODE = new GamemodeCategory();

            public static class DifficultyCategory {
                @Config.Name("[01] Force Difficulty")
                @Config.Comment("Force the difficulty to a specific value")
                public boolean force = false;

                @Config.Name("[02] Forced Difficulty")
                @Config.Comment("The difficulty the world should be forced to")
                public Difficulty difficulty = Difficulty.NORMAL;

                @Config.Name("[03] Lock Difficulty")
                @Config.Comment("Prevent players from changing the difficulty")
                public boolean lock = false;
            }

            public static class GamemodeCategory {
                @Config.Name("[01] Force Gamemode")
                @Config.Comment("Force the gamemode to a specific value")
                public boolean force = false;

                @Config.Name("[02] Forced Gamemode")
                @Config.Comment("The gamemode the world should be forced to")
                public Gamemode gamemode = Gamemode.SURVIVAL;

                @Config.Name("[03] Allow Cheats")
                @Config.Comment("Allow cheats in the world")
                public boolean cheats = true;

                @Config.Name("[04] Hardcore Mode")
                @Config.Comment("Enable hardcore mode")
                public boolean hardcore = false;
            }
        }
    }

    @Config.Name("Dark Utilities")
    @Config.LangKey("config.endermodpacktweaks.dark_utils")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/dark-utilities")
    public static final DarkUtils DARK_UTILS = new DarkUtils();

    public static class DarkUtils {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Dark Utils Tweaks")
        @Config.Comment("Enable tweaks for the Dark Utils mod.")
        public boolean enable = true;

        @Config.Name("[02] Vector Plate Item Only")
        @Config.Comment("Vector Plates can only move items.")
        public boolean vectorPlateItemOnly = true;

        @Config.Name("[03] Override Vector Plate Collision Box")
        @Config.Comment({
                "This tweak increases the height of the collision box of the vector plate.",
                "This tweak was added so Item Physics can render the item on top of the vector plate."
        })
        public boolean overrideVectorPlateCollisionBox = true;

        @Config.Name("[04] Vector Plates Insert - front")
        @Config.Comment("Vector Plates can insert items into the inventory in front of them.")
        public boolean vectorPlatesInsertFront = true;

        @Config.Name("[05] Vector Plates Insert - below")
        @Config.Comment("Vector Plates can insert items into the inventory below them.")
        public boolean vectorPlatesInsertBelow = true;
    }

    @Config.Name("Default World Generator")
    @Config.LangKey("config.endermodpacktweaks.default_world_generator")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/default-world-generator-ssp")
    public static final DefaultWorldGenerator DEFAULT_WORLD_GENERATOR = new DefaultWorldGenerator();

    public static class DefaultWorldGenerator {
        @Config.Name("[01] Enable Default World Generator Port Tweaks")
        @Config.Comment({
                "This fixes the glitched button texture in the default world generator screen,",
                "which appears when using a wide screen by giving it a fixed width."
        })
        public boolean enable = true;
    }

    @Config.Name("First Aid")
    @Config.LangKey("config.endermodpacktweaks.first_aid")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/first-aid")
    public static final FirstAid FIRST_AID = new FirstAid();

    public static class FirstAid {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable First Aid Tweaks")
        @Config.Comment("Enable tweaks for the First Aid mod.")
        public boolean enable = true;

        @Config.Name("[02] Disable Tutorial Message")
        @Config.Comment("Disable the tutorial message that appears when joining a world.")
        public boolean disableTutorial = false;

        @Config.Name("[03] Overhaul HUD placement")
        @Config.Comment("This centers the HUD on the x-axis.")
        public boolean centerHUD = false;
    }

    @Config.Name("Item Physics")
    @Config.LangKey("config.endermodpacktweaks.item_physics")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/itemphysic")
    public static final ItemPhysics ITEM_PHYSICS = new ItemPhysics();

    public static class ItemPhysics {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Item Physics Tweaks")
        @Config.Comment("Enable tweaks for the Item Physics mod.")
        public boolean enable = true;

        @Config.Name("[02] Improved Item Tooltip")
        @Config.Comment({
                "This tweak overhauls the item tooltip that is displayed when using the alternative pickup mode.",
                "It adds the stack size to the tooltip as well as coloring it based on the rarity of the item."
        })
        public boolean improveTooltip = true;

        @Config.Name("[03] Reach Distance")
        @Config.Comment("Set the pickup range to the reach distance of the player.")
        public boolean reachDistance = true;
    }

    @Config.Name("Perfect Spawn")
    @Config.LangKey("config.endermodpacktweaks.perfect_spawn")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/perfect-spawn")
    public static final PerfectSpawn PERFECT_SPAWN = new PerfectSpawn();

    public static class PerfectSpawn {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Perfect Spawn Tweaks")
        @Config.Comment({
                "This tweaks moves the PerfectSpawn config file to the config directory.",
                "It also creates a new config file if it doesn't exist."
        })
        public boolean enable = true;
    }

    @Config.Name("Pyrotech")
    @Config.LangKey("config.endermodpacktweaks.pyrotech")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/pyrotech")
    public static final Pyrotech PYROTECH = new Pyrotech();

    public static class Pyrotech {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Pyrotech Tweaks")
        @Config.Comment("Enable tweaks for the Pyrotech mod.")
        public boolean enable = true;

        @Config.Name("[02] Random Rocks")
        @Config.Comment("Enable random rocks in the world.")
        public boolean randomRocks = false;

        @Config.Name("[03] Rock Weight")
        @Config.Comment("The weight of rocks in the world.")
        public final RockWeight ROCK_WEIGHT = new RockWeight();

        public static class RockWeight {
            @Config.Name("[01] Weight: rock_stone")
            @Config.Comment("The weight of Stone Rocks.")
            public int stone = 100;

            @Config.Name("[02] Weight: rock_granite")
            @Config.Comment("The weight of Granite Rocks.")
            public int granite = 0;

            @Config.Name("[03] Weight: rock_diorite")
            @Config.Comment("The weight of Diorite Rocks.")
            public int diorite = 0;

            @Config.Name("[04] Weight: rock_andesite")
            @Config.Comment("The weight of Andesite Rocks.")
            public int andesite = 0;

            @Config.Name("[05] Weight: rock_dirt")
            @Config.Comment("The weight of Dirt Rocks.")
            public int dirt = 0;

            @Config.Name("[06] Weight: rock_sand")
            @Config.Comment("The weight of Sand Rocks.")
            public int sand = 0;

            @Config.Name("[07] Weight: rock_sandstone")
            @Config.Comment("The weight of Sandstone Rocks.")
            public int sandstone = 0;

            @Config.Name("[08] Weight: rock_wood_chips")
            @Config.Comment("The weight of Wood Chips Rocks.")
            public int wood_chips = 0;

            @Config.Name("[09] Weight: rock_limestone")
            @Config.Comment("The weight of Limestone Rocks.")
            public int limestone = 0;

            @Config.Name("[10] Weight: rock_sand_red")
            @Config.Comment("The weight of Red Sand Rocks.")
            public int sand_red = 0;

            @Config.Name("[11] Weight: rock_sandstone_red")
            @Config.Comment("The weight of Red Sandstone Rocks.")
            public int sandstone_red = 0;

            @Config.Name("[12] Weight: rock_mud")
            @Config.Comment("The weight of Mud Rocks.")
            public int mud = 0;
        }
    }

    @Config.Name("Quark")
    @Config.LangKey("config.endermodpacktweaks.quark")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/quark-rotn-edition")
    public static final Quark QUARK = new Quark();

    public static class Quark {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Quark Tweaks")
        @Config.Comment("Enable tweaks for the Quark mod.")
        public boolean enable = true;

        @Config.Name("[02] Always show the Usage Ticker")
        @Config.Comment("Stops the Usage Ticker from disappearing.")
        public boolean alwaysShowUsageTicker = true;

        @Config.Name("[03] Armor Y-Offset")
        @Config.Comment("Set the Y-Offset of the Armor Part of the Usage Ticker.")
        public int armorYOffset = 0;

        @Config.Name("[04] Tool Y-Offset")
        @Config.Comment("Set the Y-Offset of the Tool Part of the Usage Ticker.")
        public int itemYOffset = 0;

        @Config.Name("[05] Enable End Stone Speleothems")
        @Config.Comment("Add and generate End Stone Speleothems.")
        public boolean enableEndSpeleothems = true;

        @Config.Name("[06] Enable Obsidian Speleothems")
        @Config.Comment("Add and generate Obsidian Speleothems.")
        public boolean enableObsidianSpeleothems = true;
    }

    @Config.Name("Rustic")
    @Config.LangKey("config.endermodpacktweaks.rustic")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/rustic")
    public static final Rustic RUSTIC = new Rustic();

    public static class Rustic {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Rustic Tweaks")
        @Config.Comment("Set to false to disable Rustic tweaks")
        public boolean enable = true;

        @Config.Name("[02] Berry Bush generation spread")
        @Config.Comment("Tweaking the max radius Rustic's berry bushes try to generate in per patch")
        public int maxWildberrySpread = 7;

        @Config.Name("[03] Override Berry Bush placement")
        @Config.Comment("Set to true to override Rustic's berry bush placement")
        public boolean overrideBerryBushPlacement = false;

        @Config.Name("[04] Valid Berry Bush blocks")
        @Config.Comment("List of blocks that Rustic's berry bushes can be placed on")
        public String[] listBerryBushBlocks = new String[]{
                "minecraft:grass",
                "minecraft:dirt",
                "minecraft:farmland",
                "rustic:fertile_soil"
        };

        @Config.Name("[05] Override Berry Bush biome blacklist")
        @Config.Comment("Set to true to override Rustic's berry bush biome blacklist")
        public boolean overrideBerryBushBiomeBlacklist = false;

        @Config.Name("[06] Berry Bush biomes blacklist")
        @Config.Comment("List of biomes that Rustic's berry bushes won't be generated in")
        public String[] listBiomesBlacklist = new String[]{
                "COLD",
                "SNOWY",
                "SANDY",
                "SAVANNA",
                "MESA",
                "MUSHROOM",
                "NETHER",
                "END",
                "DEAD",
                "WASTELAND",
        };

        @Config.Name("[07] Enable Rustic WorldGen in Flat Worlds")
        @Config.Comment("Set to true to enable Rustic WorldGen in flat worlds")
        public boolean enableWorldGenInFlat = false;

        @Config.Name("[08] Toughness Overlay X-Offset")
        @Config.Comment("Set the X-Offset of the Armor Toughness Overlay.")
        public int armorToughnessXOffset = 0;

        @Config.Name("[09] Toughness Overlay Y-Offset")
        @Config.Comment("Set the Y-Offset of the Armor Toughness Overlay.")
        public int armorToughnessYOffset = 0;

        @Config.Name("[10] Armor Overlay X-Offset")
        @Config.Comment("Set the X-Offset of the Armor Overlay.")
        public int armorXOffset = 0;

        @Config.Name("[10] Armor Overlay Y-Offset")
        @Config.Comment("Set the Y-Offset of the Armor Overlay.")
        public int armorYOffset = 0;
    }

    @Config.Name("Simple Difficulty")
    @Config.LangKey("config.endermodpacktweaks.simple_difficulty")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/simpledifficulty-for-underdog")
    public static final SimpleDifficulty SIMPLE_DIFFICULTY = new SimpleDifficulty();

    public static class SimpleDifficulty {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Simple Difficulty Tweaks")
        @Config.Comment("Set to false to disable Simple Difficulty tweaks.")
        public boolean enable = true;

        @Config.Name("[02] Thirstbar X-Offset")
        @Config.Comment("Set the X-Offset of the Thirstbar.")
        public int thirstbarXOffset = 82;

        @Config.Name("[03] Thirstbar Y-Offset")
        @Config.Comment("Set the Y-Offset of the Thirstbar.")
        public int thirstbarYOffset = -53;
    }

    @Config.Name("Tool Progression")
    @Config.LangKey("config.endermodpacktweaks.tool_progression")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/tool-progression")
    public static final ToolProgression TOOL_PROGRESSION = new ToolProgression();

    public static class ToolProgression {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Tool Progression Tweaks")
        @Config.Comment({
                "This tweak allows changes how the configuration file of the mod are generated.",
                "It creates a few subdirectories and moves the configuration files into them."
        })
        public boolean enable = true;
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
        ConfigAnytime.register(EMTConfig.class);
    }
}
