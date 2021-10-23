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
public class NitroCellEntityRenderer extends EntityRenderer<NitroCellEntity> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("rainbow6:textures/entity/nitrocell.png");
    private static final NitroCellModel NITRO_CELL_MODEL = new NitroCellModel();

    public NitroCellEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }
    @Override
    public void render(NitroCellEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.scale(0.5f,0.5f,0.5f);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw)));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(TEXTURE_LOCATION));
        NITRO_CELL_MODEL.render(matrixStackIn,ivertexbuilder,packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(NitroCellEntity entity) {
        return TEXTURE_LOCATION;
    }

}
