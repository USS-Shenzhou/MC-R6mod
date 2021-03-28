package com.ussshenzhou.rainbow6.bullets;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * @author USS_Shenzhou
 */
public class TestBulletEntity extends AbstractBulletEntity{
    public TestBulletEntity(EntityType<? extends TestBulletEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return ModBulletItems.testBulletItem;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(damage,9);
    }
}
