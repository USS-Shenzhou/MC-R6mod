package cn.ussshenzhou.rainbow6.gui;

import cn.ussshenzhou.rainbow6.entities.DroneEntity;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.MovementInput;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author USS_Shenzhou
 */
public class R6DroneGui extends AbstractGui {
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private MatrixStack matrixStack;

    public R6DroneGui(MatrixStack matrixStack) {
        this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
        this.minecraft = Minecraft.getInstance();
        this.matrixStack = matrixStack;
    }

    private ArrayList<DroneEntity> drones = new ArrayList<>();
    private DroneEntity currentDrone = null;
    private int hasSignal = -1;

    public void init() {
        //this.tick();
        InGameClientProperties.interceptInGameMenu = true;
    }

    public void controlDrone(DroneEntity drone) {
        this.currentDrone = drone;
        drone.setController(minecraft.player.getUniqueID());
        minecraft.setRenderViewEntity(drone);
    }

    public void setDrone(DroneEntity drone) {
        this.controlDrone(drone);
    }

    public void close() {
        if (this.hasSignal == -1) {
            currentDrone.setController(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        }
        InGameClientProperties.isUsingDrone = false;
        minecraft.setRenderViewEntity(minecraft.player);
        InGameClientProperties.resetR6DroneGui();
    }

    public void render() {
        RenderSystem.disableBlend();
        //RenderSystem.enableBlend();
        /*matrixStack.push();
        fill(matrixStack, (int) (this.width / 20), (int) (this.height / 1.35), (int) (this.width / 20 + 20 * 5), (int) (this.height / 1.35 + 16), 0xff2195ec);
        matrixStack.pop();*/

    }


    public void tick() {
        ArrayList<DroneEntity> drones2;
        if (ClientMatch.isInGame()) {
            drones2 = Lists.newArrayList(minecraft.world.getEntitiesWithinAABB(DroneEntity.class, ClientMatch.getGameRange()));
        } else {
            drones2 = Lists.newArrayList(minecraft.world.getEntitiesWithinAABB(DroneEntity.class, minecraft.player.getBoundingBox().grow(50d)));
        }
        drones2.sort((DroneEntity::compareTo));
        drones = drones2;
        if (drones.isEmpty()) {
            this.hasSignal = 0;
        } else if (currentDrone == null) {
            //get first drone at init
            this.setDrone(drones.get(0));
        } else {
            //current drone destroyed
            if (!drones.contains(currentDrone)) {
                this.hasSignal = 0;
            }
            if (this.hasSignal >= 0) {
                this.hasSignal++;
            }
            if (this.hasSignal == 40) {
                currentDrone = null;
                this.hasSignal = -1;
            }
        }
        //drone control
        if (currentDrone != null) {
            MovementInput movement = minecraft.player.movementInput;
            currentDrone.updateInputs(movement.leftKeyDown, movement.rightKeyDown, movement.forwardKeyDown, movement.backKeyDown, movement.jump);
        }
    }

    public DroneEntity getCurrentDrone() {
        return currentDrone;
    }
}
