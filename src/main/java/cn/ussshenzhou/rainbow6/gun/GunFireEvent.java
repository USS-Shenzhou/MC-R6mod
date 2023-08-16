package cn.ussshenzhou.rainbow6.gun;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author USS_Shenzhou
 */
public class GunFireEvent extends PlayerEvent {
    private final ItemStack gunStack;

    public GunFireEvent(Player shooter, ItemStack gunStack) {
        super(shooter);
        this.gunStack = gunStack;
    }

    public ItemStack getGunStack() {
        return gunStack;
    }

    public static class Pre extends GunFireEvent
    {
        public Pre(Player shooter, ItemStack gunStack)
        {
            super(shooter, gunStack);
        }
    }

    public static class Post extends GunFireEvent
    {
        public Post(Player shooter, ItemStack gunStack)
        {
            super(shooter, gunStack);
        }
    }
}
