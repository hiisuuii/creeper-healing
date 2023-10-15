package xd.arkosammy.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import xd.arkosammy.configuration.tables.ModeConfig;
import xd.arkosammy.explosions.ExplosionHealingMode;

final class ModeCommands {

    private ModeCommands(){}

    static void register(LiteralCommandNode<ServerCommandSource> creeperHealingNode){

        //Mode node
        LiteralCommandNode<ServerCommandSource> modeMode = CommandManager
                .literal("mode")
                .executes(ModeCommands::getHealingModeCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Default healing mode
        LiteralCommandNode<ServerCommandSource> defaultModeNode = CommandManager
                .literal(ExplosionHealingMode.DEFAULT_MODE.getName())
                .executes(ModeCommands::setDefaultHealingModeCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Daytime healing node
        LiteralCommandNode<ServerCommandSource> daytimeHealingModeNode = CommandManager
                .literal(ExplosionHealingMode.DAYTIME_HEALING_MODE.getName())
                .executes(ModeCommands::setDaytimeHealingModeCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Difficulty based healing mode
        LiteralCommandNode<ServerCommandSource> difficultyBasedHealingModeNode = CommandManager
                .literal(ExplosionHealingMode.DIFFICULTY_BASED_HEALING_MODE.getName())
                .executes(ModeCommands::setDifficultyBasedModeCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Weather based healing mode
        LiteralCommandNode<ServerCommandSource> weatherBasedHealingMode = CommandManager
                .literal(ExplosionHealingMode.WEATHER_BASED_HEALING_MODE.getName())
                .executes(ModeCommands::setWeatherBasedHealingModeCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Root node connections
        creeperHealingNode.addChild(modeMode);

        //Modes command nodes
        modeMode.addChild(defaultModeNode);
        modeMode.addChild(daytimeHealingModeNode);
        modeMode.addChild(difficultyBasedHealingModeNode);
        modeMode.addChild(weatherBasedHealingMode);

    }

    private static int setDefaultHealingModeCommand(CommandContext<ServerCommandSource> ctx){
        ModeConfig.setHealingMode(ExplosionHealingMode.DEFAULT_MODE.getName());
        ctx.getSource().sendMessage(Text.literal("Explosion healing mode has been set to: " + ExplosionHealingMode.DEFAULT_MODE.getDisplayName()));
        return Command.SINGLE_SUCCESS;
    }

    private static int setDaytimeHealingModeCommand(CommandContext<ServerCommandSource> ctx){
        ModeConfig.setHealingMode(ExplosionHealingMode.DAYTIME_HEALING_MODE.getName());
        ctx.getSource().sendMessage(Text.literal("Explosion healing mode has been set to: " + ExplosionHealingMode.DAYTIME_HEALING_MODE.getDisplayName()));
        return Command.SINGLE_SUCCESS;
    }

    private static int setDifficultyBasedModeCommand(CommandContext<ServerCommandSource> ctx){
        ModeConfig.setHealingMode(ExplosionHealingMode.DIFFICULTY_BASED_HEALING_MODE.getName());
        ctx.getSource().sendMessage(Text.literal("Explosion healing mode set to: " + ExplosionHealingMode.DIFFICULTY_BASED_HEALING_MODE.getDisplayName()));
        return Command.SINGLE_SUCCESS;
    }

    private static int setWeatherBasedHealingModeCommand(CommandContext<ServerCommandSource> ctx){
        ModeConfig.setHealingMode(ExplosionHealingMode.WEATHER_BASED_HEALING_MODE.getName());
        ctx.getSource().sendMessage(Text.literal("Explosion healing mode set to: " + ExplosionHealingMode.WEATHER_BASED_HEALING_MODE.getDisplayName()));
        return Command.SINGLE_SUCCESS;
    }

    private static int getHealingModeCommand(CommandContext<ServerCommandSource> ctx){
        ctx.getSource().sendMessage(Text.literal("Explosion healing mode currently set to: " + ExplosionHealingMode.getFromName(ModeConfig.getHealingMode()).getDisplayName()));
        return Command.SINGLE_SUCCESS;
    }

}
