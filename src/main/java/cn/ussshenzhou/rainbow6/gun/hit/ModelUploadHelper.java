package cn.ussshenzhou.rainbow6.gun.hit;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author USS_Shenzhou
 */
public class ModelUploadHelper {

    @OnlyIn(Dist.CLIENT)
    public static void writePartDefinition(FriendlyByteBuf buf, PartDefinition part) {
        buf.writeCollection(part.cubes, ModelUploadHelper::writeCubeDefinition);
        writePartPose(buf, part.partPose);
        buf.writeMap(part.children, FriendlyByteBuf::writeUtf, ModelUploadHelper::writePartDefinition);
    }

    @OnlyIn(Dist.CLIENT)
    public static void writeCubeDefinition(FriendlyByteBuf buf, CubeDefinition cube) {
        //ignore comment
        buf.writeVector3f(cube.origin);
        buf.writeVector3f(cube.dimensions);
        writeCubeDeformation(buf, cube.grow);
        buf.writeBoolean(cube.mirror);
        //ignore tex..., visibleFaces
    }

    @OnlyIn(Dist.CLIENT)
    public static void writePartPose(FriendlyByteBuf buf, PartPose pose) {
        buf.writeFloat(pose.x);
        buf.writeFloat(pose.y);
        buf.writeFloat(pose.z);
        buf.writeFloat(pose.xRot);
        buf.writeFloat(pose.yRot);
        buf.writeFloat(pose.zRot);
    }

    @OnlyIn(Dist.CLIENT)
    public static void writeCubeDeformation(FriendlyByteBuf buf, CubeDeformation deformation) {
        buf.writeFloat(deformation.growX);
        buf.writeFloat(deformation.growY);
        buf.writeFloat(deformation.growZ);
    }

    public static ServerPartDefinition readServerPartDefinition(FriendlyByteBuf buf) {
        return new ServerPartDefinition(
                buf.readList(ModelUploadHelper::readServerCubeDefinition),
                readServerPartPose(buf),
                buf.readMap(FriendlyByteBuf::readUtf, ModelUploadHelper::readServerPartDefinition)
        );
    }

    public static ServerCubeDefinition readServerCubeDefinition(FriendlyByteBuf buf) {
        return new ServerCubeDefinition(
                buf.readVector3f(),
                buf.readVector3f(),
                readServerCubeDeformation(buf),
                buf.readBoolean()
        );
    }

    public static ServerPartPose readServerPartPose(FriendlyByteBuf buf) {
        return new ServerPartPose(
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat()
        );
    }

    public static ServerCubeDeformation readServerCubeDeformation(FriendlyByteBuf buf) {
        return new ServerCubeDeformation(
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat());
    }
}
