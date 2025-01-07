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
            @Config.Name("[1] Disable Inventory Crafting")
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
            @Config.Name("[1] Enable End Gateway Tweaks")
            @Config.Comment({
                    "This tweak allows you to customize the End Gateway.",
                    "You can change the blocks that make up the End Gateway."
            })
            public boolean enable = false;

            @Config.Name("[2] Replace Air")
            @Config.Comment("Replace the air block in the End Gateway.")
            public String air = "minecraft:air";

            @Config.Name("[3] Replace Bedrock")
            @Config.Comment("Replace the bedrock block in the End Gateway.")
            public String bedrock = "minecraft:bedrock";
        }

        @Config.Name("End Portal Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.end_portal")
        public final EndPodium END_PODIUM = new EndPodium();

        public static class EndPodium {
            @Config.RequiresMcRestart
            @Config.Name("[1] Enable End Podium Tweaks")
            @Config.Comment({
                    "This tweak allows you to customize the End Portal.",
                    "You can change the blocks that make up the End Portal."
            })
            public boolean enable = false;

            @Config.Name("[2] Replace Bedrock")
            @Config.Comment({
                    "Replace the bedrock block in the End Portal.",
                    "Replacing the bedrock makes it impossible to respawn the Ender Dragon."
            })
            public String bedrock = "minecraft:bedrock";

            @Config.Name("[3] Replace End Stone")
            @Config.Comment("Replace the end stone block in the End Portal.")
            public String endStone = "minecraft:end_stone";

            @Config.Name("[4] Replace Torch")
            @Config.Comment("Replace the torch block in the End Portal.")
            public String torch = "minecraft:torch";

            @Config.Name("[5] Replace Air")
            @Config.Comment("Replace the air block in the End Portal.")
            public String air = "minecraft:air";
        }

        @Config.Name("Nether Portal Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.nether_portal")
        public final NetherPortal NETHER_PORTAL = new NetherPortal();

        public static class NetherPortal {
            @Config.RequiresMcRestart
            @Config.Name("[1] Nether Portal Tweaks")
            @Config.Comment("Enable Nether Portal Tweaks")
            public boolean enable = false;

            @Config.Name("[2] Nether Portal Creation")
            @Config.Comment("Allow Nether Portal Creation")
            public boolean canBeCreated = true;

            @Config.Name("[3] End Nether Portal")
            @Config.Comment("Allow Nether Portal Creation in the End")
            public boolean canBeCreatedInEnd = false;

            @Config.Name("[4] No Entity Traverse")
            @Config.Comment("Disallow Entities to enter Nether Portals")
            public boolean disallowTraverse = false;
        }

        @Config.Name("World Tweaks")
        @Config.LangKey("config.endermodpacktweaks.minecraft.world")
        public final World WORLD = new World();

        public static class World {
            @Config.Name("Enable World Tweaks")
            @Config.Comment("Enable world tweaks")
            public boolean enable = true;

            @Config.Name("Difficulty")
            @Config.LangKey("config.endermodpacktweaks.minecraft.world.difficulty")
            public final DifficultyCategory DIFFICULTY = new DifficultyCategory();

            @Config.Name("Gamemode")
            @Config.LangKey("config.endermodpacktweaks.minecraft.world.gamemode")
            public final GamemodeCategory GAMEMODE = new GamemodeCategory();

            public static class DifficultyCategory {
                @Config.Name("[1] Force Difficulty")
                @Config.Comment("Force the difficulty to a specific value")
                public boolean force = false;

                @Config.Name("[2] Forced Difficulty")
                @Config.Comment("The difficulty the world should be forced to")
                public Difficulty difficulty = Difficulty.NORMAL;

                @Config.Name("[3] Lock Difficulty")
                @Config.Comment("Prevent players from changing the difficulty")
                public boolean lock = false;
            }

            public static class GamemodeCategory {
                @Config.Name("[1] Force Gamemode")
                @Config.Comment("Force the gamemode to a specific value")
                public boolean force = false;

                @Config.Name("[2] Forced Gamemode")
                @Config.Comment("The gamemode the world should be forced to")
                public Gamemode gamemode = Gamemode.SURVIVAL;

                @Config.Name("[3] Allow Cheats")
                @Config.Comment("Allow cheats in the world")
                public boolean cheats = true;

                @Config.Name("[4] Hardcore Mode")
                @Config.Comment("Enable hardcore mode")
                public boolean hardcore = false;
            }
        }
    }

    @Config.Name("Default World Generator")
    @Config.LangKey("config.endermodpacktweaks.default_world_generator")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/default-world-generator-ssp")
    public static final DefaultWorldGenerator DEFAULT_WORLD_GENERATOR = new DefaultWorldGenerator();

    public static class DefaultWorldGenerator {
        @Config.Name("Enable Default World Generator Port Tweaks")
        @Config.Comment({
                "This fixes the glitched button texture in the default world generator screen,",
                "which appears when using a wide screen by giving it a fixed width."
        })
        public boolean enable = true;
    }

    @Config.Name("Item Physics")
    @Config.LangKey("config.endermodpacktweaks.item_physics")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/itemphysic")
    public static final ItemPhysics ITEM_PHYSICS = new ItemPhysics();

    public static class ItemPhysics {
        @Config.RequiresMcRestart
        @Config.Name("[1] Enable Item Physics Tweaks")
        @Config.Comment("Enable tweaks for the Item Physics mod.")
        public boolean enable = true;

        @Config.Name("[2] Add Stack Size to Tooltip")
        @Config.Comment("Add the stack size to the item tooltip that shows in world, when using the alternative pickup method.")
        public boolean addStackSizeToTooltip = true;
    }

    @Config.Name("Perfect Spawn")
    @Config.LangKey("config.endermodpacktweaks.perfect_spawn")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/perfect-spawn")
    public static final PerfectSpawn PERFECT_SPAWN = new PerfectSpawn();

    public static class PerfectSpawn {
        @Config.RequiresMcRestart
        @Config.Name("Enable Perfect Spawn Tweaks")
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
    }

    @Config.Name("Simple Difficulty")
    @Config.LangKey("config.endermodpacktweaks.simple_difficulty")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/simpledifficulty-for-underdog")
    public static final SimpleDifficulty SIMPLE_DIFFICULTY = new SimpleDifficulty();

    public static class SimpleDifficulty {
        @Config.RequiresMcRestart
        @Config.Name("[1] Enable Simple Difficulty Tweaks")
        @Config.Comment("Set to false to disable Simple Difficulty tweaks.")
        public boolean enable = true;

        @Config.Name("[2] Thirstbar X-Offset")
        @Config.Comment("Set the X-Offset of the Thirstbar.")
        public int thirstbarXOffset = 82;

        @Config.Name("[3] Thirstbar Y-Offset")
        @Config.Comment("Set the Y-Offset of the Thirstbar.")
        public int thirstbarYOffset = -53;
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
