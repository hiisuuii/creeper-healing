package xd.arkosammy.util;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import xd.arkosammy.CreeperHealing;
import xd.arkosammy.handlers.ExplosionHealerHandler;

import java.io.IOException;

public class Commands {

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){

        //Root node
        LiteralCommandNode<ServerCommandSource> creeperHealingNode = CommandManager
                .literal("creeper-healing")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Settings node
        LiteralCommandNode<ServerCommandSource> settingsNode = CommandManager
                .literal("settings")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Mode node
        LiteralCommandNode<ServerCommandSource> modeMode = CommandManager
                .literal("mode")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Explosion Heal Delay node
        LiteralCommandNode<ServerCommandSource> explosionHealDelayNode = CommandManager
                .literal("explosion_heal_delay")
                .executes(Commands::getExplosionHealDelayCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Block Placement Delay node
        LiteralCommandNode<ServerCommandSource> blockPlacementDelayNode = CommandManager
                .literal("block_placement_delay")
                .executes(Commands::getBlockPlacementDelayCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Requires light node
        LiteralCommandNode<ServerCommandSource> requiresLightNode = CommandManager
                .literal("requires_light")
                .executes(Commands::getRequiresLightCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Heal on Flowing Water node
        LiteralCommandNode<ServerCommandSource> shouldHealOnFlowingWaterNode = CommandManager
                .literal("heal_on_flowing_water")
                .executes(Commands::getShouldHealOnFlowingWaterCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Heal on Flowing Lava node
        LiteralCommandNode<ServerCommandSource> shouldHealOnFlowingLavaNode = CommandManager
                .literal("heal_on_flowing_lava")
                .executes(Commands::getShouldHealOnFlowingLavaCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Play sound on block placement node
        LiteralCommandNode<ServerCommandSource> shouldPlaySoundOnBlockPlacementNode = CommandManager
                .literal("block_placement_sound_effect")
                .executes(Commands::getShouldPlaySoundOnBlockPlacement)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Daytime healing node
        LiteralCommandNode<ServerCommandSource> doDayTimeHealingNode = CommandManager
                .literal("daytime_healing_mode")
                .executes(Commands::getDoDayLightHealingCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Reload Configuration node
        LiteralCommandNode<ServerCommandSource> reloadNode = CommandManager
                .literal("reload")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .executes(context -> {

                    try {
                        Commands.reload(context);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return Command.SINGLE_SUCCESS;
                })
                .build();

        //Explosion heal delay argument node
        ArgumentCommandNode<ServerCommandSource, Double> explosionHealDelayArgumentNode = CommandManager
                .argument("seconds", DoubleArgumentType.doubleArg())
                .executes(Commands::setExplosionHealDelayCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Block placement delay argument node
        ArgumentCommandNode<ServerCommandSource, Double> blockPlacementDelayArgumentNode = CommandManager
                .argument("seconds", DoubleArgumentType.doubleArg())
                .executes(Commands::setBlockPlacementDelayCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Requires light argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> requiresLightArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(Commands::setRequiresLightCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Heal on flowing water argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> healOnFlowingWaterArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(Commands::setHealOnFlowingWaterCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Heal on flowing lava argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> healOnFlowingLavaArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(Commands::setHealOnFlowingLavaCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Play sound on block placement argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> playSoundOnBlockPlacementArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(Commands::setPlaySoundOnBlockPlacement)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Daytime healing mode argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> doDayLightHealingArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(Commands::setDoDayTimeHealingCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Root connections
        dispatcher.getRoot().addChild(creeperHealingNode);

        //Parent command connections
        creeperHealingNode.addChild(settingsNode);
        creeperHealingNode.addChild(modeMode);

        //Settings command connections
        settingsNode.addChild(explosionHealDelayNode);
        settingsNode.addChild(blockPlacementDelayNode);
        settingsNode.addChild(requiresLightNode);
        settingsNode.addChild(shouldHealOnFlowingWaterNode);
        settingsNode.addChild(shouldHealOnFlowingLavaNode);
        settingsNode.addChild(shouldPlaySoundOnBlockPlacementNode);
        settingsNode.addChild(reloadNode);

        //Mode command connections
        modeMode.addChild(doDayTimeHealingNode);

        //Argument node connections
        explosionHealDelayNode.addChild(explosionHealDelayArgumentNode);
        blockPlacementDelayNode.addChild(blockPlacementDelayArgumentNode);
        requiresLightNode.addChild(requiresLightArgumentNode);
        shouldHealOnFlowingWaterNode.addChild(healOnFlowingWaterArgumentNode);
        shouldHealOnFlowingLavaNode.addChild(healOnFlowingLavaArgumentNode);
        shouldPlaySoundOnBlockPlacementNode.addChild(playSoundOnBlockPlacementArgumentNode);
        doDayTimeHealingNode.addChild(doDayLightHealingArgumentNode);

    }

    private static int setExplosionHealDelayCommand(CommandContext<ServerCommandSource> ctx) {

        if(Math.round(Math.max(DoubleArgumentType.getDouble(ctx, "seconds"), 0) * 20L) != 0) {

            Config.setExplosionHealDelay(DoubleArgumentType.getDouble(ctx, "seconds"));

            //ExplosionHealerHandler.updateExplosionTimers();

            ctx.getSource().sendMessage(Text.literal("Explosion heal delay has been set to: " + DoubleArgumentType.getDouble(ctx, "seconds") + " second(s)"));

        } else {

            ctx.getSource().sendMessage(Text.literal("Cannot set explosion heal delay to a very low value").formatted(Formatting.RED));

        }

        return Command.SINGLE_SUCCESS;

    }

    private static int getExplosionHealDelayCommand(CommandContext<ServerCommandSource> ctx){

        ctx.getSource().sendMessage(Text.literal("Explosion heal delay currently set to: " + ((double)Config.getExplosionDelay() / 20) + " second(s)"));

        return Command.SINGLE_SUCCESS;

    }

    private static int setBlockPlacementDelayCommand(CommandContext<ServerCommandSource> ctx) {

        if (Math.round(Math.max(DoubleArgumentType.getDouble(ctx, "seconds"), 0) * 20L) != 0) {

            Config.setBlockPlacementDelay(DoubleArgumentType.getDouble(ctx, "seconds"));

            ExplosionHealerHandler.updateAffectedBlocksTimers();

            ctx.getSource().sendMessage(Text.literal("Block placement delay has been set to to: " + DoubleArgumentType.getDouble(ctx, "seconds") + " second(s)"));

        } else {

            ctx.getSource().sendMessage(Text.literal("Cannot set block placement delay to a very low value").formatted(Formatting.RED));

        }

        return Command.SINGLE_SUCCESS;

    }

    private static int getBlockPlacementDelayCommand(CommandContext<ServerCommandSource> ctx){

        ctx.getSource().sendMessage(Text.literal("Block placement delay currently set to: " + ((double)Config.getBlockPlacementDelay() / 20) + " second(s)"));

        return Command.SINGLE_SUCCESS;

    }

    private static int setRequiresLightCommand(CommandContext<ServerCommandSource> ctx){

        Config.setRequiresLight(BoolArgumentType.getBool(ctx, "value"));

        ctx.getSource().sendMessage(Text.literal("Requires light has been set to: " + BoolArgumentType.getBool(ctx, "value")));

        return Command.SINGLE_SUCCESS;

    }

    private static int getRequiresLightCommand(CommandContext<ServerCommandSource> ctx){

        ctx.getSource().sendMessage(Text.literal("Requires light currently set to: " + Config.getRequiresLight()));

        return Command.SINGLE_SUCCESS;

    }

    private static int setHealOnFlowingWaterCommand(CommandContext<ServerCommandSource> ctx) {

        Config.setShouldHealOnFlowingWater(BoolArgumentType.getBool(ctx, "value"));

        ctx.getSource().sendMessage(Text.literal("Heal on flowing water has been set to: " + BoolArgumentType.getBool(ctx, "value")));

        return Command.SINGLE_SUCCESS;

    }

    private static int getShouldHealOnFlowingWaterCommand(CommandContext<ServerCommandSource> ctx){

        ctx.getSource().sendMessage(Text.literal("Heal on flowing water currently set to: " + Config.shouldHealOnFlowingWater()));

        return Command.SINGLE_SUCCESS;

    }

    private static int setHealOnFlowingLavaCommand(CommandContext<ServerCommandSource> ctx) {

        Config.setShouldHealOnFlowingLava(BoolArgumentType.getBool(ctx, "value"));

        ctx.getSource().sendMessage(Text.literal("Heal on flowing lava has been set to: " + BoolArgumentType.getBool(ctx, "value")));

        return Command.SINGLE_SUCCESS;

    }

    private static int getShouldHealOnFlowingLavaCommand(CommandContext<ServerCommandSource> ctx){

        ctx.getSource().sendMessage(Text.literal("Heal on flowing lava currently set to: " + Config.shouldHealOnFlowingLava()));

        return Command.SINGLE_SUCCESS;

    }

    private static int setDoDayTimeHealingCommand(CommandContext<ServerCommandSource> ctx){

        Config.setDaytimeHealing(BoolArgumentType.getBool(ctx, "value"));

        ctx.getSource().sendMessage(Text.literal("Daytime healing mode has been set to: " + BoolArgumentType.getBool(ctx, "value")));

        return Command.SINGLE_SUCCESS;

    }

    private static int getDoDayLightHealingCommand(CommandContext<ServerCommandSource> ctx){

        ctx.getSource().sendMessage(Text.literal("Daytime healing mode currently set to: " + Config.isDaytimeHealingEnabled()));

        return Command.SINGLE_SUCCESS;

    }

    private static int setPlaySoundOnBlockPlacement(CommandContext<ServerCommandSource> ctx) {

        Config.setShouldPlaySoundOnBlockPlacement(BoolArgumentType.getBool(ctx, "value"));

        ctx.getSource().sendMessage(Text.literal("Play sound on block placement has been set to: " + BoolArgumentType.getBool(ctx, "value")));

        return Command.SINGLE_SUCCESS;

    }

    private static int getShouldPlaySoundOnBlockPlacement(CommandContext<ServerCommandSource> ctx) {

        ctx.getSource().sendMessage(Text.literal("Play sound on block placement currently set to: " + Config.shouldPlaySoundOnBlockPlacement()));

        return Command.SINGLE_SUCCESS;

    }

    private static void reload(CommandContext<ServerCommandSource> ctx) throws IOException {

        //If this returns true, then the config file exists, and we can update our values from it
        if(Config.reloadConfig()) ctx.getSource().sendMessage(Text.literal("Configuration successfully reloaded"));
        else ctx.getSource().sendMessage(Text.literal("Found no existing config file to reload values from").formatted(Formatting.RED));

    }

}
