package com.ussshenzhou.rainbow6.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ImpactGrenadeModel extends EntityModel<ImpactGrenadeEntity> {
    public ModelRenderer impactgrenade;

    public ImpactGrenadeModel(){
        textureWidth = 32;
        textureHeight = 32;

        impactgrenade = new ModelRenderer(this);
        impactgrenade.setRotationPoint(0.0F, 0.0F, 0.0F);
        impactgrenade.setTextureOffset(0, 0).addBox(-3.0F, -5.0F, -2.0F, 5.0F, 5.0F, 5.0F, 0.0F, false);
        impactgrenade.setTextureOffset(8, 10).addBox(-2.0F, -6.0F, 1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        impactgrenade.setTextureOffset(0, 12).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        impactgrenade.setTextureOffset(0, 10).addBox(-2.0F, -6.0F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
    }
    @Override
    public void setRotationAngles(ImpactGrenadeEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.impactgrenade.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
