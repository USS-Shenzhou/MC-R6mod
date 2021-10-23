package cn.ussshenzhou.rainbow6.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * @author USS_Shenzhou
 */
public class ProximityAlarmEntityModel extends EntityModel<ProximityAlarmEntity> {
    private final ModelRenderer bone;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer foot;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer cube_r5;
    private final ModelRenderer paint;

    public ProximityAlarmEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(4.0F, -1.0F, -3.0F);
        bone.setTextureOffset(0, 20).addBox(-7.0F, -1.0F, 0.0F, 7.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(-3.0F, 0.0F, 3.0F);
        bone.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -2.0944F, 0.0F);
        cube_r1.setTextureOffset(0, 18).addBox(-3.2321F, -1.0F, -2.5981F, 7.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(-3.0F, 0.0F, 3.0F);
        bone.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 2.0944F, 0.0F);
        cube_r2.setTextureOffset(16, 18).addBox(-3.2679F, -1.0F, -3.4641F, 7.0F, 1.0F, 1.0F, 0.0F, false);

        foot = new ModelRenderer(this);
        foot.setRotationPoint(-3.0F, -1.0F, -3.0F);


        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(4.0F, 0.0F, 3.0F);
        foot.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.3927F, 1.0472F, 0.0F);
        cube_r3.setTextureOffset(0, 12).addBox(-1.6699F, -2.7083F, -7.3563F, 3.0F, 1.0F, 4.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(4.0F, 0.0F, 3.0F);
        foot.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.3927F, -1.0472F, 0.0F);
        cube_r4.setTextureOffset(16, 8).addBox(-1.8301F, -2.3769F, -6.5562F, 3.0F, 1.0F, 4.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setRotationPoint(4.0F, 0.0F, 3.0F);
        foot.addChild(cube_r5);
        setRotationAngle(cube_r5, -0.3927F, 0.0F, 0.0F);
        cube_r5.setTextureOffset(10, 13).addBox(-2.0F, -2.5307F, 2.6955F, 3.0F, 1.0F, 4.0F, 0.0F, false);

        paint = new ModelRenderer(this);
        paint.setRotationPoint(4.0F, -1.0F, -2.0F);
        paint.setTextureOffset(0, 6).addBox(-7.0F, -0.9F, 0.0F, 7.0F, 0.0F, 6.0F, 0.0F, false);
        paint.setTextureOffset(0, 0).addBox(-7.0F, -0.1F, 0.0F, 7.0F, 0.0F, 6.0F, 0.0F, false);
    }
    @Override
    public void setRotationAngles(ProximityAlarmEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.bone.render(matrixStack, buffer, packedLight, packedOverlay);
        this.foot.render(matrixStack, buffer, packedLight, packedOverlay);
        this.paint.render(matrixStack, buffer, packedLight, packedOverlay);
    }
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
