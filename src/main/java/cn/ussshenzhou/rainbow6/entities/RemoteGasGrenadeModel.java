package cn.ussshenzhou.rainbow6.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * @author USS_Shenzhou
 */
public class RemoteGasGrenadeModel extends EntityModel<RemoteGasGrenadeEntity> {
    private final ModelRenderer bone;
    public RemoteGasGrenadeModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.setTextureOffset(0, 0).addBox(-1.0F, -1.0F, 0.0F, 3.0F, 8.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(4, 11).addBox(0.0F, -2.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(10, 10).addBox(-1.0F, -4.0F, 1.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
        bone.setTextureOffset(9, 0).addBox(-1.0F, 7.0F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(0, 11).addBox(0.0F, 7.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(12, 2).addBox(0.0F, 7.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    }
    @Override
    public void setRotationAngles(RemoteGasGrenadeEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.bone.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
