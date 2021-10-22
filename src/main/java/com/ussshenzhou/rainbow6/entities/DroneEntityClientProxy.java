package com.ussshenzhou.rainbow6.entities;

import com.ussshenzhou.rainbow6.utils.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundCategory;

/**
 * @author USS_Shenzhou
 */
public class DroneEntityClientProxy {
    private static final double MAX_SILENT_SPEED = 0.04;
    private static int startPlay = -1;
    private static float pitch;
    private static double soundX;
    private static double soundY;
    private static double soundZ;
    private static boolean moveSoundPlayed = false;
    private static SimpleSound sound;

    public static void clientProxy(DroneEntity entity, double velocity, int ticksExisted) {
        if (velocity >= MAX_SILENT_SPEED) {
            if (startPlay == -1) {
                startPlay = ticksExisted % 20;
            }
            if (ticksExisted % 20 == startPlay) {
                pitch = (float) (Math.random() * 0.15 + 0.95);
                moveSoundPlayed = true;
                soundX = entity.getPosition().getX() + 0.5;
                soundY = entity.getPosition().getY() + 0.5;
                soundZ = entity.getPosition().getZ() + 0.5;
                sound = new SimpleSound(ModSounds.DRONE_MOVE, SoundCategory.PLAYERS, 1.0f, pitch, soundX, soundY, soundZ);
                Minecraft.getInstance().getSoundHandler().play(sound);
            }
        } else if (moveSoundPlayed && Minecraft.getInstance().player != null && entity.getController() == Minecraft.getInstance().player.getUniqueID()) {
            Minecraft.getInstance().getSoundHandler().stop(sound);
            moveSoundPlayed = false;
            startPlay = -1;
        }
    }
}
