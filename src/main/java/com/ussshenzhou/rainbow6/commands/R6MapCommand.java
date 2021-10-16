package com.ussshenzhou.rainbow6.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.ussshenzhou.rainbow6.config.R6ServerMapConfig;
import com.ussshenzhou.rainbow6.config.R6ServerMapConfigManager;
import com.ussshenzhou.rainbow6.matchmaking.InGameServerProperties;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author USS_Shenzhou
 */
public class R6MapCommand implements Command<CommandSource> {
    private static R6ServerMapConfig mapConfig;
    private static BlockPos zonePos1;
    private static BlockPos zonePos2;

    private static SuggestionProvider<CommandSource> MAPS = (p, suggestionProvider) -> {
        return ISuggestionProvider.suggest(InGameServerProperties.getMapsArray(), suggestionProvider);
    };

    private static SuggestionProvider<CommandSource> BOMBS = (p, suggestionProvider) -> {
        try {
            return ISuggestionProvider.suggest(mapConfig.getBombPos().keySet().toArray(new String[0]), suggestionProvider);
        } catch (NullPointerException ignored) {
            //p.getSource().sendFeedback("commands.");
        }
        return ISuggestionProvider.suggest(Collections.singleton(""), suggestionProvider);
    };

    private static SuggestionProvider<CommandSource> SPAWNS = (p, suggestionProvider) -> {
        try {
            return ISuggestionProvider.suggest(mapConfig.getSpawnPos().keySet().toArray(new String[0]), suggestionProvider);
        } catch (NullPointerException ignored) {
            //p.getSource().sendFeedback("commands.");
        }
        return ISuggestionProvider.suggest(Collections.singleton(""), suggestionProvider);
    };

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("r6ms")
                        .then(Commands.literal("map")
                                .requires((p) -> p.hasPermissionLevel(2))
                                .then(Commands.argument("mapname", StringArgumentType.word())
                                        .suggests(MAPS)
                                        .executes((p) -> {
                                            if (InGameServerProperties.getMaps().contains(StringArgumentType.getString(p, "mapname"))) {
                                                save(p.getSource());
                                                mapConfig = R6ServerMapConfigManager.getR6Config(StringArgumentType.getString(p, "mapname"));
                                                p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.load", StringArgumentType.getString(p, "mapname")), true);
                                                return 0;
                                            } else {
                                                return newMap(p.getSource(), StringArgumentType.getString(p, "mapname"));
                                            }
                                        })
                                        .then(Commands.literal("bombPosition")
                                                .executes((p) -> {
                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                        return addNextBomb(p.getSource(), p.getSource().asPlayer().getPosition());
                                                    }
                                                    return 0;
                                                })
                                                .then(Commands.argument("bombNumber", StringArgumentType.word())
                                                        .suggests(BOMBS)
                                                        .then(Commands.argument("bombPos", BlockPosArgument.blockPos())
                                                                .executes((p) -> {
                                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                                        if (checkBombNumber(p.getSource(), StringArgumentType.getString(p, "bombNumber"))) {
                                                                            mapConfig.setBombPos(StringArgumentType.getString(p, "bombNumber"), BlockPosArgument.getBlockPos(p, "bombPos"));
                                                                            p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.setbomb", BlockPosArgument.getBlockPos(p, "bombPos").toString(), StringArgumentType.getString(p, "bombNumber")), true);
                                                                        }
                                                                    }
                                                                    return 0;
                                                                })
                                                        )
                                                        .then(Commands.literal("remove")
                                                                .executes((p) -> {
                                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                                        return removeBomb(p.getSource(), StringArgumentType.getString(p, "bombNumber"));
                                                                    }
                                                                    return 0;
                                                                })
                                                        )
                                                )
                                        )
                                        .then(Commands.literal("spawnPosition")
                                                .then(Commands.argument("spawnPointName", StringArgumentType.word())
                                                        .executes((p)->{
                                                            if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                                mapConfig.setSpawnPos(StringArgumentType.getString(p, "spawnPointName"), p.getSource().asPlayer().getPosition());
                                                                //
                                                                p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.setspawn", p.getSource().asPlayer().getPosition().toString(), StringArgumentType.getString(p, "spawnPointName")), true);
                                                            }
                                                            return 0;
                                                        })
                                                        .suggests(SPAWNS)
                                                        .then(Commands.argument("spawnPos", BlockPosArgument.blockPos())
                                                                .executes((p) -> {
                                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                                        mapConfig.setSpawnPos(StringArgumentType.getString(p, "spawnPointName"), BlockPosArgument.getBlockPos(p, "spawnPos"));
                                                                        //
                                                                        p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.setspawn", BlockPosArgument.getBlockPos(p, "spawnPos").toString(), StringArgumentType.getString(p, "spawnPointName")), true);
                                                                    }
                                                                    return 0;
                                                                })
                                                        )
                                                        .then(Commands.literal("remove")
                                                                .executes((p) -> {
                                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                                        return removeSpawn(p.getSource(), StringArgumentType.getString(p, "spawnPointName"));
                                                                    }
                                                                    return 0;
                                                                })
                                                        )
                                                )
                                        )
                                        .then(Commands.literal("zone1")
                                                .executes((p) -> {
                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                        setZonePos1(p.getSource(), p.getSource().asPlayer().getPosition());
                                                        save(p.getSource());
                                                    }
                                                    return 0;
                                                })
                                                .then(Commands.argument("zone1", BlockPosArgument.blockPos())
                                                        .executes((p) -> {
                                                            if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                                setZonePos1(p.getSource(), BlockPosArgument.getBlockPos(p, "zone1"));
                                                                save(p.getSource());
                                                            }
                                                            return 0;
                                                        }))
                                        )
                                        .then(Commands.literal("zone2")
                                                .executes((p) -> {
                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                        setZonePos2(p.getSource(), p.getSource().asPlayer().getPosition());
                                                        save(p.getSource());
                                                    }
                                                    return 0;
                                                })
                                                .then(Commands.argument("zone2", BlockPosArgument.blockPos())
                                                        .executes((p) -> {
                                                            if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                                setZonePos2(p.getSource(), BlockPosArgument.getBlockPos(p, "zone2"));
                                                                save(p.getSource());
                                                            }
                                                            return 0;
                                                        }))
                                        )
                                        .then(Commands.literal("scene")
                                                .executes((p) -> {
                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                        mapConfig.setScenePos(p.getSource().asPlayer().getPositionVec());
                                                        mapConfig.setSceneDir(p.getSource().asPlayer().getPitchYaw());
                                                        save(p.getSource());
                                                    }
                                                    p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.scene"), true);
                                                    return 0;
                                                })
                                        )
                                        .then(Commands.literal("done")
                                                .executes((p) -> {
                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                        mapConfig.setDone(true);
                                                        InGameServerProperties.setValidMaps(StringArgumentType.getString(p, "mapname"));
                                                        save(p.getSource());
                                                    }
                                                    p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.done", StringArgumentType.getString(p, "mapname")), true);
                                                    return 0;
                                                })
                                        )
                                        .then(Commands.literal("undone")
                                                .executes((p) -> {
                                                    if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
                                                        mapConfig.setDone(false);
                                                        InGameServerProperties.removeValidMaps(StringArgumentType.getString(p, "mapname"));
                                                        save(p.getSource());
                                                    }
                                                    p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.undone", StringArgumentType.getString(p, "mapname")), true);
                                                    return 0;
                                                })
                                        )
                                )
                                .then(Commands.literal("save")
                                        .executes((p) -> {
                                            if (mapConfig == null) {
                                                p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.invalidmapname"), true);
                                            }
                                            return save(p.getSource());
                                        }))
                        )
        );
    }

    public static int newMap(CommandSource source, String name) {
        //save(source);
        zonePos1 = null;
        zonePos2 = null;
        mapConfig = R6ServerMapConfigManager.getR6Config(name);
        InGameServerProperties.refreshMaps();
        source.sendFeedback(new TranslationTextComponent("commands.rainbow6.createmap", name), true);
        return 0;
    }

    public static Boolean checkMap(CommandSource source, String name) {
        if (mapConfig == null || !mapConfig.getMapName().equals(name)) {
            if (InGameServerProperties.getMaps().contains(name)) {
                zonePos1 = null;
                zonePos2 = null;
                mapConfig = R6ServerMapConfigManager.getR6Config(name);
                return true;
            } else {
                source.sendFeedback(new TranslationTextComponent("commands.rainbow6.invalidmapname"), true);
            }
        } else if (mapConfig.getMapName().equals(name)) {
            return true;
        } else {
            source.sendFeedback(new TranslationTextComponent("commands.rainbow6.invalidmapname"), true);
        }
        return false;
    }

    public static int addNextBomb(CommandSource source, BlockPos pos) {
        String nextBomb = "1a";
        if (mapConfig.getBombPos().isEmpty()) {
            mapConfig.setBombPos(nextBomb, pos);
        } else {
            String[] bombs = (String[]) (mapConfig.getBombPos().keySet().toArray(new String[0]));
            Arrays.sort(bombs);
            String lastBomb = bombs[bombs.length - 1];
            if (lastBomb.contains("a")) {
                nextBomb = lastBomb.replace("a", "b");
                mapConfig.setBombPos(nextBomb, pos);
            } else {
                nextBomb = (Integer.parseInt(lastBomb.replace("b", "")) + 1) + "a";
                mapConfig.setBombPos(nextBomb, pos);
            }
        }
        source.sendFeedback(new TranslationTextComponent("commands.rainbow6.nextbomb", nextBomb), true);
        return 0;
    }

    public static int removeBomb(CommandSource source, String name) {
        mapConfig.removeBombPos(name);
        source.sendFeedback(new TranslationTextComponent("commands.rainbow6.removebomb", name), true);
        return 0;
    }

    public static int removeSpawn(CommandSource source, String name) {
        mapConfig.removeBombPos(name);
        source.sendFeedback(new TranslationTextComponent("commands.rainbow6.removespawn", name), true);
        return 0;
    }

    public static Boolean checkBombNumber(CommandSource source, String number) {
        if (number.charAt(number.length() - 1) == 'a' || number.charAt(number.length() - 1) == 'b') {
            number = number.replace("a", "");
            number = number.replace("a", "");
            try {
                Integer.parseInt(number);
            } catch (NumberFormatException e) {
                source.sendFeedback(new TranslationTextComponent("commands.rainbow6.invalidbombnum"), true);
                return false;
            }
            return true;
        } else {
            source.sendFeedback(new TranslationTextComponent("commands.rainbow6.invalidbombnum"), true);
            return false;
        }
    }

    public static int save(CommandSource source) {
        if (mapConfig != null) {
            BlockPos zoneMin = new BlockPos(0,0,0);
            BlockPos zoneMax = new BlockPos(0,0,0);
            if (zonePos1 != null && zonePos2 != null){
                zoneMin =new BlockPos(
                        Math.min(zonePos1.getX(),zonePos2.getX()),
                        Math.min(zonePos1.getY(),zonePos2.getY()),
                        Math.min(zonePos1.getZ(),zonePos2.getZ())
                );
                zoneMax = new BlockPos(
                        Math.max(zonePos1.getX(),zonePos2.getX()),
                        Math.max(zonePos1.getY(),zonePos2.getY()),
                        Math.max(zonePos1.getZ(),zonePos2.getZ())
                );
            }
            mapConfig.setZonePointMin(zoneMin);
            mapConfig.setZonePointMax(zoneMax);
            R6ServerMapConfigManager.saveR6Config(mapConfig);
            source.sendFeedback(new TranslationTextComponent("commands.rainbow6.save"), true);
        }
        return 0;
    }

    public static void setZonePos1(CommandSource source, BlockPos pos) {
        zonePos1 = pos;
        source.sendFeedback(new TranslationTextComponent("commands.rainbow6.zone", Integer.toString(1), pos.toString()), true);
    }

    public static void setZonePos2(CommandSource source, BlockPos pos) {
        zonePos2 = pos;
        source.sendFeedback(new TranslationTextComponent("commands.rainbow6.zone", Integer.toString(2), pos.toString()), true);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        return 0;
    }
}
