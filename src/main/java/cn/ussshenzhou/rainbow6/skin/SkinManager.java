package cn.ussshenzhou.rainbow6.skin;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkinManager {
    public static final ResourceLocation UNIVERSAL_SKIN = new ResourceLocation(R6Constants.MOD_ID, ".");
    protected static final HashMap<ResourceLocation, List<Skin>> ITEM_SKINS = new HashMap<>();
    public static final HashMap<Integer, Skin> SKINS = new HashMap<>();

    /**
     * REMEMBER TO ADD NEW ITEMS AND SKINS IN {@linkplain SkinIdHelper}.
     */
    @SubscribeEvent
    public static void initSkins(RegisterEvent event) {
        if (!event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
            return;
        }
        //addSkin(ModItemRegistry.SKIN_TEST, "testskin");
    }

    public static void addSkin(RegistryObject<? extends Item> item, String... skinNames) {
        ResourceLocation itemName = item.getId();
        Arrays.stream(skinNames).forEach(s -> addSkin(itemName, s));
    }

    @SafeVarargs
    public static void addSkin(String skinName, RegistryObject<? extends Item>... items) {
        Arrays.stream(items).forEach(i -> addSkin(i.getId(), skinName));
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

    public static Skin fromId(int id) {
        return SKINS.get(id);
    }

    public static int getSkinId(ResourceLocation item, String skinName) {
        int skinNameId;
        try {
            skinNameId = SkinIdHelper.SKIN_NAME_ID.get(skinName);
        } catch (NullPointerException ignored) {
            LogUtils.getLogger().error("Cannot find id for skin {}. If this is a development environment, assign it. This should not happen in product environments.",
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
            LogUtils.getLogger().error("Cannot find id for item {}. If this is a development environment, assign it. This should not happen in product environments.",
                    skinName);
            if (R6Constants.TEST) {
                throw new IllegalStateException();
            }
            itemId = 0xffff;
        }
        return (itemId << 4) + skinNameId;
    }

    public static @Nullable Skin getSkin(ResourceLocation item, String name) {
        return ITEM_SKINS.get(item).stream().filter(skin -> skin.skinName.equals(name)).findFirst().orElse(null);
    }
}
