package cn.ussshenzhou.rainbow6.armors.ash;

import cn.ussshenzhou.rainbow6.armors.ModelArmor;
import cn.ussshenzhou.rainbow6.armors.R6ModelArmor;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

/**
 * @author USS_Shenzhou
 */
public class AshArmorModel extends R6ModelArmor {
    private final ModelRenderer helmAnchor;

    private final ModelRenderer bodyAnchor;

    private final ModelRenderer armLAnchor;

    private final ModelRenderer armRAnchor;

    private final ModelRenderer pantsAnchor;

    private final ModelRenderer legL;

    private final ModelRenderer legR;

    private final ModelRenderer bootL;

    private final ModelRenderer bootR;


    public AshArmorModel(EquipmentSlotType slot) {
        super(slot);

        textureHeight = 16;
        textureWidth = 16;
        float s = 0F;

        //helmet
        this.helmAnchor = new ModelRenderer(this, 0, 0);
        this.helmAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.helmAnchor.addBox(-7.0F, -1.0F, 0.0F, 7.0F, 1.0F, 1.0F, s);

        //body
        this.bodyAnchor = new ModelRenderer(this, 0, 0);
        this.bodyAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        //armL
        this.armLAnchor = new ModelRenderer(this, 0, 0);
        this.armLAnchor.mirror = true;
        this.armLAnchor.setRotationPoint(4.0F, 2.0F, 0.0F);

        //armR
        this.armRAnchor = new ModelRenderer(this, 0, 0);
        this.armRAnchor.mirror = true;
        this.armRAnchor.setRotationPoint(-4.0F, 2.0F, 0.0F);

        //pants
        this.pantsAnchor = new ModelRenderer(this, 0, 0);
        this.pantsAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.legL = new ModelRenderer(this, 0, 76);
        this.legL.mirror = true;
        this.legL.setRotationPoint(1.9F, 12.0F, 0.0F);

        this.legR = new ModelRenderer(this, 0, 76);
        this.legR.setRotationPoint(-1.9F, 12.0F, 0.0F);


        //boot left
        this.bootL = new ModelRenderer(this, 0, 94);
        this.bootL.mirror = true;
        this.bootL.setRotationPoint(1.9F, 12.0F, 0.0F);

        //boot right
        this.bootR = new ModelRenderer(this, 0, 94);
        this.bootR.setRotationPoint(-1.9F, 12.0F, 0.0F);
    }
}
