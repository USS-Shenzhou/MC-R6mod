package cn.ussshenzhou.rainbow6.skin;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * @author USS_Shenzhou
 */
public class SkinItemOverridesHandler extends ItemOverrides {

    @Nullable
    @Override
    public BakedModel resolve(BakedModel pModel, ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
        var modelManager = Minecraft.getInstance().getModelManager();
        String skinName = pStack.getOrCreateTag().getString("r6Skin");
        if ("".equals(skinName)) {
            return pModel;
        } else {
            var skin = SkinManager.getSkin(BuiltInRegistries.ITEM.getKey(pStack.getItem()), skinName);
            if (skin == null) {
                LogUtils.getLogger().error("Failed to find skin {} for item {}. This should not happen.", skinName, pStack.getItem());
                return pModel;
            }
            return modelManager.getModel(skin.getModelLocation());
        }
    }
}
