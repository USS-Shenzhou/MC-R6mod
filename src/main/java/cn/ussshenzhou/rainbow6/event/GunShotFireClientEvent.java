package cn.ussshenzhou.rainbow6.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author USS_Shenzhou
 */
public class GunShotFireClientEvent extends PlayerEvent {

    public GunShotFireClientEvent(Player shooter) {
        super(shooter);
    }

    public static class Pre extends GunShotFireClientEvent
    {
        public Pre(Player shooter)
        {
            super(shooter);
        }
    }

    public static class Post extends GunShotFireClientEvent
    {
        public Post(Player shooter)
        {
            super(shooter);
        }
    }
}
