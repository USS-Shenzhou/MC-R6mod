package cn.ussshenzhou.rainbow6.command;

import cn.ussshenzhou.rainbow6.config.Map;
import cn.ussshenzhou.t88.config.MultiInstanceConfigHelper;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.network.chat.Component;

/**
 * @author USS_Shenzhou
 */
public class MapCommand {

    private static Map mapCache;
    private static final Class<Map> MAP_CLAZZ = Map.class;

    private static SuggestionProvider<CommandSourceStack> MAPS = (commandContext, suggestionsBuilder) -> {
        MultiInstanceConfigHelper.getConfigInstancesRead(MAP_CLAZZ).values().forEach(map -> suggestionsBuilder.suggest(map.getName()));
        return suggestionsBuilder.buildFuture();
    };

    private static SuggestionProvider<CommandSourceStack> BOMBS = (commandContext, suggestionsBuilder) -> {
        try {
            mapCache.getBombSites().forEach(bombSite -> suggestionsBuilder.suggest(bombSite.getSubSite1Name()).suggest(bombSite.getSubSite2Name()));
        } catch (NullPointerException ignored) {
        }
        return suggestionsBuilder.buildFuture();
    };

    private static SuggestionProvider<CommandSourceStack> SPAWNS = (commandContext, suggestionsBuilder) -> {
        try {
            mapCache.getSpawnPositions().forEach(spawnPos -> suggestionsBuilder.suggest(spawnPos.getSpawnPosName()));
        } catch (NullPointerException ignored) {
        }
        return suggestionsBuilder.buildFuture();
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("r6ms")
                .then(Commands.literal("map")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("mapname", StringArgumentType.word())
                                .suggests(MAPS)
                                .executes(MapCommand::fromName)
                                .then(Commands.literal("bombPosition")
                                        .executes(MapCommand::addBombSite)
                                        .then(Commands.argument("bombNumber", StringArgumentType.word())
                                                .suggests(BOMBS)
                                                .then(Commands.argument("bombPos", BlockPosArgument.blockPos())
                                                        .executes(MapCommand::setBombSite)
                                                )
                                                .then(Commands.literal("remove")
                                                        .executes(MapCommand::removeBombSite)
                                                )
                                        )
                                )
                                .then(Commands.literal("spawnPosition")
                                        .then(Commands.argument("spawnPointName", StringArgumentType.word())
                                                .executes(MapCommand::addSpawnPos)
                                                .suggests(SPAWNS)
                                                .then(Commands.argument("spawnPos", BlockPosArgument.blockPos())
                                                        .executes(MapCommand::setSpawnPos)
                                                )
                                                .then(Commands.literal("remove")
                                                        .executes(MapCommand::removeSpawnPos)
                                                )
                                        )
                                )
                                .then(Commands.literal("zone1")
                                        .executes((context) -> addZonePos(context, 1))
                                        .then(Commands.argument("zone1", BlockPosArgument.blockPos())
                                                .executes((context) -> setZonePos(context, 1)))
                                )
                                .then(Commands.literal("zone2")
                                        .executes((context) -> addZonePos(context, 2))
                                        .then(Commands.argument("zone2", BlockPosArgument.blockPos())
                                                .executes((context) -> setZonePos(context, 2))
                                        )
                                        .then(Commands.literal("scene")
                                                .executes(MapCommand::setScenePoint)
                                        )
                                        .then(Commands.literal("usable")
                                                .executes((context) -> setUsable(context, true))
                                        )
                                        .then(Commands.literal("unusable")
                                                .executes((context) -> setUsable(context, false))
                                        )
                                )
                                .then(Commands.literal("save")
                                        .executes(MapCommand::saveCache))
                        )
                )
        );
    }

    private static int fromName(CommandContext<CommandSourceStack> context) {
        String mapName = context.getArgument("mapname", String.class);
        saveCache(context);
        var map = MultiInstanceConfigHelper.getConfigInstanceRead(MAP_CLAZZ, mapName);
        if (map != null) {
            mapCache = map;
            feedBack(context, "commands.rainbow6.map.load", mapName);
        } else {
            mapCache = new Map();
            mapCache.setName(mapName);
            feedBack(context, "commands.rainbow6.map.create", mapName);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int addBombSite(CommandContext<CommandSourceStack> context) {

        //TODO
        return Command.SINGLE_SUCCESS;
    }

    private static int setBombSite(CommandContext<CommandSourceStack> context) {
        //TODO
        return Command.SINGLE_SUCCESS;
    }

    private static int removeBombSite(CommandContext<CommandSourceStack> context) {
        //TODO
        return Command.SINGLE_SUCCESS;
    }

    private static int addSpawnPos(CommandContext<CommandSourceStack> context) {
        //TODO
        /*if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
            mapConfig.setSpawnPos(StringArgumentType.getString(p, "spawnPointName"), p.getSource().asPlayer().getPosition());
            //
            p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.setspawn", p.getSource().asPlayer().getPosition().toString(), StringArgumentType.getString(p, "spawnPointName")), true);
        }
        return 0;*/
        return Command.SINGLE_SUCCESS;
    }

    private static int setSpawnPos(CommandContext<CommandSourceStack> context) {
        //TODO
        /*if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
            mapConfig.setSpawnPos(StringArgumentType.getString(p, "spawnPointName"), BlockPosArgument.getBlockPos(p, "spawnPos"));
            //
            p.getSource().sendFeedback(new TranslationTextComponent("commands.rainbow6.setspawn", BlockPosArgument.getBlockPos(p, "spawnPos").toString(), StringArgumentType.getString(p, "spawnPointName")), true);
        }
        return 0;*/
        return Command.SINGLE_SUCCESS;
    }

    private static int removeSpawnPos(CommandContext<CommandSourceStack> context) {
        //TODO
        /*if (checkMap(p.getSource(), StringArgumentType.getString(p, "mapname"))) {
            return removeSpawn(p.getSource(), StringArgumentType.getString(p, "spawnPointName"));
        }
        return 0;*/
        return Command.SINGLE_SUCCESS;
    }

    private static int addZonePos(CommandContext<CommandSourceStack> context, int number) {
        //TODO
        return Command.SINGLE_SUCCESS;
    }

    private static int setZonePos(CommandContext<CommandSourceStack> context, int number) {
        //TODO
        return Command.SINGLE_SUCCESS;
    }

    private static int setScenePoint(CommandContext<CommandSourceStack> context) {
        //TODO
        return Command.SINGLE_SUCCESS;
    }

    private static int setUsable(CommandContext<CommandSourceStack> context, boolean usable) {
        //TODO
        return Command.SINGLE_SUCCESS;
    }

    private static int saveCache(CommandContext<CommandSourceStack> context) {
        saveMap(context, mapCache);
        return Command.SINGLE_SUCCESS;
    }

    private static void checkMap(CommandContext<CommandSourceStack> context) {
        String mapName = context.getArgument("name", String.class);
        if (!mapName.equals(mapCache.getName())) {
            context.getSource().sendFailure(Component.translatable("commands.rainbow6.map.mismatch", mapName, mapCache.getName()));
        }
    }

    private static void saveMap(CommandContext<CommandSourceStack> context, Map map) {
        if (map == null) {
            return;
        }
        MultiInstanceConfigHelper.saveConfig(map);
        feedBack(context, "commands.rainbow6.map.save", map.getName());
    }

    private static void feedBack(CommandContext<CommandSourceStack> context, String key, Object... args) {
        context.getSource().sendSystemMessage(Component.translatable(key, args));
    }

}
