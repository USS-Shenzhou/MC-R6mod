package com.ussshenzhou.rainbow6.particles;

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

/**
 * @author USS_Shenzhou
 */
public class GasSmokeParticle extends SpriteTexturedParticle {
    protected GasSmokeParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        maxAge=120;
        particleScale = 1.0f;
        this.canCollide=false;
        this.setAlphaF(0.8f);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
