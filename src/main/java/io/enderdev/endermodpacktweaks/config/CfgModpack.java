package io.enderdev.endermodpacktweaks.config;

import com.cleanroommc.configanytime.ConfigAnytime;
import io.enderdev.endermodpacktweaks.Tags;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MOD_ID, name =  Tags.CFG_FOLDER + Tags.CFG_MODPACK, category = "")
public class CfgModpack {

    @Config.Name("modpack")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.modpack")
    @Config.Comment("Made with <3 by Ender-Development")
    public static final Modpack MODPACK = new Modpack();

    public static class Modpack {

        @Config.RequiresMcRestart
        @Config.Name("[01] Modpack Name")
        @Config.Comment("The name of the modpack.")
        public String modpackName = "";

        @Config.RequiresMcRestart
        @Config.Name("[02] Modpack Version")
        @Config.Comment("The version of the modpack.")
        public String modpackVersion = "";

        @Config.RequiresMcRestart
        @Config.Name("[03] Modpack Author")
        @Config.Comment("The author of the modpack.")
        public String modpackAuthor = "";

        @Config.RequiresMcRestart
        @Config.Name("[04] Modpack URL")
        @Config.Comment("A URL for the pack download.")
        public String modpackDownload = "";
    }

    @Config.Name("customization")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.customization")
    @Config.Comment("Customize the game window.")
    public static final Customization CUSTOMIZATION = new Customization();

    public static class Customization {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Window Customization")
        @Config.Comment("Customize the window of the game.")
        public boolean enable = false;

        @Config.RequiresMcRestart
        @Config.Name("[02] Change window title")
        @Config.Comment("Set the window title to the modpack name")
        public boolean windowTitle = false;

        @Config.RequiresMcRestart
        @Config.Name("[03] Window title format")
        @Config.Comment({
                "Customize how the window title is displayed",
                "Format: <lang_key>;<your_title>",
                "[name] - will be replaced by the modpack name",
                "[version] - will be replaced by the modpack version",
                "[author] - will be replaced by the modpack author"
        })
        public String[] windowTitleFormat = new String[]{"en_us;[name] ([version]) by [author]"};

        @Config.RequiresMcRestart
        @Config.Name("[04] Replace window icon")
        @Config.Comment("Replace the default icon with a custom one.")
        public boolean windowIcon = false;

        @Config.RequiresMcRestart
        @Config.Name("[05] Window icon path")
        @Config.Comment("Override the path to the icon.")
        public String windowIconPath = "config/endermodpacktweaks/icon.png";
    }

    @Config.Name("crash_info")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.crash_info")
    @Config.Comment("Add additional modpack information to the crash report.")
    public static final CrashInfo CRASH_INFO = new CrashInfo();

    public static class CrashInfo {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Crash Info")
        @Config.Comment({
                "Enable the Crash Info feature. This adds additional information to the crash report.",
                "It tries to read the information from the manifest file of the modpack.",
                "Alternatively, you can provide the information in the config file."
        })
        public boolean enable = true;

        @Config.RequiresMcRestart
        @Config.Name("[02] Read from Manifest")
        @Config.Comment({
                "Read the information from the manifest file of the modpack.",
                "This will override the config values."
        })
        public boolean readFromManifest = true;
    }

    @Config.Name("default_server")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.default_server")
    @Config.Comment("Add a default server to your modpack.")
    public static final DefaultServer DEFAULT_SERVER = new DefaultServer();

    public static class DefaultServer {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Default Server")
        @Config.Comment("Allows adding a default server.")
        public boolean enable = false;

        @Config.RequiresMcRestart
        @Config.Name("[02] Server Name")
        @Config.Comment("The name of the dedicated server to add.")
        public String serverName = "Localhost";

        @Config.RequiresMcRestart
        @Config.Name("[02] Server IP")
        @Config.Comment("The IP of the dedicated server to add.")
        public String serverIp = "127.0.0.1:25555";
    }

    @Config.Name("options_menu_buttons")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.options_menu_buttons")
    @Config.Comment("Link everything related to the modpack in the options and main menu.")
    public static final OptionsMenuButtons OPTIONS_MENU_BUTTONS = new OptionsMenuButtons();

    public static class OptionsMenuButtons {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Options Menu Buttons")
        @Config.Comment("Enable the Options Menu Buttons feature. This adds additional buttons to the options menu.")
        public boolean enable = false;

        @Config.RequiresMcRestart
        @Config.Name("[02] Custom Main Menu Integration")
        @Config.Comment("Show the buttons on Custom Main Menu menus.")
        public boolean cmmIntegration = false;

