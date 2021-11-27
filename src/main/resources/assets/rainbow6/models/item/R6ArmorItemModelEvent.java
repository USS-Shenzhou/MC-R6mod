package cn.ussshenzhou.rainbow6.armors;

import cn.ussshenzhou.rainbow6.items.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class R6ArmorItemModelEvent {

    @SubscribeEvent
    public static void onModelLoad(ModelRegistryEvent event) {
        //ModelLoader.addSpecialModel(new ModelResourceLocation(ModItems.reinforcementItem.getRegistryName() + "_hand", "inventory"));
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        Map<ResourceLocation, IBakedModel> modelMap = event.getModelRegistry();

        //changeInventoryModel(modelMap,ModItems.reinforcementItem);

    }

    private static void changeInventoryModel(Map<ResourceLocation, IBakedModel> modelMap, Item item) {
        ResourceLocation resourceLocation = item.getRegistryName();

        ResourceLocation resourceLocationHand = new ModelResourceLocation(resourceLocation + "_hand", "inventory");
        IBakedModel handModel = modelMap.get(resourceLocationHand);

        ResourceLocation resourceLocationInventory = new ModelResourceLocation(resourceLocation, "inventory");
        IBakedModel defaultModel = modelMap.get(resourceLocationInventory);

        IBakedModel modelWrapper = new IBakedModel() {
            @Override
            public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
                return defaultModel.getQuads(state,side,rand);
            }

            @Override
            public boolean isAmbientOcclusion() {
                return defaultModel.isAmbientOcclusion();
            }

            @Override
            public boolean isGui3d() {
                return defaultModel.isGui3d();
            }

            @Override
            public boolean isSideLit() {
                return defaultModel.isSideLit();
            }

            @Override
            public boolean isBuiltInRenderer() {
                return defaultModel.isBuiltInRenderer();
            }

            @Override
            public TextureAtlasSprite getParticleTexture() {
                return defaultModel.getParticleTexture();
            }

            @Override
            public ItemOverrideList getOverrides() {
                return defaultModel.getOverrides();
            }

            @Override
            public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
                IBakedModel modelToUse = defaultModel;
                if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || cameraTransformType == ItemCameraTransforms.TransformType.GROUND){
                    modelToUse = handModel;
                }
                return ForgeHooksClient.handlePerspective(modelToUse, cameraTransformType, mat);
            }
        };

        modelMap.put(resourceLocationInventory,modelWrapper);
    }
}
