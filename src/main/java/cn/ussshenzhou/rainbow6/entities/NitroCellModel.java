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
public class NitroCellModel extends EntityModel<NitroCellEntity> {
    public ModelRenderer nitroCellModel;

    public NitroCellModel(){
        textureWidth = 64;
        textureHeight = 64;

        nitroCellModel = new ModelRenderer(this);
        nitroCellModel.setRotationPoint(0.0F, 0.0F, 0.0F);
        nitroCellModel.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -6.0F, 5.0F, 2.0F, 12.0F, 0.0F, false);
        nitroCellModel.setTextureOffset(0, 14).addBox(0.0F, -3.0F, -2.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);
        nitroCellModel.setTextureOffset(0, 2).addBox(2.0F, -3.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        nitroCellModel.setTextureOffset(0, 0).addBox(-1.0F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
    }
    @Override
    public void setRotationAngles(NitroCellEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.nitroCellModel.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}