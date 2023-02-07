package cn.ussshenzhou.rainbow6.client.gui;

import cn.ussshenzhou.rainbow6.client.match.ClientMatch;
import cn.ussshenzhou.rainbow6.data.Map;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.MaterialColor;

import java.util.Random;

import static cn.ussshenzhou.rainbow6.util.R6Constants.*;

/**
 * @author USS_Shenzhou
 */
public class MapHelper {

    public static DynamicTexture generateMap() {
        DynamicTexture mapTexture;
        Map map = ClientMatch.getMap();
        int playableX0 = map.getZonePointMin().getX();
        int playableX1 = map.getZonePointMax().getX();
        int playableZ0 = map.getZonePointMin().getZ();
        int playableZ1 = map.getZonePointMax().getZ();
        int playableMapWidth = playableX1 - playableX0;
        int playableMapHeight = playableZ1 - playableZ0;
        int completeX0;
        int completeZ0;
        int completeMapWidth;
        int completeMapHeight;
        int pixelRatio;
        //if (playableMapWidth >= playableMapHeight) {
        if (playableMapWidth <= TINY_MAP_PLAYABLE.x && playableMapHeight <= TINY_MAP_PLAYABLE.y) {
            completeX0 = playableX0 - 75;
            completeZ0 = playableZ0 - 30;
            completeMapWidth = TINY_MAP_COMPLETE.x;
            completeMapHeight = TINY_MAP_COMPLETE.y;
            pixelRatio = 8;
        } else if (playableMapWidth <= SMALL_MAP_PLAYABLE.x && playableMapHeight <= SMALL_MAP_PLAYABLE.y) {
            completeX0 = playableX0 - 100;
            completeZ0 = playableZ0 - 40;
            completeMapWidth = SMALL_MAP_COMPLETE.x;
            completeMapHeight = SMALL_MAP_COMPLETE.y;
            pixelRatio = 6;
        } else if (playableMapWidth <= MID_MAP_PLAYABLE.x && playableMapHeight <= MID_MAP_PLAYABLE.y) {
            completeX0 = playableX0 - 150;
            completeZ0 = playableZ0 - 60;
            completeMapWidth = MID_MAP_COMPLETE.x;
            completeMapHeight = MID_MAP_COMPLETE.y;
            pixelRatio = 4;
        } else if (playableMapWidth <= BIG_MAP_PLAYABLE.x && playableMapHeight <= BIG_MAP_PLAYABLE.y) {
            completeX0 = playableX0 - 300;
            completeZ0 = playableZ0 - 120;
            completeMapWidth = BIG_MAP_COMPLETE.x;
            completeMapHeight = BIG_MAP_COMPLETE.y;
            pixelRatio = 2;
        } else {
            //600 = ( 1920 - 1080 ) / 7 * 5
            completeX0 = playableX0 - 600;
            //240 = ( 1080 - 720 ) / 3 * 2
            completeZ0 = playableZ0 - 240;
            completeMapWidth = HUGE_MAP_COMPLETE.x;
            completeMapHeight = HUGE_MAP_COMPLETE.y;
            pixelRatio = 1;
        }
        mapTexture = new DynamicTexture(1920, 1080, true);
        NativeImage nativeImage = mapTexture.getPixels();
        if (pixelRatio <= 2) {
            fillByMaterialColor(completeMapWidth, completeMapHeight, completeX0, completeZ0, nativeImage, pixelRatio);
        } else {
            fillByTexture(completeMapWidth, completeMapHeight, completeX0, completeZ0, nativeImage, pixelRatio);
        }
        //} else {
        //    //TODO playableMapWidth < playableMapHeight
        //}
        mapTexture.upload();
        return mapTexture;
    }

    private static void fillByMaterialColor(int completeMapWidth, int completeMapHeight, int x0, int z0, NativeImage map, int pixelRatio) {
        Minecraft minecraft = Minecraft.getInstance();
        for (int i = 0; i < completeMapWidth; i++) {
            for (int j = 0; j < completeMapHeight; j++) {
                int maxY = minecraft.level.getHeight(Heightmap.Types.WORLD_SURFACE, x0 + i, z0 + j) - 1;
                BlockPos blockPos = new BlockPos(x0 + i, maxY, z0 + j);
                BlockState blockState = minecraft.level.getBlockState(blockPos);
                int color = getMaterialColor(maxY, x0 + i, z0 + j, blockPos, blockState, minecraft);
                if (pixelRatio == 1) {
                    map.setPixelRGBA(i, j, color);
                } else {
                    map.setPixelRGBA(i * 2, j * 2, color);
                    map.setPixelRGBA(i * 2 + 1, j * 2, color);
                    map.setPixelRGBA(i * 2, j * 2 + 1, color);
                    map.setPixelRGBA(i * 2 + 1, j * 2 + 1, color);
                }
            }
        }
    }

