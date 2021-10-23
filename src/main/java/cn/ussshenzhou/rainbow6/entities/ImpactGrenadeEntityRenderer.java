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

import javax.annotation.Nullable;
/**
 * @author USS_Shenzhou
 */
public class ImpactGrenadeEntityRenderer extends EntityRenderer<ImpactGrenadeEntity> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("rainbow6:textures/entity/impactgrenade.png");
    private static final ImpactGrenadeModel IMPACT_GRENADE_MODEL = new ImpactGrenadeModel();

    public ImpactGrenadeEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }
    @Override
    public void render(ImpactGrenadeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) ));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) ));
        matrixStackIn.scale(0.6f,0.6f,0.6f);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(TEXTURE_LOCATION));
        IMPACT_GRENADE_MODEL.render(matrixStackIn,ivertexbuilder,packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(ImpactGrenadeEntity entity) {
        return TEXTURE_LOCATION;
    }

}
