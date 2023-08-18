package cn.ussshenzhou.rainbow6.gun;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author USS_Shenzhou
 */
public class GunShotFireEvent extends PlayerEvent {

    public GunShotFireEvent(Player shooter) {
        super(shooter);
    }

    public static class Pre extends GunShotFireEvent
    {
        public Pre(Player shooter)
        {
            super(shooter);
        }
    }

    public static class Post extends GunShotFireEvent
    {
        public Post(Player shooter)
        {
            super(shooter);
        }
    }
}
