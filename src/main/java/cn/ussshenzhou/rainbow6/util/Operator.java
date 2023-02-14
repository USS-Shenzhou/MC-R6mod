package cn.ussshenzhou.rainbow6.util;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author USS_Shenzhou
 */

@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum Operator {
    RECRUIT_A(Side.ATTACKER),
    SLEDGE(Side.ATTACKER),
    THATCHER(Side.ATTACKER),
    ASH(Side.ATTACKER),
    THERMITE(Side.ATTACKER),
    TWITCH(Side.ATTACKER),
    MONTAGNE(Side.ATTACKER),
    GLAZ(Side.ATTACKER),
    FUZE(Side.ATTACKER),
    BLITZ(Side.ATTACKER),
    IQ(Side.ATTACKER),

    RECRUIT_D(Side.DEFENDER),
    SMOKE(Side.DEFENDER),
    MUTE(Side.DEFENDER),
    CASTLE(Side.DEFENDER),
    PULSE(Side.DEFENDER),
    DOC(Side.DEFENDER),
    ROOK(Side.DEFENDER),
    KAPKAN(Side.DEFENDER),
    TACHANKA(Side.DEFENDER),
    JAGER(Side.DEFENDER),
    BANDIT(Side.DEFENDER);

    public final Side side;

    Operator(Side side) {
        this.side = side;
    }

    @SuppressWarnings({"AlibabaSwitchStatement", "SpellCheckingInspection"})
    public String getNameUpperCase() {
        return switch (this) {
            case RECRUIT_A, RECRUIT_D -> "RECRUIT";
            case JAGER -> "JÄGER";
            default -> this.name().toUpperCase(Locale.ENGLISH);
        };
    }

    public String getNameLowerCaseForResource() {
        return this.name().toLowerCase(Locale.ENGLISH);
    }

    /*@SuppressWarnings({"AlibabaSwitchStatement", "SpellCheckingInspection"})
    public String getNamePascal() {
        switch (this) {
            case JAGER -> {
                return "Jäger";
            }
            default -> {
                return this.name().toLowerCase(Locale.ENGLISH);
            }
        }
    }*/

    public static Operator[] getOperatorsExceptRecruit(Side s) {
        return Arrays.stream(Operator.values()).filter(operator -> operator.side == s && operator != RECRUIT_A && operator != RECRUIT_D).toArray(Operator[]::new);
    }

    public ResourceLocation getIcon(int size) {
        if (this != RECRUIT_A && this != RECRUIT_D) {
            return new ResourceLocation(R6Constants.MOD_ID, "textures/gui/operator_icon" + size + "/" + this.getNameLowerCaseForResource() + ".png");
        } else {
            String s = switch (ClientMatch.getNumberInTeam()) {
                case 1 -> "blue";
                case 2 -> "green";
                case 3 -> "orange";
                case 4 -> "red";
                default -> "yellow";
            };
            return new ResourceLocation(R6Constants.MOD_ID, "textures/gui/operator_icon" + size + "/recruit_" + s + ".png");
        }
    }
}
