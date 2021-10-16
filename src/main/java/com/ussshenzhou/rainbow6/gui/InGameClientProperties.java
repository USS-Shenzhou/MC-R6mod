package com.ussshenzhou.rainbow6.gui;

import net.minecraft.util.math.vector.Vector3i;

import java.util.HashMap;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author USS_Shenzhou
 */
public class InGameClientProperties {
    private static boolean isWaitingMatchMaking = false;
    public static boolean isUsingDrone = false;
    private static R6DroneGui r6DroneGui = null;
    public static boolean interceptInGameMenu = false;
    //operator - x,y,0
    private static final HashMap<String, Vector3i> OPERATOR_LOGO = new HashMap<String, Vector3i>() {{
        put("unknown", new Vector3i(0, 0, 0));

        put("ace", new Vector3i(0, 32, 0));
        put("alex", new Vector3i(32, 32, 0));
        put("alibi", new Vector3i(64, 32, 0));
        put("amaru", new Vector3i(96, 32, 0));

        put("ash", new Vector3i(0, 64, 0));
        put("bandit", new Vector3i(32, 64, 0));
        put("blackbeard", new Vector3i(64, 64, 0));
        put("blitz", new Vector3i(96, 64, 0));

        put("buck", new Vector3i(0, 96, 0));
        put("capitao", new Vector3i(32, 96, 0));
        put("castle", new Vector3i(64, 96, 0));
        put("caveira", new Vector3i(96, 96, 0));

        put("clash", new Vector3i(0, 128, 0));
        put("doc", new Vector3i(32, 128, 0));
        put("dokkaebi", new Vector3i(64, 128, 0));
        put("echo", new Vector3i(96, 128, 0));

        put("ela", new Vector3i(0, 160, 0));
        put("finka", new Vector3i(32, 160, 0));
        put("frost", new Vector3i(64, 160, 0));
        put("fuze", new Vector3i(96, 160, 0));

        put("glaz", new Vector3i(0, 192, 0));
        put("goyo", new Vector3i(32, 192, 0));
        put("gridlock", new Vector3i(64, 192, 0));
        put("hibana", new Vector3i(96, 192, 0));

        put("iana", new Vector3i(0, 224, 0));
        put("iq", new Vector3i(32, 224, 0));
        put("jackal", new Vector3i(64, 224, 0));
        put("jager", new Vector3i(96, 224, 0));

        put("kaid", new Vector3i(0, 256, 0));
        put("kali", new Vector3i(32, 256, 0));
        put("kapkan", new Vector3i(64, 256, 0));
        put("lesion", new Vector3i(96, 256, 0));

        put("lion", new Vector3i(0, 288, 0));
        put("maestro", new Vector3i(32, 288, 0));
        put("maverick", new Vector3i(64, 288, 0));
        put("melusi", new Vector3i(96, 288, 0));

        put("mira", new Vector3i(0, 320, 0));
        put("montagne", new Vector3i(32, 320, 0));
        put("mozzie", new Vector3i(64, 320, 0));
        put("mute", new Vector3i(96, 320, 0));

        put("nokk", new Vector3i(0, 352, 0));
        put("nomad", new Vector3i(32, 352, 0));
        put("oryx", new Vector3i(64, 352, 0));
        put("recruit1_blue", new Vector3i(96, 352, 0));

        put("recruit1_green", new Vector3i(0, 384, 0));
        put("recruit1_orange", new Vector3i(32, 384, 0));
        put("recruit1_red", new Vector3i(64, 384, 0));
        put("recruit1_yellow", new Vector3i(96, 384, 0));

        put("pulse", new Vector3i(0, 416, 0));
        put("recruit2_blue", new Vector3i(32, 416, 0));
        put("recruit2_green", new Vector3i(64, 416, 0));
        put("recruit2_orange", new Vector3i(96, 416, 0));

        put("recruit2_red", new Vector3i(0, 448, 0));
        put("recruit2_yellow", new Vector3i(32, 448, 0));
        put("rook", new Vector3i(64, 448, 0));
        put("sledge", new Vector3i(96, 448, 0));

        put("smoke", new Vector3i(0, 480, 0));
        put("steve", new Vector3i(32, 480, 0));
        put("tachanka", new Vector3i(64, 480, 0));
        put("thatcher", new Vector3i(96, 480, 0));

        put("thermite", new Vector3i(0, 512, 0));
        put("twitch", new Vector3i(32, 512, 0));
        put("valkyrie", new Vector3i(64, 512, 0));
        put("vigil", new Vector3i(96, 512, 0));

        put("wamai", new Vector3i(0, 544, 0));
        put("warden", new Vector3i(32, 544, 0));
        put("ying", new Vector3i(64, 544, 0));
        put("zofia", new Vector3i(96, 544, 0));
    }};

    public static Vector3i getOperatorLogo(String operator) {
        try {
            return OPERATOR_LOGO.get(operator);
        } catch (NullPointerException e) {
            Logger logger = LogManager.getLogger();
            logger.error(String.format("Invalid operator name: %s", operator));
            return OPERATOR_LOGO.get("unknown");
        }
    }

    public static R6DroneGui getR6DroneGui(RenderGameOverlayEvent event) {
        if (r6DroneGui == null) {
            r6DroneGui = new R6DroneGui(event.getMatrixStack());
            r6DroneGui.init();
        }
        return r6DroneGui;
    }

    public static R6DroneGui getR6DroneGui() {
        return r6DroneGui;
    }

    public static void resetR6DroneGui() {
        r6DroneGui = null;
    }

    public static void setIsWaitingMatchMaking(Boolean b) {
        isWaitingMatchMaking = b;
    }

    public static Boolean getIsWaitingMatchMaking() {
        return isWaitingMatchMaking;
    }
}
