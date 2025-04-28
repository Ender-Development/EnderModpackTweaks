package io.enderdev.endermodpacktweaks.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import io.enderdev.endermodpacktweaks.Tags;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MOD_ID, name = Tags.CFG_FOLDER + Tags.CFG_TWEAK, category = "")
public class CfgTweaks {

    @Config.Name("arcane_world")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.arcane_world")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/arcane-world-fixed")
    public static final ArcaneWorld ARCANE_WORLD = new ArcaneWorld();

    public static class ArcaneWorld {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Arcane World Tweaks")
        @Config.Comment("This fixes the lag in the other dimensions.")
        public boolean enable = true;
    }

    @Config.Name("astral_sorcery")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.astral_sorcery")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/astral-sorcery")
    public static final AstralSorcery ASTRAL_SORCERY = new AstralSorcery();

    public static class AstralSorcery {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Astral Sorcery Tweaks")
        @Config.Comment("Enable tweaks for the Astral Sorcery mod.")
        public boolean enable = false;

        @Config.Name("[02] Allow Fake Players")
        @Config.Comment("Allow Fake Players to interact with the Astral Sorcery mod.")
        public boolean allowFakePlayer = false;
    }

    @Config.Name("bp_opener")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.bp_opener")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/backpack-opener")
    public static final BpOpener BP_OPENER = new BpOpener();

    public static class BpOpener {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Backpack Opener Tweaks")
        @Config.Comment("Enable tweaks for the Backpack Opener mod.")
        public boolean enable = false;

        @Config.Name("[02] Valid Items")
        @Config.Comment({
                "A list of valid items that can be used with the Backpack Opener.",
                "Format: modid:itemid;boolean",
                "Boolean: true = does the item require sneaking, false = no sneaking required"
        })
        public String[] validItems = new String[]{};
    }

    @Config.Name("better_end")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.better_end")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/betterendforge-backport")
    public static final BetterEnd BETTER_END = new BetterEnd();

    public static class BetterEnd {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Better End Tweaks")
        @Config.Comment("Enable tweaks for the Better End mod.")
        public boolean enable = true;
    }

    @Config.Name("cases")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.cases")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/cases")
    public static final Cases CASES = new Cases();

    public static class Cases {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Cases Tweaks")
        @Config.Comment("Enable tweaks for the Cases mod.")
        public boolean enable = false;

        @Config.Name("[02] Disable Animation")
        @Config.Comment("Disable the opening animation of the cases.")
        public boolean disableAnimation = false;
    }

    @Config.Name("crissaegrim")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.crissaegrim")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/crissaegrim")
    public static final Crissaegrim CRISSAEGRIM = new Crissaegrim();

    public static class Crissaegrim {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Crissaegrim Tweaks")
        @Config.Comment("Enable tweaks for the Crissaegrim mod.")
        public boolean enable = true;

        @Config.Name("[02] Disable Random Drop")
        @Config.Comment("Disable the random drop of the Crissaegrim.")
        public boolean disableRandomDrop = false;

        @Config.Name("[03] Override Special Mob")
        @Config.Comment("Override the mob that can drop the Crissaegrim.")
        public String specialMob = "quark:wraith";

        @Config.Name("[04] Override Special Mob Chance")
        @Config.Comment("The chance that the special mob drops the Crissaegrim.")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        public double specialMobChance = 0.01;

        @Config.Name("[05] Change Color")
        @Config.Comment("The color of the Crissaegrim slashes and cuts. Format: #RRGGBBAA")
        public String color = "#5959FFFF";
    }

    @Config.Name("dark_utils")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.dark_utils")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/dark-utilities")
    public static final DarkUtils DARK_UTILS = new DarkUtils();

    public static class DarkUtils {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Dark Utils Tweaks")
        @Config.Comment("Enable tweaks for the Dark Utils mod.")
        public boolean enable = false;

        @Config.Name("[02] Vector Plate Item Only")
        @Config.Comment("Vector Plates can only move items.")
        public boolean vectorPlateItemOnly = false;

        @Config.Name("[03] Override Vector Plate Collision Box")
        @Config.Comment({
                "This tweak increases the height of the collision box of the vector plate.",
                "This tweak was added so Item Physics can render the item on top of the vector plate."
        })
        public boolean overrideVectorPlateCollisionBox = false;

        @Config.Name("[04] Vector Plates Insert - front")
        @Config.Comment("Vector Plates can insert items into the inventory in front of them.")
        public boolean vectorPlatesInsertFront = false;

        @Config.Name("[05] Vector Plates Insert - below")
        @Config.Comment("Vector Plates can insert items into the inventory below them.")
        public boolean vectorPlatesInsertBelow = false;
    }

    @Config.Name("default_world_generator")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.default_world_generator")
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

    @Config.Name("delivery")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.delivery")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/delivery")
    public static final Delivery DELIVERY = new Delivery();

    public static class Delivery {
        @Config.Name("[01] Enable Delivery Tweaks")
        @Config.Comment("This fixes a NPE each time you try to open the Store block.")
        public boolean enable = true;
    }

    @Config.Name("ds_huds")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.ds_huds")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/dynamic-surroundings-huds")
    public static final DSHUDs DSHUDS = new DSHUDs();

    public static class DSHUDs {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Dynamic Surroundings HUD Tweaks")
        @Config.Comment("Enable tweaks for the Dynamic Surroundings HUDs.")
        public boolean enable = false;

        @Config.Name("[02] Potion HUD Hide List Type")
        @Config.Comment("Should the potion HUD be a blacklist or a whitelist?")
        public EnumListType potionHUDHideListType = EnumListType.BLACKLIST;

        @Config.Name("[03] Potion HUD Hide List")
        @Config.Comment({
                "A list of potion effects that depending on the list type are hidden or shown.",
                "FORMAT: modid:potionid",
                "EXAMPLE: minecraft:night_vision"
        })
        public String[] potionHUDHideList = new String[]{};
    }

    @Config.Name("elenai_dodge")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.elenai_dodge")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/elenai-dodge-2")
    public static final ElenaiDodge ELENAI_DODGE = new ElenaiDodge();

    public static class ElenaiDodge {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Elenai Dodge 2 Tweaks")
        @Config.Comment("Enable tweaks for the Elenai Dodge 2 mod.")
        public boolean enable = false;

        @Config.RequiresMcRestart
        @Config.Name("[02] Stamina Potion Effects")
        @Config.Comment({
                "Requires 'Player Effects' in the 'Features.cfg' to be enabled.",
                "Add additional potion effects to the player depending on the stamina level.",
                "The stamina bounds must be between 0 and 20.",
                "FORMAT: lower_bound;upper_bound;effect;amplifier",
                "Example: 0;5;minecraft:slowness;2"
        })
        public String[] staminaPotions = new String[]{};

        @Config.Name("[03] Enable Simple Difficulty Integration")
        @Config.Comment("Enable the integration with the Simple Difficulty mod.")
        public boolean enableSimpleDifficulty = false;

        @Config.Name("[04] Simple Difficulty: Thirst Exhaustion on Dodge")
        @Config.Comment("How much thirst should be added when the player dodges.")
        @Config.RangeDouble(min = 0.0, max = 40.0)
        public double thirst = 6.0;

        @Config.Name("[05] Simple Difficulty: Thirst Threshold")
        @Config.Comment("The threshold at which the dodge should be canceled.")
        @Config.RangeInt(min = 0, max = 20)
        public int thirstThreshold = 6;

        @Config.Name("[06] Simple Difficulty: Stamina Regeneration")
        @Config.Comment("The minimum thirst level required to regenerate stamina.")
        @Config.RangeInt(min = 0, max = 20)
        public int dodgeRegeneration = 8;

        @Config.Name("[07] Simple Difficulty: Stamina Regeneration Rate")
        @Config.Comment("How much longer (in ticks) it takes to regenerate stamina for each missing thirst level.")
        @Config.RangeInt(min = 0, max = 100)
        public int dodgeRegenerationRate = 10;

        @Config.Name("[08] Simple Difficulty: Thirst Exhaustion on Stamina Regeneration")
        @Config.Comment("How much thirst should be added when the player regenerates stamina.")
        @Config.RangeDouble(min = 0.0, max = 40.0)
        public double thirstRegeneration = 0.2;
    }

    @Config.Name("ender_storage")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.ender_storage")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/ender-storage-1-12-continuation")
    public static final EnderStorage ENDER_STORAGE = new EnderStorage();

    public static class EnderStorage {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Ender Storage Tweaks")
        @Config.Comment({
                "Enable tweaks for the Ender Storage mod.",
                "This tweak fixes the crash report spam caused by the Ender Storage mod.",
                "https://github.com/igentuman/EnderStorage-continuation/issues/19",
                "This fix is still heavy WIP and currently breaks Ender Tanks! Use with caution!"
        })
        public boolean enable = false;
    }

    @Config.Name("first_aid")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.first_aid")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/first-aid")
    public static final FirstAid FIRST_AID = new FirstAid();

    public static class FirstAid {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable First Aid Tweaks")
        @Config.Comment("Enable tweaks for the First Aid mod.")
        public boolean enable = false;

        @Config.Name("[02] Disable Tutorial Message")
        @Config.Comment("Disable the tutorial message that appears when joining a world.")
        public boolean disableTutorial = false;

        @Config.Name("[03] Overhaul HUD placement")
        @Config.Comment("This centers the HUD on the x-axis.")
        public boolean centerHUD = false;
    }

    @Config.Name("flux_networks")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.flux_networks")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/flux-networks")
    public static final FluxNetworks FLUX_NETWORKS = new FluxNetworks();

    public static class FluxNetworks {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Flux Networks Tweaks")
        @Config.Comment("Enable tweaks for the Flux Networks mod.")
        public boolean enable = false;

        @Config.RequiresMcRestart
        @Config.Name("[02] Fix IC2 Energy Limit")
        @Config.Comment("Sets the maximum energy limit for IC2 Energy to Max Integer.")
        public boolean fixIC2EnergyLimit = false;

        @Config.RequiresMcRestart
        @Config.Name("[03] Override IC2 Sink Tier")
        @Config.Comment("Override the IC2 Sink Tier.")
        @Config.RangeInt(min = 1)
        public int ic2SinkTier = 4;

        @Config.RequiresMcRestart
        @Config.Name("[04] Override IC2 Source Tier")
        @Config.Comment("Override the IC2 Source Tier.")
        @Config.RangeInt(min = 1)
        public int ic2SourceTier = 4;
    }

    @Config.Name("game_stages")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.game_stages")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/game-stages")
    public static final GameStages GAME_STAGES = new GameStages();

    public static class GameStages {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Game Stages Tweaks")
        @Config.Comment("Enable tweaks for the Game Stages mod.")
        public boolean enable = false;

        @Config.Name("[02] Disable Item Stages Tooltip")
        @Config.Comment("Disable the Tooltip that is displayed when hovering over a staged item.")
        public boolean disableTooltip = false;

        @Config.Name("[03] Localize Stage Names")
        @Config.Comment({
                "Allow localization of stages. This works by adding a localization key",
                "to each game stage in the format of 'emt.game_stages.<stage_name>'"
        })
        public boolean localizeRecipeStages = false;

        @Config.Name("[04] Recipe Stages Tooltip X-Offset")
        @Config.Comment("Set the X-Offset of the recipe stages tooltip.")
        public int recipeStagesTooltipXOffset = 0;

        @Config.Name("[05] Recipe Stages Tooltip Y-Offset")
        @Config.Comment("Set the Y-Offset of the recipe stages tooltip.")
        public int recipeStagesTooltipYOffset = 0;

        @Config.Name("[06] Recipe Stages Tooltip Sliding")
        @Config.Comment("Enable the Tooltip to slide.")
        public boolean recipeStagesTooltipSliding = false;

        @Config.Name("[07] Recipe Stages Tooltip On Demand Sliding")
        @Config.Comment("Only slide the Tooltip when text width exceeds the set width.")
        public boolean recipeStagesTooltipOnDemandSliding = true;

        @Config.Name("[08] Recipe Stages Tooltip Rect Width")
        @Config.Comment("The width of the Tooltip display area.")
        public int recipeStagesTooltipRectWidth = 70;

        @Config.Name("[09] Recipe Stages Sliding Tooltip Gap Width")
        @Config.Comment("The gap between the end of one sliding tooltip and the start of the next.")
        public int recipeStagesTooltipsGap = 20;

        @Config.Name("[10] Recipe Stages Tooltip Sliding Speed")
        @Config.Comment("The speed of sliding.")
        public int recipeStagesTooltipSlidingSpeed = 15;

        @Config.Name("[11] Recipe Stages Tooltip Sliding Freeze Time")
        @Config.Comment("The time the sliding tooltip stays still before sliding again.")
        public float recipeStagesTooltipFreezeTime = 2f;
    }

    @Config.Name("item_physics")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.item_physics")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/itemphysic")
    public static final ItemPhysics ITEM_PHYSICS = new ItemPhysics();

    public static class ItemPhysics {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Item Physics Tweaks")
        @Config.Comment("Enable tweaks for the Item Physics mod.")
        public boolean enable = false;

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

    @Config.Name("lightweight_blood_mechanics")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.lightweight_blood_mechanics")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/lightweight-blood-mechanics")
    public static final Lbm LBM = new Lbm();

    public static class Lbm {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Lightweight Blood Mechanics Tweaks")
        @Config.Comment("Enable tweaks for the Lightweight Blood Mechanics mod.")
        public boolean enable = false;

        @Config.Name("[02] Blood Overlay X-Offset")
        @Config.Comment("Set the X-Offset of the Blood Overlay.")
        public int bloodXOffset = 0;

        @Config.Name("[03] Blood Overlay Y-Offset")
        @Config.Comment("Set the Y-Offset of the Blood Overlay.")
        public int bloodYOffset = 0;

        @Config.Name("[04] Always Show Blood Overlay")
        @Config.Comment("Always show the Blood Overlay.")
        public boolean alwaysShowBloodOverlay = false;

        @Config.Name("[05] Blood Overlay Background Color")
        @Config.Comment("Set the background color of the Blood Overlay.")
        public int bloodBackgroundColor = 2005401600;

        @Config.Name("[06] Blood Overlay Foreground Color")
        @Config.Comment("Set the foreground color of the Blood Overlay.")
        public int bloodForegroundColor = 2013200384;

        @Config.Name("[07] Blood Overlay Height")
        @Config.Comment("Set the height of the Blood Overlay.")
        @Config.RangeInt(min = 0)
        public int bloodHeight = 30;

        @Config.Name("[08] Blood Overlay Width")
        @Config.Comment("Set the width of the Blood Overlay.")
        @Config.RangeInt(min = 0)
        public int bloodWidth = 5;

        @Config.Name("[09] Blood Icon X-Offset")
        @Config.Comment("Set the X-Offset of the Blood Icon.")
        public int bloodIconXOffset = 0;

        @Config.Name("[10] Blood Icon Y-Offset")
        @Config.Comment("Set the Y-Offset of the Blood Icon.")
        public int bloodIconYOffset = 0;
    }

    @Config.Name("matter_overdrive")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.matter_overdrive")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/matter-overdrive-community-edition")
    public static final MatterOverdrive MATTER_OVERDRIVE = new MatterOverdrive();

    public static class MatterOverdrive {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Matter Overdrive Tweaks")
        @Config.Comment("Enable tweaks for the Matter Overdrive mod.")
        public boolean enable = false;

        @Config.Name("[02] Always Show Matter Info")
        @Config.Comment("Always show the matter info without sneaking.")
        public boolean alwaysShowMatterInfo = false;
    }

    @Config.Name("multi_builder_tool")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.multi_builder_tool")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/multi-builder-tool")
    public static final MultiBuilderTool MULTI_BUILDER_TOOL = new MultiBuilderTool();

    public static class MultiBuilderTool {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Multi Builder Tool Tweaks")
        @Config.Comment({
                "This tweak fixes issues with the Multi Builder Tool mod.",
                "Crash with Flux Networks: https://github.com/igentuman/multi-builder-tool/issues/11"
        })
        public boolean enable = true;
    }

    @Config.Name("perfect_spawn")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.perfect_spawn")
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

    @Config.Name("pickle_tweaks")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.pickle_tweaks")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/pickle-tweaks")
    public static final PickleTweaks PICKLE_TWEAKS = new PickleTweaks();

    public static class PickleTweaks {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Pickle Tweaks")
        @Config.Comment("Enable tweaks for the Pickle Tweaks mod.")
        public boolean enable = false;

        @Config.Name("[02] Always Show Tool Info")
        @Config.Comment("Always show the tool info without sneaking.")
        public boolean alwaysShowToolInfo = false;

        @Config.Name("[03] Always Show Sword Info")
        @Config.Comment("Always show the sword info without sneaking.")
        public boolean alwaysShowSwordInfo = false;

        @Config.Name("[04] Always Show Hoe Info")
        @Config.Comment("Always show the hoe info without sneaking.")
        public boolean alwaysShowHoeInfo = false;

        @Config.Name("[05] Always Show Bow Info")
        @Config.Comment("Always show the bow info without sneaking.")
        public boolean alwaysShowBowInfo = false;
    }

    @Config.Name("potion_core")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.potion_core")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/potion-core")
    public static final PotionCore POTION_CORE = new PotionCore();

    public static class PotionCore {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Potion Core Tweaks")
        @Config.Comment("Enable tweaks for the Potion Core mod.")
        public boolean enable = false;

        @Config.Name("[02] Render Offset")
        @Config.Comment("Set the render offset of the HUD renderer.")
        public int renderOffset = 0;
    }

    @Config.Name("pyrotech")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.pyrotech")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/pyrotech")
    public static final Pyrotech PYROTECH = new Pyrotech();

    public static class Pyrotech {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Pyrotech Tweaks")
        @Config.Comment("Enable tweaks for the Pyrotech mod.")
        public boolean enable = false;

        @Config.Name("[02] Random Rocks")
        @Config.Comment("Enable random rocks in the world.")
        public boolean randomRocks = false;

        @Config.Name("[03] Weight: rock_stone")
        @Config.Comment("The weight of Stone Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int stone = 100;

        @Config.Name("[04] Weight: rock_granite")
        @Config.Comment("The weight of Granite Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int granite = 0;

        @Config.Name("[05] Weight: rock_diorite")
        @Config.Comment("The weight of Diorite Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int diorite = 0;

        @Config.Name("[06] Weight: rock_andesite")
        @Config.Comment("The weight of Andesite Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int andesite = 0;

        @Config.Name("[07] Weight: rock_dirt")
        @Config.Comment("The weight of Dirt Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int dirt = 0;

        @Config.Name("[08] Weight: rock_sand")
        @Config.Comment("The weight of Sand Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int sand = 0;

        @Config.Name("[09] Weight: rock_sandstone")
        @Config.Comment("The weight of Sandstone Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int sandstone = 0;

        @Config.Name("[10] Weight: rock_wood_chips")
        @Config.Comment("The weight of Wood Chips Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int wood_chips = 0;

        @Config.Name("[11] Weight: rock_limestone")
        @Config.Comment("The weight of Limestone Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int limestone = 0;

        @Config.Name("[12] Weight: rock_sand_red")
        @Config.Comment("The weight of Red Sand Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int sand_red = 0;

        @Config.Name("[13] Weight: rock_sandstone_red")
        @Config.Comment("The weight of Red Sandstone Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int sandstone_red = 0;

        @Config.Name("[14] Weight: rock_mud")
        @Config.Comment("The weight of Mud Rocks.")
        @Config.RangeInt(min = 0, max = 1000)
        public int mud = 0;
    }

    @Config.Name("quark")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.quark")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/quark-rotn-edition")
    public static final Quark QUARK = new Quark();

    public static class Quark {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Quark Tweaks")
        @Config.Comment("Enable tweaks for the Quark mod.")
        public boolean enable = false;

        @Config.Name("[02] Always show the Usage Ticker")
        @Config.Comment("Stops the Usage Ticker from disappearing.")
        public boolean alwaysShowUsageTicker = false;

        @Config.Name("[03] Armor Y-Offset")
        @Config.Comment("Set the Y-Offset of the Armor Part of the Usage Ticker.")
        public int armorYOffset = 0;

        @Config.Name("[04] Tool Y-Offset")
        @Config.Comment("Set the Y-Offset of the Tool Part of the Usage Ticker.")
        public int itemYOffset = 0;

        @Config.Name("[05] Enable End Stone Speleothems")
        @Config.Comment("Add and generate End Stone Speleothems.")
        public boolean enableEndSpeleothems = false;

        @Config.Name("[06] Enable Obsidian Speleothems")
        @Config.Comment("Add and generate Obsidian Speleothems.")
        public boolean enableObsidianSpeleothems = false;
    }

    @Config.Name("reskillable")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.reskillable")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/reskillable-fork")
    public static final Reskillable RESKILLABLE = new Reskillable();

    public static class Reskillable {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Reskillable Tweaks")
        @Config.Comment("Enable tweaks for the Reskillable mod.")
        public boolean enable = true;

        @Config.Name("[02] Max Level")
        @Config.Comment("Set the max level of the sum of all skills a player can have. Set to 0 to disable.")
        @Config.RangeInt(min = 0)
        public int maxLevel = 0;
    }

    @Config.Name("rustic")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.rustic")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/rustic")
    public static final Rustic RUSTIC = new Rustic();

    public static class Rustic {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Rustic Tweaks")
        @Config.Comment("Set to false to disable Rustic tweaks")
        public boolean enable = false;

        @Config.Name("[02] Berry Bush generation spread")
        @Config.Comment("Tweaking the max radius Rustic's berry bushes try to generate in per patch")
        @Config.RangeInt(min = 1, max = 16)
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

    @Config.Name("scalinghealth")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.scalinghealth")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/scaling-health")
    public static final ScalingHealth SCALING_HEALTH = new ScalingHealth();

    public static class ScalingHealth {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Scaling Health Tweaks")
        @Config.Comment("Enable tweaks for the Scaling Health mod.")
        public boolean enable = false;

        @Config.Name("[02] Y-Offset")
        @Config.Comment("Set the Y-Offset of the health bar.")
        public int yOffset = 0;
    }

    @Config.Name("simple_difficulty")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.simple_difficulty")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/simpledifficulty-for-underdog")
    public static final SimpleDifficulty SIMPLE_DIFFICULTY = new SimpleDifficulty();

    public static class SimpleDifficulty {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Simple Difficulty Tweaks")
        @Config.Comment("Set to false to disable Simple Difficulty tweaks.")
        public boolean enable = false;

        @Config.Name("[02] Thirstbar X-Offset")
        @Config.Comment("Set the X-Offset of the Thirstbar.")
        public int thirstbarXOffset = 82;

        @Config.Name("[03] Thirstbar Y-Offset")
        @Config.Comment("Set the Y-Offset of the Thirstbar.")
        public int thirstbarYOffset = -53;

        @Config.RequiresMcRestart
        @Config.Name("[04] Temperature Potion Effects")
        @Config.Comment({
                "Requires 'Player Effects' in the 'Features.cfg' to be enabled.",
                "Add additional potion effects to the player depending on the temperature.",
                "The temperature bounds must be between 0 and 25.",
                "FORMAT: lower_bound;upper_bound;effect;amplifier",
                "Example: 0;5;minecraft:slowness;2"
        })
        public String[] temperaturePotions = new String[]{};

        @Config.RequiresMcRestart
        @Config.Name("[05] Thirst Potion Effects")
        @Config.Comment({
                "Requires 'Player Effects' in the 'Features.cfg' to be enabled.",
                "Add additional potion effects to the player depending on the thirst level.",
                "The thirst bounds must be between 0 and 20.",
                "FORMAT: lower_bound;upper_bound;effect;amplifier",
                "Example: 0;5;minecraft:slowness;2"
        })
        public String[] thirstPotions = new String[]{};

        @Config.Name("[06] Thirst on Respawn")
        @Config.Comment("Set the thirst level of the player on respawn.")
        @Config.RangeInt(min = 0, max = 20)
        public int respawnThirst = 20;

        @Config.Name("[07] Thirst Saturation on Respawn")
        @Config.Comment("Set the thirst saturation of the player on respawn.")
        @Config.RangeInt(min = 0, max = 20)
        public int respawnThirstSaturation = 6;

        @Config.Name("[08] Cold Resistance Upper Limit")
        @Config.Comment("Set the upper limit of the cold resistance effect to block temperature effects.")
        @Config.RangeInt(min = 0, max = 25)
        public int coldResistanceUpperLimit = 12;

        @Config.Name("[09] Heat Resistance Lower Limit")
        @Config.Comment("Set the lower limit of the heat resistance effect to block temperature effects.")
        @Config.RangeInt(min = 0, max = 25)
        public int heatResistanceLowerLimit = 13;
    }

    @Config.Name("simple_storage_network")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.simple_storage_network")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/simple-storage-network")
    public static final SimpleStorageNetwork SIMPLE_STORAGE_NETWORK = new SimpleStorageNetwork();

    public static class SimpleStorageNetwork {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Simple Storage Network Tweaks")
        @Config.Comment("Enable tweaks for the Simple Storage Network mod.")
        public boolean enable = false;

        @Config.RequiresMcRestart
        @Config.Name("[02] Auto Select Search Bar")
        @Config.Comment("If the search bar should be auto selected on opening the GUI.")
        public boolean autoSelect = false;
    }

    @Config.Name("tool_progression")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.tool_progression")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/tool-progression")
    public static final ToolProgression TOOL_PROGRESSION = new ToolProgression();

    public static class ToolProgression {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Tool Progression Tweaks")
        @Config.Comment({
                "This tweak allows changes how the configuration file of the mod are generated.",
                "It creates a few subdirectories and moves the configuration files into them."
        })
        public boolean enable = false;
    }

    @Config.Name("waila")
    @Config.LangKey("cfg.endermodpacktweaks.tweaks.waila")
    @Config.Comment("https://www.curseforge.com/minecraft/mc-mods/hwyla")
    public static final Waila WAILA = new Waila();

    public static class Waila {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable WAILA Tweaks")
        @Config.Comment("Enable tweaks for the WAILA mod.")
        public boolean enable = false;

        @Config.RequiresMcRestart
        @Config.Name("[02] Override Block Name")
        @Config.Comment({
                "Override a block name with the name of another block.",
                "This feature mimics the Monster Egg Block behavior.",
                "FORMAT: [what block to replace];[what block to replace with]",
                "FORMAT: modid:blockid:metadata;modid:blockid:metadata",
                "EXAMPLE: minecraft:trapped_chest;minecraft:chest",
                "NOTE: this doesn't change the block preview that's displayed, only the name.",
        })
        public String[] overrideBlockName = new String[]{};
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
        ConfigAnytime.register(CfgTweaks.class);
    }
}