    private static void fillByTexture(int completeMapWidth, int completeMapHeight, int x0, int z0, NativeImage map, int pixelRatio) {
        Minecraft minecraft = Minecraft.getInstance();
        Random random = new Random(42L);
        //TODO texturePack: 16+
        //sampleStep > 0 => pixelRatio <= 16;
        int sampleStep = 16 / pixelRatio;
        for (int i = 0; i < completeMapWidth; i++) {
            for (int j = 0; j < completeMapHeight; j++) {
                int failed = 0;
                while (!innerFillByTexture(i, j, x0, z0, pixelRatio, sampleStep, failed, minecraft, random, map)) {
                    failed++;
                }
            }
        }
    }

    private static int getMaterialColor(int maxY, int x, int z, BlockPos blockPos, BlockState blockState, Minecraft minecraft) {
        byte b = (byte) (blockState.getMapColor(minecraft.level, blockPos).id * 4 + getBrightness(x, z, maxY, 0, minecraft));
        //then fill into texture
        int l = b & 255;
        if (l / 4 == 0) {
            return 0;
        } else {
            return MaterialColor.MATERIAL_COLORS[l / 4].calculateRGBColor(MaterialColor.Brightness.byId(l & 3));
        }
    }

    private static boolean innerFillByTexture(int i, int j, int x0, int z0, int pixelRatio, int sampleStep, int failed, Minecraft minecraft, Random random, NativeImage map) {
        int maxY = minecraft.level.getHeight(Heightmap.Types.WORLD_SURFACE, x0 + i, z0 + j) - 1 - failed;
        if (maxY < minecraft.level.getMinBuildHeight()) {
            //give up
            return true;
        }
        BlockPos blockPos = new BlockPos(x0 + i, maxY, z0 + j);
        BlockState blockState = minecraft.level.getBlockState(blockPos);
        try {
            /* TODO fix grass_path
            BakedQuad a;
            BakedModel model = minecraft.getBlockRenderer().getBlockModel(blockState);
            List<BakedQuad> l = model.getQuads(blockState, Direction.UP, random, null);
            if (l.size() > 0) {
                a = l.get(0);
            } else if (model instanceof SimpleBakedModel) {
                a = ((SimpleBakedModelAccessor) model).getUnculledFaces().stream().filter(bakedQuad -> bakedQuad.getDirection()==Direction.UP).toList().get(0);
            } else {
                throw new IndexOutOfBoundsException();
            }*/
            BakedQuad a = minecraft.getBlockRenderer().getBlockModel(blockState).getQuads(blockState, Direction.UP, random, null).get(0);
            TextureAtlasSprite textureAtlasSprite = a.getSprite();
            for (int i0 = 0; i0 < pixelRatio; i0++) {
                for (int j0 = 0; j0 < pixelRatio; j0++) {
                    if (i * pixelRatio + i0 >= 1920 || j * pixelRatio + j0 >= 1080) {
                        continue;
                    }
                    int color = textureAtlasSprite.getPixelRGBA(0, i0 * sampleStep, j0 * sampleStep);
                    color = calculateBrightness(color, getBrightness(x0 + i, z0 + j, maxY, 0, minecraft));
                    map.setPixelRGBA(i * pixelRatio + i0, j * pixelRatio + j0, color);
                }
            }
            return true;
        } catch (Exception ignored) {
            for (int i0 = 0; i0 < pixelRatio; i0++) {
                for (int j0 = 0; j0 < pixelRatio; j0++) {
                    if (i * pixelRatio + i0 >= 1920 || j * pixelRatio + j0 >= 1080) {
                        continue;
                    }
                    int color = getMaterialColor(maxY, x0 + i, z0 + j, blockPos, blockState, minecraft);
                    if (color == 0) {
                        return false;
                    }
                    map.setPixelRGBA(i * pixelRatio + i0, j * pixelRatio + j0, color);
                }
            }
            return true;
        }
    }

    private static int getBrightness(int x, int z, int maxY, int failed, Minecraft minecraft) {
        int brightness = 1;
        int northTopY = minecraft.level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z - 1) - 1 - failed;
        if (maxY > northTopY) {
            brightness++;
        } else if (maxY < northTopY) {
            brightness--;
        }
        //0:lower
        //1:normal
        //2:higher
        //3:not exist
        return brightness;
    }


    /**
     * @see MaterialColor#calculateRGBColor(MaterialColor.Brightness)
     */
    private static int calculateBrightness(int color, int brightness) {
        int i;
        switch (brightness) {
            case 0 -> i = 180;
            case 1 -> i = 220;
            case 3 -> i = 135;
            default -> i = 255;
        }
        int r = (color >> 16 & 255) * i / 255;
        int g = (color >> 8 & 255) * i / 255;
        int b = (color & 255) * i / 255;
        //return -16777216 | b << 16 | g << 8 | r;
        return 0xff000000 | r << 16 | g << 8 | b;
    }
}
