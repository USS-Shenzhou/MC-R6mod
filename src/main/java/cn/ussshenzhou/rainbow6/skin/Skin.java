package cn.ussshenzhou.rainbow6.skin;

import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class Skin {
    /**
     * r6ms:test_item, r6ms:.
     */
    public final ResourceLocation item;
    /**
     * test_skin
     */
    public final String skinName;
    /**
     * 0x0000 0000 item id - skin id
     * <br/>Item-id 00 stands for universal skins.
     * <br/>Skins with the same name but for different items use the same skin-id.
     */
    public final int id;

    private Skin(ResourceLocation item, String skinName, int id) {
        this.item = item;
        this.skinName = skinName;
        this.id = id;
    }

    public Skin(ResourceLocation item, String skinName) {
        this(item, skinName, SkinManager.getSkinId(item, skinName));
    }

    public Skin(String skinName) {
        this(SkinManager.UNIVERSAL_SKIN, skinName, SkinManager.getSkinId(SkinManager.UNIVERSAL_SKIN, skinName));
    }

    public String getFullName() {
        return item.getPath() + "_" + skinName;
    }

    public ResourceLocation getModelLocation() {
        return new ResourceLocation(R6Constants.MOD_ID, "item/" + getFullName());
    }

    public ResourceLocation getModelJsonLocation() {
        return new ResourceLocation(R6Constants.MOD_ID, "models/item/" + getFullName() + ".json");
    }

    public String getFakeJsonString() {
        return String.format("""
                        {
                            "parent": "%s",
                            "textures": {
                                "skin": "r6ms:item/skin/%s"
                            }
                        }
                        """,
                "r6ms:item/" + item.getPath(),
                getFullName()
        );
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Skin) {
            return id == ((Skin) obj).id;
        } else {
            return false;
        }
    }
}
