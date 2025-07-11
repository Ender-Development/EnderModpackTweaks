# Current Features of Ender's Modpack Tweaks

## 🆕 Features

### 🐲 [Enhanced Boss Bars](https://www.curseforge.com/minecraft/mc-mods/enhanced-boss-bars) for 1.12.2

- my own version of the mod, since the original one is ARR licensed and only available for modern versions
- utilizes [AssetMover](https://www.curseforge.com/minecraft/mc-mods/assetmover) to obtain the textures without breaking the license
- now also contains some custom made ones by `维生素` and `thomaslovlin`
- Rats Boss Bars borrowed from [Dynamitastic](https://www.curseforge.com/minecraft/texture-packs/improved-boss-bars-rats-mod)

### 🔘 Menu Buttons

- add the most important links to the options and main menu
- links to the modpack's GitHub, Discord, etc.
- compatible with [Custom Main Menu](https://www.curseforge.com/minecraft/mc-mods/custom-main-menu)

### ⏰ TimeSync

- sync the in-game time with the system time of the server
- this makes minecraft days actually 24 hours long
- added an option that disables sleeping

## 👩🏻‍🔧 Tweaks

### 🌎 Minecraft

- Inventory Crafting
    - option to disable the 2x2 crafting field in the inventory
    - the implementation isn't optimal as I would like to remove it completely, but it is a start
    - the crafting field can now be unlocked with a game stage
- End Gateway
    - change the bedrock to something else
    - replace the entire gateway with a structure file
    - change the radius and the height the gateway can generate
    - replace this file `endermodpacktweaks:structures/end_gateway.nbt`
    - alternatively change the location in the config file
- End Portal
    - change the bedrock / torches / endstone that is generated
    - replace the entire portal with a structure file
    - replace these files `endermodpacktweaks:structures/end_portal.nbt` and `endermodpacktweaks:structures/end_portal_active.nbt`
    - alternatively change the location in the config file
- Obsidian Spikes
    - change the obsidian that is generated
    - change the radius and the height the spikes can generate
    - change the number of spikes that generate
    - force every spike to be guarded
- Nether Portal Tweaks
    - allow portal creation in the end
    - disallow the traverse of entities
    - disallow the creation of portals in the first place
- Difficulty Tweaks
    - force a specific difficulty
    - lock the difficulty so it can't be changed afterwards
- Gamemode Tweaks
    - force a specific gamemode
    - force hardcore
    - allow commands
- Visuals
    - disable the display of the held item name above the hotbar
    - disable various overlays
    - remove the 'Java Edition' text from the main menu
- Player Effects
    - apply potion effects to the player based on hunger and health
    - similar to the effects of Simple Difficulty
- Credits
  - replace the default credits with a custom one
  - replace the default end poem with a custom one
  - trigger the credits via a command or advancement

### [Arcane World](https://www.curseforge.com/minecraft/mc-mods/arcane-world-fixed)

- fix the lag when being in the arena or dungeon dimension
- the `Levitator` can now be disabled with a redstone signal

### [Astral Sorcery](https://www.curseforge.com/minecraft/mc-mods/astral-sorcery)

- allow Fake Players to interact with various blocks

### [Backpack Opener](https://www.curseforge.com/minecraft/mc-mods/backpack-opener)

- remove CraftTweaker dependency
- allow adding entries via the config file

### [BetterEndForge Backport](https://www.curseforge.com/minecraft/mc-mods/betterendforge-backport)

- override a few hardcoded values to make it compatible with my other end tweaks
- **these may be removed in the future when the mod author actually makes them configurable**

### [Cases](https://www.curseforge.com/minecraft/mc-mods/cases)

- allow skipping the case opening animation

### [Crissaegrim](https://www.curseforge.com/minecraft/mc-mods/crissaegrim)

- [works again with the newest version of MysticalLib](https://github.com/MysticMods/MysticalLib/issues/40)
- allow setting the mob it drops from
- allow setting the drop chance
- disable the drop
- change the color of the slash effect

### [Dark Utilities](https://www.curseforge.com/minecraft/mc-mods/dark-utilities)

- increase the CollisionBox of the Vector Plate, to make items visible when using something like ItemPhysics
- allow Vector Plates to insert items into inventories (either in front or below the plate)
- only allow Items to be moved by the Vector Plate

### [Default World Generator without Server Side Prompts](https://www.curseforge.com/minecraft/mc-mods/default-world-generator-ssp)

- fixed a bug where the texture of the world selection screen would break if your window is too large

### [Delivery](https://www.curseforge.com/minecraft/mc-mods/delivery)

- fix the NPE log spam

### [Dynamics Surroundings: HUDs](https://www.curseforge.com/minecraft/mc-mods/dynamic-surroundings-huds)

- added the options to blacklist potion effects from showing on the potion HUD

### [Elenai Dodge 2](https://www.curseforge.com/minecraft/mc-mods/elenai-dodge-2)

- add potion effects according to the stamina of the player

### [First Aid](https://www.curseforge.com/minecraft/mc-mods/first-aid)

- center the HUD, so it's easier to place next to the hotbar, independent of the screen width
- disable the Tutorial Message

### [Flux Networks](https://www.curseforge.com/minecraft/mc-mods/flux-networks)

- allow overriding the max tier of IC2 Energy
- allow manually setting the max tier of IC2 Energy

### [Game Stages](https://www.curseforge.com/minecraft/mc-mods/game-stages)

- disable the [Item Stages](https://www.curseforge.com/minecraft/mc-mods/item-stages) tooltip
- localize the stage names displayed by [Recipe Stages](https://www.curseforge.com/minecraft/mc-mods/recipe-stages)
- added a scroll through effect if the stage name becomes to long
- a bunch of config option for said effect

### [ItemPhysic Full](https://www.curseforge.com/minecraft/mc-mods/itemphysic)

- improve the tooltip in alternative pickup mode to show the size of the item stack that is being looked at
- color the tooltip respectively to the item rarity

### [Lightweight Blood Mechanics](https://www.curseforge.com/minecraft/mc-mods/lightweight-blood-mechanics)

- add an offset to the bleeding overlay renderer
- increase the size of the bleeding overlay

### [Matter Overdrive](https://www.curseforge.com/minecraft/mc-mods/matter-overdrive-community-edition)

- always render the matter tooltip of the items

### [Modular Materials](https://www.curseforge.com/minecraft/mc-mods/modular-materials)

- fix registering OreDicts to late for `CraftTweaker` to access them

### [Morph](https://www.curseforge.com/minecraft/mc-mods/morph)

- tweak to replace the FlapFly ability with creative flight, so you no longer kill your spacebar when trying to fly

### [Multi Builder Tool](https://www.curseforge.com/minecraft/mc-mods/multi-builder-tool)

- fix crash with [Flux Networks](https://github.com/igentuman/multi-builder-tool/issues/11)

### [NoCtrl](https://www.curseforge.com/minecraft/mc-mods/noctrl)

- now compatible with my implementation of `Controlling`

### [PerfectSpawn](https://github.com/lumien231/Perfect-Spawn)

- generate a default config file if it doesn't exist
- moved the config file to the config folder

### [Pickle Tweaks](https://www.curseforge.com/minecraft/mc-mods/pickle-tweaks)

- always render the tooltip of Bows, Hoes, Weapons, and Tools

### [Potion Core](https://www.curseforge.com/minecraft/mc-mods/potion-core)

- add an offset to the potion HUD

### [Pyrotech](https://github.com/codetaylor/pyrotech-1.12)

- tweak the rock type that generates in the world
- you can now specify the weight of each rock type
- now also allows ignitions from items that extend the base FlintAndSteelItem class

### [Quark](https://www.curseforge.com/minecraft/mc-mods/quark-rotn-edition)

- always show the usage ticker
- allow setting a vertical offset to the usage ticker
- add more Speleothems to the world (obsidian, endstone)

### [Reskillable](https://www.curseforge.com/minecraft/mc-mods/reskillable-fork)

- order all skills alphabetically
- set a max level for the player, which can be spread across all skills
  - comes with appropriate tooltips
  - tooltips are translatable

### [Rustic](https://www.curseforge.com/minecraft/mc-mods/rustic)

- tweak the generation of Wildberry Bushes
- allow placing Wildberry Bushes on more blocks
- allow adding an offset to the Armor and Toughness Renderer

### [Scaling Health](https://www.curseforge.com/minecraft/mc-mods/scaling-health)

- add an offset to the health bar renderer

### [Simple Difficulty](https://www.curseforge.com/minecraft/mc-mods/simpledifficulty)

- allow moving the thirst bar if it overlaps with something else
- add potion effects according to the temperature of the player
- add potion effects according to the thirst of the player
- define the amount of thirst the player respawns with

### [Simple Storage Network](https://www.curseforge.com/minecraft/mc-mods/simple-storage-network)

- disable the auto select of the search bar

### [Tool Progression](https://www.curseforge.com/minecraft/mc-mods/tool-progression)

- restructured the config folder
- prevents spamming the server chat everytime someone wants to write a command
- load the magic mushroom item only when tconstruct is loaded

### [WAILA / HWYLA](https://www.curseforge.com/minecraft/mc-mods/hwyla)

- allow lying about the block the player is looking at

## 💱 Replacements

### [Anti FOV Change](https://www.curseforge.com/minecraft/mc-mods/anti-fov-change)

- licensed under ARR
- rewrote the functionality from scratch

### [Astral Sorcery Anti Anti Fake Player](https://curseforge.com/minecraft/mc-mods/astral-sorcery-anti-anti-fake-player-asaafp)

- licensed under the Unlicense
- single mixin mod, merged

### [Bonemeal Mod](https://www.curseforge.com/minecraft/mc-mods/bonemeal-mod)

- licensed under Public Domain
- single class mod, merged
- fixed a small issue where the mod wouldn't work properly
- added a check so you won't waste bone meal on fully grown plants

### [Client Tweaks](https://www.curseforge.com/minecraft/mc-mods/client-tweaks)

- licensed under MIT
- EMT doesn't contain all of it's features to avoid duplication
- if you want a specific tweak from it let me know
- the following tweaks are part of EMT right now:
  - disable Auto-Jump (this includes the Button in the Controls)
  - add a master Volume Slider to the main options screen

### [ChunkOMG](https://www.curseforge.com/minecraft/mc-mods/chunkomg)

- licensed under Public Domain
- single class mod, merged

### [Controlling](https://www.curseforge.com/minecraft/mc-mods/controlling)

- licensed under MIT
- as I have plans to expand on its functionality, I decided to adapt it
- more features will be added in the future

### [Dragon Murder](https://www.curseforge.com/minecraft/mc-mods/dragon-murder)

- licensed under MIT
- rewrote from scratch
- added a bunch more config options
  - spawn dragon egg
  - replace first dragon egg with something else
  - light the portal back to the overworld
  - generate an end gateway

### [Forced Resourcepacks](https://www.curseforge.com/minecraft/mc-mods/forced-resourcepacks)

- licensed under ARR
- rewrote its functionality
- added an option to not remove unlisted resourcepacks

### [Hide Item Names](https://www.curseforge.com/minecraft/mc-mods/hide-item-names)

- licensed under MIT
- single class mod, merged

### [Hide Name Tags](https://www.curseforge.com/minecraft/mc-mods/hide-name-tags)

- licensed under MIT
- single class mod, merged

### [It's the little things](https://www.curseforge.com/minecraft/mc-mods/its-the-little-things)

- licensed under MIT
- title can now use general modpack information
- title can now be localized via config
- path to icon can be customized
- general cleanup

### [Material Changer](https://www.curseforge.com/minecraft/mc-mods/material-changer)

- licensed under MIT
- instead of one long list, entries are now separated by categories

### [Mod Pack Update Checker](https://www.curseforge.com/minecraft/mc-mods/mod-pack-update-checker)

- licensed under WTFPL
- originally made for RotN
- last update was in October 2020
- merged as it can be seen as a modpack tool
- screen can now be localized
- better version matching

### [Neat](https://www.curseforge.com/minecraft/mc-mods/neat)

- rewrote parts of it
- achieved a noticeable performance improvement
- added a bunch more config options
- only allow rendering when a specific item is equipped
- allow specific items to increase the render distance of the health bar
- allow the render to linger, when using the "focused only" mode

### [Pack Crash Info](https://www.curseforge.com/minecraft/mc-mods/pack-crash-info)

- licensed under ARR
- rewrote its functionality from scratch

### [Startup Timer](https://www.curseforge.com/minecraft/mc-mods/startup-timer)

- added my own version
- has a few more config options

### [Witherproof](https://www.curseforge.com/minecraft/mc-mods/witherproof)

- licensed under LGPLv2.1
- mimic its functionality

## 🔀 Mod Integration

### [Simple Difficulty](https://www.curseforge.com/minecraft/mc-mods/simpledifficulty) and [Elenai Dodge 2](https://www.curseforge.com/minecraft/mc-mods/elenai-dodge-2)

- How much thirst should be added when the player dodges
- The threshold at which the dodge should be canceled
- The minimum thirst level required to regenerate stamina
- How much longer (in ticks) it takes to regenerate stamina for each missing thirst level
- How much thirst should be added when the player regenerates stamina