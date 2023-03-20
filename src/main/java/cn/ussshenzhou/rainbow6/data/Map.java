package cn.ussshenzhou.rainbow6.data;

import com.google.gson.annotations.SerializedName;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
public class Map {

    public Map() {
    }

    @SerializedName("map name")
    private String name;

    @SerializedName("world name")
    private ResourceKey<Level> dimension;

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

    public Map(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
        this.dimension = ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation());
        this.bombSites = buf.readCollection(ArrayList::new, b -> {
            BombSite site = new BombSite();
            site.setSubSite1Name(b.readUtf());
            site.setSubSite1Pos(b.readBlockPos());
            site.setSubSite2Name(b.readUtf());
            site.setSubSite2Pos(b.readBlockPos());
            return site;
        });
        this.spawnPositions = buf.readCollection(ArrayList::new, b -> {
            SpawnPos pos = new SpawnPos();
            pos.setSpawnPosName(b.readUtf());
            pos.setSpawnPosPos(b.readBlockPos());
            return pos;
        });
        this.zonePointMin = buf.readBlockPos();
        this.zonePointMax = buf.readBlockPos();
        this.scenePos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.sceneDir = new Vec2(buf.readFloat(), buf.readFloat());
        this.isUsable = true;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(name);
        buf.writeResourceLocation(dimension.location());
        buf.writeCollection(bombSites, (b, bombSite) -> {
            b.writeUtf(bombSite.subSite1Name);
            b.writeBlockPos(bombSite.subSite1Pos);
            b.writeUtf(bombSite.subSite2Name);
            b.writeBlockPos(bombSite.subSite2Pos);
        });
        buf.writeCollection(spawnPositions, (b, spawnPos) -> {
            b.writeUtf(spawnPos.spawnPosName);
            b.writeBlockPos(spawnPos.spawnPosPos);
        });
        buf.writeBlockPos(zonePointMin);
        buf.writeBlockPos(zonePointMax);
        buf.writeDouble(scenePos.x);
        buf.writeDouble(scenePos.y);
        buf.writeDouble(scenePos.z);
        buf.writeFloat(sceneDir.x);
        buf.writeFloat(sceneDir.y);
    }

    public static class BombSite {

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

    public static class SpawnPos {
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
        var a = new BombSite();
        a.setSubSite1Name("BombSite 1a");
        a.setSubSite1Pos(new BlockPos(-32, -59, 62));
        a.setSubSite2Name("BombSite 1b");
        a.setSubSite2Pos(new BlockPos(-24, -59, 71));
        l.add(a);
        var b = new BombSite();
        b.setSubSite1Name("BombSite 2a");
        b.setSubSite1Pos(new BlockPos(-26, -52, 60));
        b.setSubSite2Name("BombSite 2b");
        b.setSubSite2Pos(new BlockPos(-18, -52, 60));
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
        s.setSpawnPosPos(new BlockPos(-24, -60, 112));
        l.add(s);
        var s1 = new SpawnPos();
        s1.setSpawnPosName("SpawnPos 2");
        s1.setSpawnPosPos(new BlockPos(-68, -59, 26));
        l.add(s1);
        return l;
        //return spawnPositions;
    }

    public void setSpawnPositions(ArrayList<SpawnPos> spawnPositions) {
        this.spawnPositions = spawnPositions;
    }

    public BlockPos getZonePointMin() {
        //---dev---
        return new BlockPos(-95, -61, 15);
        //return zonePointMin;
    }

    public void setZonePointMin(BlockPos zonePointMin) {
        this.zonePointMin = zonePointMin;
    }

    public BlockPos getZonePointMax() {
        //---dev---
        return new BlockPos(57, -30, 122);
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

    public ResourceKey<Level> getDimension() {
        return dimension;
    }

    public void setDimension(ResourceKey<Level> dimension) {
        this.dimension = dimension;
    }
}
