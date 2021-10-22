package com.ussshenzhou.rainbow6.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nullable;

/**
 * @author USS_Shenzhou
 */
public class ExposedEffect extends Effect {
    public ExposedEffect() {
        super(EffectType.NEUTRAL, 0x00ff00);
        this.setRegistryName("rainbow6:exposed");
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        //entityLivingBaseIn.setGlowing(true);
    }
    static final DataParameter<Byte> FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        entityLivingBaseIn.setGlowing(true);
        byte b0 = entityLivingBaseIn.getDataManager().get(FLAGS);
        entityLivingBaseIn.getDataManager().set(FLAGS, (byte)(b0 | 1 << 6));
    }

}
