# Ender's Modpack Tweaks

This is a collection of tweaks and changes for the modpacks I'm working on. I will be adding more as I go along. Now that I'm somewhat comfortable with writing mods, I don't have to rely on other mods to make the changes I want. Each feature can be enabled or disabled in the config file. So you can pick and choose what features you need for your own modpack.
If you have any suggestions, feel free to let me know.

<a href="https://www.akliz.net/enderman"><img src="https://github.com/Ender-Development/PatchouliBooks/raw/master/banner.png" align="center"/></a>

## Current Features
### Minecraft
- ✅ option to disable the 2x2 crafting field in the inventory
  - the implementation isn't optimal as I would like to remove it completely, but it is a start
- ✅ End Gateway
  - change the bedrock to something else
  - replace the entire gateway with a structure file
  - change the radius and the height the gateway can generate
- ✅ End Portal
  - change the bedrock / torches / endstone that is generated
  - replace the entire portal with a structure file
- ✅ Obsidian Spikes
  - change the obsidian that is generated
  - change the radius and the height the spikes can generate
  - change the number of spikes that generate
  - force every spike to be guarded
- ✅ Nether Portal Tweaks
  - allow portal creation in the end
  - disallow the traverse of entities
  - disallow the creation of portals in the first place
- ✅ Difficulty Tweaks
  - force a specific difficulty
  - lock the difficulty so it can't be changed afterwards
- ✅ Gamemode Tweaks
  - force a specific Gamemode
  - force Hardcore Gamemode
  - allow commands

### Mods
- ✅ tweaking [PerfectSpawn](https://github.com/lumien231/Perfect-Spawn)
  - generate a default config file if it doesn't exist
  - moved the config file to the config folder
- ✅ tweaking [Pyrotech](https://github.com/codetaylor/pyrotech-1.12)
  - tweak the rock type that generates in the world
  - you can now specify the weight of each rock type
- ✅ tweaking [Rustic](https://www.curseforge.com/minecraft/mc-mods/rustic)
  - tweak the generation of Wildberry Bushes
  - allow placing the Wildberry Bushes on more blocks
  - allow adding an offset to the Armor and Toughness Renderer
- ✅ adapting [Dragon Murder](https://www.curseforge.com/minecraft/mc-mods/dragon-murder)
  - autokill the first "free" ender dragon
  - a bunch of config options to tweak the initial end to your likings 
- ✅ tweaking [Default World Generator without Server Side Prompts](https://www.curseforge.com/minecraft/mc-mods/default-world-generator-ssp)
  - fixing a bug where the texture of the world selection screen would break if your window is too large
- ✅ tweaking [Simple Difficulty](https://www.curseforge.com/minecraft/mc-mods/simpledifficulty)
  - allow moving the thirst bar if it does overlap with something else
- ✅ tweaking [ItemPhysic Full](https://www.curseforge.com/minecraft/mc-mods/itemphysic)
  - improve the tooltip in alternative pickup mode to show the size of the itemstack that is being looked at
  - color the tooltip respectively to the item rarity
- ❌ [WIP] tweaking [Tool Progression](https://www.curseforge.com/minecraft/mc-mods/tool-progression)
  - restructures the config folder
  - prevents spamming the server chat everytime someone wants to write a command
  - load the magic mushroom item only when tconstruct is loaded
  - **currently unusable as the mod blocks mixins when outside a dev environment**
- ✅ tweaking [First Aid](https://www.curseforge.com/minecraft/mc-mods/first-aid)
  - center the HUD, so it's easier to place next to the hotbar, independent of the screen width
  - disable the Tutorial Message
- ✅ tweaking [Quark](https://www.curseforge.com/minecraft/mc-mods/quark-rotn-edition)
  - always show the usage ticker
  - added config options to add more Speleothems to the world
- ✅ tweaking [Dark Utilities](https://www.curseforge.com/minecraft/mc-mods/dark-utilities)
  - increase the CollisionBox of the Vector Plate, to make items visible when using something like ItemPhysics
  - allow Vector Plates to insert items into inventories (either in front or below the plate)
  - only allow Items to be moved by the Vector Plate
- ✅ tweaking [Lightweight Blood Mechanics](https://www.curseforge.com/minecraft/mc-mods/lightweight-blood-mechanics
  - add an offset to the bleeding overlay renderer
  - increase the size of the bleeding overlay
- ✅ tweaking [BetterEndForge Backport](https://www.curseforge.com/minecraft/mc-mods/betterendforge-backport)
  - override a few hardcoded values to make it compatible with my other end tweaks
  - **These may be removed in the future when the mod author makes them actually configurable**
- ✅ adapted [Pack Crash Info](https://www.curseforge.com/minecraft/mc-mods/pack-crash-info)
  - the mods license (ARR) doesn't allow forking so I rewrote the functionality from scratch
- ✅ tweaking [Backpack Opener](https://www.curseforge.com/minecraft/mc-mods/backpack-opener)
  - remove CraftTweaker dependency
  - allow adding entries via the config file


## Possible Bugfixes

- 🔳 [Hearth Well Cores](https://github.com/wolforcept/hearthwell/issues/60)
- 🔳 [Campfire Client Code on server](https://github.com/jbredwards/Campfire-Mod/issues/9)

## [Ender-Development](https://github.com/Ender-Development)

Our Team currently includes:
- `_MasterEnderman_` - Project-Manager, Developer
- `Klebestreifen` - Developer

You can contact us on our [Discord](https://discord.gg/JF7x2vG).

## Contributing
Feel free to contribute to the project. We are always happy about pull requests.
If you want to help us, you can find potential tasks in the [issue tracker](https://github.com/Ender-Development/EnderModpackTweaks/issues).
Of course, you can also create new issues if you find a bug or have a suggestion for a new feature.
Should you have any questions, feel free to ask us on [Discord](https://discord.gg/JF7x2vG).

## Partnership with Akliz

> It's a pleasure to be partnered with Akliz. Besides being a fantastic server provider, which makes it incredibly easy to set up a server of your choice, they help me to push myself and the quality of my projects to the next level. Furthermore, you can click on the banner below to get a discount. :')

<a href="https://www.akliz.net/enderman"><img src="https://github.com/MasterEnderman/Zerblands-Remastered/raw/master/Akliz_Partner.png" align="center"/></a>

If you aren't located in the [US](https://www.akliz.net/enderman), Akliz now offers servers in:

- [Europe](https://www.akliz.net/enderman-eu)
- [Oceania](https://www.akliz.net/enderman-oce)