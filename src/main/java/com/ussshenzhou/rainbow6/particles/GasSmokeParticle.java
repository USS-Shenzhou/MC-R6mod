package com.ussshenzhou.rainbow6.particles;

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

import java.util.Random;

/**
 * @author USS_Shenzhou
 */
public class GasSmokeParticle extends SpriteTexturedParticle {
    protected GasSmokeParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);

        maxAge=120;
        particleScale = (float) (Math.random() * 0.6 + 0.7);
        this.canCollide=true;
        this.setAlphaF((float) (Math.random() * 0.35 + 0.55));
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        //super.tick();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.motionY -= 0.04D * (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.943F;
            this.motionY *= (double)0.943F;
            this.motionZ *= (double)0.943F;
            if (this.onGround) {
                this.motionX *= (double)0.63F;
                this.motionZ *= (double)0.63F;
            }

        }
    }
}
