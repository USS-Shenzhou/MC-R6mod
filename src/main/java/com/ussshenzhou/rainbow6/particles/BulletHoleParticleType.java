package com.ussshenzhou.rainbow6.particles;

import com.mojang.serialization.Codec;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

/**
 * @author USS_Shenzhou
 */
public class BulletHoleParticleType extends ParticleType<BulletHoleParticleData> {
    public BulletHoleParticleType() {
        super(true,BulletHoleParticleData.DESERIALIZER);
    }

    @Override
    public Codec<BulletHoleParticleData> func_230522_e_() {
        return null;
    }
}
