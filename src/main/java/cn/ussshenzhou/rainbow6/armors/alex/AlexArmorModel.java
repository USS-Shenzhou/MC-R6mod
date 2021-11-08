package cn.ussshenzhou.rainbow6.armors.alex;

import cn.ussshenzhou.rainbow6.armors.R6ArmorModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * @author USS_Shenzhou
 */
public class AlexArmorModel extends R6ArmorModel {
    public AlexArmorModel(EquipmentSlotType slot) {
        super(slot);

        textureHeight = 64;
        textureWidth = 64;

        //helmet
        this.helmetAnchor = new ModelRenderer(this, 0, 0);
        this.helmetAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        ModelRenderer helmetBody = new ModelRenderer(this, 0, 0);
        helmetBody.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F);
        this.helmetAnchor.addChild(helmetBody);

        ModelRenderer helmetArmor = new ModelRenderer(this, 0, 16);
        helmetArmor.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 7.0F, 8.0F, STD_ARMOR_FACTOR);
        this.helmetAnchor.addChild(helmetArmor);

        //body
        this.bodyAnchor = new ModelRenderer(this, 0, 0);
        this.bodyAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        ModelRenderer bodyBody = new ModelRenderer(this, 28, 27);
        bodyBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F);
        this.bodyAnchor.addChild(bodyBody);

        ModelRenderer bodyArmor1 = new ModelRenderer(this, 0, 31);
        bodyArmor1.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 4.0F, STD_ARMOR_FACTOR);
        this.bodyAnchor.addChild(bodyArmor1);

        ModelRenderer bodyArmor2 = new ModelRenderer(this, 48, 22);
        bodyArmor2.addBox(-3.0F, 1.0F, 2.0F, 6.0F, 5.0F, 1.0F, STD_ARMOR_FACTOR);
        this.bodyAnchor.addChild(bodyArmor2);

        ModelRenderer bodyArmor3 = new ModelRenderer(this, 0, 4);
        bodyArmor3.addBox(1.0F, 4.0F, -3.0F, 2.0F, 3.0F, 1.0F, STD_ARMOR_FACTOR);
        this.bodyAnchor.addChild(bodyArmor3);

        ModelRenderer bodyArmor4 = new ModelRenderer(this, 0, 0);
        bodyArmor4.addBox(-3.0F, 4.0F, -3.0F, 2.0F, 3.0F, 1.0F, STD_ARMOR_FACTOR);
        this.bodyAnchor.addChild(bodyArmor4);

        //armL
        //.addBox(x-1,...)
        this.armLAnchor = new ModelRenderer(this, 0, 0);
        this.armLAnchor.setRotationPoint(4.0F, 2.0F, 0.0F);

        ModelRenderer armLBody = new ModelRenderer(this, 0, 45);
        armLBody.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F);
        this.armLAnchor.addChild(armLBody);

        //armR
        //.addBox(x+1,...)
        this.armRAnchor = new ModelRenderer(this, 0, 0);
        this.armRAnchor.setRotationPoint(-4.0F, 2.0F, 0.0F);

        ModelRenderer armRBody = new ModelRenderer(this, 36, 43);
        armRBody.addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F);
        this.armRAnchor.addChild(armRBody);

        //pants
        this.pantsAnchor = new ModelRenderer(this, 0, 0);
        this.pantsAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        ModelRenderer pantsArmor = new ModelRenderer(this, 24, 0);
        pantsArmor.addBox(-4.0F, 8.0F, -2.0F, 8.0F, 4.0F, 4.0F, STD_ARMOR_FACTOR);
        this.pantsAnchor.addChild(pantsArmor);

        //legL
        this.legLAnchor = new ModelRenderer(this, 0, 0);
        this.legLAnchor.setRotationPoint(1.9F, 12.0F, 0.0F);

        ModelRenderer legLBody = new ModelRenderer(this, 20, 43);
        legLBody.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
        this.legLAnchor.addChild(legLBody);

        ModelRenderer legLArmor = new ModelRenderer(this, 48, 11);
        legLArmor.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, STD_ARMOR_FACTOR);
        this.legLAnchor.addChild(legLArmor);

        //legR
        this.legRAnchor = new ModelRenderer(this, 0, 0);
        this.legRAnchor.setRotationPoint(-1.9F, 12.0F, 0.0F);

        ModelRenderer legRBody = new ModelRenderer(this, 32, 8);
        legRBody.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
        this.legRAnchor.addChild(legRBody);

        ModelRenderer legRArmor1 = new ModelRenderer(this, 48, 0);
        legRArmor1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, STD_ARMOR_FACTOR);
        this.legRAnchor.addChild(legRArmor1);

        ModelRenderer legRArmor2 = new ModelRenderer(this, 0, 16);
        legRArmor2.addBox(-2.9F, 1.0F, 0.0F, 1.0F, 1.0F, 2.0F, STD_ARMOR_FACTOR);
        legRArmor2.rotateAngleX = -0.1745F;
        this.legRAnchor.addChild(legRArmor2);

        ModelRenderer legRArmor3 = new ModelRenderer(this, 0, 19);
        legRArmor3.addBox(-2.9F, 1.0F, -1.0F, 1.0F, 4.0F, 1.0F, STD_ARMOR_FACTOR);
        legRArmor3.rotateAngleX = -0.1745F;
        this.legRAnchor.addChild(legRArmor3);

        //bootL
        this.bootLAnchor = new ModelRenderer(this, 0, 0);
        this.bootLAnchor.setRotationPoint(1.9F, 12.0F, 0.0F);

        ModelRenderer bootLArmor = new ModelRenderer(this, 48, 39);
        bootLArmor.addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, STD_ARMOR_FACTOR);
        this.bootLAnchor.addChild(bootLArmor);

        //bootR
        this.bootRAnchor = new ModelRenderer(this, 0, 0);
        this.bootRAnchor.setRotationPoint(-1.9F, 12.0F, 0.0F);

        ModelRenderer bootRArmor = new ModelRenderer(this, 48, 56);
        bootRArmor.addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, STD_ARMOR_FACTOR);
        this.bootRAnchor.addChild(bootRArmor);
    }
}
