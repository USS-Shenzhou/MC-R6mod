package cn.ussshenzhou.rainbow6.entities;

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
public class SmokeGrenadeEntityRenderer extends EntityRenderer<SmokeGrenadeEntity> {
    protected SmokeGrenadeEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("rainbow6:textures/item/smokegrenade.png");
    private static final SmokeGrenadeModel SMOKE_GRENADE_MODEL = new SmokeGrenadeModel();

    @Override
    public void render(SmokeGrenadeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.scale(0.5f,0.6f,0.5f);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw)));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(TEXTURE_LOCATION));
        SMOKE_GRENADE_MODEL.render(matrixStackIn,ivertexbuilder,packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(SmokeGrenadeEntity entity) {
        return TEXTURE_LOCATION;
    }
}
