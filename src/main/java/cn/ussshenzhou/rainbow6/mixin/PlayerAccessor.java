package cn.ussshenzhou.rainbow6.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Player.class)
public interface PlayerAccessor {
    @Mutable
    @Accessor
    void setInventory(Inventory inventory);

    @Mutable
    @Accessor
    void setInventoryMenu(InventoryMenu inventoryMenu);
}
