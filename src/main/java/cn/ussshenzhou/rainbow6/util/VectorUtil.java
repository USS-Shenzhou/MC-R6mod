package cn.ussshenzhou.rainbow6.util;

import net.minecraft.world.phys.Vec3;

/**
 * This file is copied and modified from com.alrex.parcool.utilities.VectorUtil under GPLv3.
 *
 * @author USS_Shenzhou
 * TODO use vanilla lib instaed.
 */
public class VectorUtil {
    public static double toYawDegree(Vec3 vec) {
        return (Math.atan2(vec.z, vec.x) * 180.0 / Math.PI - 90);
    }

    public static double toPitchDegree(Vec3 vec) {
        return -(Math.atan2(vec.y, Math.sqrt(vec.x * vec.x + vec.z * vec.z)) * 180.0 / Math.PI);
    }

    public static Vec3 fromYawDegree(double degree) {
        return new Vec3(-Math.sin(Math.toRadians(degree)), 0, Math.cos(Math.toRadians(degree)));
    }
}
