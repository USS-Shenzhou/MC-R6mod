package com.ussshenzhou.rainbow6.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ussshenzhou.rainbow6.capabilities.R6PlayerCapability;
import com.ussshenzhou.rainbow6.network.MatchMakingPack;
import com.ussshenzhou.rainbow6.network.MatchMakingPackSend;
import com.ussshenzhou.rainbow6.network.PlayerOperatorPack;
import com.ussshenzhou.rainbow6.network.PlayerOperatorPackSend;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.network.NetworkInstance;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author USS_Shenzhou
 */
public class R6RoundPrepareScreen extends Screen {
    public R6RoundPrepareScreen(ITextComponent titleIn) {
        super(titleIn);
        this.width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        this.height = Minecraft.getInstance().getMainWindow().getScaledHeight();
    }

    public final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("rainbow6:textures/gui/mainmenu_background.png");
    public final ResourceLocation SP_LOCATION = new ResourceLocation("rainbow6:textures/gui/spawnpoint.png");
    private DynamicTexture mapTexture;
    float scaleFactorX = Minecraft.getInstance().getMainWindow().getScaledWidth() / 1920.0f;
    float scaleFactorY = Minecraft.getInstance().getMainWindow().getScaledHeight() / 1080.0f;
    private int iM;
    private int jM;

    enum Stages {
        LOCATIONS,
        OPERATORS,
        LOADOUT
    }

    private Stages stage = Stages.LOCATIONS;
    R6Button locationsButton;
    R6Button operatorsButton;
    R6Button loadoutButton;
    private int locationsBarColor = 0x80ffffff;
    private int operatorsBarColor = 0x80bbbbbb;
    private int loadoutBarColor = 0x80bbbbbb;
    private ArrayList<R6Button> spawnPoints = new ArrayList<>();
    private ArrayList<R6OperatorButton> operatorButtons = new ArrayList<>();

