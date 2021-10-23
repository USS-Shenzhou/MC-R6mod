package cn.ussshenzhou.rainbow6.particles;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;
/**
 * @author USS_Shenzhou
 */
public class GasSmokeParticleType extends ParticleType<GasSmokeParticleData>{

    public GasSmokeParticleType() {
        super(false,GasSmokeParticleData.DESERIALIZER);
    }

    @Override
    public Codec<GasSmokeParticleData> func_230522_e_() {
        return Codec.unit(new GasSmokeParticleData(new Vector3d(0,0,0),new Color(0),0));
    }
}
