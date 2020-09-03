package com.ussshenzhou.rainbow6.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ussshenzhou.rainbow6.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class InventoryModelReplace {
    @SubscribeEvent
    public static void onModelLoad(FMLClientSetupEvent event){
        ModelLoader.addSpecialModel(new ModelResourceLocation(ModItems.reinforcement.getRegistryName()+"_hand","inventory"));
    }
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event){
        Map<ResourceLocation, IBakedModel> modelMap = event.getModelRegistry();
        ResourceLocation reinforcement = ModItems.reinforcement.getRegistryName();
        ResourceLocation reinforcementHand = new ModelResourceLocation(reinforcement+"_hand","inventory");
        ResourceLocation reinforcementInventory = new ModelResourceLocation(reinforcement,"inventory");
        IBakedModel reinforcementHandModel = modelMap.get(reinforcementHand);
        IBakedModel reinforcementDefaultModel = modelMap.get(reinforcementInventory);

        IBakedModel reinforcementModelWrapper = new IBakedModel() {
            @Override
            public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
                return reinforcementDefaultModel.getQuads(state, side, rand);
            }

            @Override
            public boolean isAmbientOcclusion() {
                return reinforcementDefaultModel.isAmbientOcclusion();
            }

            @Override
            public boolean isGui3d() {
                return reinforcementDefaultModel.isGui3d();
            }

            @Override
            public boolean func_230044_c_() {
                return reinforcementDefaultModel.func_230044_c_();
            }

            @Override
            public boolean isBuiltInRenderer() {
                return reinforcementDefaultModel.isBuiltInRenderer();
            }

            @Override
            public TextureAtlasSprite getParticleTexture() {
                return reinforcementDefaultModel.getParticleTexture();
            }

            @Override
            public ItemOverrideList getOverrides() {
                return reinforcementDefaultModel.getOverrides();
            }

            @Override
            public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
                IBakedModel modelToUse = reinforcementDefaultModel;
                if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND||cameraTransformType== ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND||cameraTransformType== ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND||cameraTransformType== ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND){
                    modelToUse = reinforcementHandModel;
                }
                return ForgeHooksClient.handlePerspective(modelToUse,cameraTransformType,mat);
            }
        };
        modelMap.put(reinforcementInventory,reinforcementModelWrapper);
    }
}
