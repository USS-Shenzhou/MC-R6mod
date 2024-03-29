package cn.ussshenzhou.rainbow6.skin;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkinManager {
    public static final ResourceLocation UNIVERSAL_SKIN = new ResourceLocation(R6Constants.MOD_ID, ".") {
        @Override
        public String getPath() {
            return "universal";
        }
    };
    protected static final HashMap<ResourceLocation, List<Skin>> ITEM_SKINS = new HashMap<>();
    public static final HashMap<Integer, Skin> SKINS = new HashMap<>();

    /**
     * REMEMBER TO ADD NEW ITEMS AND SKINS IN {@linkplain SkinIdHelper}.
     */
    @SubscribeEvent
    public static void initSkins(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.ITEM)) {
            return;
        }
        //addSkin(ModItemRegistry.SKIN_TEST, "testskin");
    }

    public static void addSkin(Supplier<? extends Item> item, String... skinNames) {
        ResourceLocation itemName = BuiltInRegistries.ITEM.getKey(item.get());
        Arrays.stream(skinNames).forEach(s -> addSkin(itemName, s));
    }

    @SafeVarargs
    public static void addSkin(String skinName, Supplier<? extends Item>... items) {
        Arrays.stream(items).forEach(i -> addSkin(BuiltInRegistries.ITEM.getKey(i.get()), skinName));
    }

    public static void addSkin(ResourceLocation itemName, String skinName) {
        Skin newSkin = new Skin(itemName, skinName);
        SKINS.put(newSkin.id, newSkin);
        ITEM_SKINS.compute(itemName, ((item, skins) -> {
            if (skins == null) {
                return List.of(newSkin);
            } else {
                skins.add(newSkin);
                return skins;
            }
        }));
    }

    //needtest
    public static void addUniversalSkin(String... skinNames) {
        Arrays.stream(skinNames).forEach(s -> addSkin(UNIVERSAL_SKIN, s));
    }

    public static Skin fromId(int id) {
        return SKINS.get(id);
    }

    public static int getSkinId(ResourceLocation item, String skinName) {
        int skinNameId;
        try {
            skinNameId = SkinIdHelper.SKIN_NAME_ID.get(skinName);
        } catch (NullPointerException ignored) {
            LogUtils.getLogger().error("Cannot find id for skin {}. If this is a development environment, assign it. This should not happen.",
                    skinName);
            if (R6Constants.TEST) {
                throw new IllegalStateException();
            }
            skinNameId = 0xffff;
        }
        int itemId;
        try {
            itemId = SkinIdHelper.ITEM_ID.get(item);
        } catch (NullPointerException ignored) {
            LogUtils.getLogger().error("Cannot find id for item {}. If this is a development environment, assign it. This should not happen.",
                    skinName);
            if (R6Constants.TEST) {
                throw new IllegalStateException();
            }
            itemId = 0xffff;
        }
        return (itemId << 4) + skinNameId;
    }

    public static @Nullable Skin getSkin(ResourceLocation item, String name) {
        return ITEM_SKINS.get(item).stream().filter(skin -> skin.skinName.equals(name)).findFirst().orElse(
                ITEM_SKINS.get(UNIVERSAL_SKIN).stream().filter(skin -> skin.skinName.equals(name)).findFirst().orElse(null)
        );
    }
}