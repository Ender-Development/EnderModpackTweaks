package io.enderdev.endermodpacktweaks.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import io.enderdev.endermodpacktweaks.Tags;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MOD_ID, name = Tags.CFG_FEATURE, category = Tags.CFG_FOLDER + Tags.CFG_FEATURE)
public class CfgFeatures {

    @Config.Name("instant_bone_meal")
    @Config.LangKey("config.endermodpacktweaks.instant_bone_meal")
    @Config.Comment("Instantly grow crops, trees, and other plants with bone meal.")
    public static final InstantBoneMeal INSTANT_BONE_MEAL = new InstantBoneMeal();

    public static class InstantBoneMeal {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Instant Bone Meal")
        @Config.Comment({
                "Enable the Instant Bone Meal feature. This allows you to instantly grow crops, trees, and other plants with bone meal.",
                "It works like 99% of the time and I have no idea why it sometimes doesn't. So if anyone can provide some insight on that, please do."
        })
        public boolean enable = false;
    }

    @Config.Name("material_tweaker")
    @Config.LangKey("config.endermodpacktweaks.material_tweaker")
    @Config.Comment("A tool is not to your linkings? Tweak it to your likings!")
    public static final MaterialTweaker MATERIAL_TWEAKER = new MaterialTweaker();

    public static class MaterialTweaker {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Material Tweaker")
        @Config.Comment("Enable the Material Tweaker feature. This allows you to tweak the materials of the game.")
        public boolean enable = false;

        @Config.Name("[02] Tweak Stacksize")
        @Config.Comment({
                "Tweak the stacksize of items.",
                "Format: modid:itemid;stacksize"
        })
        public String[] stacksize = new String[]{};

        @Config.Name("[03] Tweak Durability")
        @Config.Comment({
                "Tweak the durability of items.",
                "Format: modid:itemid;durability"
        })
        public String[] durability = new String[]{};

        @Config.Name("[04] Tweak Harvest Level")
        @Config.Comment({
                "Tweak the harvest level of items.",
                "Format: modid:itemid;harvestlevel"
        })
        public String[] harvestLevel = new String[]{};

        @Config.Name("[05] Tweak Enchantability")
        @Config.Comment({
                "Tweak the enchantability of items.",
                "Format: modid:itemid;enchantability"
        })
        public String[] enchantability = new String[]{};

        @Config.Name("[06] Tweak Efficiency")
        @Config.Comment({
                "Tweak the efficiency of items.",
                "Format: modid:itemid;efficiency"
        })
        public String[] efficiency = new String[]{};

        @Config.Name("[07] Tweak Attack Damage")
        @Config.Comment({
                "Tweak the attack damage of items.",
                "Format: modid:itemid;attackdamage"
        })
        public String[] attackDamage = new String[]{};

        @Config.Name("[08] Tweak Attack Speed")
        @Config.Comment({
                "Tweak the attack speed of items.",
                "Format: modid:itemid;attackspeed"
        })
        public String[] attackSpeed = new String[]{};

        @Config.Name("[09] Tweak Armor Protection")
        @Config.Comment({
                "Tweak the protection of armor.",
                "Format: modid:itemid;protection"
        })
        public String[] armorProtection = new String[]{};

        @Config.Name("[10] Tweak Armor Toughness")
        @Config.Comment({
                "Tweak the toughness of armor.",
                "Format: modid:itemid;toughness"
        })
        public String[] armorToughness = new String[]{};
    }

    @Config.Name("mob_health_bar")
    @Config.LangKey("config.endermodpacktweaks.mob_health_bar")
    @Config.Comment("Add a health bar to mobs. This is a 'neat' feature.")
    public static final MobHealthBar MOB_HEALTH_BAR = new MobHealthBar();

    public static class MobHealthBar {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Mob Health Bar")
        @Config.Comment("Enable the Mob Health Bar feature. This adds a health bar to mobs.")
        public boolean enable = false;

        @Config.RequiresMcRestart
        @Config.Name("[02] Only Render with Equipment")
        @Config.Comment({
                "Should the health bar only be rendered when the player has a specific item equipped?",
                "Leaving this empty will always render the health bar.",
                "Format: modid:itemid[:metadata]",
                "Example: minecraft:diamond_helmet"
        })
        public String[] onlyRenderWithEquipment = new String[]{};

        @Config.Name("[03] Max Distance")
        @Config.Comment("The maximum distance the health bar is rendered at.")
        @Config.RangeInt(min = 0)
        public int maxDistance = 24;

        @Config.RequiresMcRestart
        @Config.Name("[04] Distance Multipliers")
        @Config.Comment({
                "Specify multipliers for the distance at which the health bar is rendered.",
                "These apply when the specified item is equipped by the player.",
                "(main hand, off hand, or armor slot)",
                "Format: modid:itemid[:metadata];multiplier",
                "Example: minecraft:diamond_sword;2.0",
        })
        public String[] distanceMultipliers = new String[]{};

        @Config.Name("[05] Render In F1")
        @Config.Comment("Should the health bar be rendered when the Interface is disabled?")
        public boolean renderInF1 = false;

        @Config.Name("[06] Height Above Mob")
        @Config.Comment("The height above the mob the health bar is rendered at.")
        @Config.RangeInt(min = 0)
        public double heightAbove = 0.6;

        @Config.Name("[07] Draw Background")
        @Config.Comment("Should the whole health bar have a background?")
        public boolean drawBackground = true;

