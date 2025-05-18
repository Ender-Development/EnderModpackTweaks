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

    @Config.Name("Pack Updater")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.pack_updater")
    @Config.Comment("Check a JSON file from a given URL and compare it with the current modpack version.")
    public static final PackUpdater PACK_UPDATER = new PackUpdater();

    public static class PackUpdater {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Server Message")
        @Config.Comment("Enable the Server Message feature. This adds a message to the server console when the server starts.")
        public boolean enable = false;

        @Config.Name("[02] Version JSON Url")
        @Config.Comment("The URL to the JSON file that contains the newest version information.")
        public String jsonUrl = "";
    }

    @Config.Name("Server Message")
    @Config.LangKey("cfg.endermodpacktweaks.modpack.server_message")
    @Config.Comment("Similar to something that Nomifactory came up with.")
    public static final ServerMessage SERVER_MESSAGE = new ServerMessage();

    public static class ServerMessage {
        @Config.RequiresMcRestart
        @Config.Name("[01] Enable Server Message")
        @Config.Comment("Enable the Server Message feature. This adds a message to the server console when the server starts.")
        public boolean enable = true;

        @Config.Name("[02] Server Name")
        @Config.Comment("The name of the server. Only used if 'Enable Server Message' is enabled.")
        public String serverName = "Minecraft";
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
