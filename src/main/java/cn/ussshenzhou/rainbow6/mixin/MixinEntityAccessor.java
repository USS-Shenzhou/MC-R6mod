package cn.ussshenzhou.rainbow6.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author USS_Shenzhou
 */
@Mixin(value = Entity.class,remap = false)
public interface MixinEntityAccessor {

    @Invoker("getFlag")
    public boolean r6GetFlag(int flag);

    @Invoker("setFlag")
    public void r6SetFlag(int flag, boolean set);

    /*@Invoker
    public void setR6Flag(int flag, boolean set);

    @Invoker()
    public boolean getR6Flag(int flag);*/
}
