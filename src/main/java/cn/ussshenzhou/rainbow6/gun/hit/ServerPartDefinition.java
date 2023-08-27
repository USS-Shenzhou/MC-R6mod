package cn.ussshenzhou.rainbow6.gun.hit;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author USS_Shenzhou modified from net.minecraft.client.model.*
 */
public class ServerPartDefinition {
    protected final List<ServerCubeDefinition> cubes;
    protected final ServerPartPose serverPartPose;
    protected final Map<String, ServerPartDefinition> children;

    ServerPartDefinition(List<ServerCubeDefinition> pCubes, ServerPartPose pServerPartPose) {
        this.cubes = pCubes;
        this.serverPartPose = pServerPartPose;
        this.children = Maps.newHashMap();
    }

    ServerPartDefinition(List<ServerCubeDefinition> pCubes, ServerPartPose pServerPartPose, Map<String, ServerPartDefinition> children) {
        this.cubes = pCubes;
        this.serverPartPose = pServerPartPose;
        this.children = children;
    }

    public ServerPartDefinition addOrReplaceChild(String pName, ServerCubeListBuilder pCubes, ServerPartPose pServerPartPose) {
        ServerPartDefinition p = new ServerPartDefinition(pCubes.getCubes(), pServerPartPose);
        ServerPartDefinition p1 = this.children.put(pName, p);
        if (p1 != null) {
            p.children.putAll(p1.children);
        }

        return p;
    }

    public ServerPartDefinition getChild(String pName) {
        return this.children.get(pName);
    }
}