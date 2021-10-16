package com.ussshenzhou.rainbow6.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

/**
 * @author USS_Shenzhou
 */
public class SmokeGrenadeParticleFactory implements IParticleFactory<SmokeGrenadeParticleData> {
    private final IAnimatedSprite sprite;

    public SmokeGrenadeParticleFactory(IAnimatedSprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public Particle makeParticle(SmokeGrenadeParticleData typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        SmokeGrenadeParticle particle = new SmokeGrenadeParticle(worldIn ,x,y,z,xSpeed,ySpeed,zSpeed);
        particle.selectSpriteRandomly(sprite);
        return particle;
    }
}
