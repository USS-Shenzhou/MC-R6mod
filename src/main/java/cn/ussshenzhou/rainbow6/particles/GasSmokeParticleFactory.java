package cn.ussshenzhou.rainbow6.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

/**
 * @author USS_Shenzhou
 */
public class GasSmokeParticleFactory implements IParticleFactory<GasSmokeParticleData> {
    private final IAnimatedSprite sprite;

    public GasSmokeParticleFactory(IAnimatedSprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public Particle makeParticle(GasSmokeParticleData typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        GasSmokeParticle particle = new GasSmokeParticle(worldIn ,x,y,z,xSpeed,ySpeed,zSpeed);
        particle.selectSpriteRandomly(sprite);
        return particle;
    }
}
