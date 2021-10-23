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
public class ImpactGrenadeModel extends EntityModel<ImpactGrenadeEntity> {
    public ModelRenderer impactGrenade;

    public ImpactGrenadeModel(){
        textureWidth = 32;
        textureHeight = 32;

        impactGrenade = new ModelRenderer(this);
        impactGrenade.setRotationPoint(0.0F, 0.0F, 0.0F);
        impactGrenade.setTextureOffset(0, 0).addBox(-3.0F, -5.0F, -2.0F, 5.0F, 5.0F, 5.0F, 0.0F, false);
        impactGrenade.setTextureOffset(8, 10).addBox(-2.0F, -6.0F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        impactGrenade.setTextureOffset(0, 12).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        impactGrenade.setTextureOffset(0, 10).addBox(-2.0F, -6.0F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
    }
    @Override
    public void setRotationAngles(ImpactGrenadeEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.impactGrenade.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
