package cn.ussshenzhou.rainbow6.effects;

import cn.ussshenzhou.rainbow6.mixin.MixinEntity;
import cn.ussshenzhou.rainbow6.mixin.MixinEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import org.apache.logging.log4j.LogManager;


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
        ((MixinEntityAccessor) entityLivingBaseIn).r6SetFlag(6, true);
        entityLivingBaseIn.setGlowing(true);
        ((MixinEntityAccessor) entityLivingBaseIn).r6SetFlag(8 + 0, true);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }


}