        @Config.Name("[08] Background Shape")
        @Config.Comment("The shape of the health bar background.")
        public EnumShapeType shapeBackground = EnumShapeType.STRAIGHT;

        @Config.Name("[09] Background Radius")
        @Config.Comment("The radius of the health bar background. Only used if the shape is ROUND.")
        @Config.RangeInt(min = 0)
        public int backgroundRadius = 4;

        @Config.Name("[10] Background Color")
        @Config.Comment("The color of the background. Format: #RRGGBBAA")
        public String backgroundColor = "#00000040";

        @Config.Name("[11] Draw Gray Space")
        @Config.Comment("Should the actual bar have a gray background?")
        public boolean drawGraySpace = true;

        @Config.Name("[12] Gray Space Color")
        @Config.Comment("The color of the gray space. Format: #RRGGBBAA")
        public String graySpaceColor = "#7F7F7F7F";

        @Config.Name("[13] Draw Health Bar")
        @Config.Comment("Should the health bar be drawn?")
        public boolean drawHealthBar = true;

        @Config.Name("[14] Bar Shape")
        @Config.Comment("The shape of the health bar.")
        public EnumShapeType shapeBar = EnumShapeType.STRAIGHT;

        @Config.Name("[15] Bar Radius")
        @Config.Comment("The radius of the health bar. Only used if the shape is ROUND.")
        @Config.RangeInt(min = 0)
        public int barRadius = 2;

        @Config.Name("[16] Health Bar Alpha")
        @Config.Comment("The alpha of the health bar.")
        @Config.RangeInt(min = 0, max = 255)
        public int healthBarAlpha = 127;

        @Config.Name("[17] Background Padding")
        @Config.Comment("The padding of the background.")
        @Config.RangeInt(min = 0)
        public int backgroundPadding = 2;

        @Config.Name("[18] Background Height")
        @Config.Comment("The height of the background.")
        @Config.RangeInt(min = 0)
        public int backgroundHeight = 6;

        @Config.Name("[19] Health Bar Height")
        @Config.Comment("The height of the health bar.")
        @Config.RangeInt(min = 0)
        public int barHeight = 4;

        @Config.Name("[20] Plate Size")
        @Config.Comment("The size of the health bar plate.")
        @Config.RangeInt(min = 0)
        public int plateSize = 25;

        @Config.Name("[21] Plate Size (Boss)")
        @Config.Comment("The size of the health bar plate for bosses.")
        @Config.RangeInt(min = 0)
        public int plateSizeBoss = 50;

        @Config.Name("[22] Show Attributes")
        @Config.Comment("Should the health bar show the attributes of the mob?")
        public boolean showAttributes = true;

        @Config.Name("[23] Show Armor")
        @Config.Comment("Should the health bar show the armor of the mob?")
        public boolean showArmor = true;

        @Config.Name("[24] Group Armor")
        @Config.Comment({
                "Should the health bar group the armor of the mob?",
                "e.g. condense 5 iron icons into 1 diamond icon."
        })
        public boolean groupArmor = true;

        @Config.Name("[25] Color by Type")
        @Config.Comment("Should the health bar be colored by the type of mob instead of health percentage?")
        public boolean colorByType = false;

        @Config.Name("[26] HP Text Height")
        @Config.Comment("The height of the health text.")
        public int hpTextHeight = 14;

        @Config.Name("[27] Show Max HP")
        @Config.Comment("Should the health bar show the max HP of the mob?")
        public boolean showMaxHP = true;

        @Config.Name("[28] Show Current HP")
        @Config.Comment("Should the health bar show the current HP of the mob?")
        public boolean showCurrentHP = true;

        @Config.Name("[29] Show Percentage")
        @Config.Comment("Should the health bar show the percentage of the mob?")
        public boolean showPercentage = true;

        @Config.Name("[30] Show Name")
        @Config.Comment("Should the health bar show the name of the mob?")
        public boolean showName = true;

        @Config.Name("[31] Show Health Bar on Players")
        @Config.Comment("Should the health bar be shown on players?")
        public boolean showOnPlayers = true;

        @Config.Name("[32] Show Health Bar on Bosses")
        @Config.Comment("Should the health bar be shown on bosses?")
        public boolean showOnBosses = true;

        @Config.Name("[33] Show Health Bar on Focused")
        @Config.Comment("Should the health bar only be shown for the entity looked at?")
        public boolean showOnlyFocused = false;

        @Config.Name("[34] Show Debug Info")
        @Config.Comment("Should additional debug information be shown with F3 active?")
        public boolean enableDebugInfo = true;

        @Config.Name("[35] Mob Blacklist")
        @Config.Comment("Blacklist uses entity IDs. FORMAT: modid:entityid")
        public String[] mobBlacklist = new String[]{
                "minecraft:armorstand"
        };
    }

    @Config.Name("sync_time")
    @Config.LangKey("config.endermodpacktweaks.sync_time")
    @Config.Comment("Never see the light of day again. (If you only play at night.)")
    public static final SyncTime SYNC_TIME = new SyncTime();

    public static class SyncTime {
        @Config.RequiresWorldRestart
        @Config.Name("[01] Enable Sync Time")
        @Config.Comment("Enable the Sync Time feature. This synchronizes the world time with the system time of the server.")
        public boolean enable = false;

        @Config.Name("[02] Sleeping")
        @Config.Comment("Should sleeping be disabled?")
        public boolean sleeping = false;
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
        ConfigAnytime.register(CfgFeatures.class);
    }
}
