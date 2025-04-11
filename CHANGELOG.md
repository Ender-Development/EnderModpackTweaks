# Changelog

## [0.4.4]

- 🔄 improved bar renderer with rounded options for all drawables
- 🔄 improved the description of some config options

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
- ⚙ added [AssetMover](https://www.curseforge.com/minecraft/mc-mods/assetmover) as dependency, this way minecraft yells if it is missing

## [0.1.0]

- Initial release