        @Config.Name("changelog_button")
        @Config.LangKey("cfg.endermodpacktweaks.modpack.options_menu_buttons.changelog")
        public final OptionsMenuButton CHANGELOG_BUTTON = new OptionsMenuButton("Changelog", "");

        @Config.Name("donation_button")
        @Config.LangKey("cfg.endermodpacktweaks.modpack.options_menu_buttons.donation")
        public final OptionsMenuButton DONATION_BUTTON = new OptionsMenuButton("Donation", "");

        @Config.Name("github_button")
        @Config.LangKey("cfg.endermodpacktweaks.modpack.options_menu_buttons.github")
        public final OptionsMenuButton GITHUB_BUTTON = new OptionsMenuButton("GitHub", "");

        @Config.Name("discord_button")
        @Config.LangKey("cfg.endermodpacktweaks.modpack.options_menu_buttons.discord")
        public final OptionsMenuButton DISCORD_BUTTON = new OptionsMenuButton("Discord", "");

        @Config.Name("twitch_button")
        @Config.LangKey("cfg.endermodpacktweaks.modpack.options_menu_buttons.twitch")
        public final OptionsMenuButton TWITCH_BUTTON = new OptionsMenuButton("Twitch", "");

        @Config.Name("youtube_button")
        @Config.LangKey("cfg.endermodpacktweaks.modpack.options_menu_buttons.youtube")
        public final OptionsMenuButton YOUTUBE_BUTTON = new OptionsMenuButton("Youtube", "");

        public static class OptionsMenuButton {
            @Config.Name("[01] Enable Button")
            public boolean enable;

            @Config.Name("[02] Button Text")
            public String buttonText;

            @Config.Name("[03] Button URL")
            public String url;

            OptionsMenuButton(String name, String url) {
                this.enable = false;
                this.buttonText = name;
                this.url = url;
            }
        }
    }

    @Config.Name("pack_updater")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.pack_updater")
    @Config.Comment("Check a JSON file from a given URL and compare it with the current modpack version.")
    public static final PackUpdater PACK_UPDATER = new PackUpdater();

    public static class PackUpdater {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Pack Updater")
        @Config.Comment("Enable the Pack Update Check feature. Check an external json url if there is a newer modpack version available.")
        public boolean enable = false;

        @Config.Name("[02] Version JSON Url")
        @Config.Comment("The URL to the JSON file that contains the newest version information.")
        public String jsonUrl = "";
    }

    @Config.Name("server_message")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.server_message")
    @Config.Comment("Similar to something that Nomifactory came up with.")
    public static final ServerMessage SERVER_MESSAGE = new ServerMessage();

    public static class ServerMessage {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Server Message")
        @Config.Comment("Enable the Server Message feature. This adds a message to the server console when the server starts.")
        public boolean enable = false;

        @Config.Name("[02] Server Name")
        @Config.Comment("The name of the server. Only used if 'Enable Server Message' is enabled.")
        public String serverName = "Minecraft";
    }

    @Config.Name("startup_timer")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.startup_timer")
    @Config.Comment("Display how long it takes for your pack to load.")
    public static final StartupTimer STARTUP_TIMER = new StartupTimer();

    public static class StartupTimer {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Startup Timer")
        @Config.Comment("Enable the Startup Timer feature and display the pack load time.")
        public boolean enable = false;

        @Config.RequiresMcRestart
        @Config.Name("[02] Timer History Size")
        @Config.Comment("How many loading times should be kept in the history?")
        public int sizeHistory = 10;

        @Config.Name("[03] Display Startup Time")
        @Config.Comment("Should the startup time be displayed in the main menu?")
        public boolean display = true;

        @Config.Name("[04] Display X-Offset")
        @Config.Comment("The x offset of the time display.")
        public int xOffset = 0;

        @Config.Name("[05] Display Y-Offset")
        @Config.Comment("The y offset of the time display.")
        public int yOffset = 0;

        @Config.Name("[06] Display Color")
        @Config.Comment("The color of the time display. Format: #RRGGBAA")
        public String color = "#FFFFFFFF";

        @Config.Name("[06] Default Startup Time")
        @Config.Comment("The default startup time before the measuring took over. In milliseconds, 0 to disable.")
        public int defaultTime = 0;

        @Config.RequiresMcRestart
        @Config.Name("[07] Time format")
        @Config.Comment({
                "Customize how the start time is displayed",
                "[minutes] - will be replaced with the minutes it took for the pack to start",
                "[seconds] - will be replaced with the seconds it took for the pack to start"
        })
        public String timeFormat = "[minutes]m [seconds]s";
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
        ConfigAnytime.register(CfgModpack.class);
    }
}
