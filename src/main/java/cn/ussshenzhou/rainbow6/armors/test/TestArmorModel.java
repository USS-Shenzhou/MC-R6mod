package cn.ussshenzhou.rainbow6.armors.test;

import cn.ussshenzhou.rainbow6.armors.R6ArmorModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * @author USS_Shenzhou
 */
public class TestArmorModel extends R6ArmorModel {
    public TestArmorModel(EquipmentSlotType slot) {
        super(slot);

        textureHeight = 64;
        textureWidth = 64;

        //helmet
        this.helmetAnchor = new ModelRenderer(this, 0, 0);
        this.helmetAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        //body
        this.bodyAnchor = new ModelRenderer(this, 0, 16);
        this.bodyAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        //armL
        this.armLAnchor = new ModelRenderer(this, 16, 32);
        //this.armLAnchor.mirror = true;
        this.armLAnchor.setRotationPoint(4.0F, 2.0F, 0.0F);

        //armR
        this.armRAnchor = new ModelRenderer(this, 32, 0);
        //this.armRAnchor.mirror = true;
        this.armRAnchor.setRotationPoint(-4.0F, 2.0F, 0.0F);

        //pants
        this.pantsAnchor = new ModelRenderer(this, 0, 0);
        this.pantsAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        //legL
        this.legLAnchor = new ModelRenderer(this, 0, 32);
        //this.legL.mirror = true;
        this.legLAnchor.setRotationPoint(1.9F, 12.0F, 0.0F);

        //legR
        this.legRAnchor = new ModelRenderer(this, 24, 16);
        this.legRAnchor.setRotationPoint(-1.9F, 12.0F, 0.0F);

        //boot left
        this.bootLAnchor = new ModelRenderer(this, 0, 0);
        //this.bootL.mirror = true;
        this.bootLAnchor.setRotationPoint(1.9F, 12.0F, 0.0F);

        //boot right
        this.bootRAnchor = new ModelRenderer(this, 0, 0);
        this.bootRAnchor.setRotationPoint(-1.9F, 12.0F, 0.0F);

        helmetAnchor.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, STD_ARMOR_FACTOR);

        bodyAnchor.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, STD_ARMOR_FACTOR);

        armLAnchor.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, STD_ARMOR_FACTOR);

        armRAnchor.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, STD_ARMOR_FACTOR);

        legLAnchor.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, STD_ARMOR_FACTOR);

        legRAnchor.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, STD_ARMOR_FACTOR);
    }
}
