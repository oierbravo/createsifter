package com.oierbravo.createsifter.ponders;

import com.oierbravo.createmechanicalextruder.components.extruder.ExtruderBlockEntity;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class PonderScenes {
    public static void extruderBasic(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("extruder", "Block generation");
        scene.configureBasePlate(0, 0, 5);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        //scene.world.showSection(util.select.layer(2), Direction.UP);
        //scene.world.showSection(util.select.fromTo(0, 0, 0, 5, 0, 5), Direction.UP);
        for(int i = 0; i < 5;i++){
            for(int j= 0; j < 5;j++){
               // scene.world.showSection(util.select.position(i, 0, j), Direction.UP);
               // scene.idle(5);
            }
        }
        //scene.world.showSection(util.select.position(0, 0, 0), Direction.UP);
        //scene.world.showSection(util.select.position(0, 0, 4), Direction.UP);
        //Cogs
        scene.idle(5);
        scene.world.showSection(util.select.position(2, 1, 5), Direction.DOWN); // back cog
        scene.idle(5);
        scene.world.showSection(util.select.position(2, 2, 5), Direction.DOWN); // back cog
        scene.idle(5);
        scene.world.showSection(util.select.position(2, 2, 4), Direction.DOWN); //shaft
        scene.idle(5);

        scene.world.showSection(util.select.position(2, 1, 3), Direction.DOWN); //andesite casing
        scene.idle(5);

        BlockPos extruderPos = util.grid.at(2, 2, 3);
        Selection extruderS = util.select.position(2, 2, 3);

        scene.world.setKineticSpeed(extruderS, 0);
        scene.world.showSection(extruderS, Direction.DOWN);

        scene.idle(5);
        scene.effects.indicateSuccess(extruderPos);
        scene.idle(10);
        scene.overlay.showText(50)
                .text("The Extruder uses rotational force to generate blocks")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.vector.topOf(extruderPos));
        scene.idle(60);

        scene.overlay.showOutline(PonderPalette.GREEN, new Object(), util.select.position(extruderPos.relative(Direction.WEST)), 60);
        scene.overlay.showOutline(PonderPalette.GREEN, new Object(), util.select.position(extruderPos.relative(Direction.EAST)), 60);
        scene.overlay.showOutline(PonderPalette.GREEN, new Object(), util.select.position(extruderPos.relative(Direction.DOWN)), 60);

        Vec3 extruderSide = util.vector.blockSurface(extruderPos, Direction.WEST);
        scene.overlay.showText(80)
                .attachKeyFrame()
                .colored(PonderPalette.GREEN)
                .text("Generation depends on side blocks.")
                .pointAt(extruderSide)
                .placeNearTarget();
        scene.idle(90);



        //scene.idle(5);

        scene.world.showSection(util.select.position(3, 2, 4), Direction.DOWN);
        scene.world.showSection(util.select.position(4, 2, 3), Direction.DOWN);
        scene.world.showSection(util.select.position(3, 2, 2), Direction.DOWN);

        scene.world.showSection(util.select.position(1, 2, 4), Direction.DOWN);
        scene.world.showSection(util.select.position(0, 2, 3), Direction.DOWN);
        scene.world.showSection(util.select.position(1, 2, 2), Direction.DOWN);

        scene.idle(5);
        scene.world.showSection(util.select.position(3, 2, 3), Direction.DOWN);
        scene.idle(5);
        scene.world.showSection(util.select.position(1, 2, 3), Direction.DOWN);
        scene.idle(5);

        scene.world.setKineticSpeed(extruderS, 32);

        Class<ExtruderBlockEntity> type = ExtruderBlockEntity.class;
        scene.world.modifyBlockEntity(extruderPos, type, pte -> pte.getExtrudingBehaviour()
                .start());
        ItemStack output = new ItemStack(Blocks.COBBLESTONE);
        scene.world.modifyBlockEntity(extruderPos, ExtruderBlockEntity.class,
                ms -> ms.outputInv.setStackInSlot(0, output));

        scene.overlay.showText(50)
                .text("When the process is done, the result can be obtained via Right-click")
                .pointAt(util.vector.blockSurface(extruderPos, Direction.WEST))
                .placeNearTarget();
        scene.idle(60);



        scene.overlay.showControls(
                new InputWindowElement(util.vector.blockSurface(extruderPos, Direction.NORTH), Pointing.RIGHT).rightClick()
                        .withItem(output),
                40);
        scene.idle(50);

        scene.addKeyframe();

        scene.idle(20);
        scene.world.showSection(util.select.position(2, 2, 2), Direction.DOWN);
        scene.world.showSection(util.select.position(2, 1, 2), Direction.NORTH);
        scene.world.showSection(util.select.position(2, 1, 1), Direction.NORTH);
        scene.world.showSection(util.select.position(2, 1, 0), Direction.NORTH);

        scene.world.modifyBlockEntity(extruderPos, type, pte -> pte.getExtrudingBehaviour()
                .start());
        scene.idle(35);
        scene.world.createItemOnBelt(util.grid.at(2, 1, 2), Direction.UP, output);

        scene.overlay.showText(50)
                .text("The outputs can also be extracted by automation")
                .pointAt(util.vector.blockSurface(extruderPos, Direction.WEST)
                        .add(-.5, .4, 0))
                .placeNearTarget();
        scene.idle(60);

        //scene.world.showSection(util.select.position(extruder), Direction.DOWN);
        /*for (int i = 0; i < 6; i++) {
            scene.idle(5);
            scene.world.showSection(util.select.position(i, 1, 2), Direction.DOWN);
        }*/

        /*for (int i = 0; i < 6; i++) {
            scene.idle(5);
            scene.world.showSection(util.select.position(i, 1, 2), Direction.DOWN);
        }*/

        scene.idle(10);


        /*scene.overlay.showText(50)
                .text("To manualy input items, drop Ingots or Plates on the top of the Mill")
                .placeNearTarget()
                .pointAt(util.vector.topOf(mill));
        scene.idle(60);


        scene.overlay.showControls(new InputWindowElement(util.vector.topOf(mill), Pointing.DOWN).rightClick(), 50);
        scene.overlay.showText(50)
                .text("Manualy retrieve the rolled output by R-clicking the Mill")
                .placeNearTarget()
                .pointAt(util.vector.topOf(mill));
        scene.idle(60);*/
    }
}
