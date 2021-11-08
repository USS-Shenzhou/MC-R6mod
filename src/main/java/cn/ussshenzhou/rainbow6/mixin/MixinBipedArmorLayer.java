package cn.ussshenzhou.rainbow6.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


/**
 * @author USS_Shenzhou
 */
@OnlyIn(Dist.CLIENT)
//@Mixin(BipedArmorLayer.class)
public abstract class MixinBipedArmorLayer <T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends LayerRenderer<T, M> {
    public MixinBipedArmorLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }/*



    @Inject(method = "Lnet/minecraft/client/renderer/entity/layers/BipedArmorLayer;renderModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLnet/minecraft/client/renderer/entity/model/BipedModel;FFFLnet/minecraft/util/ResourceLocation;)V",cancellable = true,at = @At("HEAD"),remap = false)
    private void r6renderModel(MatrixStack p_241738_1_, IRenderTypeBuffer p_241738_2_, int p_241738_3_, boolean p_241738_5_, A p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource, CallbackInfo ci) {
        //IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(p_241738_2_,RenderType.getEntityTranslucent(armorResource), false, p_241738_5_);
        //IVertexBuilder ivertexbuilder = ItemRenderer.getArmorVertexBuilder(p_241738_2_, RenderType.getArmorCutoutNoCull(armorResource), false, p_241738_5_);
        IVertexBuilder ivertexbuilder = p_241738_2_.getBuffer(RenderType.getTranslucent());
        //IVertexBuilder ivertexbuilder = p_241738_2_.getBuffer(getEntityTranslucentNoCull(armorResource));
        p_241738_6_.render(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
        LogManager.getLogger().info("!!!!!!!!!");
    }

    private static RenderType getEntityTranslucentNoCull(ResourceLocation LocationIn) {
        RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(LocationIn, false, false))
                .transparency(MixinRenderStateAccessor.getTRANSLUCENT_TRANSPARENCY())
                .diffuseLighting(MixinRenderStateAccessor.getDIFFUSE_LIGHTING_ENABLED())
                .alpha(MixinRenderStateAccessor.getDEFAULT_ALPHA())
                .cull(MixinRenderStateAccessor.getCULL_DISABLED())
                .lightmap(MixinRenderStateAccessor.getLIGHTMAP_ENABLED())
                .overlay(MixinRenderStateAccessor.getOVERLAY_ENABLED())
                .layer(MixinRenderStateAccessor.getVIEW_OFFSET_Z_LAYERING())
                .build(true);
        return RenderType.makeType("entity_translucent_no_null", DefaultVertexFormats.ENTITY, 7, 256, true, true, rendertype$state);
    }*/
}
