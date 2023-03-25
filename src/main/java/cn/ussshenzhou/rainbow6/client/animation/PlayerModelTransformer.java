package cn.ussshenzhou.rainbow6.client.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

/**
 * This file is copied and modified from com.alrex.parcool.client.animation.Animator under GPLv3.
 *
 * @author USS_Shenzhou
 * Using Radians
 */
public class PlayerModelTransformer {
    private final Player player;
    private final PlayerModel model;
    private final boolean slim;
    private final float partial;
    private final float ageInTicks;
    private final float limbSwing;
    private final float limbSwingAmount;
    private final float netHeadYaw;
    private final float headPitch;

    public float getPartialTick() {
        return partial;
    }

    public float getHeadPitch() {
        return headPitch;
    }

    public float getNetHeadYaw() {
        return netHeadYaw;
    }

    public float getLimbSwing() {
        return limbSwing;
    }

    public float getLimbSwingAmount() {
        return limbSwingAmount;
    }

    public PlayerModel getRawModel() {
        return model;
    }

    public PlayerModelTransformer(
            Player player,
            PlayerModel model,
            boolean slim,
            float ageInTicks,
            float limbSwing,
            float limbSwingAmount,
            float netHeadYaw,
            float headPitch
    ) {
        this.player = player;
        this.model = model;
        this.slim = slim;
        this.partial = Minecraft.getInstance().getFrameTime();
        this.ageInTicks = ageInTicks;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
    }

    /**
     * @param angleX swing arm frontward or backward
     * @param angleY rotate arm around
     * @param angleZ swing arm upward or downward
     */
    public PlayerModelTransformer rotateRightArm(float angleX, float angleY, float angleZ) {
        ModelPart rightArm = model.rightArm;
        if (rightArm.visible) {
            setRotations(rightArm, angleX, angleY, angleZ);
        }
        return this;
    }

    public PlayerModelTransformer rotateRightArm(float angleX, float angleY, float angleZ, float factor) {
        ModelPart rightArm = model.rightArm;
        if (rightArm.visible) {
            setRotations(rightArm,
                    Mth.lerp(factor, rightArm.xRot, angleX),
                    Mth.lerp(factor, rightArm.yRot, angleY),
                    Mth.lerp(factor, rightArm.zRot, angleZ)
            );
        }
        return this;
    }

    /**
     * @param angleX swing arm frontward or backward
     * @param angleY rotate arm around
     * @param angleZ swing arm upward or downward
     */
    public PlayerModelTransformer rotateLeftArm(float angleX, float angleY, float angleZ) {
        ModelPart leftArm = model.leftArm;
        if (leftArm.visible) {
            setRotations(leftArm, angleX, angleY, angleZ);
        }
        return this;
    }

    public PlayerModelTransformer rotateLeftArm(float angleX, float angleY, float angleZ, float factor) {
        ModelPart leftArm = model.leftArm;
        if (leftArm.visible) {
            setRotations(leftArm,
                    Mth.lerp(factor, leftArm.xRot, angleX),
                    Mth.lerp(factor, leftArm.yRot, angleY),
                    Mth.lerp(factor, leftArm.zRot, angleZ)
            );
        }
        return this;
    }

    /**
     * @param angleX swing leg frontward or backward
     * @param angleY rotate leg around
     * @param angleZ swing leg upward or downward
     */
    public PlayerModelTransformer rotateRightLeg(float angleX, float angleY, float angleZ) {
        ModelPart rightLeg = model.rightLeg;
        if (rightLeg.visible) {
            setRotations(rightLeg, angleX, angleY, angleZ);
        }
        return this;
    }

    public PlayerModelTransformer rotateRightLeg(float angleX, float angleY, float angleZ, float factor) {
        ModelPart rightLeg = model.rightLeg;
        if (rightLeg.visible) {
            setRotations(rightLeg,
                    Mth.lerp(factor, rightLeg.xRot, angleX),
                    Mth.lerp(factor, rightLeg.yRot, angleY),
                    Mth.lerp(factor, rightLeg.zRot, angleZ)
            );
        }
        return this;
    }

    /**
     * @param angleX swing leg frontward or backward
     * @param angleY rotate leg around
     * @param angleZ swing leg upward or downward
     */
    public PlayerModelTransformer rotateLeftLeg(float angleX, float angleY, float angleZ) {
        ModelPart leftLeg = model.leftLeg;
        if (leftLeg.visible) {
            setRotations(leftLeg, angleX, angleY, angleZ);
        }
        return this;
    }

    public PlayerModelTransformer rotateLeftLeg(float angleX, float angleY, float angleZ, float factor) {
        ModelPart leftLeg = model.leftLeg;
        if (leftLeg.visible) {
            setRotations(leftLeg,
                    Mth.lerp(factor, leftLeg.xRot, angleX),
                    Mth.lerp(factor, leftLeg.yRot, angleY),
                    Mth.lerp(factor, leftLeg.zRot, angleZ)
            );
        }
        return this;
    }

