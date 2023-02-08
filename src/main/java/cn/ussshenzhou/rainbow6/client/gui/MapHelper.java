package cn.ussshenzhou.rainbow6.client.gui;

/**
 * @author USS_Shenzhou
 */
public class MapHelper {

    /*public static DynamicTexture generateMap() {
        DynamicTexture mapTexture;
        float playableWidth = ClientMatch.getMap().getZonePointMax().getX() - ClientMatch.getMap().getZonePointMin().getX();
        float playableHeight = ClientMatch.getMap().getZonePointMax().getZ() - ClientMatch.getMap().getZonePointMin().getZ();
        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
        //default: 16 / 9
        float screenRatio = (float) window.getWidth() / window.getHeight();
        float cameraZoomFactor;
        //if (playableWidth >= playableHeight) {
            float completeWidth;
            float completeHeight;
            if (playableWidth / playableHeight >= screenRatio) {
                completeWidth = playableWidth / 9 * 16;
                completeHeight = completeWidth / screenRatio;
            } else {
                completeHeight = playableHeight / 6 * 9;
                completeWidth = completeHeight * screenRatio;
            }
            cameraZoomFactor = 8640 / (1080 / (completeHeight / 16));
            float centerX = ClientMatch.getMap().getZonePointMin().getX() - completeWidth * 7 / 16 + completeWidth / 2;
            float centerZ = ClientMatch.getMap().getZonePointMin().getZ() - completeHeight * 2 / 9 + completeHeight / 2;

        /*} else {

        }
    }*/
}
