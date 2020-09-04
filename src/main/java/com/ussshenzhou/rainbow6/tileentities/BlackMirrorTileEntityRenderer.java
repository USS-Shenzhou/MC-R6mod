package com.ussshenzhou.rainbow6.tileentities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.ussshenzhou.rainbow6.blocks.BlackMirror;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;

public class BlackMirrorTileEntityRenderer extends TileEntityRenderer<BlackMirrorTileEntity> {
    public static final ResourceLocation BLACK_MIRROR_GLASS_TEXTURE_LOCATION = new ResourceLocation("rainbow6:block/blackmirror_glass");
    public Boolean left = true;
    public Boolean broken = false;
    public BlackMirrorTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v){
        renderer.pos(stack.getLast().getMatrix(),x,y,z)
                .color(1.0f,1.0f,1.0f,1.0f)
                .tex(u,v)
                .lightmap(0,240)
                .normal(1,0,0)
                .endVertex();
    }
    @Override
    public void render(BlackMirrorTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(BLACK_MIRROR_GLASS_TEXTURE_LOCATION);
        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getTranslucent());
        left = tileEntityIn.getBlockState().get(BlackMirror.LEFT);
        broken = tileEntityIn.getBlockState().get(BlackMirror.BROKEN);
        float MinU = sprite.getMinU();
        float MinV = sprite.getMinV();
        float MaxU = sprite.getMaxU();
        float MaxV = sprite.getMaxV();
        if (!broken){
            if (left) {
                matrixStackIn.push();
                float f = tileEntityIn.getBlockState().get(BlockStateProperties.FACING).getHorizontalAngle();
                matrixStackIn.translate(0.5D, 0.5D, 0.5D);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
                matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
                add(builder, matrixStackIn, 1, 0.0625f, 0.375f, (MinU + MaxU) * 0.5f, (MinV + MaxV) * 0.5f);
                add(builder, matrixStackIn, 0.125f, 0.0625f, 0.375f, MaxU, (MinV + MaxV) * 0.5f);
                add(builder, matrixStackIn, 0.125f, 0.9375f, 0.375f, MaxU, MinV);
                add(builder, matrixStackIn, 1, 0.9375f, 0.375f, (MinU + MaxU) * 0.5f, MinV);

                add(builder, matrixStackIn, 1, 0.9375f, 0.625f, (MinU + MaxU) * 0.5f, (MinV + MaxV) * 0.5f);
                add(builder, matrixStackIn, 0.125f, 0.9375f, 0.625f, MinU, (MinV + MaxV) * 0.5f);
                add(builder, matrixStackIn, 0.125f, 0.0625f, 0.625f, MinU, MaxV);

                add(builder, matrixStackIn, 1, 0.0625f, 0.625f, (MinU + MaxU) * 0.5f, MaxV);
                matrixStackIn.pop();
            }
            else{
                matrixStackIn.push();
                float f = tileEntityIn.getBlockState().get(BlockStateProperties.FACING).getHorizontalAngle();
                matrixStackIn.translate(0.5D, 0.5D, 0.5D);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
                matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
                add(builder,matrixStackIn,0,0.9375f,0.375f,(MinU+MaxU)*0.5f,MinV);
                add(builder,matrixStackIn,0.875f,0.9375f,0.375f,MinU,MinV);
                add(builder,matrixStackIn,0.875f,0.0625f,0.375f,MinU,(MinV+MaxV)*0.5f);
                add(builder,matrixStackIn,0,0.0625f,0.375f,(MinU+MaxU)*0.5f,(MinV+MaxV)*0.5f);

                add(builder,matrixStackIn,0,0.0625f,0.625f,(MinU+MaxU)*0.5f,MaxV);
                add(builder,matrixStackIn,0.875f,0.0625f,0.625f,MaxU,MaxV);
                add(builder,matrixStackIn,0.875f,0.9375f,0.625f,MaxU,(MinV+MaxV)*0.5f);
                add(builder,matrixStackIn,0,0.9375f,0.625f,(MinU+MaxU)*0.5f,(MinV+MaxV)*0.5f);
                matrixStackIn.pop();
            }
        }
        else{
            matrixStackIn.push();
            matrixStackIn.pop();
        }
    }
}
