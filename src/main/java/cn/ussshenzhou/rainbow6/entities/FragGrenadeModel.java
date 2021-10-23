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
public class FragGrenadeModel extends EntityModel<FragGrenadeEntity> {

    private final ModelRenderer group;
    private final ModelRenderer part1;
    private final ModelRenderer part2;
    private final ModelRenderer bone;

    public FragGrenadeModel() {
        textureWidth = 32;
        textureHeight = 32;

        group = new ModelRenderer(this);
        group.setRotationPoint(0.0F, 0.0F, 0.0F);
        group.setTextureOffset(0, 0).addBox(-3.0F, -2.0F, -2.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
        group.setTextureOffset(13, 13).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);

        part1 = new ModelRenderer(this);
        part1.setRotationPoint(0.0F, 0.0F, 0.0F);
        group.addChild(part1);
        part1.setTextureOffset(0, 10).addBox(-3.0F, -3.0F, -1.0F, 5.0F, 1.0F, 3.0F, 0.0F, false);
        part1.setTextureOffset(15, 2).addBox(-2.0F, -3.0F, 2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        part1.setTextureOffset(15, 0).addBox(-2.0F, -3.0F, -2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        part2 = new ModelRenderer(this);
        part2.setRotationPoint(0.0F, 0.0F, 0.0F);
        group.addChild(part2);
        part2.setTextureOffset(0, 6).addBox(-3.0F, -1.0F, -1.0F, 5.0F, 1.0F, 3.0F, 0.0F, false);
        part2.setTextureOffset(8, 14).addBox(-2.0F, -1.0F, 2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);
        part2.setTextureOffset(0, 14).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 0.0F, 0.0F);
        bone.setTextureOffset(10, 16).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(13, 11).addBox(-3.0F, -5.0F, 0.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
    }
    @Override
    public void setRotationAngles(FragGrenadeEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        group.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        bone.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}
