package cn.ussshenzhou.rainbow6.armors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public abstract class R6ArmorModel extends ModelArmor {
    protected ModelRenderer helmetAnchor;

    protected ModelRenderer bodyAnchor;

    protected ModelRenderer armLAnchor;

    protected ModelRenderer armRAnchor;

    protected ModelRenderer pantsAnchor;

    protected ModelRenderer legLAnchor;

    protected ModelRenderer legRAnchor;

    protected ModelRenderer bootLAnchor;

    protected ModelRenderer bootRAnchor;

    /**
     * 1.0f = 1 pixel
     */
    public static final float STD_ARMOR_FACTOR = 0.1F;

    public R6ArmorModel(EquipmentSlotType slot) {
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
        //addBox(x-1,...)
        this.armLAnchor = new ModelRenderer(this, 0, 0);
        this.armLAnchor.setRotationPoint(4.0F, 2.0F, 0.0F);

        //armR
        //.addBox(x+1,...)
        this.armRAnchor = new ModelRenderer(this, 0, 0);
        this.armRAnchor.setRotationPoint(-4.0F, 2.0F, 0.0F);

        //pants
        this.pantsAnchor = new ModelRenderer(this, 0, 0);
        this.pantsAnchor.setRotationPoint(0.0F, 0.0F, 0.0F);

        //legL
        this.legLAnchor = new ModelRenderer(this, 0, 0);
        this.legLAnchor.setRotationPoint(1.9F, 12.0F, 0.0F);

        //legR
        this.legRAnchor = new ModelRenderer(this, 0, 0);
        this.legRAnchor.setRotationPoint(-1.9F, 12.0F, 0.0F);

        //bootL
        this.bootLAnchor = new ModelRenderer(this, 0, 0);
        this.bootLAnchor.setRotationPoint(1.9F, 12.0F, 0.0F);

        //bootR
        this.bootRAnchor = new ModelRenderer(this, 0, 0);
        this.bootRAnchor.setRotationPoint(-1.9F, 12.0F, 0.0F);
    }


    @Override
    public void render(MatrixStack ms, IVertexBuilder buffer, int light, int overlay, float r, float g, float b, float a) {

        helmetAnchor.showModel = slot == EquipmentSlotType.HEAD;

        bodyAnchor.showModel = slot == EquipmentSlotType.CHEST;
        armLAnchor.showModel = slot == EquipmentSlotType.CHEST;
        armRAnchor.showModel = slot == EquipmentSlotType.CHEST;

        legRAnchor.showModel = slot == EquipmentSlotType.LEGS;
        legLAnchor.showModel = slot == EquipmentSlotType.LEGS;

        bootLAnchor.showModel = slot == EquipmentSlotType.FEET;
        bootRAnchor.showModel = slot == EquipmentSlotType.FEET;

        bipedHeadwear.showModel = false;

        bipedHead = helmetAnchor;
        bipedBody = bodyAnchor;
        bipedRightArm = armRAnchor;
        bipedLeftArm = armLAnchor;
        if (slot == EquipmentSlotType.LEGS) {
            bipedBody = pantsAnchor;
            bipedRightLeg = legRAnchor;
            bipedLeftLeg = legLAnchor;
        } else {
            bipedRightLeg = bootRAnchor;
            bipedLeftLeg = bootLAnchor;
        }

        super.render(ms, buffer, light, overlay, r, g, b, a);
    }
}
