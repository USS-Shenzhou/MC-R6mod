package cn.ussshenzhou.rainbow6.skin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

/**
 * @author USS_Shenzhou
 */
public class SkinIdHelper {
    protected static final HashMap<ResourceLocation, Integer> ITEM_ID = new HashMap<>();

    static {
        //putItemId(ModItemRegistry.SKIN_TEST, 0x0000);
    }

    private static void putItemId(RegistryObject<? extends Item> item, int id) {
        SkinIdHelper.ITEM_ID.put(item.getId(), id);
    }

    protected static final HashMap<String, Integer> SKIN_NAME_ID = new HashMap<>();

    static {
        //SkinIdHelper.SKIN_NAME_ID.put("testskin", 0x0000);
    }
}
