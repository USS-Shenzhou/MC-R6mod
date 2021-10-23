package cn.ussshenzhou.rainbow6.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author USS_Shenzhou
 */
@OnlyIn(Dist.CLIENT)
public class DroneEntityModel extends EntityModel<DroneEntity> {
    private final ModelRenderer body;
    private final ModelRenderer wheels;
    private final ModelRenderer group1;
    private final ModelRenderer group2;

    public DroneEntityModel() {
        textureWidth = 16;
        textureHeight = 16;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 3.6F, 0.0F);
        body.setTextureOffset(0, 0).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 2.0F, 2.0F, 0.0F, false);

        wheels = new ModelRenderer(this);
        wheels.setRotationPoint(-8.0F, 3.6F, 8.0F);


        group1 = new ModelRenderer(this);
        group1.setRotationPoint(8.0F, 0.0F, -8.0F);
        wheels.addChild(group1);
        group1.setTextureOffset(4, 6).addBox(3.0F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        group1.setTextureOffset(4, 10).addBox(3.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        group1.setTextureOffset(8, 8).addBox(3.0F, -4.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        group1.setTextureOffset(3, 13).addBox(3.0F, -3.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        group1.setTextureOffset(12, 7).addBox(3.0F, -3.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        group2 = new ModelRenderer(this);
        group2.setRotationPoint(8.0F, 0.0F, -8.0F);
        wheels.addChild(group2);
        group2.setTextureOffset(0, 4).addBox(-4.0F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        group2.setTextureOffset(8, 4).addBox(-4.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        group2.setTextureOffset(0, 8).addBox(-4.0F, -4.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        group2.setTextureOffset(10, 11).addBox(-4.0F, -3.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        group2.setTextureOffset(0, 11).addBox(-4.0F, -3.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
    }
    @Override
    public void setRotationAngles(DroneEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.body.render(matrixStack, buffer, packedLight, packedOverlay);
        this.wheels.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
