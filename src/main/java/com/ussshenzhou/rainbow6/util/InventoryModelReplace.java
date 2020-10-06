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
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class InventoryModelReplace {
    @SubscribeEvent
    public static void onModelLoad(ModelRegistryEvent event){
        ModelLoader.addSpecialModel(new ModelResourceLocation(ModItems.reinforcement.getRegistryName()+"_hand","inventory"));
        ModelLoader.addSpecialModel(new ModelResourceLocation(ModItems.blackMirrorItem.getRegistryName()+"_hand","inventory"));
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
            public boolean isSideLit() {
                return reinforcementDefaultModel.isSideLit();
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
                IBakedModel reinforcementModelToUse = reinforcementDefaultModel;
                if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND||cameraTransformType== ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND||cameraTransformType== ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND||cameraTransformType== ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND){
                    reinforcementModelToUse = reinforcementHandModel;
                }
                return ForgeHooksClient.handlePerspective(reinforcementModelToUse,cameraTransformType,mat);
            }


        };
        ResourceLocation blackMirrorItem = ModItems.blackMirrorItem.getRegistryName();
        ResourceLocation blackMirrorItemHand = new ModelResourceLocation(blackMirrorItem+"_hand","inventory");
        ResourceLocation blackMirrorItemInventory = new ModelResourceLocation(blackMirrorItem,"inventory");
        IBakedModel blackMirrorItemHandModel = modelMap.get(blackMirrorItemHand);
        IBakedModel blackMirrorItemDefaultModel = modelMap.get(blackMirrorItemInventory);

        IBakedModel blackMirrorItemModelWrapper = new IBakedModel() {
            @Override
            public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
                return blackMirrorItemDefaultModel.getQuads(state, side, rand);
            }

            @Override
            public boolean isAmbientOcclusion() {
                return blackMirrorItemDefaultModel.isAmbientOcclusion();
            }

            @Override
            public boolean isGui3d() {
                return blackMirrorItemDefaultModel.isGui3d();
            }

            @Override
            public boolean isSideLit() {
                return blackMirrorItemDefaultModel.isSideLit();
            }

            @Override
            public boolean isBuiltInRenderer() {
                return blackMirrorItemDefaultModel.isBuiltInRenderer();
            }

            @Override
            public TextureAtlasSprite getParticleTexture() {
                return blackMirrorItemDefaultModel.getParticleTexture();
            }

            @Override
            public ItemOverrideList getOverrides() {
                return blackMirrorItemDefaultModel.getOverrides();
            }

            @Override
            public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
                IBakedModel blackMirrorItemModelToUse = blackMirrorItemDefaultModel;
                if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND||cameraTransformType== ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND||cameraTransformType== ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND||cameraTransformType== ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND){
                    blackMirrorItemModelToUse = blackMirrorItemHandModel;
                }
                return ForgeHooksClient.handlePerspective(blackMirrorItemModelToUse,cameraTransformType,mat);
            }


        };
        modelMap.put(blackMirrorItemInventory,blackMirrorItemModelWrapper);
        modelMap.put(reinforcementInventory,reinforcementModelWrapper);
    }
}
