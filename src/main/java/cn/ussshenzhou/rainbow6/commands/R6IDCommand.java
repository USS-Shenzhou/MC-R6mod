package cn.ussshenzhou.rainbow6.commands;

import cn.ussshenzhou.rainbow6.capabilities.IR6PlayerCapability;
import cn.ussshenzhou.rainbow6.capabilities.ModCapabilities;
import cn.ussshenzhou.rainbow6.capabilities.R6PlayerCapability;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;


/**
 * @author USS_Shenzhou
 */
public class R6IDCommand implements Command<CommandSource> {

    public static final SuggestionProvider<CommandSource> ATTACKERS = (p, suggestionsBuilder) -> {
        return ISuggestionProvider.suggest(R6PlayerCapability.ATTACKERS, suggestionsBuilder);
    };

    public static final SuggestionProvider<CommandSource> DEFENDERS = (p, suggestionsBuilder) -> {
        return ISuggestionProvider.suggest(R6PlayerCapability.DEFENDERS, suggestionsBuilder);
    };

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("r6ms")
                        .then(Commands.literal("id")
                                .requires((p) -> p.hasPermissionLevel(2))
                                .then(Commands.argument("target", EntityArgument.players())
                                        .executes((p) -> {
                                            return showID(p.getSource(), EntityArgument.getPlayer(p, "target"));
                                        })
                                        //r6ms id player team none/a/d
                                        //r6ms id player operator xxx
                                        .then(Commands.literal("none")
                                                .executes((p) -> {
                                                    return setID(p.getSource(), EntityArgument.getPlayer(p, "target"), "none", "player");
                                                })
                                        )
                                        .then(Commands.literal("attacker")
                                                .then(Commands.argument("operator", StringArgumentType.word())
                                                        .suggests(ATTACKERS)
                                                        .executes((p) -> {
                                                            return setID(p.getSource(), EntityArgument.getPlayer(p, "target"), "attacker", StringArgumentType.getString(p, "operator"));
                                                        })
                                                )
                                        )
                                        .then(Commands.literal("defender")
                                                .then(Commands.argument("operator", StringArgumentType.word())
                                                        .suggests(DEFENDERS)
                                                        .executes((p) -> {
                                                            return setID(p.getSource(), EntityArgument.getPlayer(p, "target"), "defender", StringArgumentType.getString(p, "operator"));
                                                        })
                                                )
                                        )

                                )


                        ));
    }


    public static int showID(CommandSource source, PlayerEntity player) {
        LazyOptional<IR6PlayerCapability> r6PlayerCap = player.getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
        IR6PlayerCapability ir6PlayerCapability = r6PlayerCap.orElse(ModCapabilities.R6_PLAYER_CAPABILITY.getDefaultInstance());
        source.sendFeedback(new TranslationTextComponent("commands.rainbow6.showid", player.getDisplayName(), ir6PlayerCapability.getOperator(), ir6PlayerCapability.getR6Team()), true);
        return 0;
    }

    public static int setID(CommandSource source, PlayerEntity player, String team, String operator) {
        LazyOptional<IR6PlayerCapability> r6PlayerCap = player.getCapability(ModCapabilities.R6_PLAYER_CAPABILITY);
        IR6PlayerCapability ir6PlayerCapability = r6PlayerCap.orElse(ModCapabilities.R6_PLAYER_CAPABILITY.getDefaultInstance());
        ir6PlayerCapability.setR6Team(team);
        ir6PlayerCapability.setOperator(operator);
        //broadcast to everyone in player's match
        /*try {
            ServerMatch servermatch = InGameServerProperties.getServerMatch(source.asPlayer());
            for (ServerPlayerEntity p : servermatch.getOperator().keySet()) {
                PlayerOperatorPackSend.channel.send(PacketDistributor.PLAYER.with(
                        () -> p
                        ),
                        new MatchMakingPack());
            }
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }*/
        source.sendFeedback(new TranslationTextComponent("commands.rainbow6.setid", player.getDisplayName(), ir6PlayerCapability.getOperator(), ir6PlayerCapability.getR6Team()), true);
        return 0;
    }


    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        return 0;
    }

}
