package com.ussshenzhou.rainbow6.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;

/**
 * @author USS_Shenzhou
 */
public class BulletHoleParticleFactory implements IParticleFactory<BulletHoleParticleData> {
    //private final IAnimatedSprite sprite;

    public BulletHoleParticleFactory(IAnimatedSprite sprite){
        //this.sprite = sprite;
    }

    @Override
    public Particle makeParticle(BulletHoleParticleData typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        BulletHoleParticle particle = new BulletHoleParticle(worldIn,x,y,z,typeIn.getDirection(),typeIn.getPos());
        //particle.selectSpriteRandomly(sprite);
        return particle;
    }
}
