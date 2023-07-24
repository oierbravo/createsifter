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

[![](https://img.shields.io/badge/REQUIRES%20CREATE%20v0.5.1c%20for%201.18.2%2F1.19.2-gold?logo=curseforge&labelColor=gray&style=for-the-badge)][CREATE]
[![](https://img.shields.io/badge/REQUIRES%20CREATE%20v0.5.1d%20for%201.20.1-gold?logo=curseforge&labelColor=gray&style=for-the-badge)][CREATE]

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

## Waterlogged feature (optional)
- Sifters can be waterlogged to get different output.
- In hand meshes act as waterlogged when the player is in a LiquidBlock

## Minimum Speed feature (optional)
- Recipes can have custom speed requeriments

## Sifting recipes
- Put the mesh and the siftable block in the `ingredients`, in any order.
- `results` is a list of items
- Default `processingTime` is 200. You can override this value in the recipe.
- Default `waterlogged` is `false`. You can override this value in the recipe.
- Default `minimumSpeed` is `0.0` You can override this value in the recipe. In RPM as `float`. Maximum allowed value is `254.0`
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

Waterlogged Example:
```
{
  "type": "createsifter:sifting",
  "ingredients": [
    {
      "item": "createsifter:string_mesh"
    },
    {
      "item": "minecraft:dirt"
    }
  ],
  "results": [
    {
      "item": "minecraft:kelp",
      "chance": 0.1
    }
  ],
  "processingTime": 500,
  "waterlogged": true
}
```

Minimum Speed example
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
  "processingTime": 500,
  "minimumSpeed": 64.0
}
```
**Thanks to the Creators of Create.**

Code inspiration from [Create Craft & Additions](https://www.curseforge.com/minecraft/mc-mods/createaddition "Create Crafts & Additions") and the [Create](https://www.curseforge.com/minecraft/mc-mods/create "Create") mod itself.


### KubeJS 5 integration:
- For minecraft `1.18.2`

#### Adding recipes (server script)

```
// event.recipes.createsifterSifting(output[], input[])
// Optional .waterlogged() .processingTime(int time)

// Basic Example
event.recipes.createsifterSifting([Item.of('minecraft:clay').withChance(0.5),Item.of('minecraft:redstone').withChance(0.1).toJson()], ['minecraft:sand','createsifter:string_mesh'])

//Waterlogged example
event.recipes.createsifterSifting([Item.of('minecraft:clay').withChance(0.5)], ['minecraft:sand','createsifter:string_mesh']).waterlogged()
```

#### Adding custom meshes (startup script)

```
event.create('example_mesh','createsifter:mesh').displayName('Example mesh')
```

### KubeJS 6.1 integration (tested with `1902.6.1-build.300` version)
- For minecraft `1.19.2`
- KubeJS 6.1 is in a very active development phase. Breaking changes may happen... be patient plz. I'll try to keep up with updates.

#### Adding recipes (server script)
```
// event.recipes.createsifterSifting(output[], input[])
// Optional .waterlogged() .processingTime(int time)

// Basic Example
event.recipes.createsifterSifting([Item.of('minecraft:clay').withChance(0.5).toJson(),Item.of('minecraft:redstone').withChance(0.1).toJson()], ['minecraft:sand','createsifter:string_mesh'])

// Waterlogged example
event.recipes.createsifterSifting([Item.of('minecraft:clay').withChance(0.5).toJson()], ['minecraft:sand','createsifter:string_mesh']).waterlogged()

// Minimum Speed Example
event.recipes.createsifterSifting([Item.of('minecraft:redstone_block').withChance(0.5).toJson(),Item.of('minecraft:redstone').withChance(0.1).toJson()], ['minecraft:sand','createsifter:string_mesh']).minimumSpeed(64)

// Custom mesh example. Custom mesh ID comes from the Startup Script
event.recipes.createsifterSifting([Item.of('minecraft:glowstone_dust').withChance(0.5).toJson(),Item.of('minecraft:redstone').withChance(0.1).toJson()], ['minecraft:sand','kubejs:example_mesh'])

```

#### Adding custom meshes (startup script)
```
event.create('example_mesh','createsifter:mesh').displayName('Example Mesh').parentModel("createsifter:block/meshes/mesh").texture("mesh","kubejs:item/example_mesh").texture("frame","kubejs:block/example_mesh_frame");
```
