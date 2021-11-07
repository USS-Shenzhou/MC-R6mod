package cn.ussshenzhou.rainbow6.armors.test;

import cn.ussshenzhou.rainbow6.armors.R6ModelArmor;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * @author USS_Shenzhou
 */
public class TestArmorModel extends R6ModelArmor {
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
        this.legL = new ModelRenderer(this, 0, 32);
        //this.legL.mirror = true;
        this.legL.setRotationPoint(1.9F, 12.0F, 0.0F);

        //legR
        this.legR = new ModelRenderer(this, 24, 16);
        this.legR.setRotationPoint(-1.9F, 12.0F, 0.0F);

        //boot left
        this.bootL = new ModelRenderer(this, 0, 0);
        //this.bootL.mirror = true;
        this.bootL.setRotationPoint(1.9F, 12.0F, 0.0F);

        //boot right
        this.bootR = new ModelRenderer(this, 0, 0);
        this.bootR.setRotationPoint(-1.9F, 12.0F, 0.0F);

        helmetAnchor.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, expandFactor);

        bodyAnchor.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, expandFactor);

        armLAnchor.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, expandFactor);

        armRAnchor.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, expandFactor);

        legL.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, expandFactor);

        legR.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, expandFactor);
    }
}
