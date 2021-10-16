package com.ussshenzhou.rainbow6.config;

import com.google.gson.annotations.SerializedName;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashMap;

/**
 * @author USS_Shenzhou
 * The config system's idea comes from https://github.com/TartaricAcid/BakaDanmaku.
 */
public class R6ServerMapConfig {

    public R6ServerMapConfig(String name) {
        this.name = name;
    }

    @SerializedName("map name")
    private String name;

    @SerializedName("bomb position")
    private HashMap<String, BlockPos> bombPos = new HashMap<>();

    @SerializedName("spawn position")
    private HashMap<String, BlockPos> spawnPos = new HashMap<>();

    @SerializedName("zone point min(NW)")
    private BlockPos zonePointMin;

    @SerializedName("zone point max(SE)")
    private BlockPos zonePointMax;

    @SerializedName("scene position")
    private Vector3d scenePos;

    @SerializedName("scene direction")
    private Vector2f sceneDir;

    @SerializedName("done")
    private Boolean isDone;

    public Boolean isDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getMapName() {
        return this.name;
    }

    public void setMapName(String name) {
        this.name = name;
    }

    public HashMap<String, BlockPos> getBombPos() {
        return bombPos;
    }

    public void setBombPos(HashMap<String, BlockPos> bombPos) {
        this.bombPos = bombPos;
    }

    public void setBombPos(String s, BlockPos pos) {
        this.bombPos.merge(s, pos, (pos1, pos2) -> pos2);
    }

    public void removeBombPos(String name) {
        this.bombPos.remove(name);
    }

    public HashMap<String, BlockPos> getSpawnPos() {
        return spawnPos;
    }

    public void setSpawnPos(HashMap<String, BlockPos> spawnPos) {
        this.spawnPos = spawnPos;
    }

    public void setSpawnPos(String name, BlockPos pos) {
        this.spawnPos.merge(name, pos, ((pos1, pos2) -> pos2));
    }

    public void removeSpawnPos(String name) {
        this.spawnPos.remove(name);
    }

    public BlockPos getZonePointMax() {
        return zonePointMax;
    }

    public void setZonePointMax(BlockPos zonePointMax) {
        this.zonePointMax = zonePointMax;
    }

    public BlockPos getZonePointMin() {
        return zonePointMin;
    }

    public void setZonePointMin(BlockPos zonePointMin) {
        this.zonePointMin = zonePointMin;
    }

    public Vector3d getScenePos() {
        return scenePos;
    }

    public void setScenePos(Vector3d scenePos) {
        this.scenePos = scenePos;
    }

    public Vector2f getSceneDir() {
        return sceneDir;
    }

    public void setSceneDir(Vector2f sceneDir) {
        this.sceneDir = sceneDir;
    }
}
