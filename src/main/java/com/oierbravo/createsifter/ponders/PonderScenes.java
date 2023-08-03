package com.oierbravo.createsifter.ponders;

import com.oierbravo.createsifter.content.contraptions.components.sifter.SifterBlockEntity;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.EntityElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class PonderScenes {
    public static void sifter(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("sifter", "Processing Items in the Sifter");
        scene.configureBasePlate(0, 0, 5);

        Selection belt = util.select.fromTo(1, 1, 5, 0, 1, 2)
                .add(util.select.position(1, 2, 2));
        Selection beltCog = util.select.position(2, 0, 5);

        scene.world.showSection(util.select.layer(0)
                .substract(beltCog), Direction.UP);

        BlockPos sifter = util.grid.at(2, 2, 2);
        Selection sifterSelect = util.select.position(2, 2, 2);
        Selection cogs = util.select.fromTo(3, 1, 2, 3, 2, 2);
        scene.world.setKineticSpeed(sifterSelect, 0);

        scene.idle(5);
        scene.world.showSection(util.select.position(4, 1, 3), Direction.DOWN);
        scene.world.showSection(util.select.position(2, 1, 2), Direction.DOWN);
        scene.idle(10);
        scene.world.showSection(util.select.position(sifter), Direction.DOWN);
        scene.idle(10);
        Vec3 sifterTop = util.vector.topOf(sifter);
        scene.overlay.showText(60)
                .attachKeyFrame()
                .text("Sifter process items by sifting them")
                .pointAt(sifterTop)
                .placeNearTarget();
        scene.idle(70);

        scene.world.showSection(cogs, Direction.DOWN);
        scene.idle(10);
        scene.world.setKineticSpeed(sifterSelect, 32);
        scene.effects.indicateSuccess(sifter);
        scene.idle(10);

        scene.overlay.showText(60)
                .attachKeyFrame()
                .colored(PonderPalette.GREEN)
                .text("They can be powered from the side using cogwheels")
                .pointAt(util.vector.topOf(sifter.east()))
                .placeNearTarget();
        scene.idle(70);

        ItemStack itemStack = new ItemStack(Items.GRAVEL,32);
        Vec3 entitySpawn = util.vector.topOf(sifter.above(3));

        ElementLink<EntityElement> entity1 =
                scene.world.createItemEntity(entitySpawn, util.vector.of(0, 0.2, 0), itemStack);
        scene.idle(18);
        scene.world.modifyEntity(entity1, Entity::discard);
        scene.world.modifyBlockEntity(sifter, SifterBlockEntity.class,
                ms -> ms.inputInv.setStackInSlot(0, itemStack));
        scene.idle(10);
        scene.overlay.showControls(new InputWindowElement(sifterTop, Pointing.DOWN).withItem(itemStack), 30);
        scene.idle(7);

        scene.overlay.showText(40)
                .attachKeyFrame()
                .text("Throw or Insert items at the top")
                .pointAt(sifterTop)
                .placeNearTarget();
        scene.idle(60);

        scene.world.modifyBlockEntity(sifter, SifterBlockEntity.class,
                ms -> ms.inputInv.setStackInSlot(0, ItemStack.EMPTY));

        scene.overlay.showText(50)
                .text("After some time, the result can be obtained via Right-click")
                .pointAt(util.vector.blockSurface(sifter, Direction.WEST))
                .placeNearTarget();
        scene.idle(60);

        ItemStack nugget = AllItems.COPPER_NUGGET.asStack();
        scene.overlay.showControls(
                new InputWindowElement(util.vector.blockSurface(sifter, Direction.NORTH), Pointing.RIGHT).rightClick(),
                40);
        scene.idle(50);

        scene.addKeyframe();
        scene.world.showSection(beltCog, Direction.UP);
        scene.world.showSection(belt, Direction.EAST);
        scene.idle(15);

        BlockPos beltPos = util.grid.at(1, 1, 2);
        scene.world.createItemOnBelt(beltPos, Direction.EAST, nugget);
        scene.idle(15);
        scene.world.createItemOnBelt(beltPos, Direction.EAST, new ItemStack(Items.COAL));
        scene.idle(20);

        scene.overlay.showText(50)
                .text("The outputs can also be extracted by automation")
                .pointAt(util.vector.blockSurface(sifter, Direction.WEST)
                        .add(-.5, .4, 0))
                .placeNearTarget();
        scene.idle(60);
    }
}
