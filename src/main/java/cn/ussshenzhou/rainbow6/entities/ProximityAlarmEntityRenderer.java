package cn.ussshenzhou.rainbow6.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

/**
 * @author USS_Shenzhou
 */
public class ProximityAlarmEntityRenderer extends EntityRenderer<ProximityAlarmEntity> {
    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("rainbow6:textures/entity/proximityalarm.png");
    private static final ProximityAlarmEntityModel PROXIMITY_ALARM_ENTITY_MODEL = new ProximityAlarmEntityModel();

    public ProximityAlarmEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(ProximityAlarmEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.scale(0.6f,0.6f,0.6f);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(entityIn.Y));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(entityIn.Z));
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.X));
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutout(RESOURCE_LOCATION));
        PROXIMITY_ALARM_ENTITY_MODEL.render(matrixStackIn,ivertexbuilder,entityIn.light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(ProximityAlarmEntity entity) {
        return RESOURCE_LOCATION;
    }
}
