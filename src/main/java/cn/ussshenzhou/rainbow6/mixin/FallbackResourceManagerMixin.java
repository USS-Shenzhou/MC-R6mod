package cn.ussshenzhou.rainbow6.mixin;

import cn.ussshenzhou.rainbow6.skin.SkinManager;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author USS_Shenzhou
 */
@Mixin(FallbackResourceManager.class)
public class FallbackResourceManagerMixin {

    @Shadow
    @Final
    public List<FallbackResourceManager.PackEntry> fallbacks;

    @Inject(method = "listResources", at = @At("RETURN"))
    private void r6msFakeSkinnedItems(String pPath, Predicate<ResourceLocation> pFilter, CallbackInfoReturnable<Map<ResourceLocation, Resource>> cir) {
        if (!"models".equals(pPath)) {
            return;
        }
        Map<ResourceLocation, Resource> map = cir.getReturnValue();
        PackResources resources = fallbacks.get(0).resources();
        SkinManager.SKINS.values().forEach(skin -> {
            ResourceLocation jsonLocation = skin.getModelJsonLocation();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(skin.getFakeJsonString().getBytes());
            map.put(jsonLocation, new Resource(resources, () -> inputStream));
        });
    }
}
