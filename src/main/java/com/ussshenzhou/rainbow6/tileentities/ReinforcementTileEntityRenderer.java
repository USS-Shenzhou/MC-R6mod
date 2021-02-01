package com.ussshenzhou.rainbow6.tileentities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.ussshenzhou.rainbow6.blocks.BlackMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

/**
 * @author USS_Shenzhou
 */
public class ReinforcementTileEntityRenderer extends TileEntityRenderer<ReinforcementTileEntity> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("rainbow6:block/reinforcement_extra");
    public ReinforcementTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(ReinforcementTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(TEXTURE_LOCATION);
        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getCutout());
        float MinU = sprite.getMinU();
        float MinV = sprite.getMinV();
        float MaxU = sprite.getMaxU();
        float MaxV = sprite.getMaxV();
        matrixStackIn.push();
        float f = tileEntityIn.getBlockState().get(BlockStateProperties.FACING).getHorizontalAngle();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
        matrixStackIn.translate(-0.5D, -0.5D, -0.5D);

        addFace(builder,matrixStackIn,0,0,-1.001f,1,1,-1.001f,MinU,MinV,MaxU,MaxV);

        matrixStackIn.pop();
    }

    private void add(IVertexBuilder builder, MatrixStack stack, float x, float y, float z, float u, float v){
        builder.pos(stack.getLast().getMatrix(),x,y,z)
                .color(1.0f,1.0f,1.0f,1.0f)
                .tex(u,v)
                .lightmap(0,240)
                .normal(1,0,0)
                .endVertex();
    }

    private void addFace(IVertexBuilder builder,MatrixStack stack,float minX,float minY,float minZ,float maxX,float maxY,float maxZ,float minU,float minV,float maxU,float maxV){
        add(builder,stack,minX,minY,minZ,maxU,maxV);
        add(builder,stack,minX,maxY,maxZ,maxU,minV);
        add(builder,stack,maxX,maxY,maxZ,minU,minV);
        add(builder,stack,maxX,minY,minZ,minU,maxV);

        add(builder,stack,maxX,minY,minZ,minU,maxV);
        add(builder,stack,maxX,maxY,maxZ,minU,minV);
        add(builder,stack,minX,maxY,maxZ,maxU,minV);
        add(builder,stack,minX,minY,minZ,maxU,maxV);
    }
}
