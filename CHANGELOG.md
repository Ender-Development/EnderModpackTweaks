# Changelog

## [0.5.10]

- 🆕 the `Arcane World` Levitator can now be controlled with redstone
- 🆕 tweak for the `morph` mod to replace the FlapFly ability with creative flight
- 🆕 remove the 'Java Edition' text from the main menu
- 🔄 even more performance improvements to the mob health renderer (thanks to tttsaurus)
- 🔄 removed `modernsplash` from the "incompatible mods list"
- 🔄 inventory crafting can now be unlocked via a gamestage
- 🔄 my game window and main menu tweaks are now compatible with `fancymenu` (thanks to CaliforniaDemise)
- 🧰 a few small improvements (thanks to rozbrajaczpoziomow)
- 🧰 fix broken Astral Sorcery mixin

## [0.5.9]

- 🆕 set default gamerules via config (setting via world creation screen will be added later)
- 🆕 fixed an issue in [Modular Materials](https://www.curseforge.com/minecraft/mc-mods/modular-materials), where it registers its oredicts too late
- 🔄 added a few default entries for the boss proof ore dictionaries
- 🧰 my implementation of controlling is now compatible with [NoCtrl](https://www.curseforge.com/minecraft/mc-mods/noctrl)

## [0.5.8]

- 🔄 window title and icon are now set before the window is created
- 🔄 moved start up time string format to config, default is now equivalent with the main menu string
- 🔄 main menu startup string can now be anchored to different positions
- 🧰 fixed window icon not showing up in taskbar

## [0.5.7]

- 🆕 added option to force specific resource packs
- 🆕 set a default startup time, if no history is found
- 🔄 window title can now be localized via config (please check your `modpack.cfg`)
- 🧰 fixed compat with tinyinventory
- 🧰 fixed crash with startup time, when starting the pack too often

## [0.5.6]

- 🆕 added my own version of a startup timer with additional config options
- 🆕 update screen text can now be translated
- 🔄 small improvement when comparing versions for the updater
- 🧰 fixed update screen not showing
- 🧰 fixed window icon getting changed later than window title

## [0.5.5]

- 🆕 merged [It's the little things](https://www.curseforge.com/minecraft/mc-mods/its-the-little-things)
- 🆕 window title can now utilize the modpack info from the config
- 🆕 window icon location can be anywhere in the minecraft instance directory
- 🆕 merged [Mod Pack Update Checker](https://www.curseforge.com/minecraft/mc-mods/mod-pack-update-checker)
- 🆕 added boss bars to 'Rats'
- 🆕 allow the mob health renderer to linger, when using the "focused only" mode (thanks to rozbrajaczpoziomow)
- 🧰 fixed boss bar renderer being off center
- 🧰 fixed Item Name Hide tweak breaking under certain circumstances
- ⚙ various small improvements by rozbrajaczpoziomow

## [0.5.4]

- 🔄 improved Mob Health Bar rendering time again (thanks to tttsaurus)
- 🧰 fix broken end related mixins
- 🧰 fix broken boss proof blocks mixins

## [0.5.3]

- 🆕 added a tweak to simple storage network to disable the auto select of the search bar (thanks to Invader)
- 🆕 added a way to blacklist blocks from being destroyed by the Ender Dragon and Wither
- 🆕 modpack buttons are now compatible with custom main menu
- 🆕 added a config option for the effect color of the Crissaegrim (thanks to rozbrajaczpoziomow)
- 🆕 fixed the lag when being in the Arcane World dimensions
- 🔄 improved Mob Health Bar rendering time again (thanks to rozbrajaczpoziomow)
- 🔄 improved a few EventHandler (thanks to rozbrajaczpoziomow)
- 🧰 fixed server crash for mob health bar
- 🧰 fixed mob health bar boss rendering option
- 🧰 fixed showOnlyFocused mode not respecting max range

## [0.5.2]

- 🆕 fixed the NPE spam when using the 'delivery' mod (suggested by Sereath)
- 🔄 removed categories from config (requested by WaitingIdly) to remove an unnecessary indentation
- 🧰 fixed name hide tweak

## [0.5.1]

- 🧰 fixed cases mod mixins

## [0.5.0]

- 🆕 overhauled the config system, the mod now creates a directory in the config folder with all configs
- 🆕 added a tweak that mimics 'Controlling' (to be extended in the future)
- 🆕 added a tweak to the cases mod to skip the opening animation
- 🆕 added an offset to the 'scaling health' health renderer
- 🆕 added boss bars to 'Scape and Run: Parasites' (Thanks to thomaslovlin)
- 🆕 added Elenai Dodge Stamina as player potion criteria
- 🔄 improved bar renderer with rounded options for all drawables
- 🔄 improved the description of some config options
- 🔄 improved instant bone meal tweak to not work on full-grown plants
- 🧰 fixed the changelog missing on export
- 🧰 fixed the rounded mob health bar having weird artefacts
- 🧰 fixed weird behavior with the player potion effects

## [0.4.3]

- 🆕 added an additional boss bar for the `Ancient Wyrk` (Thanks to 维生素)
- 🆕 added an additional boss bar for the `Knight of Burning Hell` (Thanks to 维生素)
- 🆕 added a mob health bar renderer for all mods (similar to Neat)
- 🆕 added a tweak to make bone meal instantly grow plants
- 🔄 improved the server greeting message
- 🔄 the main menu / options buttons now actually render in the middle of the screen
- 🧰 fixed server crash with rustic mixins
- 🧰 fixed server crash with item physic mixins
- 🧰 fixed server crash with crissaegrim mixins
- 🧰 fixed server crash with item stages mixins
- 👾 refactored internal proxy, further splitting the client and server code

## [0.4.2]

- 🆕 added a server greeting message
- 🆕 added main menu / options buttons for related pack links (e.g. Discord, GitHub, etc.)
- 🧰 fixed potion effects crashing when a potion is registered after my mod
- 🧰 fixed boss bars without a custom texture not rendering the boss name
- 🧰 fixed the bossbar of the Frostmaw using the wrong texture
- 👾 refactored how I obtain the modpack info for future features (this may cause some formatting issues in existing configs)

## [0.4.1]

- 🆕 added a tweak to item stages to remove the tooltip
- 🆕 added a tweak to game stages related mods to localize stage names
- 🧰 fixed localization of a few config options
- 🧰 fixed potion effects of various conditions
- 🧰 fixed temperature potion effects not respecting the heat/cold resistance potion

## [0.4.0]

- 🆕 added a few mixins to Pyrotech to allow ignition items that extend the base FlintAndSteelItem
- 🆕 added a tweak to apply potion effects to the player based on hunger and health
- 🧰 fixed MatterOverdrive mixin

## [0.3.1]

- 🆕 added tweaks for Pickle Tweaks to always render certain tooltips
- 🆕 added tweaks for Matter Overdrive to always render certain tooltips
- 🆕 added another tweak to Reskillable to allow setting a max level for the player

## [0.3.0]

- 🆕 added render offset tweak to potion core
- 🆕 added a config to let HWYLA _lie_ to you
- 🧰 fixed Crissaegrim mixins

## [0.2.1]

- 🆕 added more client tweaks
- 🆕 added more tweaks to Simple Difficulty
- 🆕 added tweaks for Dynamics Surroundings HUDs
- 🆕 added a warning screen (similar to Universal Tweaks) when using a mod that this mod replaces
- 🧰 fixed Quark Usage Ticker
- 🧰 fixed Rustic mixins
- ⚙ reworked the dependencies
- ⚙ improved assetmover implementation

## [0.2.0]

- 🆕 moved all features to a separate file to make it easier to maintain [FEATURES](https://github.com/Ender-Development/EnderModpackTweaks/FEATURES.md)
- ⚙ added [AssetMover](https://www.curseforge.com/minecraft/mc-mods/assetmover) as depe