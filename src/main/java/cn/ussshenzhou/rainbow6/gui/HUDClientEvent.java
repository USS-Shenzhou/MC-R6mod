package cn.ussshenzhou.rainbow6.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author USS_Shenzhou
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class HUDClientEvent {
    @SubscribeEvent
    public static void playerIconListBackGroundGuiRender(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (ClientMatch.getIsShowIconList()) {
        R6PlayerIconListBackGroundGui r6PlayerIconListBackGroundGui = new R6PlayerIconListBackGroundGui(event.getMatrixStack());
        switch (ClientMatch.getTeam()) {
            case "blue":
                r6PlayerIconListBackGroundGui.renderBlue();
                break;
            case "orange":
                r6PlayerIconListBackGroundGui.renderOrange();
                break;
            default:
                break;
        }
        }
    }

    @SubscribeEvent
    public static void matchMakingBarRender(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (!InGameClientProperties.getIsWaitingMatchMaking()) {
            return;
        }
        R6MatchMakingBarGui r6MatchMakingBarGui = new R6MatchMakingBarGui(event.getMatrixStack());
        r6MatchMakingBarGui.render();
    }

    @SubscribeEvent
    public static void matchMadeBarRender(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (ClientMatch.getIsMatchMade()) {
            R6MatchMadeBarGui r6MatchMadeBarGui = new R6MatchMadeBarGui(event.getMatrixStack());
            r6MatchMadeBarGui.render();
        }
    }

    @SubscribeEvent
    public static void sceneRender(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (ClientMatch.getIsShowScene()) {
            R6MapSceneGui r6MapSceneGui = new R6MapSceneGui(event.getMatrixStack());
            switch (ClientMatch.getTeam()) {
                case "blue":
                    r6MapSceneGui.renderBlue();
                    break;
                case "orange":
                    r6MapSceneGui.renderOrange();
                    break;
                default:
                    break;
            }
        }
    }

    @SubscribeEvent
    public static void removeNamePlate(RenderNameplateEvent event) {
        if (!"player".equals(ClientMatch.getOperator())) {
            event.setResult(net.minecraftforge.eventbus.api.Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void spotEnemy(RenderPlayerEvent event) {
        NetworkPlayerInfo networkPlayerInfo = Minecraft.getInstance().player.connection.getPlayerInfo(event.getPlayer().getUniqueID());
        if (ClientMatch.getSpotEnemy().containsKey(networkPlayerInfo) && !ClientMatch.getIsSpotted(networkPlayerInfo)) {
            ClientMatch.setSpottedEnemy(networkPlayerInfo);
        }
    }

    @SubscribeEvent
    public static void droneGui(RenderGameOverlayEvent event){
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        /*if (InGameClientProperties.isUsingDrone){
            InGameClientProperties.getR6DroneGui(event).render();
        }*/
    }
    @SubscribeEvent
    public static void hideHand(RenderHandEvent event){
        if (InGameClientProperties.isUsingDrone){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void interceptInGameMainMenu(GuiOpenEvent event){
        if (InGameClientProperties.interceptInGameMenu && event.getGui() instanceof IngameMenuScreen){
            event.setCanceled(true);
            InGameClientProperties.interceptInGameMenu = false;
        }
    }
    @SubscribeEvent
    public static void ee1dWarn(RenderGameOverlayEvent event){
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        if (EE1DWarnGui.isRender){
            EE1DWarnGui ee1DWarnGui = new EE1DWarnGui(event.getMatrixStack());
            ee1DWarnGui.render();
        }
    }
}
