package com.ussshenzhou.rainbow6.particles;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

/**
 * @author USS_Shenzhou
 */
public class SmokeGrenadeParticleType extends ParticleType<SmokeGrenadeParticleData> {

        public SmokeGrenadeParticleType() {
        super(false,SmokeGrenadeParticleData.DESERIALIZER);
        }

        @Override
        public Codec<SmokeGrenadeParticleData> func_230522_e_() {
                return Codec.unit(new SmokeGrenadeParticleData(new Vector3d(0,0,0),new Color(0),0));
        }
}