    public PlayerModelTransformer addRotateRightArm(float angleX, float angleY, float angleZ) {
        ModelPart arm = model.rightArm;
        if (arm.visible) {
            setRotations(arm, arm.xRot + angleX, arm.yRot + angleY, arm.zRot + angleZ);
        }
        return this;
    }

    public PlayerModelTransformer addRotateLeftArm(float angleX, float angleY, float angleZ) {
        ModelPart arm = model.leftArm;
        if (arm.visible) {
            setRotations(arm, arm.xRot + angleX, arm.yRot + angleY, arm.zRot + angleZ);
        }
        return this;
    }

    public PlayerModelTransformer addRotateRightLeg(float angleX, float angleY, float angleZ) {
        ModelPart leg = model.rightLeg;
        if (leg.visible) {
            setRotations(leg, leg.xRot + angleX, leg.yRot + angleY, leg.zRot + angleZ);
        }
        return this;
    }

    public PlayerModelTransformer addRotateLeftLeg(float angleX, float angleY, float angleZ) {
        ModelPart leg = model.leftLeg;
        if (leg.visible) {
            setRotations(leg, leg.xRot + angleX, leg.yRot + angleY, leg.zRot + angleZ);
        }
        return this;
    }

    public PlayerModelTransformer makeArmsNatural() {
        AnimationUtils.bobArms(model.rightArm, model.leftArm, ageInTicks);
        return this;
    }

    public PlayerModelTransformer makeArmsMovingDynamically(float factor) {
        model.rightArm.zRot += Mth.cos(ageInTicks * 0.56F) * 0.8F * factor + 0.05F;
        model.leftArm.zRot -= Mth.cos(ageInTicks * 0.56F) * 0.8F * factor + 0.05F;
        model.rightArm.xRot += Mth.sin(ageInTicks * 0.56F) * 0.8F * factor;
        model.leftArm.xRot -= Mth.sin(ageInTicks * 0.56F) * 0.8F * factor;
        return this;
    }

    public PlayerModelTransformer makeLegsLittleMoving() {
        AnimationUtils.bobArms(model.rightLeg, model.leftLeg, ageInTicks);
        return this;
    }

    public PlayerModelTransformer makeLegsShakingDynamically(float factor) {
        model.rightLeg.zRot += Mth.cos(ageInTicks * 0.56F) * 0.8F * factor + 0.05F;
        model.leftLeg.zRot += Mth.cos(ageInTicks * 0.56F) * 0.8F * factor + 0.05F;
        model.rightLeg.xRot += Mth.sin(ageInTicks * 0.56F) * 0.2F * factor;
        model.leftLeg.xRot -= Mth.sin(ageInTicks * 0.56F) * 0.2F * factor;
        return this;
    }

    public PlayerModelTransformer rotateAdditionallyHeadPitch(float pitchDegree) {
        model.head.xRot = (float) Math.toRadians(pitchDegree + headPitch);
        return this;
    }

    public PlayerModelTransformer rotateHeadPitch(float pitchDegree) {
        model.head.xRot = (float) Math.toRadians(pitchDegree);
        return this;
    }

    public void end() {
    }

    public PlayerModelTransformer rotateAdditionallyHeadYaw(float yawDegree) {
        model.head.yRot = (float) Math.toRadians(yawDegree + netHeadYaw);
        return this;
    }

    public void copyFromBodyToWear() {
        model.rightSleeve.copyFrom(model.rightArm);
        model.leftSleeve.copyFrom(model.leftArm);
        model.rightPants.copyFrom(model.rightLeg);
        model.leftPants.copyFrom(model.leftLeg);
        model.jacket.copyFrom(model.body);
        model.hat.copyFrom(model.head);
    }

    private void setRotations(ModelPart renderer, float angleX, float angleY, float angleZ) {
        renderer.xRot = angleX;
        renderer.yRot = angleY;
        renderer.zRot = angleZ;
    }

    public void reset() {
        resetModel(model.head);
        resetModel(model.hat);
        resetModel(model.jacket);
        resetModel(model.body);
        {
            resetModel(model.rightArm);
            model.rightArm.x = 5.2F;
            model.rightArm.y = this.slim ? 2.5F : 2.0F;
            model.rightArm.z = 0.0F;
            model.rightSleeve.copyFrom(model.rightArm);
        }
        {
            resetModel(model.leftArm);
            model.leftArm.x = 2.5F;
            model.leftArm.y = this.slim ? 2.5F : 2.0F;
            model.leftArm.z = 0.0F;

            model.leftSleeve.copyFrom(model.leftArm);
        }
        {
            resetModel(model.leftLeg);
            model.leftLeg.x = 1.9F;
            model.leftLeg.y = 12.0F;
            model.leftLeg.z = 0.0F;

            model.leftPants.copyFrom(model.leftLeg);
        }
        {
            resetModel(model.rightLeg);
            model.rightLeg.x = -1.9F;
            model.rightLeg.y = 12.0F;
            model.rightLeg.z = 0.0F;

            model.rightPants.copyFrom(model.rightLeg);
        }
    }

    public void resetModel(ModelPart model) {
        model.xRot = 0;
        model.yRot = 0;
        model.zRot = 0;
        model.x = 0;
        model.y = 0;
        model.z = 0;
    }
}
