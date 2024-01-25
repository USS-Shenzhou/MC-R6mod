package cn.ussshenzhou.rainbow6.datagen;

import cn.ussshenzhou.rainbow6.item.armor.BaseR6ArmorItem;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * @author USS_Shenzhou
 */
public class ModItemModelProvider extends ItemModelProvider {
    public static final ResourceLocation GENERATED = new ResourceLocation("item/generated");

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, R6Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BuiltInRegistries.ITEM.stream().filter(e -> e instanceof BaseR6ArmorItem).forEach(e -> {
            //TODO .replace('/', '.') ?
            var name = BuiltInRegistries.ITEM.getKey(e).getPath();
            this.withExistingParent(name, GENERATED)
                    .texture("layer0", modLoc("item/armor/" + name.split("_")[0] + "_bg"))
                    .texture("layer1", modLoc("item/armor/" + name));
            this.withExistingParent(name + "_hand", GENERATED)
                    .texture("layer0", modLoc("item/armor/" + name));
        });
    }
}
