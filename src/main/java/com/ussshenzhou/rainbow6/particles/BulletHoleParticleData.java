package com.ussshenzhou.rainbow6.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author USS_Shenzhou
 *
 * Most code below are imported from MrCrayfishGunMod-1.16.X BulletHoleData.java under lGPLv2.
 * Source: https://github.com/MrCrayfish/MrCrayfishGunMod
 * also: https://mrcrayfish.com/
 */
public class BulletHoleParticleData implements IParticleData {

    /**
     * Author: MrCrayfish
     */
    public static final Codec<BulletHoleParticleData> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(Codec.INT.fieldOf("dir").forGetter((data) -> {
            return data.direction.ordinal();
        }), Codec.LONG.fieldOf("pos").forGetter((p_239806_0_) -> {
            return p_239806_0_.pos.toLong();
        })).apply(builder, BulletHoleParticleData::new);
    });

    private final Direction direction;
    private final BlockPos pos;

    public BulletHoleParticleData(int dir, long pos)
    {
        this.direction = Direction.values()[dir];
        this.pos = BlockPos.fromLong(pos);
    }

    public BulletHoleParticleData(Direction dir, BlockPos pos)
    {
        this.direction = dir;
        this.pos = pos;
    }

    public Direction getDirection()
    {
        return this.direction;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticleTypeRegistry.BULLET_HOLE_PARTICLE.get();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeEnumValue(this.direction);
        buffer.writeBlockPos(this.pos);
    }

    @Override
    public String getParameters() {
        return ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()) + " " + this.direction.getName2();
    }

    public static final IParticleData.IDeserializer<BulletHoleParticleData> DESERIALIZER = new IParticleData.IDeserializer<BulletHoleParticleData>()
    {
        @Override
        public BulletHoleParticleData deserialize(ParticleType<BulletHoleParticleData> particleType, StringReader reader) throws CommandSyntaxException
        {
            reader.expect(' ');
            int dir = reader.readInt();
            reader.expect(' ');
            long pos = reader.readLong();
            return new BulletHoleParticleData(dir, pos);
        }

        @Override
        public BulletHoleParticleData read(ParticleType<BulletHoleParticleData> particleType, PacketBuffer buffer)
        {
            return new BulletHoleParticleData(buffer.readInt(), buffer.readLong());
        }
    };
}
