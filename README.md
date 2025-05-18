[CREATE]: https://www.curseforge.com/minecraft/mc-mods/create

# Create Sifting

A simple sifter for the amazing Create mod.
This mod it's meant to be used in modpacks.

Heavily inspired on ex nihilo sieve.

## [Dedicated wiki](https://wiki.mechanicalmods.net/mods/create-sifter/)

## 1.21.1-2.x Version Requires Mechanicals Lib 
- [Curseforge](https://www.curseforge.com/minecraft/mc-mods/mechanicals-lib)
- [Modrinth](https://modrinth.com/mod/mechanicals-lib")

## Version support & documentation
- 1.21.1: Supported. Documentation refers to this version.
- 1.20.1: Only critical issues [1.20.x Documentation](https://github.com/oierbravo/createsifter/tree/mc1.20.1/dev)
- 1.19.x: Unsupported: [1.19.x Documentation](https://github.com/oierbravo/createsifter/tree/mc1.19/dev) 
- 1.18.x: Unsupported: [1.18.x Documentation](https://github.com/oierbravo/createsifter/tree/mc1.18/dev)

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
- Output filter.
- 8x processing per cycle, configurable.
- Bigger output capaticy, configurable.


## Recipes

### Waterlogged feature (optional)
- `"waterlogged":"true"`
- Sifters can be waterlogged to get different output.
- In hand meshes act as waterlogged when the player is in a LiquidBlock

### Advanced sifter per recipe (optional)
- `"advancedSifter":"true"`
- Recipes can require brass sifter for normal meshes too.

### Compact recipes.
- Recipes with same mesh and input block gets merged allowing easier compat.
- Current mods:
  - EnderIO
  - Ars nouveau
  - AE2

### Recipes examples
- See [Included recipes](https://github.com/oierbravo/createsifter/tree/mc1.21.1/dev/src/generated/resources/data/createsifter/recipe/sifting "Included recipes")

### KubeJS 7.1 (Minecraft 1.21.1)
- Remove al Sifting recipes.
```js
ServerEvents.recipes(event => {
  event.remove({ type: 'createsifter:sifting' })
})
```
- Chanced output (binding)
```js
Output.of('minecraft:clay', 0.5)
```
- Add recipes
```js
ServerEvents.recipes(event => {
  /** 
    createsifter.sifting(Output[] result, Ingredient ingredient, ItemStack mesh)
    .processingTime(int time) // optional, default: 500
    .waterlogged(true) //optional, default: false
    .advancedSifter(true) //optional, default: false
  **/
  event.recipes.createsifter.sifting([Output.of('minecraft:clay',0.5),Output.of('minecraft:redstone')],'minecraft:sand',"createsifter:andesite_mesh")
})
```
- Custom meshes
  - Texture location (for this examples): `kubejs/assets/kubejs/textures/item/diamond_mesh.png`
```js
StartupEvents.registry('item', event => {
    
    //Basic mesh
	event.create('diamond_mesh','createsifter:mesh')
      .displayName('Diamond Mesh')
      .parentModel("createsifter:block/meshes/mesh")
      .texture("mesh","kubejs:item/diamond_mesh")
      .maxDamage(77) //Mesh durability
	
	//Advanced mesh
	event.create('advanced_diamond_mesh','createsifter:advanced_mesh')
      .displayName('Advanced Diamond Mesh')
      .parentModel("createsifter:block/meshes/mesh")
      .texture("mesh","kubejs:item/diamond_mesh")
      .texture("frame","micraft:block/diamond_block")
      .maxDamage(200) //Mesh durability
	
})
```


**Thanks to the Creators of Create.**