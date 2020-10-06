package com.ussshenzhou.rainbow6.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

/**
 * @author USS_Shenzhou
 */
public class FragGrenadeEntityRenderer extends EntityRenderer<FragGrenadeEntity> {
    public FragGrenadeEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("rainbow6:textures/item/fraggrenade.png");
    private static final FragGrenadeModel FRAG_GRENADE_MODEL = new FragGrenadeModel();
    private float Y = (float) Math.random()*360;
    private float Z = (float) Math.random()*360;

    @Override
    public void render(FragGrenadeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) + Y));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) + Z));
        matrixStackIn.scale(0.5f,0.5f,0.5f);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(TEXTURE_LOCATION));
        FRAG_GRENADE_MODEL.render(matrixStackIn,ivertexbuilder,packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
    @Override
    public ResourceLocation getEntityTexture(FragGrenadeEntity entity) {
        return TEXTURE_LOCATION;
    }
}
