[CREATE]: https://www.curseforge.com/minecraft/mc-mods/create

# Create Sifting

A simple sifter for the amazing create mod.
This mod it's meant to be used in modpacks.

Heavily inspired on ex nihilo sieve.

## 1.21.1-2.x Version Requires Mechanicals Lib 
- [Curseforge](https://github.com/oierbravo/createsifter/tree/mc1.19/dev "Curseforge")
- [Modrinth](https://github.com/oierbravo/createsifter/tree/mc1.19/dev "Modrinth")

## Version support & documentation
- 1.21.1: Supported. Documentation refers to this version.
- 1.20.1: Only critical issues
- 1.19.x: Unsupported: [Documentation](https://github.com/oierbravo/createsifter/tree/mc1.19/dev "Documentation") 
- 1.18.x: Unsupported: [Documentation](https://github.com/oierbravo/createsifter/tree/mc1.18/dev "Documentation")

## Meshes
> Works with the sifter or in hand (like create sandpaper)
- String, Andesite, Brass, Sturdy
- Advanced meshes only work with the brass sifter.
- Configurable mesh durability.
  - By default, only applies to hand sifting.
- Added zinc mesh for modpacks.

## Sifter
> Automated sifting. Based on the Millstone block works more or less in the same way. You can toss items on top or feed it with any item automated way (hoppers, funnels, chute...)
- Right Click with a mesh to add it.
- Right Click with empty hand to clear inventories.
  1. Output
  2. Input
  3. Shift + right click: Mesh

## Brass Sifter
- `High` redstone disables processing
- Filters for allow or deny outputs.
- 8x processing per cycle, configurable.
- Bigger output capaticy, configurable.


## Recipes

### Waterlogged feature (optional)
- Sifters can be waterlogged to get different output.
- In hand meshes act as waterlogged when the player is in a LiquidBlock

### Advanced sifter per recipe (optional)
- Recipes can require brass sifter for normal meshes too.

### Compact recipes.
- Recipes with same mesh and input block gets merged allowing easier compat.
- Current mods:
  - EnderIO
  - Ars nouveau
  - AE2

### Recipes examples... working on the documentation.

**Thanks to the Creators of Create.**