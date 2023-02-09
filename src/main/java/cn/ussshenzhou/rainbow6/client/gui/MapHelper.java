package cn.ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.data.Map;
import cn.ussshenzhou.rainbow6.mixinproxy.GameRendererProxy;
import cn.ussshenzhou.rainbow6.mixinproxy.LevelRendererProxy;
import cn.ussshenzhou.rainbow6.network.RoundPrepareTopView;
import cn.ussshenzhou.rainbow6.util.Sides;
import cn.ussshenzhou.t88.network.PacketProxy;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3d;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
public class MapHelper {
    public static Minecraft minecraft = Minecraft.getInstance();

    /**
     * DO NOT call this in main thread.
     */
    public static ArrayList<DynamicTexture> generateMap() {
        ArrayList<DynamicTexture> maps = new ArrayList<>();
        float playableWidth = ClientMatch.getMap().getZonePointMax().getX() - ClientMatch.getMap().getZonePointMin().getX();
        float playableHeight = ClientMatch.getMap().getZonePointMax().getZ() - ClientMatch.getMap().getZonePointMin().getZ();
        Window window = minecraft.getWindow();
        float screenRatio = (float) window.getWidth() / window.getHeight();
        float cameraZoomFactor;
        float completeWidth;
        float completeHeight;
        Map map = ClientMatch.getMap();
        float playableLeftUpX = map.getZonePointMin().getX();
        float playableLeftUpZ = map.getZonePointMin().getZ();
        ;
        boolean turn = false;
        if (playableWidth < playableHeight) {
            float a = playableWidth;
            //noinspection SuspiciousNameCombination
            playableWidth = playableHeight;
            playableHeight = a;
            //noinspection SuspiciousNameCombination
            playableLeftUpX += playableHeight;
            turn = true;
        }
        if (playableWidth / playableHeight >= screenRatio) {
            completeWidth = playableWidth / 9 * 16;
            completeHeight = completeWidth / screenRatio;
        } else {
            completeHeight = playableHeight / 6 * 9;
            completeWidth = completeHeight * screenRatio;
        }
        cameraZoomFactor = 8640 / (1080 / (completeHeight / 16));
        float centerX = turn
                ? playableLeftUpX + completeHeight * 5 / 18 - completeHeight / 2
                : playableLeftUpX - completeWidth * 5 / 16 + completeWidth / 2;
        float centerZ = turn
                ? playableLeftUpZ - completeWidth * 5 / 16 + completeWidth / 2
                : playableLeftUpZ - completeHeight * 5 / 18 + completeHeight / 2;
        if (ClientMatch.getSide() == Sides.ATTACKER) {
            maps.add(teleportAndTakeScreenshot(centerX, map.getZonePointMax().getY(), centerZ, cameraZoomFactor, false, turn));
        } else {
            for (Map.BombSite bombSite : map.getBombSites()) {
                maps.add(teleportAndTakeScreenshot(centerX, bombSite.getSubSite1Pos().getY() + 1, centerZ, cameraZoomFactor, true, turn));
            }
        }
        return maps;
    }

    private static volatile boolean hasRenderedAllChunks;
    private static volatile boolean hasRenderedAllChunksOld;
    private static volatile Vec3 playerPos;

    public static DynamicTexture teleportAndTakeScreenshot(float centerX, float y, float centerZ, float cameraZoomFactor, boolean clipRoof, boolean turn) {
        PacketProxy.getChannel(RoundPrepareTopView.class).sendToServer(new RoundPrepareTopView(centerX, y, centerZ, turn));
        minecraft.execute(() -> {
            minecraft.options.renderClouds = CloudStatus.OFF;
            ((LevelRendererProxy) minecraft.levelRenderer).enableOrthographic(cameraZoomFactor).setClipRoof(clipRoof);
            //TODO set spectator
        });
        CloudStatus cloudsBuffer = minecraft.options.getCloudsType();
        playerPos = minecraft.player.getPosition(1);
        //wait for tp
        while (!(Math.abs(playerPos.x - centerX) < 0.5 && Math.abs(playerPos.y - y) < 0.5 && Math.abs(playerPos.z - centerZ) < 0.5)) {
            Minecraft.getInstance().execute(() -> {
                playerPos = minecraft.player.getPosition(1);
            });
        }
        hasRenderedAllChunksOld = hasRenderedAllChunks = minecraft.levelRenderer.hasRenderedAllChunks();
        //wait for chunkLoad
        Minecraft.getInstance().execute(() -> {
            hasRenderedAllChunksOld = hasRenderedAllChunks;
            hasRenderedAllChunks = minecraft.levelRenderer.hasRenderedAllChunks();
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
        int i = 0;
        //TODO any better way?
        while (!hasRenderedAllChunks || hasRenderedAllChunksOld) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            Minecraft.getInstance().execute(() -> {
                hasRenderedAllChunksOld = hasRenderedAllChunks;
                hasRenderedAllChunks = minecraft.levelRenderer.hasRenderedAllChunks();
            });
            if (hasRenderedAllChunks && hasRenderedAllChunksOld && i >= 20) {
                break;
            }
            i++;
        }
        //call and wait for screenShot
        GameRendererProxy gameRenderer = ((GameRendererProxy) minecraft.gameRenderer);
        gameRenderer.needScreenShot();
        NativeImage screenShot = null;
        while (screenShot == null) {
            screenShot = gameRenderer.getScreenShot();
        }
        gameRenderer.clearScreenShot();
        minecraft.execute(() -> {
            minecraft.options.renderClouds = cloudsBuffer;
            ((LevelRendererProxy) minecraft.levelRenderer).disableOrthographic();
        });
        hasRenderedAllChunks = false;
        DynamicTexture map = new DynamicTexture(screenShot);
        map.upload();
        return map;
    }
}