    @Override
    protected void init() {
        //init mapTexture
        this.initMap();
        locationsButton = new R6Button((int) (this.width / 14),
                (int) (this.height / 5.4),
                (int) (70),
                (int) (20),
                new TranslationTextComponent("gui.rainbow6.locations"),
                (playButton) -> {
                    //if stage changed, remove former stages' child-buttons, and add this stages' child-buttons.
                    if (stage == Stages.OPERATORS) {
                        for (R6OperatorButton b : operatorButtons) {
                            this.buttons.remove(b);
                            this.children.remove(b);
                        }
                        for (R6Button b : spawnPoints) {
                            this.addButton(b);
                        }
                    } else if (stage == Stages.LOADOUT) {
                        //
                        for (R6Button b : spawnPoints) {
                            this.addButton(b);
                        }
                    }
                    stage = Stages.LOCATIONS;
                    //operatorsButton.setPressable(true);
                });
        locationsButton.setAlpha(0);
        this.addButton(locationsButton);
        operatorsButton = new R6Button((int) (this.width / 14 + 70),
                (int) (this.height / 5.4),
                (int) (70),
                (int) (20),
                new TranslationTextComponent("gui.rainbow6.operators"),
                (playButton) -> {
                    if (stage == Stages.LOCATIONS) {
                        for (R6Button b : spawnPoints) {
                            this.buttons.remove(b);
                            this.children.remove(b);
                        }
                        for (R6OperatorButton b : operatorButtons) {
                            this.addButton(b);
                        }
                    } else if (stage == Stages.LOADOUT) {
                        //
                        for (R6OperatorButton b : operatorButtons) {
                            this.addButton(b);
                        }
                    }
                    stage = Stages.OPERATORS;
                    //loadoutButton.setPressable(true);
                });
        operatorsButton.setAlpha(0);
        operatorsButton.setPressable(false);
        this.addButton(operatorsButton);
        loadoutButton = new R6Button((int) (this.width / 14 + 140),
                (int) (this.height / 5.4),
                (int) (70),
                (int) (20),
                new TranslationTextComponent("gui.rainbow6.loadout"),
                (playButton) -> {
                    if (stage == Stages.LOCATIONS) {
                        for (R6Button b : spawnPoints) {
                            this.buttons.remove(b);
                            this.children.remove(b);
                        }
                        //
                    } else if (stage == Stages.OPERATORS) {
                        for (R6OperatorButton b : operatorButtons) {
                            this.buttons.remove(b);
                            this.children.remove(b);
                        }
                        //
                    }
                    stage = Stages.LOADOUT;
                });
        loadoutButton.setAlpha(0);
        loadoutButton.setPressable(false);
        this.addButton(loadoutButton);
        //spawn point button
        int i = 0;
        for (String s : ClientMatch.getSpawnPos().keySet()) {
            R6Button button = new R6Button((int) (this.width / 10),
                    (int) (this.height / 3.5 + 25 * i),
                    (int) (100),
                    (int) (20),
                    new TranslationTextComponent(s),
                    (playButton) -> {
                        stage = Stages.OPERATORS;
                        operatorsButton.setPressable(true);
                        operatorsBarColor = 0x80ffffff;
                        ClientMatch.setChosenSPName(s);
                        for (R6Button b : spawnPoints) {
                            this.buttons.remove(b);
                            this.children.remove(b);
                        }
                        for (R6OperatorButton b : operatorButtons) {
                            this.addButton(b);
                        }
                    });
            this.addButton(button);
            spawnPoints.add(button);
            i++;
        }
        //operator icons
        //attacker
        int j = 0;
        if (("blue".equals(ClientMatch.getTeam()) && ClientMatch.getIsBlueAttacker()) || ("orange".equals(ClientMatch.getTeam()) && !ClientMatch.getIsBlueAttacker())) {
            ArrayList<String> list = Lists.newArrayList(R6PlayerCapability.ATTACKERS);
            list.remove("unknown");
            list.remove("recruit");
            for (String s : list) {
                R6OperatorButton r6OperatorButton = new R6OperatorButton(s, (this.width / 10 + 33 * (j % 7)),
                        (int) (this.height / 3.5 + 33 * (int) (j / 7)),
                        new TranslationTextComponent(s),
                        (playButton) -> {
                            stage = Stages.LOADOUT;
                            loadoutButton.setPressable(true);
                            loadoutBarColor = 0x80ffffff;
                            for (R6OperatorButton b : operatorButtons) {
                                this.buttons.remove(b);
                                this.children.remove(b);
                            }
                            ClientMatch.setOperator(s);
                            //tell server
                            PlayerOperatorPackSend.channel.sendToServer(new PlayerOperatorPack(minecraft.player.getUniqueID(), s));
                        });
                operatorButtons.add(r6OperatorButton);
                j++;
            }
        } else {
            //defender
            ArrayList<String> list = Lists.newArrayList(R6PlayerCapability.DEFENDERS);
            list.remove("unknown");
            list.remove("recruit");
            for (String s : list) {
                R6OperatorButton r6OperatorButton = new R6OperatorButton(s, (this.width / 10 + 32 * j),
                        (int) (this.height / 3.5 + 32 * (j % 7)),
                        new TranslationTextComponent(s),
                        (playButton) -> {
                            stage = Stages.LOADOUT;
                            loadoutButton.setPressable(true);
                            loadoutBarColor = 0x80ffffff;
                            for (R6OperatorButton b : operatorButtons) {
                                this.buttons.remove(b);
                                this.children.remove(b);
                            }
                            ClientMatch.setOperator(s);
                            //tell server
                            PlayerOperatorPackSend.channel.sendToServer(new PlayerOperatorPack(minecraft.player.getUniqueID(), s));
                        });
                operatorButtons.add(r6OperatorButton);
                j++;
            }
        }

        super.init();
    }

