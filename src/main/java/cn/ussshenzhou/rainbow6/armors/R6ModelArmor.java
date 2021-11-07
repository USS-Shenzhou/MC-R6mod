package cn.ussshenzhou.rainbow6.armors;

import cn.ussshenzhou.rainbow6.armors.ModelArmor;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public abstract class R6ModelArmor extends ModelArmor {
    protected ModelRenderer helmetAnchor;

    protected ModelRenderer bodyAnchor;

    protected ModelRenderer armLAnchor;

    protected ModelRenderer armRAnchor;

    protected ModelRenderer pantsAnchor;

    protected ModelRenderer legL;

    protected ModelRenderer legR;

    protected ModelRenderer bootL;

    protected ModelRenderer bootR;

    /**
     * 1.0f = 1 pixel
     */
    protected float expandFactor = 0.05F;

    public R6ModelArmor(EquipmentSlotType slot) {
        super(slot);

        textureHeight = 16;
        textureWidth = 16;

        //helmet
        this.helmetAnchor = new ModelRenderer(this, 0, 0);
        this.helmetAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

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
        //legL.addBox(x-1,...)
        this.legL = new ModelRenderer(this, 0, 0);
        //this.legL.mirror = true;
        this.legL.setRotationPoint(1.9F, 12.0F, 0.0F);

        //legR
        //legR.addBox(x+1,...)
        this.legR = new ModelRenderer(this, 0, 0);
        this.legR.setRotationPoint(-1.9F, 12.0F, 0.0F);

        //boot left
        this.bootL = new ModelRenderer(this, 0, 0);
        //this.bootL.mirror = true;
        this.bootL.setRotationPoint(1.9F, 12.0F, 0.0F);

        //boot right
        this.bootR = new ModelRenderer(this, 0, 0);
        this.bootR.setRotationPoint(-1.9F, 12.0F, 0.0F);
    }


    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {

        helmetAnchor.showModel = slot == EquipmentSlotType.HEAD;

        bodyAnchor.showModel = slot == EquipmentSlotType.CHEST;
        armLAnchor.showModel = slot == EquipmentSlotType.CHEST;
        armRAnchor.showModel = slot == EquipmentSlotType.CHEST;

        legR.showModel = slot == EquipmentSlotType.LEGS;
        legL.showModel = slot == EquipmentSlotType.LEGS;

        bootL.showModel = slot == EquipmentSlotType.FEET;
        bootR.showModel = slot == EquipmentSlotType.FEET;

        bipedHeadwear.showModel = false;

        bipedHead = helmetAnchor;
        bipedBody = bodyAnchor;
        bipedRightArm = armRAnchor;
        bipedLeftArm = armLAnchor;
        if (slot == EquipmentSlotType.LEGS) {
            bipedBody = pantsAnchor;
            bipedRightLeg = legR;
            bipedLeftLeg = legL;
        } else {
            bipedRightLeg = bootR;
            bipedLeftLeg = bootL;
        }

        super.render(ms, buffer, light, overlay, r, g, b, a);
    }
}
