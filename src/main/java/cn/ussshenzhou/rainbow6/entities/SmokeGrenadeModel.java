package cn.ussshenzhou.rainbow6.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * @author USS_Shenzhou
 */
public class SmokeGrenadeModel extends EntityModel<SmokeGrenadeEntity> {
    private final ModelRenderer group;
    private final ModelRenderer bone;

    public SmokeGrenadeModel() {
        textureWidth = 32;
        textureHeight = 32;

        group = new ModelRenderer(this);
        group.setRotationPoint(-12.0F, 17.0F, 11.0F);
        group.setTextureOffset(12, 5).addBox(12.0F, -24.0F, -11.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        group.setTextureOffset(9, 0).addBox(10.0F, -25.0F, -11.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 24.0F, 0.0F);
        bone.setTextureOffset(0, 0).addBox(-1.0F, -30.0F, -1.0F, 3.0F, 6.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(SmokeGrenadeEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        group.render(matrixStack, buffer, packedLight, packedOverlay);
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