    /**
     * From:
     * MapData.java
     * FilledMapItem.java
     */
    private void initMap() {
        //map texture
        int minX = ClientMatch.getZonePosMin().getX();
        int minZ = ClientMatch.getZonePosMin().getZ();
        //i - x
        //j - z
        iM = ClientMatch.getZonePosMax().getX() - minX;
        jM = ClientMatch.getZonePosMax().getZ() - minZ;
        mapTexture = new DynamicTexture(iM, jM, true);
        for (int i = 0; i < iM; ++i) {
            for (int j = 0; j < jM; ++j) {
                //get pixel color
                int topY = minecraft.world.getHeight(Heightmap.Type.WORLD_SURFACE, minX + i, minZ + j) - 1;
                MaterialColor materialcolor = minecraft.world.getBlockState(new BlockPos(minX + i, topY, minZ + j)).getBlock().getMaterialColor();
                //pixel brightness
                int brightness = 1;
                int northTopY = minecraft.world.getHeight(Heightmap.Type.WORLD_SURFACE, minX + i, minZ + j - 1) - 1;
                if (topY > northTopY) {
                    brightness++;
                } else if (topY < northTopY) {
                    brightness--;
                }
                //0:lower
                //1:normal
                //2:higher
                //3:not exist
                byte color = (byte) (materialcolor.colorIndex * 4 + brightness);
                //then fill into texture
                int l = color & 255;
                if (l / 4 == 0) {
                    this.mapTexture.getTextureData().setPixelRGBA(i, j, 0);
                } else {
                    //this.mapTexture.getTextureData().setPixelRGBA(i, j, MaterialColor.COLORS[l / 4].getMapColor(l & 3));
                    this.mapTexture.getTextureData().setPixelRGBA(i, j, MaterialColor.COLORS[l / 4].getMapColor(l & 3));
                }
            }
        }
        //rotate
        if (iM < jM) {
            DynamicTexture rotatedMap = new DynamicTexture(jM, iM, true);
            for (int i = 0; i < iM; ++i) {
                for (int j = 0; j < jM; ++j) {
                    rotatedMap.getTextureData().setPixelRGBA(j, iM - i - 1, mapTexture.getTextureData().getPixelRGBA(i, j));
                }
            }
            mapTexture = rotatedMap;
        }
        mapTexture.updateDynamicTexture();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableBlend();
        matrixStack.push();
        matrixStack.scale(scaleFactorX, scaleFactorY, 1f);
        //background
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_LOCATION);
        blit(matrixStack, 0, 0, 0, 0, 1920, 1080, 1920, 1080);
        //render mapName
        float scaleFactor = 1.4f / scaleFactorY;
        matrixStack.scale(scaleFactor, scaleFactor, 1);
        drawString(matrixStack, minecraft.fontRenderer,
                new TranslationTextComponent(ClientMatch.getMapName()),
                (int) (120 / scaleFactor),
                (int) (80 / scaleFactor),
                0xffffff);
        matrixStack.scale(1 / scaleFactor, 1 / scaleFactor, 1);
        //stage chooser
        switch (stage) {
            case LOCATIONS:
                //map
                if (iM >= jM) {
                    scaleFactor = 1150f / iM;
                    matrixStack.scale(scaleFactor, scaleFactor, 1);
                    mapTexture.bindTexture();
                    fill(matrixStack, (int) (750 / scaleFactor) - 3, (int) (350 / scaleFactor) - 3, (int) (750 / scaleFactor) + iM + 3, (int) (350 / scaleFactor) + jM + 3, 0x80222222);
                    blit(matrixStack, (int) (750 / scaleFactor), (int) (350 / scaleFactor), 0, 0, iM, jM, iM, jM);
                } else {
                    scaleFactor = 1150f / jM;
                    matrixStack.scale(scaleFactor, scaleFactor, 1);
                    mapTexture.bindTexture();
                    fill(matrixStack, (int) (750 / scaleFactor) - 3, (int) (350 / scaleFactor) - 3, (int) (750 / scaleFactor) + jM + 3, (int) (350 / scaleFactor) + iM + 3, 0x80222222);
                    blit(matrixStack, (int) (750 / scaleFactor), (int) (350 / scaleFactor), 0, 0, jM, iM, jM, iM);
                }
                matrixStack.scale(1 / scaleFactor, 1 / scaleFactor, 1);
                //spawn points
                matrixStack.scale(1 / scaleFactorX, 1 / scaleFactorY, 1f);
                for (R6Button button : spawnPoints) {
                    button.render(matrixStack, mouseX, mouseY, partialTicks);
                }
                matrixStack.scale(scaleFactorX, scaleFactorY, 1f);
                //spawn icons
                this.minecraft.getTextureManager().bindTexture(SP_LOCATION);
                for (R6Button button : spawnPoints) {
                    int x = ClientMatch.getSpawnPos().get(button.getMessage().getString()).getX();
                    int z = ClientMatch.getSpawnPos().get(button.getMessage().getString()).getZ();
                    int offsetX = x - ClientMatch.getZonePosMin().getX();
                    int offsetZ = z - ClientMatch.getZonePosMin().getZ();
                    float scaleAgain = 0.45f;
                    if (iM >= jM) {
                        scaleFactor = 1150f / iM * scaleAgain;
                        matrixStack.scale(scaleFactor, scaleFactor, 1);
                        blit(matrixStack, (int) (750 / scaleFactor + offsetX / scaleAgain - 7), (int) (350 / scaleFactor + offsetZ / scaleAgain - 7), button.isHovered() ? 0 : 16, 0, 16, 16, 32, 16);
                    } else {
                        scaleFactor = 1150f / jM * scaleAgain;
                        matrixStack.scale(scaleFactor, scaleFactor, 1);
                        blit(matrixStack, (int) (750 / scaleFactor + offsetZ / scaleAgain - 7), (int) (350 / scaleFactor + (iM - offsetX) / scaleAgain - 7), button.isHovered() ? 0 : 16, 0, 16, 16, 32, 16);
                    }
                    matrixStack.scale(1 / scaleFactor, 1 / scaleFactor, 1);
                }
                break;
            case OPERATORS:
                matrixStack.scale(1 / scaleFactorX, 1 / scaleFactorY, 1f);
                for (R6OperatorButton b : operatorButtons) {
                    b.render(matrixStack, mouseX, mouseY, partialTicks);
                }
                InventoryScreen.drawEntityOnScreen((int) (width / 1.2), (int) (height / 1.2), 100, (float) (width / 1.2 - mouseX), (float) (height - height / 1.2 - mouseY), minecraft.player);
                matrixStack.scale(scaleFactorX, scaleFactorY, 1f);
                break;
            case LOADOUT:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + stage);
        }
        matrixStack.pop();
        //stages
        this.locationsButton.render(matrixStack, mouseX, mouseY, partialTicks);
        fill(matrixStack, (int) (this.width / 14 + 35), (int) (this.height / 5.4 + 18), (int) (this.width / 14 + 70), (int) (this.height / 5.4 + 20), locationsBarColor);
        this.operatorsButton.render(matrixStack, mouseX, mouseY, partialTicks);
        fill(matrixStack, (int) (this.width / 14 + 70), (int) (this.height / 5.4 + 18), (int) (this.width / 14 + 140), (int) (this.height / 5.4 + 20), operatorsBarColor);
        this.loadoutButton.render(matrixStack, mouseX, mouseY, partialTicks);
        fill(matrixStack, (int) (this.width / 14 + 140), (int) (this.height / 5.4 + 18), (int) (this.width / 14 + 175), (int) (this.height / 5.4 + 20), loadoutBarColor);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        //return false;
        return true;
    }

}
