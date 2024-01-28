package cn.ussshenzhou.rainbow6.util;

import cn.ussshenzhou.rainbow6.client.gui.DynamicTextureWithMapData;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.rainbow6.mixinproxy.GameRendererProxy;
import cn.ussshenzhou.rainbow6.mixinproxy.LevelRendererProxy;
import cn.ussshenzhou.rainbow6.network.onlyto.server.RoundPreTopViewPacket;
import cn.ussshenzhou.t88.network.NetworkHelper;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
public class MapTopViewHelper {
    public static Minecraft minecraft = Minecraft.getInstance();

    /**
     * DO NOT call this in main thread.
     */
    public static ArrayList<DynamicTextureWithMapData> generateMap() {
        ArrayList<DynamicTextureWithMapData> maps = new ArrayList<>();
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
        maps.add(new DynamicTextureWithMapData(teleportAndTakeScreenshot(centerX, map.getZonePointMax().getY(), centerZ, cameraZoomFactor, false, turn),
                centerX, centerZ, completeWidth, completeHeight, turn));
        if (ClientMatch.getSide() == Side.DEFENDER) {
            alreadyLoaded = false;
            for (Map.BombSite bombSite : map.getBombSites()) {
                maps.add(new DynamicTextureWithMapData(teleportAndTakeScreenshot(centerX, bombSite.getSubSite1Pos().getY() + 1, centerZ, cameraZoomFactor, true, turn),
                        centerX, centerZ, completeWidth, completeHeight, turn));
            }
        }
        return maps;
    }

    private static boolean alreadyLoaded = false;
    private static volatile boolean hasRenderedAllChunks;
    private static volatile boolean hasRenderedAllChunksOld;
    private static volatile Vec3 playerPos;

    public static NativeImage teleportAndTakeScreenshot(float centerX, float y, float centerZ, float cameraZoomFactor, boolean clipRoof, boolean turn) {
        NetworkHelper.sendToServer(new RoundPreTopViewPacket(centerX, y, centerZ, turn));
        minecraft.execute(() -> {
            minecraft.options.cloudStatus().set(CloudStatus.OFF);
            ((LevelRendererProxy) minecraft.levelRenderer).r6msEnableOrthographic(cameraZoomFactor).setR6msClipRoof(clipRoof);
            ClientMatch.stopRenderPlayer();
        });
        CloudStatus cloudsBuffer = minecraft.options.cloudStatus().get();
        checkPlayerPosition(centerX, y, centerZ);
        checkChunkRender(centerX, y, centerZ);
        //call and wait for screenShot
        GameRendererProxy gameRenderer = ((GameRendererProxy) minecraft.gameRenderer);
        gameRenderer.needScreenShot();
        NativeImage screenShot = null;
        while (screenShot == null) {
            screenShot = gameRenderer.getR6msScreenShot();
        }
        gameRenderer.clearScreenShot();
        minecraft.execute(() -> {
            minecraft.options.cloudStatus().set(cloudsBuffer);
            ((LevelRendererProxy) minecraft.levelRenderer).r6msDisableOrthographic();
            ClientMatch.resumeRenderPlayer();
        });
        hasRenderedAllChunks = false;
        return screenShot;
    }

    private static void checkPlayerPosition(float centerX, float y, float centerZ) {
        playerPos = minecraft.player.getPosition(1);
        boolean tpDone = false;
        //timeout: 10s
        int i = 0;
        while (!tpDone) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            Minecraft.getInstance().execute(() -> playerPos = minecraft.player.getPosition(1));
            tpDone = Math.abs(playerPos.x - centerX) < 1 && Math.abs(playerPos.y - y) < 1 && Math.abs(playerPos.z - centerZ) < 1;
            if (i >= 20) {
                break;
            }
            i++;
        }
    }

    private static void checkChunkRender(float centerX, float y, float centerZ) {
        if (alreadyLoaded) {
            return;
        }
        hasRenderedAllChunksOld = false;
        //TODO check
        hasRenderedAllChunks = minecraft.levelRenderer.hasRenderedAllSections();
        boolean chunkRenderDoneClaimed = hasRenderedAllChunks && hasRenderedAllChunksOld;
        boolean chunkRenderDoneReally;
        //timeout: 20s
        int i = 0;
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            boolean timeOut = i >= (R6Constants.TEST ? 5 : 40);
            chunkRenderDoneReally = chunkRenderDoneClaimed && checkCoreChunkRendered(centerX, y, centerZ);
            if (chunkRenderDoneReally || timeOut) {
                alreadyLoaded = true;
                break;
            }
            Minecraft.getInstance().execute(() -> {
                hasRenderedAllChunksOld = hasRenderedAllChunks;
                hasRenderedAllChunks = minecraft.levelRenderer.hasRenderedAllSections();
            });
            chunkRenderDoneClaimed = hasRenderedAllChunksOld && hasRenderedAllChunks;
            i++;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    private static boolean checkCoreChunkRendered(float centerX, float y, float centerZ) {
        //needtest Does this really work?
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (!minecraft.levelRenderer.isSectionCompiled(new BlockPos((int) (centerX) + i * 16, (int) y, (int) (centerZ) + i * 16))) {
                    return false;
                }
            }
        }
        return true;
    }
}
