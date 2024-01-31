package cn.ussshenzhou.rainbow6.client.gui.screen;

import cn.ussshenzhou.rainbow6.client.gui.ScreenManager;
import cn.ussshenzhou.rainbow6.client.gui.hud.PlayerInfoBarHud;
import cn.ussshenzhou.rainbow6.client.gui.panel.*;
import cn.ussshenzhou.t88.gui.advanced.TFocusSensitiveImageSelectButton;
import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.util.R6Constants;
import cn.ussshenzhou.rainbow6.util.Side;
import cn.ussshenzhou.t88.gui.HudManager;
import cn.ussshenzhou.t88.gui.util.HorizontalAlignment;
import cn.ussshenzhou.t88.gui.util.LayoutHelper;
import cn.ussshenzhou.t88.gui.widegt.TButton;
import cn.ussshenzhou.t88.gui.widegt.TLabel;
import cn.ussshenzhou.t88.gui.widegt.TPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * @author USS_Shenzhou
 */
public class RoundPrepareScreen extends AbstractR6Screen {
    private final TLabel header = new TLabel(Component.literal(ClientMatch.getMap().getName() + "  "));
    private static final ResourceLocation UNSELECTED_BUTTON = new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button18_cutout_unselected14.png");
    private static final ResourceLocation SELECTED_BUTTON = new ResourceLocation(R6Constants.MOD_ID, "textures/gui/button18_cutout_selected_centered.png");
    private final TFocusSensitiveImageSelectButton locationsButton = new TFocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.round_prepare.locations"),
            pButton -> {
                setButtonSelectedAndPanelVisible((TFocusSensitiveImageSelectButton) ((TButton) pButton).getParent(), getLocationsPanel());
            },
            UNSELECTED_BUTTON,
            SELECTED_BUTTON
    );
    private final TFocusSensitiveImageSelectButton operatorsButton = new TFocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.round_prepare.operators"),
            pButton -> {
                setButtonSelectedAndPanelVisible((TFocusSensitiveImageSelectButton) ((TButton) pButton).getParent(), getOperatorsPanel());
            },
            UNSELECTED_BUTTON,
            SELECTED_BUTTON
    );
    private final TFocusSensitiveImageSelectButton loadoutButton = new TFocusSensitiveImageSelectButton(
            Component.translatable("gui.r6ms.round_prepare.loadout"),
            pButton -> {
                setButtonSelectedAndPanelVisible((TFocusSensitiveImageSelectButton) ((TButton) pButton).getParent(), getLoadoutPanel());
            },
            UNSELECTED_BUTTON,
            SELECTED_BUTTON
    );
    private final RoundPreLocationsPanel locationsPanel;
    private final RoundPreOperatorsPanel operatorsPanel = new RoundPreOperatorsPanel();
    private final RoundPreLoadoutPanel loadoutPanel = new RoundPreLoadoutPanel();

    protected RoundPrepareScreen() {
        super("RoundPrepareScreen");
        locationsPanel = ClientMatch.getSide() == Side.ATTACKER
                ? new RoundPreLocationsPanelAttacker()
                : new RoundPreLocationsPanelDefender();
        this.add(locationsPanel);
        operatorsPanel.setVisibleT(false);
        this.add(operatorsPanel);
        loadoutPanel.setVisibleT(false);
        this.add(loadoutPanel);
        header.setBackground(0x80000000);
        header.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        this.add(header);
        locationsButton.setPadding(R6Constants.PADDING_SMALL);
        locationsButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        locationsButton.setSelected(true);
        this.add(locationsButton);
        operatorsButton.setPadding(R6Constants.PADDING_SMALL);
        operatorsButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(operatorsButton);
        loadoutButton.setPadding(R6Constants.PADDING_SMALL);
        loadoutButton.getText().setHorizontalAlignment(HorizontalAlignment.CENTER);
        this.add(loadoutButton);
    }

    public static void newRoundPrepareScreenAndShow() {
        RoundPrepareScreen a = new RoundPrepareScreen();
        ScreenManager.showNewLayerClearBg(a);
        LoadingScreen.WithFullBackground loadingScreen = new LoadingScreen.WithFullBackground("gui.r6ms.round_prepare.loading_top_view");
        ScreenManager.showNewLayerOverBg(loadingScreen);
        CompletableFuture.runAsync(() -> {
            while (!a.locationsPanel.isReady()) {
            }
            Minecraft.getInstance().execute(() -> {
                loadingScreen.onClose(true);
                initiatePlayerInfoBar();
            });
        });
    }

    private static void initiatePlayerInfoBar() {
        ScreenManager.playerInfoBarHud = new PlayerInfoBarHud(40);
        HudManager.add(ScreenManager.playerInfoBarHud);
    }

    @Override
    public void layout() {
        header.setBounds(0, 0, width, 18);
        locationsPanel.setBounds(0, 0, width, height);
        locationsButton.setBounds(10, 36, 64, 18);
        LayoutHelper.BRightOfA(operatorsButton, -4, locationsButton);
        LayoutHelper.BRightOfA(loadoutButton, -4, operatorsButton);
        LayoutHelper.BSameAsA(operatorsPanel, locationsPanel);
        LayoutHelper.BSameAsA(loadoutPanel, locationsPanel);
        super.layout();
    }

    public void setButtonSelectedAndPanelVisible(TFocusSensitiveImageSelectButton button, TPanel tPanel) {
        Stream.of(locationsButton, operatorsButton, loadoutButton).forEach(b -> b.setSelected(false));
        button.setSelected(true);
        Stream.of(locationsPanel, operatorsPanel, loadoutPanel).forEach(b -> b.setVisibleT(false));
        tPanel.setVisibleT(true);
    }

    public RoundPreLocationsPanel getLocationsPanel() {
        return locationsPanel;
    }

    public RoundPreOperatorsPanel getOperatorsPanel() {
        return operatorsPanel;
    }

    public RoundPreLoadoutPanel getLoadoutPanel() {
        return loadoutPanel;
    }

    public TFocusSensitiveImageSelectButton getLocationsButton() {
        return locationsButton;
    }

    public TFocusSensitiveImageSelectButton getOperatorsButton() {
        return operatorsButton;
    }

    public TFocusSensitiveImageSelectButton getLoadoutButton() {
        return loadoutButton;
    }
}
