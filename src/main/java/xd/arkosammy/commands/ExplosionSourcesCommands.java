package xd.arkosammy.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import xd.arkosammy.configuration.tables.ExplosionSourceConfig;

final class ExplosionSourcesCommands {

    private ExplosionSourcesCommands(){}

    static void register(LiteralCommandNode<ServerCommandSource> creeperHealingNode){

        //Explosion source node
        LiteralCommandNode<ServerCommandSource> explosionSourceMode = CommandManager
                .literal("explosion_source")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Heal creeper explosions node
        LiteralCommandNode<ServerCommandSource> healCreeperExplosionsNode = CommandManager
                .literal("heal_creeper_explosions")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .executes(ExplosionSourcesCommands::getHealCreeperExplosionsCommand)
                .build();

        //Heal ghast explosions node
        LiteralCommandNode<ServerCommandSource> healGhastExplosionsNode = CommandManager
                .literal("heal_ghast_explosions")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .executes(ExplosionSourcesCommands::getHealGhastExplosionsCommand)
                .build();

        //Heal wither explosions node
        LiteralCommandNode<ServerCommandSource> healWitherExplosionsNode = CommandManager
                .literal("heal_wither_explosions")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .executes(ExplosionSourcesCommands::getHealWitherExplosionsCommand)
                .build();

        //Heal tnt explosions node
        LiteralCommandNode<ServerCommandSource> healTNTExplosionsNode = CommandManager
                .literal("heal_tnt_explosions")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .executes(ExplosionSourcesCommands::getHealTNTExplosionsCommand)
                .build();

        //Heal tnt minecart explosions node
        LiteralCommandNode<ServerCommandSource> healTNTMinecartExplosionsNode = CommandManager
                .literal("heal_tnt_minecart_explosions")
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .executes(ExplosionSourcesCommands::getHealTNTMinecartExplosionCommand)
                .build();

        //Heal creeper explosions argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> healCreeperExplosionsArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(ExplosionSourcesCommands::setHealCreeperExplosionsCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Heal ghast explosions argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> healGhastExplosionsArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(ExplosionSourcesCommands::setHealGhastExplosionsCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Heal wither explosions argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> healWitherExplosionsArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(ExplosionSourcesCommands::setHealWitherExplosionsCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Heal tnt explosions argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> healTNTExplosionsArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(ExplosionSourcesCommands::setHealTNTExplosionsCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Heal tnt minecart explosions argument node
        ArgumentCommandNode<ServerCommandSource, Boolean> healTNTMinecartExplosionsArgumentNode = CommandManager
                .argument("value", BoolArgumentType.bool())
                .executes(ExplosionSourcesCommands::setHealTNTMinecartExplosionsCommand)
                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                .build();

        //Root node connection
        creeperHealingNode.addChild(explosionSourceMode);

        //Explosion sources commands nodes
        explosionSourceMode.addChild(healCreeperExplosionsNode);
        explosionSourceMode.addChild(healGhastExplosionsNode);
        explosionSourceMode.addChild(healWitherExplosionsNode);
        explosionSourceMode.addChild(healTNTExplosionsNode);
        explosionSourceMode.addChild(healTNTMinecartExplosionsNode);

        //Argument nodes
        healCreeperExplosionsNode.addChild(healCreeperExplosionsArgumentNode);
        healGhastExplosionsNode.addChild(healGhastExplosionsArgumentNode);
        healWitherExplosionsNode.addChild(healWitherExplosionsArgumentNode);
        healTNTExplosionsNode.addChild(healTNTExplosionsArgumentNode);
        healTNTMinecartExplosionsNode.addChild(healTNTMinecartExplosionsArgumentNode);

    }

    private static int setHealCreeperExplosionsCommand(CommandContext<ServerCommandSource> ctx){
        ExplosionSourceConfig.setHealCreeperExplosions(BoolArgumentType.getBool(ctx, "value"));
        ctx.getSource().sendMessage(Text.literal("Heal Creeper explosions has been set to: " + BoolArgumentType.getBool(ctx, "value")));
        return Command.SINGLE_SUCCESS;
    }

    private static int setHealGhastExplosionsCommand(CommandContext<ServerCommandSource> ctx){
        ExplosionSourceConfig.setHealGhastExplosions(BoolArgumentType.getBool(ctx, "value"));
        ctx.getSource().sendMessage(Text.literal("Heal Ghast explosions has been set to: " + BoolArgumentType.getBool(ctx, "value")));
        return Command.SINGLE_SUCCESS;
    }

    private static int setHealWitherExplosionsCommand(CommandContext<ServerCommandSource> ctx){
        ExplosionSourceConfig.setHealWitherExplosions(BoolArgumentType.getBool(ctx, "value"));
        ctx.getSource().sendMessage(Text.literal("Heal Wither explosions has been set to: " + BoolArgumentType.getBool(ctx, "value")));
        return Command.SINGLE_SUCCESS;
    }

    private static int setHealTNTExplosionsCommand(CommandContext<ServerCommandSource> ctx){
        ExplosionSourceConfig.setHealTNTExplosions(BoolArgumentType.getBool(ctx, "value"));
        ctx.getSource().sendMessage(Text.literal("Heal TNT explosions has been set to: " + BoolArgumentType.getBool(ctx, "value")));
        return Command.SINGLE_SUCCESS;
    }

    private static int setHealTNTMinecartExplosionsCommand(CommandContext<ServerCommandSource> ctx){
        ExplosionSourceConfig.setHealTNTMinecartExplosions(BoolArgumentType.getBool(ctx, "value"));
        ctx.getSource().sendMessage(Text.literal("Heal TNT Minecart explosions has been set to: " + BoolArgumentType.getBool(ctx, "value")));
        return Command.SINGLE_SUCCESS;
    }

    private static int getHealCreeperExplosionsCommand(CommandContext<ServerCommandSource> ctx){
        ctx.getSource().sendMessage(Text.literal("Heal Creeper explosions currently set to: " + ExplosionSourceConfig.getHealCreeperExplosions()));
        return Command.SINGLE_SUCCESS;
    }

    private static int getHealGhastExplosionsCommand(CommandContext<ServerCommandSource> ctx){
        ctx.getSource().sendMessage(Text.literal("Heal Ghast explosions currently set to: " + ExplosionSourceConfig.getHealGhastExplosions()));
        return Command.SINGLE_SUCCESS;
    }

    private static int getHealWitherExplosionsCommand(CommandContext<ServerCommandSource> ctx){
        ctx.getSource().sendMessage(Text.literal("Heal Wither explosions currently set to: " + ExplosionSourceConfig.getHealWitherExplosions()));
        return Command.SINGLE_SUCCESS;
    }

    private static int getHealTNTExplosionsCommand(CommandContext<ServerCommandSource> ctx){
        ctx.getSource().sendMessage(Text.literal("Heal TNT explosions currently set to: " + ExplosionSourceConfig.getHealTNTExplosions()));
        return Command.SINGLE_SUCCESS;
    }

    private static int getHealTNTMinecartExplosionCommand(CommandContext<ServerCommandSource> ctx){
        ctx.getSource().sendMessage(Text.literal("Heal TNT minecart explosions currently set to: " + ExplosionSourceConfig.getHealTNTMinecartExplosions()));
        return Command.SINGLE_SUCCESS;
    }

}
