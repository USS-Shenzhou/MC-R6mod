package cn.ussshenzhou.rainbow6.data;

import com.google.gson.annotations.SerializedName;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
public class Map {
    @SerializedName("map name")
    private String name;

    @SerializedName("bomb sites")
    private ArrayList<BombSite> bombSites = new ArrayList<>();

    @SerializedName("spawn positions")
    private ArrayList<SpawnPos> spawnPositions = new ArrayList<>();

    @SerializedName("zone point min(NW)")
    private BlockPos zonePointMin;

    @SerializedName("zone point max(SE)")
    private BlockPos zonePointMax;

    @SerializedName("scene position")
    private Vec3 scenePos;

    @SerializedName("scene direction")
    private Vec2 sceneDir;

    @SerializedName("usable")
    private Boolean isUsable;

    public class BombSite {

        @SerializedName("subSite1 name")
        private String subSite1Name;

        @SerializedName("subSite1 pos")
        private BlockPos subSite1Pos;

        @SerializedName("subSite2 name")
        private String subSite2Name;

        @SerializedName("subSite2 pos")
        private BlockPos subSite2Pos;

        public String getSubSite1Name() {
            return subSite1Name;
        }

        public void setSubSite1Name(String subSite1Name) {
            this.subSite1Name = subSite1Name;
        }

        public BlockPos getSubSite1Pos() {
            return subSite1Pos;
        }

        public void setSubSite1Pos(BlockPos subSite1Pos) {
            this.subSite1Pos = subSite1Pos;
        }

        public String getSubSite2Name() {
            return subSite2Name;
        }

        public void setSubSite2Name(String subSite2Name) {
            this.subSite2Name = subSite2Name;
        }

        public BlockPos getSubSite2Pos() {
            return subSite2Pos;
        }

        public void setSubSite2Pos(BlockPos subSite2Pos) {
            this.subSite2Pos = subSite2Pos;
        }
    }

    public class SpawnPos {
        @SerializedName("spawnPos name")
        private String spawnPosName;

        @SerializedName("spawnPos pos")
        private BlockPos spawnPosPos;

        public String getSpawnPosName() {
            return spawnPosName;
        }

        public void setSpawnPosName(String spawnPosName) {
            this.spawnPosName = spawnPosName;
        }

        public BlockPos getSpawnPosPos() {
            return spawnPosPos;
        }

        public void setSpawnPosPos(BlockPos spawnPosPos) {
            this.spawnPosPos = spawnPosPos;
        }
    }

    public String getName() {
        //---dev---
        return "Test";
        //return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BombSite> getBombSites() {
        //---dev---
        ArrayList<BombSite> l = new ArrayList<>();
        var b = new BombSite();
        b.setSubSite1Name("BombSite 1a");
        b.setSubSite1Pos(new BlockPos(-2, -22, -199));
        l.add(b);
        return l;
        //return bombSites;
    }

    public void setBombSites(ArrayList<BombSite> bombSites) {
        this.bombSites = bombSites;
    }

    public ArrayList<SpawnPos> getSpawnPositions() {
        //---dev---
        ArrayList<SpawnPos> l = new ArrayList<>();
        var s = new SpawnPos();
        s.setSpawnPosName("SpawnPos 1");
        s.setSpawnPosPos(new BlockPos(-16, -22, -231));
        l.add(s);
        return l;
        //return spawnPositions;
    }

    public void setSpawnPositions(ArrayList<SpawnPos> spawnPositions) {
        this.spawnPositions = spawnPositions;
    }

    public BlockPos getZonePointMin() {
        //---dev---
        return new BlockPos(-40, -22, -218);
        //return zonePointMin;
    }

    public void setZonePointMin(BlockPos zonePointMin) {
        this.zonePointMin = zonePointMin;
    }

    public BlockPos getZonePointMax() {
        //---dev---
        return new BlockPos(20, -8, -180);
        //return zonePointMax;
    }

    public void setZonePointMax(BlockPos zonePointMax) {
        this.zonePointMax = zonePointMax;
    }

    public Vec3 getScenePos() {
        return scenePos;
    }

    public void setScenePos(Vec3 scenePos) {
        this.scenePos = scenePos;
    }

    public Vec2 getSceneDir() {
        return sceneDir;
    }

    public void setSceneDir(Vec2 sceneDir) {
        this.sceneDir = sceneDir;
    }

    public Boolean getUsable() {
        return isUsable;
    }

    public void setUsable(Boolean usable) {
        isUsable = usable;
    }
}