[CREATE]: https://www.curseforge.com/minecraft/mc-mods/create
[DOWNLOAD]: https://www.curseforge.com/minecraft/mc-mods/create-sifting/files
[CURSEFORGE]: https://www.curseforge.com/minecraft/mc-mods/create-sifting
[MODRINTH]: https://modrinth.com/mod/create-sifter
[ISSUES]: https://github.com/oierbravo/createsifter/issues

<!-- modrinth_exclude.start -->
# Create Sifting
[![Release](https://img.shields.io/github/v/release/oierbravo/createsifter?label=Version&sort=semver)][DOWNLOAD]
[![Downloads](http://cf.way2muchnoise.eu/full_661938_downloads.svg)][CURSEFORGE]
[![Version](http://cf.way2muchnoise.eu/versions/661938.svg)][DOWNLOAD]
[![Issues](https://img.shields.io/github/issues/oierbravo/createsifter?label=Issues)][ISSUES]
[![Modrinth](https://modrinth-utils.vercel.app/api/badge/downloads?id=r018adCw&logo=true)][MODRINTH]
<!-- modrinth_exclude.end -->

[![](https://img.shields.io/badge/REQUIRES%20CREATE%20v0.5.0d-gold?logo=curseforge&labelColor=gray&style=for-the-badge)][CREATE]

A simple sifter for the amazing create mod.
This mod it's meant to be used in modpacks. Only contains very basic ore recipes.

Heavily inspired on ex nihilo sieve.

## Meshes
> Works with the sifter or in hand (like create sandpaper)
- **Tiers**: String, Andesite, Zinc, Brass
- When in hand it sifts the block in the off-hand.
## Sifter
> Automated sifting. Based on the Millstone block works more or less in the same way. You can toss items on top or feed it with any item automated way (hoppers, funnels, chute...)
- Right Click with a mesh to add it.
- Recipes can have a mesh, but it's not required.


## Sifting recipes
- Put the mesh and the siftable block in the `ingredients`, in any order.
- `results` is a list of items
- Default `processingTime` is 200. You can override this value in the recipe.
Example:
```
{
  "type": "createsifter:sifting",
  "ingredients": [
    {
      "item": "createsifter:andesite_mesh"
    },
    {
      "item": "minecraft:gravel"
    }
  ],
  "results": [
    {
      "item": "create:copper_nugget",
      "chance": 0.1
    },
    {
      "item": "create:zinc_nugget",
      "chance": 0.1
    },
    {
      "item": "minecraft:iron_nugget",
      "chance": 0.05
    },
    {
      "item": "minecraft:gold_nugget",
      "chance": 0.15
    },
    {
      "item": "minecraft:coal",
      "chance": 0.1
    },
    {
      "item": "minecraft:flint",
      "chance": 0.1
    }
  ],
  "processingTime": 500
}
```

**Thanks to the Creators of Create.**

Code inspiration from [Create Craft & Additions](https://www.curseforge.com/minecraft/mc-mods/createaddition "Create Crafts & Additions") and the [Create](https://www.curseforge.com/minecraft/mc-mods/create "Create") mod itself.


### KubeJS integration:

```
//event.recipes.createsifterSifting(output[], input[])

event.recipes.createsifterSifting([Item.of('minecraft:redstone').withChance(0.5)], ['minecraft:sand','createsifter:andesite_mesh'])
```
