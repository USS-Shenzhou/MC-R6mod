package cn.ussshenzhou.rainbow6.util;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import net.minecraft.resources.ResourceLocation;

/**
 * @author USS_Shenzhou
 */
public class IconHelper {
    public static ResourceLocation getAllyIconWhite(int size) {
        return new ResourceLocation(R6Constants.MOD_ID, "textures/gui/" +
                (
                        ClientMatch.getSide() == Side.ATTACKER
                                ? "attacker" + size + "_white"
                                : "defender" + size + "_white"
                )
                + ".png"
        );
    }

    public static ResourceLocation getEnemyIconWhite(int size) {
        return new ResourceLocation(R6Constants.MOD_ID, "textures/gui/" +
                (
                        ClientMatch.getSide() != Side.ATTACKER
                                ? "attacker" + size + "_white"
                                : "defender" + size + "_white"
                )
                + ".png"
        );
    }

    public static ResourceLocation getAllyIconColored(int size) {
        return new ResourceLocation(R6Constants.MOD_ID, "textures/gui/" +
                (
                        ClientMatch.getSide() == Side.ATTACKER
                                ? "attacker" + size + "_" + ClientMatch.getAllyTeamColor().name().toLowerCase()
                                : "defender" + size + "_" + ClientMatch.getAllyTeamColor().name().toLowerCase()
                )
                + ".png"
        );
    }

    public static ResourceLocation getEnemyIconColored(int size) {
        return new ResourceLocation(R6Constants.MOD_ID, "textures/gui/" +
                (
                        ClientMatch.getSide() != Side.ATTACKER
                                ? "attacker" + size + "_" + ClientMatch.getAllyTeamColor().opposite().name().toLowerCase()
                                : "defender" + size + "_" + ClientMatch.getAllyTeamColor().opposite().name().toLowerCase()
                )
                + ".png"
        );
    }
}
