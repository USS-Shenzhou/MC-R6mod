package cn.ussshenzhou.rainbow6.armors.mira;

import cn.ussshenzhou.rainbow6.armors.R6ArmorModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * @author USS_Shenzhou
 */
public class MiraArmorModel extends R6ArmorModel {
    public MiraArmorModel(EquipmentSlotType slot) {
        super(slot);

        textureHeight = 128;
        textureWidth = 128;

        //helmet
        this.helmetAnchor = new ModelRenderer(this, 0, 0);
        this.helmetAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        ModelRenderer helmet1 = new ModelRenderer(this, 0, 26);
        helmet1.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, STD_ARMOR_FACTOR);
        this.helmetAnchor.addChild(helmet1);

        ModelRenderer helmet2 = new ModelRenderer(this, 0, 0);
        helmet2.addBox(-4.5F, -8.5F, -4.5F, 9.0F, 4.0F, 9.0F, STD_ARMOR_FACTOR);
        this.helmetAnchor.addChild(helmet2);

        ModelRenderer helmet3 = new ModelRenderer(this, 26, 38);
        helmet3.addBox(-4.5F, -4.5F, -1.5F, 9.0F, 3.0F, 6.0F, STD_ARMOR_FACTOR);
        this.helmetAnchor.addChild(helmet3);

        ModelRenderer helmet4 = new ModelRenderer(this, 0, 13);
        helmet4.addBox(-5.0F, -7.0F, -8.0F, 10.0F, 5.0F, 8.0F, STD_ARMOR_FACTOR);
        helmet4.rotateAngleX = -0.3927f;
        this.helmetAnchor.addChild(helmet4);


        //body
        this.bodyAnchor = new ModelRenderer(this, 0, 0);
        this.bodyAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        //armL
        this.armLAnchor = new ModelRenderer(this, 0, 0);
        //this.armLAnchor.mirror = true;
        this.armLAnchor.setRotationPoint(4.0F, 2.0F, 0.0F);

        //armR
        this.armRAnchor = new ModelRenderer(this, 0, 0);
        //this.armRAnchor.mirror = true;
        this.armRAnchor.setRotationPoint(-4.0F, 2.0F, 0.0F);

        //pants
        this.pantsAnchor = new ModelRenderer(this, 0, 0);
        this.pantsAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        //legL
        this.legLAnchor = new ModelRenderer(this, 0, 0);
        //this.legL.mirror = true;
        this.legLAnchor.setRotationPoint(1.9F, 12.0F, 0.0F);

        //legR
        this.legRAnchor = new ModelRenderer(this, 0, 0);
        this.legRAnchor.setRotationPoint(-1.9F, 12.0F, 0.0F);

        //boot left
        this.bootLAnchor = new ModelRenderer(this, 0, 0);
        //this.bootL.mirror = true;
        this.bootLAnchor.setRotationPoint(1.9F, 12.0F, 0.0F);

        //boot right
        this.bootRAnchor = new ModelRenderer(this, 0, 0);
        this.bootRAnchor.setRotationPoint(-1.9F, 12.0F, 0.0F);
    }

    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {
        super.render(ms, buffer, light, overlay, r, g, b, a);
    }


}
