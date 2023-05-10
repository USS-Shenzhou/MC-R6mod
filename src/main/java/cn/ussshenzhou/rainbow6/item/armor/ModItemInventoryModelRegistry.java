package cn.ussshenzhou.rainbow6.item.armor;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModItemInventoryModelRegistry {

    public static final LinkedList<RegistryObject<?>> HAVE_SPECIAL_HAND_MODEL = new LinkedList<>();

    @SubscribeEvent
    public static void regHandModel(ModelEvent.RegisterAdditional event) {
        HAVE_SPECIAL_HAND_MODEL.forEach(o -> event.register(new ModelResourceLocation(R6Constants.MOD_ID, o.getId().getPath() + "_hand", "inventory")));
    }

    @SubscribeEvent
    public static void replaceOnBake(ModelEvent.ModifyBakingResult event) {
        var models = event.getModels();
        HAVE_SPECIAL_HAND_MODEL.forEach(o -> changeInventoryModel(models, o.getId().getPath()));
    }

    private static void changeInventoryModel(Map<ResourceLocation, BakedModel> modelMap, String itemName) {

        ResourceLocation resourceLocationHand = new ModelResourceLocation(R6Constants.MOD_ID, itemName + "_hand", "inventory");
        BakedModel handModel = modelMap.get(resourceLocationHand);

        ResourceLocation resourceLocationInventory = new ModelResourceLocation(R6Constants.MOD_ID, itemName, "inventory");
        BakedModel defaultModel = modelMap.get(resourceLocationInventory);

        BakedModel modelWrapper = new BakedModel() {
            @Override
            public List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, RandomSource pRandom) {
                return defaultModel.getQuads(pState, pDirection, pRandom);
            }

            @Override
            public boolean useAmbientOcclusion() {
                return defaultModel.useAmbientOcclusion();
            }

            @Override
            public boolean isGui3d() {
                return defaultModel.isGui3d();
            }

            @Override
            public boolean usesBlockLight() {
                return defaultModel.usesBlockLight();
            }

            @Override
            public boolean isCustomRenderer() {
                return defaultModel.isCustomRenderer();
            }

            @Override
            public TextureAtlasSprite getParticleIcon() {
                return defaultModel.getParticleIcon();
            }

            @Override
            public ItemOverrides getOverrides() {
                return defaultModel.getOverrides();
            }

            @Override
            public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
                BakedModel modelToUse = defaultModel;
                if (transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND || transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || transformType == ItemDisplayContext.GROUND) {
                    modelToUse = handModel;
                }
                return modelToUse.applyTransform(transformType, poseStack, applyLeftHandTransform);
            }
        };

        modelMap.put(resourceLocationInventory, modelWrapper);
    }
}
