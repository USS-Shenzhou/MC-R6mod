package cn.ussshenzhou.rainbow6.item.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author USS_Shenzhou
 */
public abstract class BaseR6ArmorModel extends HumanoidModel<Player> {
    private final ArmorItem.Type type;
    private final ModelPart helmet;
    private final ModelPart chest;
    private final ModelPart armL;
    private final ModelPart armR;
    private final ModelPart legL;
    private final ModelPart legR;
    private final ModelPart bootL;
    private final ModelPart bootR;


    public BaseR6ArmorModel(@NotNull ArmorItem.Type type) {
        super(fakeRoot());
        this.type = type;
        var root = createBodyLayer().bakeRoot();
        this.helmet = root.getChild("helmet");
        this.chest = root.getChild("chest");
        this.armL = root.getChild("armL");
        this.armR = root.getChild("armR");
        this.legL = root.getChild("legL");
        this.legR = root.getChild("legR");
        this.bootL = root.getChild("bootL");
        this.bootR = root.getChild("bootR");
    }

    public static ModelPart fakeRoot() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        List.of("head", "hat", "body", "right_arm", "left_arm", "right_leg", "left_leg").forEach(s -> partdefinition.addOrReplaceChild(s, CubeListBuilder.create(), PartPose.ZERO));
        return LayerDefinition.create(meshdefinition, 16, 16).bakeRoot();
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition helmet = partdefinition.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition chest = partdefinition.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition armL = partdefinition.addOrReplaceChild("armL", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, 0.0F));

        PartDefinition armR = partdefinition.addOrReplaceChild("armR", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, 0.0F));

        PartDefinition legL = partdefinition.addOrReplaceChild("legL", CubeListBuilder.create().texOffs(16, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition legR = partdefinition.addOrReplaceChild("legR", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition bootL = partdefinition.addOrReplaceChild("bootL", CubeListBuilder.create().texOffs(36, 12).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition bootR = partdefinition.addOrReplaceChild("bootR", CubeListBuilder.create().texOffs(32, 32).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    private void copyRotationAndRender(ModelPart to, ModelPart from, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        to.setRotation(from.xRot, from.yRot, from.zRot);
        to.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        switch (type) {
            case HELMET ->
                    copyRotationAndRender(helmet, head, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            case CHESTPLATE -> {
                copyRotationAndRender(chest, body, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
                copyRotationAndRender(armL, leftArm, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
                copyRotationAndRender(armR, rightArm, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            }
            case LEGGINGS -> {
                copyRotationAndRender(legL, leftLeg, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
                copyRotationAndRender(legR, rightLeg, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            }
            case BOOTS -> {
                copyRotationAndRender(bootL, leftLeg, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
                copyRotationAndRender(bootR, rightLeg, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            }
        }
    }


}
