package cn.ussshenzhou.rainbow6.gun;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author USS_Shenzhou
 */
public class GunProperty implements INBTSerializable<CompoundTag> {

    private int roundsPerMinute;

    public GunProperty(int roundsPerMinute) {
        this.roundsPerMinute = roundsPerMinute;
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        tag.putInt("fireRate", roundsPerMinute);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        roundsPerMinute = nbt.getInt("fireRate");
    }

    public int getShotsPerMinute() {
        return roundsPerMinute;
    }

    public float getShotsPerTick() {
        return roundsPerMinute / 60f / 20;
    }

    public float getPartialTickBetweenShots() {
        return 60f * 20 / roundsPerMinute;
    }
}
